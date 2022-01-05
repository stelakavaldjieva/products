package bg.startit.products.controller;

import bg.startit.products.model.Car;
import bg.startit.products.model.Phone;
import bg.startit.products.model.Sale;
import bg.startit.products.model.TV;
import bg.startit.products.service.CarService;
import bg.startit.products.service.PhoneService;
import bg.startit.products.service.SaleService;
import bg.startit.products.service.TVService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "dev")
@ContextConfiguration(loader = SpringBootContextLoader.class)
@TestExecutionListeners({WithSecurityContextTestExecutionListener.class})
class SaleResourceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private TVService tvService;

    @Autowired
    private SaleResource saleResource;

    private Sale sale;

    @BeforeEach
    void setUp() {

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        sale = new Sale(1L, 1L, localDateTimeNow, 1, BigDecimal.ONE);

        if (saleService.create(sale)) {
            if (saleService.findByDate(localDateTimeNow) != null) {
                sale.setSale_id(saleService.findByDate(localDateTimeNow).getSale_id());
            }
        }
    }

    @AfterEach
    void tearDown() {

        saleService.delete(sale.getSale_id());
    }

    @Test
    public void contextLoads() {

        assertThat(saleResource).isNotNull();
    }

    @Test
    void createRest_returnUnauthorized_ifNoLoggedUser() throws Exception {

        mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", "1")
                        .param("product_type", "1")
                        .param("quantity", "1")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_ifMissingParameters() throws Exception {

        mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_ifCarIsSoldOut() throws Exception {

        Car car = new Car("brand_name", "color", BigDecimal.ONE, 1L, 1L, 0L);
        if (carService.create(car)) {
            if (carService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                car.setId(((Car) carService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", car.getId().toString())
                        .param("product_type", "1")
                        .param("quantity", "1")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        carService.delete(car.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale not created! Not such products are left.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnOk_ifCarIsSold() throws Exception {

        Car car = new Car("brand_name", "color", BigDecimal.ONE, 1L, 1L, 1L);
        if (carService.create(car)) {
            if (carService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                car.setId(((Car) carService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", car.getId().toString())
                        .param("product_type", "1")
                        .param("quantity", "2")
                )
                .andExpect(status().isOk())
                .andReturn();

        carService.delete(car.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale successfully created!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_ifPhoneIsSoldOut() throws Exception {

        Phone phone = new Phone("brand_name", "color", BigDecimal.ONE, 1L, 1L, 0L);
        if (phoneService.create(phone)) {
            if (phoneService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                phone.setId(((Phone) phoneService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", phone.getId().toString())
                        .param("product_type", "2")
                        .param("quantity", "1")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        phoneService.delete(phone.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale not created! Not such products are left.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnOk_ifPhoneIsSold() throws Exception {

        Phone phone = new Phone("brand_name", "color", BigDecimal.ONE, 1L, 1L, 1L);
        if (phoneService.create(phone)) {
            if (phoneService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                phone.setId(((Phone) phoneService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", phone.getId().toString())
                        .param("product_type", "2")
                        .param("quantity", "2")
                )
                .andExpect(status().isOk())
                .andReturn();

        phoneService.delete(phone.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale successfully created!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_ifTvIsSoldOut() throws Exception {

        TV tv = new TV("brand_name", "color", BigDecimal.ONE, 1L, 1L, 0L);
        if (tvService.create(tv)) {
            if (tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                tv.setId(((TV) tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", tv.getId().toString())
                        .param("product_type", "3")
                        .param("quantity", "1")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        tvService.delete(tv.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale not created! Not such products are left.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnOk_ifTvIsSold() throws Exception {

        TV tv = new TV("brand_name", "color", BigDecimal.ONE, 1L, 1L, 1L);
        if (tvService.create(tv)) {
            if (tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                tv.setId(((TV) tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", tv.getId().toString())
                        .param("product_type", "3")
                        .param("quantity", "2")
                )
                .andExpect(status().isOk())
                .andReturn();

        tvService.delete(tv.getId());

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale successfully created!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_ifNoSuchProductType() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/sales/create")
                        .param("product_id", "1")
                        .param("product_type", "4")
                        .param("quantity", "1")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("Sale not created! No such product type exists.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void reportRest_returnBadRequest_ifYearsMissing() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/sales/report"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void reportRest_returnOk_ifMonthsMissing() throws Exception {

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/sales/report")
                        .param("start_year", String.valueOf(localDateTimeNow.getYear() - 1))
                        .param("end_year", String.valueOf(localDateTimeNow.getYear()))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Products sold: ");
        assertThat(content).contains("; Final profit: ");
        assertThat(content).contains(" lv.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void reportRest_returnOk_ifYearsAfterNow() throws Exception {

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/sales/report")
                        .param("start_year", String.valueOf(localDateTimeNow.getYear() + 1))
                        .param("end_year", String.valueOf(localDateTimeNow.getYear() + 1))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Products sold: ");
        assertThat(content).contains("; Final profit: ");
        assertThat(content).contains(" lv.");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void reportRest_returnOk_ifYearsNotIntersectReverse() throws Exception {

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/sales/report")
                        .param("start_year", String.valueOf(localDateTimeNow.getYear()))
                        .param("end_year", String.valueOf(localDateTimeNow.getYear() - 1))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Products sold: ");
        assertThat(content).contains("; Final profit: ");
        assertThat(content).contains(" lv.");
    }
}