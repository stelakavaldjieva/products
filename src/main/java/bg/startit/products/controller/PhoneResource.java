package bg.startit.products.controller;

import bg.startit.products.IStaff;
import bg.startit.products.dto.phone.PhoneDto;
import bg.startit.products.dto.phone.PhoneListDto;
import bg.startit.products.model.Phone;
import bg.startit.products.model.Product;
import bg.startit.products.service.PhoneService;
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
 * REST controller for Phone operations.
 */
@RestController
@RequestMapping("/api/v1/phones")
public class PhoneResource implements IStaff {

    private final PhoneService phoneService;

    public PhoneResource(PhoneService phoneService) {

        this.phoneService = phoneService;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {

        return phoneService.getAll(pageable);
    }

    // Get all phones
    @GetMapping("/all")
    public ResponseEntity<PhoneListDto> getAllRest(@RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                   @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Phone> phones = pageProductToPagePhone(pageable, getAll(pageable));

        return ResponseEntity.ok(mapPhoneToPhoneListDto(pageNumber, pageSize, phones));
    }

    @Override
    public Product findProduct(Long id) {

        return phoneService.findProduct(id);
    }

    // Get phone by id
    @GetMapping("/{id}")
    public ResponseEntity<PhoneDto> getPhoneByIdRest(@PathVariable @Min(1) Long id) {

        // Find phone by id
        final Phone phone = (Phone) findProduct(id);

        PhoneDto phoneDto = new PhoneDto();
        if (phone == null) {
            phoneDto.setBrand_name("Phone not found!");
            return ResponseEntity.badRequest().body(phoneDto);
        }

        phoneDto = new PhoneDto(
                phone.getId(),
                phone.getBrand_name(),
                phone.getColor(),
                phone.getPrice(),
                phone.getWeight(),
                phone.getLength(),
                phone.getQuantity());

        return ResponseEntity.ok(phoneDto);
    }

    @Override
    public String getColor(Long id) {

        return phoneService.getColor(id);
    }

    // Get color by id
    @GetMapping("/{id}/color")
    public ResponseEntity<String> getColorRest(@PathVariable @Min(1) Long id) {

        final String color = getColor(id);

        if (color.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone not found!");
        }

        return ResponseEntity.ok(color);
    }

    @Override
    public String getBrandName(Long id) {

        return phoneService.getBrandName(id);
    }

    // Get brand name by id
    @GetMapping("/{id}/brand_name")
    public ResponseEntity<String> getBrandNameRest(@PathVariable @Min(1) Long id) {

        final String brandName = getBrandName(id);

        if (brandName.isEmpty()) {
            return ResponseEntity.badRequest().body("Phone not found!");
        }

        return ResponseEntity.ok(brandName);
    }

    @Override
    public BigDecimal getPrice(Long id) {

        return phoneService.getPrice(id);
    }

    // Get price by id
    @GetMapping("/{id}/price")
    public ResponseEntity<String> getPriceRest(@PathVariable @Min(1) Long id) {

        final BigDecimal price = getPrice(id);

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Phone not found!");
        }

        return ResponseEntity.ok(String.format("%.2f lv.", price));
    }

    @Override
    public Long getWeight(Long id) {

        return phoneService.getWeight(id);
    }

    // Get weight by id
    @GetMapping("/{id}/weight")
    public ResponseEntity<String> getWeightRest(@PathVariable @Min(1) Long id) {

        final Long weight = getWeight(id);

        if (weight <= 0) {
            return ResponseEntity.badRequest().body("Phone not found!");
        }

        return ResponseEntity.ok(String.format("%d kg.", weight));
    }

    @Override
    public Long getLength(Long id) {

        return phoneService.getLength(id);
    }

    // Get length by id
    @GetMapping("/{id}/length")
    public ResponseEntity<String> getLengthRest(@PathVariable @Min(1) Long id) {

        final Long length = getLength(id);

        if (length <= 0) {
            return ResponseEntity.badRequest().body("Phone not found!");
        }

        return ResponseEntity.ok(String.format("%d cm.", length));
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {

        return phoneService.findProduct(color, pageable);
    }

    // Get phones by color
    @GetMapping("/colors/{color}")
    public ResponseEntity<PhoneListDto> getPhoneByColorRest(@PathVariable @Size(min = 1) String color,
                                                            @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                            @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Phone> phones = pageProductToPagePhone(pageable, findProduct(color, pageable));

        return ResponseEntity.ok(mapPhoneToPhoneListDto(pageNumber, pageSize, phones));
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {

        return phoneService.findProduct(price, pageable);
    }

    // Get phones by price
    @GetMapping("/price/{price}")
    public ResponseEntity<PhoneListDto> getPhoneByPriceRest(@PathVariable @Positive Double price,
                                                            @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                            @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<Phone> phones = pageProductToPagePhone(pageable, findProduct(BigDecimal.valueOf(price), pageable));

        return ResponseEntity.ok(mapPhoneToPhoneListDto(pageNumber, pageSize, phones));
    }

    @Override
    public boolean create(Product product) {

        return phoneService.create(product);
    }

    // Create a phone
    @PostMapping("/create")
    public ResponseEntity<String> createRest(@RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        if (create(new Phone(brand_name, color, price, weight, length, quantity))) {
            return ResponseEntity.ok("Phone successfully created!");
        }

        return ResponseEntity.badRequest().body("Phone not created!");
    }

    @Override
    public boolean update(Product product) {

        return phoneService.update(product);
    }

    // Update phone by id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRest(@PathVariable @Min(1) Long id,
                                             @RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        // Find phone by id
        final Phone phone = (Phone) findProduct(id);
        if (phone == null) {
            return ResponseEntity.badRequest().body("Phone not updated!");
        }

        // Update phone properties
        phone.setBrand_name(brand_name);
        phone.setColor(color);
        phone.setPrice(price);
        phone.setWeight(weight);
        phone.setLength(length);
        phone.setQuantity(quantity);

        if (update(phone)) {
            return ResponseEntity.ok("Phone successfully updated!");
        }

        return ResponseEntity.badRequest().body("Phone not updated!");
    }

    @Override
    public boolean delete(Long id) {

        return phoneService.delete(id);
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRest(@PathVariable @Min(1) Long id) {

        if (delete(id)) {
            return ResponseEntity.ok("Phone successfully deleted!");
        }

        return ResponseEntity.badRequest().body("Phone not deleted!");
    }

    private Page<Phone> pageProductToPagePhone(Pageable pageable, Page<Product> pageOfProduct) {

        List<Phone> phoneList = new ArrayList<>();

        pageOfProduct.forEach(entity -> phoneList.add((Phone) entity));

        return new PageImpl<>(phoneList, pageable, phoneList.size());
    }

    private PhoneListDto mapPhoneToPhoneListDto(Integer pageNumber, Integer pageSize, Page<Phone> pageOfPhone) {

        return new PhoneListDto(
                pageNumber,
                pageSize,
                pageOfPhone.getNumberOfElements(),
                (int) pageOfPhone.getTotalElements(),

                // Map the phone list to content list of phone DTO.
                pageOfPhone.getContent()
                        .stream()
                        .map(phone -> new PhoneDto(
                                phone.getId(),
                                phone.getBrand_name(),
                                phone.getColor(),
                                phone.getPrice(),
                                phone.getWeight(),
                                phone.getLength(),
                                phone.getQuantity()
                        )).collect(Collectors.toList()));
    }
}
