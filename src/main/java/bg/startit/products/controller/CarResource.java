package bg.startit.products.controller;

import bg.startit.products.IStaff;
import bg.startit.products.dto.car.CarDto;
import bg.startit.products.dto.car.CarListDto;
import bg.startit.products.model.Car;
import bg.startit.products.model.Product;
import bg.startit.products.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for Car operations.
 */
@RestController
@RequestMapping("/api/v1/cars")
public class CarResource implements IStaff {

    private final CarService carService;

    public CarResource(CarService carService) {

        this.carService = carService;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {

        return carService.getAll(pageable);
    }

    // Get all cars
    @GetMapping("/all")
    public ResponseEntity<CarListDto> getAllRest(@RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                 @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Car> cars = pageProductToPageCar(pageable, getAll(pageable));

        return ResponseEntity.ok(mapCarToCarListDto(pageNumber, pageSize, cars));
    }

    @Override
    public Product findProduct(Long id) {

        return carService.findProduct(id);
    }

    // Get car by id
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarByIdRest(@PathVariable @Min(1) Long id) {

        // Find car by id
        final Car car = (Car) findProduct(id);

        CarDto carDto = new CarDto();
        if (car == null) {
            carDto.setBrand_name("Car not found!");
            return ResponseEntity.badRequest().body(carDto);
        }

        carDto = new CarDto(
                car.getId(),
                car.getBrand_name(),
                car.getColor(),
                car.getPrice(),
                car.getWeight(),
                car.getLength(),
                car.getQuantity());

        return ResponseEntity.ok(carDto);
    }

    @Override
    public String getColor(Long id) {

        return carService.getColor(id);
    }

    // Get color by id
    @GetMapping("/{id}/color")
    public ResponseEntity<String> getColorRest(@PathVariable @Min(1) Long id) {

        final String color = getColor(id);

        if (color.isEmpty()) {
            return ResponseEntity.badRequest().body("Car not found!");
        }

        return ResponseEntity.ok(color);
    }

    @Override
    public String getBrandName(Long id) {

        return carService.getBrandName(id);
    }

    // Get brand name by id
    @GetMapping("/{id}/brand_name")
    public ResponseEntity<String> getBrandNameRest(@PathVariable @Min(1) Long id) {

        final String brandName = getBrandName(id);

        if (brandName.isEmpty()) {
            return ResponseEntity.badRequest().body("Car not found!");
        }

        return ResponseEntity.ok(brandName);
    }

    @Override
    public BigDecimal getPrice(Long id) {

        return carService.getPrice(id);
    }

    // Get price by id
    @GetMapping("/{id}/price")
    public ResponseEntity<String> getPriceRest(@PathVariable @Min(1) Long id) {

        final BigDecimal price = getPrice(id);

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Car not found!");
        }

        return ResponseEntity.ok(String.format("%.2f lv.", price));
    }

    @Override
    public Long getWeight(Long id) {

        return carService.getWeight(id);
    }

    // Get weight by id
    @GetMapping("/{id}/weight")
    public ResponseEntity<String> getWeightRest(@PathVariable @Min(1) Long id) {

        final Long weight = getWeight(id);

        if (weight <= 0) {
            return ResponseEntity.badRequest().body("Car not found!");
        }

        return ResponseEntity.ok(String.format("%d kg.", weight));
    }

    @Override
    public Long getLength(Long id) {

        return carService.getLength(id);
    }

    // Get length by id
    @GetMapping("/{id}/length")
    public ResponseEntity<String> getLengthRest(@PathVariable @Min(1) Long id) {

        final Long length = getLength(id);

        if (length <= 0) {
            return ResponseEntity.badRequest().body("Car not found!");
        }

        return ResponseEntity.ok(String.format("%d cm.", length));
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {

        return carService.findProduct(color, pageable);
    }

    // Get cars by color
    @GetMapping("/colors/{color}")
    public ResponseEntity<CarListDto> getCarByColorRest(@PathVariable @Size(min = 1) String color,
                                                        @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                        @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Car> cars = pageProductToPageCar(pageable, findProduct(color, pageable));

        return ResponseEntity.ok(mapCarToCarListDto(pageNumber, pageSize, cars));
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {

        return carService.findProduct(price, pageable);
    }

    // Get cars by price
    @GetMapping("/price/{price}")
    public ResponseEntity<CarListDto> getCarByPriceRest(@PathVariable @Positive Double price,
                                                        @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                        @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Car> cars = pageProductToPageCar(pageable, findProduct(BigDecimal.valueOf(price), pageable));

        return ResponseEntity.ok(mapCarToCarListDto(pageNumber, pageSize, cars));
    }

    @Override
    public boolean create(Product product) {

        return carService.create(product);
    }

    // Create a car
    @PostMapping("/create")
    public ResponseEntity<String> createRest(@RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        if (create(new Car(brand_name, color, price, weight, length, quantity))) {
            return ResponseEntity.ok("Car successfully created!");
        }

        return ResponseEntity.badRequest().body("Car not created!");
    }

    @Override
    public boolean update(Product product) {

        return carService.update(product);
    }

    // Update car by id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRest(@PathVariable @Min(1) Long id,
                                             @RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        // Find car by id
        final Car car = (Car) findProduct(id);
        if (car == null) {
            return ResponseEntity.badRequest().body("Car not updated!");
        }

        // Update car properties
        car.setBrand_name(brand_name);
        car.setColor(color);
        car.setPrice(price);
        car.setWeight(weight);
        car.setLength(length);
        car.setQuantity(quantity);

        if (update(car)) {
            return ResponseEntity.ok("Car successfully updated!");
        }

        return ResponseEntity.badRequest().body("Car not updated!");
    }

    @Override
    public boolean delete(Long id) {

        return carService.delete(id);
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRest(@PathVariable @Min(1) Long id) {

        if (delete(id)) {
            return ResponseEntity.ok("Car successfully deleted!");
        }

        return ResponseEntity.badRequest().body("Car not deleted!");
    }

    private Page<Car> pageProductToPageCar(Pageable pageable, Page<Product> pageOfProduct) {

        List<Car> carList = new ArrayList<>();

        pageOfProduct.forEach(entity -> carList.add((Car) entity));

        return new PageImpl<>(carList, pageable, carList.size());
    }

    private CarListDto mapCarToCarListDto(Integer pageNumber, Integer pageSize, Page<Car> pageOfCar) {

        return new CarListDto(
                pageNumber,
                pageSize,
                pageOfCar.getNumberOfElements(),
                (int) pageOfCar.getTotalElements(),

                // Map the car list to content list of car DTO.
                pageOfCar.getContent()
                        .stream()
                        .map(car -> new CarDto(
                                car.getId(),
                                car.getBrand_name(),
                                car.getColor(),
                                car.getPrice(),
                                car.getWeight(),
                                car.getLength(),
                                car.getQuantity()
                        )).collect(Collectors.toList()));
    }
}
