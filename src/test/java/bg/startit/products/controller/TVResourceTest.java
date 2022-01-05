package bg.startit.products.controller;

import bg.startit.products.model.TV;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "dev")
@ContextConfiguration(loader = SpringBootContextLoader.class)
@TestExecutionListeners({WithSecurityContextTestExecutionListener.class})
class TVResourceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TVResource tvResource;

    @Autowired
    private TVService tvService;

    private TV tv;

    @BeforeEach
    void setUp() {

        tv = new TV("brand_name", "color", BigDecimal.ONE, 1L, 1L, 1L);

        if (tvService.create(tv)) {
            if (tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().isPresent()) {
                tv.setId(((TV) tvService.findProduct("color", PageRequest.of(1, 1)).get().findFirst().get()).getId());
            }
        }
    }

    @AfterEach
    void tearDown() {

        tvService.delete(tv.getId());
    }

    @Test
    public void contextLoads() {

        assertThat(tvResource).isNotNull();
    }

    @Test
    void getAllRest_returnUnauthorized_ifNoLoggedUser() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/all")
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getAllRest_returnOk_ifUserIsLogged() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/all")
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getAllRest_returnSuccess_defaultPage() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/all")
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageNumber").value(0));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getTVByIdRest_returnSuccess() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId())
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("brand_name").value("brand_name"))
                .andExpect(jsonPath("color").value("color"))
                .andExpect(jsonPath("price").value("1.0 lv."))
                .andExpect(jsonPath("weight").value("1 kg."))
                .andExpect(jsonPath("length").value("1 cm."))
                .andExpect(jsonPath("quantity").value(1));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getTVByIdRest_returnMethodNotAllowed_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getTVByIdRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("brand_name").value("TV not found!"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getBrandNameRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId() + "/brand_name"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(tv.getBrand_name());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getBrandNameRest_returnBadRequest_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs//brand_name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getBrandNameRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id + "/brand_name"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not found!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getColorRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId() + "/color"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(tv.getColor());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getColorRest_returnBadRequest_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs//color"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getColorRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id + "/color"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not found!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getPriceRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId() + "/price"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(String.format("%.2f lv.", tv.getPrice()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getPriceRest_returnBadRequest_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs//price"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getPriceRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id + "/price"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not found!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getWeightRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId() + "/weight"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(String.format("%d kg.", tv.getWeight()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getWeightRest_returnMethodNotAllowed_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs//weight"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getWeightRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id + "/weight"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not found!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getLengthRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv.getId() + "/length"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(String.format("%d cm.", tv.getWeight()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getLengthRest_returnMethodNotAllowed_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs//length"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getLengthRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long tv2Id = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/" + tv2Id + "/length"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not found!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getTVByColorRest_returnSuccess() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/colors/" + "color")
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void getTVByPriceRest_returnSuccess() throws Exception {

        mockMvc
                .perform(request(HttpMethod.GET, "/api/v1/tvs/price/" + BigDecimal.ONE)
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnSuccess() throws Exception {
        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/tvs/create")
                        .param("brand_name", "brandName2")
                        .param("color", "color2")
                        .param("price", "2")
                        .param("weight", "2")
                        .param("length", "2")
                        .param("quantity", "2")
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV successfully created!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_whenMissingParameters() throws Exception {
        mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/tvs/create"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void createRest_returnBadRequest_whenNotCreated() throws Exception {
        MvcResult result = mockMvc
                .perform(request(HttpMethod.POST, "/api/v1/tvs/create")
                        .param("brand_name", "")
                        .param("color", "")
                        .param("price", "")
                        .param("weight", "")
                        .param("length", "")
                        .param("quantity", "")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not created!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnSuccess() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/" + tv.getId())
                        .param("brand_name", "brandName2")
                        .param("color", "color2")
                        .param("price", "2")
                        .param("weight", "2")
                        .param("length", "2")
                        .param("quantity", "2")
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV successfully updated!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnBadRequest_whenParamsMissing() throws Exception {

        mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/" + tv.getId()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnBadRequest_whenParamsEmpty() throws Exception {

        MvcResult result = mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/" + tv.getId())
                        .param("brand_name", "")
                        .param("color", "")
                        .param("price", "")
                        .param("weight", "")
                        .param("length", "")
                        .param("quantity", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not updated!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnBadRequest_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/")
                        .param("brand_name", "")
                        .param("color", "")
                        .param("price", "")
                        .param("weight", "")
                        .param("length", "")
                        .param("quantity", ""))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnBadRequest_whenIdNotLong() throws Exception {

        mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/" + "notLong")
                        .param("brand_name", "")
                        .param("color", "")
                        .param("price", "")
                        .param("weight", "")
                        .param("length", "")
                        .param("quantity", ""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long idNotExists = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.PUT, "/api/v1/tvs/update/" + idNotExists)
                        .param("brand_name", "")
                        .param("color", "")
                        .param("price", "")
                        .param("weight", "")
                        .param("length", "")
                        .param("quantity", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not updated!");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteRest_returnSuccess() throws Exception {

        mockMvc
                .perform(request(HttpMethod.DELETE, "/api/v1/tvs/delete/" + tv.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteRest_returnMethodNotAllowed_whenIdBlank() throws Exception {

        mockMvc
                .perform(request(HttpMethod.DELETE, "/api/v1/tvs/delete/"))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteRest_returnBadRequest_whenIdNotLong() throws Exception {

        mockMvc
                .perform(request(HttpMethod.DELETE, "/api/v1/tvs/delete/" + "notLong"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteRest_returnBadRequest_whenIdNotFound() throws Exception {

        final long idNotExists = 2L;

        MvcResult result = mockMvc
                .perform(request(HttpMethod.DELETE, "/api/v1/tvs/delete/" + idNotExists))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("TV not deleted!");
    }
}