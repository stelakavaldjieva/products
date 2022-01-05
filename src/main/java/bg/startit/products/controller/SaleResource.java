package bg.startit.products.controller;

import bg.startit.products.model.Car;
import bg.startit.products.model.Phone;
import bg.startit.products.model.Sale;
import bg.startit.products.model.TV;
import bg.startit.products.service.CarService;
import bg.startit.products.service.PhoneService;
import bg.startit.products.service.SaleService;
import bg.startit.products.service.TVService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * REST controller for Sale operations.
 */
@RestController
@RequestMapping("/api/v1/sales")
public class SaleResource {

    private final CarService carService;

    private final PhoneService phoneService;

    private final TVService tvService;

    private final SaleService saleService;

    public SaleResource(CarService carService, PhoneService phoneService, TVService tvService, SaleService saleService) {
        this.carService = carService;
        this.phoneService = phoneService;
        this.tvService = tvService;
        this.saleService = saleService;
    }

    // Create a car
    @PostMapping("/create")
    public ResponseEntity<String> createRest(@RequestParam @NotNull Long product_id,
                                             @RequestParam @NotNull Integer product_type,
                                             @RequestParam @NotNull Long quantity) {

        Sale sale;

        if (product_type == 1) {

            Car car = (Car) carService.findProduct(product_id);

            if (car.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Sale not created! Not such products are left.");
            }
            if (car.getQuantity() < quantity) {
                quantity = car.getQuantity();
            }
            car.setQuantity(car.getQuantity() - quantity);

            carService.update(car);

            sale = new Sale(product_id, quantity, LocalDateTime.now(), product_type, car.getPrice().multiply(BigDecimal.valueOf(quantity)));
        } else if (product_type == 2) {

            Phone phone = (Phone) phoneService.findProduct(product_id);

            if (phone.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Sale not created! Not such products are left.");
            }
            if (phone.getQuantity() < quantity) {
                quantity = phone.getQuantity();
            }
            phone.setQuantity(phone.getQuantity() - quantity);

            phoneService.update(phone);

            sale = new Sale(product_id, quantity, LocalDateTime.now(), product_type, phone.getPrice().multiply(BigDecimal.valueOf(quantity)));
        } else if (product_type == 3) {

            TV tv = (TV) tvService.findProduct(product_id);

            if (tv.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Sale not created! Not such products are left.");
            }
            if (tv.getQuantity() < quantity) {
                quantity = tv.getQuantity();
            }
            tv.setQuantity(tv.getQuantity() - quantity);

            tvService.update(tv);

            sale = new Sale(product_id, quantity, LocalDateTime.now(), product_type, tv.getPrice().multiply(BigDecimal.valueOf(quantity)));
        } else {

            return ResponseEntity.badRequest().body("Sale not created! No such product type exists.");
        }

        saleService.create(sale);

        return ResponseEntity.ok("Sale successfully created!");
    }

    // Report sales
    @GetMapping("/report")
    public ResponseEntity<String> reportRest(@Min(1) @Max(12) @RequestParam(defaultValue = "1") Integer start_month,
                                             @RequestParam Integer start_year,
                                             @Min(1) @Max(12) @RequestParam(defaultValue = "12") Integer end_month,
                                             @RequestParam Integer end_year) {

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        LocalDateTime start, end;

        if (start_year >= LocalDateTime.MIN.getYear() && start_year <= localDateTimeNow.getYear()) {
            start = LocalDateTime.of(start_year, start_month, 1, 0, 0);
        } else {
            start = LocalDateTime.of(LocalDateTime.MIN.getYear(), start_month, 1, 0, 0);
        }

        if (end_year >= LocalDateTime.MIN.getYear() && end_year <= localDateTimeNow.getYear()) {
            end = LocalDateTime.of(end_year, end_month, 1, 0, 0);
        } else {
            end = LocalDateTime.of(localDateTimeNow.getYear(), end_month, 1, 0, 0);
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        Long countAllSalesInDateRange = saleService.getCountAllInDateRange(start, end);

        BigDecimal sumAllMoneyInDateRange = saleService.getSumAllMoneyInDateRange(start, end);

        return ResponseEntity.ok(String.format("Products sold: %d; Final profit: %.2f lv.",
                countAllSalesInDateRange, sumAllMoneyInDateRange));
    }
}