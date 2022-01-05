package bg.startit.products.controller;

import bg.startit.products.IStaff;
import bg.startit.products.dto.tv.TVDto;
import bg.startit.products.dto.tv.TVListDto;
import bg.startit.products.model.TV;
import bg.startit.products.model.Product;
import bg.startit.products.service.TVService;
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
 * REST controller for TV operations.
 */
@RestController
@RequestMapping("/api/v1/tvs")
public class TVResource implements IStaff {

    private final TVService tvService;

    public TVResource(TVService tvService) {

        this.tvService = tvService;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {

        return tvService.getAll(pageable);
    }

    // Get all tvs
    @GetMapping("/all")
    public ResponseEntity<TVListDto> getAllRest(@RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<TV> tvs = pageProductToPageTV(pageable, getAll(pageable));

        return ResponseEntity.ok(mapTVToTVListDto(pageNumber, pageSize, tvs));
    }

    @Override
    public Product findProduct(Long id) {

        return tvService.findProduct(id);
    }

    // Get tv by id
    @GetMapping("/{id}")
    public ResponseEntity<TVDto> getTVByIdRest(@PathVariable @Min(1) Long id) {

        // Find tv by id
        final TV tv = (TV) findProduct(id);

        TVDto tvDto = new TVDto();
        if (tv == null) {
            tvDto.setBrand_name("TV not found!");
            return ResponseEntity.badRequest().body(tvDto);
        }

        tvDto = new TVDto(
                tv.getId(),
                tv.getBrand_name(),
                tv.getColor(),
                tv.getPrice(),
                tv.getWeight(),
                tv.getLength(),
                tv.getQuantity());

        return ResponseEntity.ok(tvDto);
    }

    @Override
    public String getColor(Long id) {

        return tvService.getColor(id);
    }

    // Get color by id
    @GetMapping("/{id}/color")
    public ResponseEntity<String> getColorRest(@PathVariable @Min(1) Long id) {

        final String color = getColor(id);

        if (color.isEmpty()) {
            return ResponseEntity.badRequest().body("TV not found!");
        }

        return ResponseEntity.ok(color);
    }

    @Override
    public String getBrandName(Long id) {

        return tvService.getBrandName(id);
    }

    // Get brand name by id
    @GetMapping("/{id}/brand_name")
    public ResponseEntity<String> getBrandNameRest(@PathVariable @Min(1) Long id) {

        final String brandName = getBrandName(id);

        if (brandName.isEmpty()) {
            return ResponseEntity.badRequest().body("TV not found!");
        }

        return ResponseEntity.ok(brandName);
    }

    @Override
    public BigDecimal getPrice(Long id) {

        return tvService.getPrice(id);
    }

    // Get price by id
    @GetMapping("/{id}/price")
    public ResponseEntity<String> getPriceRest(@PathVariable @Min(1) Long id) {
        final BigDecimal price = getPrice(id);

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("TV not found!");
        }

        return ResponseEntity.ok(String.format("%.2f lv.", price));
    }

    @Override
    public Long getWeight(Long id) {

        return tvService.getWeight(id);
    }

    // Get weight by id
    @GetMapping("/{id}/weight")
    public ResponseEntity<String> getWeightRest(@PathVariable @Min(1) Long id) {

        final Long weight = getWeight(id);

        if (weight <= 0) {
            return ResponseEntity.badRequest().body("TV not found!");
        }

        return ResponseEntity.ok(String.format("%d kg.", weight));
    }

    @Override
    public Long getLength(Long id) {

        return tvService.getLength(id);
    }

    // Get length by id
    @GetMapping("/{id}/length")
    public ResponseEntity<String> getLengthRest(@PathVariable @Min(1) Long id) {

        final Long length = getLength(id);

        if (length <= 0) {
            return ResponseEntity.badRequest().body("TV not found!");
        }

        return ResponseEntity.ok(String.format("%d cm.", length));
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {

        return tvService.findProduct(color, pageable);
    }

    // Get tvs by color
    @GetMapping("/colors/{color}")
    public ResponseEntity<TVListDto> getTVByColorRest(@PathVariable @Size(min = 1) String color,
                                                      @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                      @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<TV> tvs = pageProductToPageTV(pageable, findProduct(color, pageable));

        return ResponseEntity.ok(mapTVToTVListDto(pageNumber, pageSize, tvs));
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {

        return tvService.findProduct(price, pageable);
    }

    // Get tvs by price
    @GetMapping("/price/{price}")
    public ResponseEntity<TVListDto> getTVByPriceRest(@PathVariable @Positive Double price,
                                                      @RequestParam(defaultValue = "0") @Min(0) Integer pageNumber,
                                                      @RequestParam(defaultValue = "20") @Min(1) Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        final Page<TV> tvs = pageProductToPageTV(pageable, findProduct(BigDecimal.valueOf(price), pageable));

        return ResponseEntity.ok(mapTVToTVListDto(pageNumber, pageSize, tvs));
    }

    @Override
    public boolean create(Product product) {

        return tvService.create(product);
    }

    // Create a tv
    @PostMapping("/create")
    public ResponseEntity<String> createRest(@RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        if (create(new TV(brand_name, color, price, weight, length, quantity))) {
            return ResponseEntity.ok("TV successfully created!");
        }

        return ResponseEntity.badRequest().body("TV not created!");
    }

    @Override
    public boolean update(Product product) {

        return tvService.update(product);
    }

    // Update tv by id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRest(@PathVariable @Min(1) Long id,
                                             @RequestParam @Size(min = 1) String brand_name,
                                             @RequestParam @Size(min = 1) String color,
                                             @RequestParam @Positive BigDecimal price,
                                             @RequestParam @Min(1) Long weight,
                                             @RequestParam @Min(1) Long length,
                                             @RequestParam @Min(1) Long quantity) {

        // Find tv by id
        final TV tv = (TV) findProduct(id);
        if (tv == null) {
            return ResponseEntity.badRequest().body("TV not updated!");
        }

        // Update tv properties
        tv.setBrand_name(brand_name);
        tv.setColor(color);
        tv.setPrice(price);
        tv.setWeight(weight);
        tv.setLength(length);
        tv.setQuantity(quantity);

        if (update(tv)) {
            return ResponseEntity.ok("TV successfully updated!");
        }

        return ResponseEntity.badRequest().body("TV not updated!");
    }

    @Override
    public boolean delete(Long id) {

        return tvService.delete(id);
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRest(@PathVariable @Min(1) Long id) {

        if (delete(id)) {
            return ResponseEntity.ok("TV successfully deleted!");
        }

        return ResponseEntity.badRequest().body("TV not deleted!");
    }

    private Page<TV> pageProductToPageTV(Pageable pageable, Page<Product> pageOfProduct) {

        List<TV> tvList = new ArrayList<>();

        pageOfProduct.forEach(entity -> tvList.add((TV) entity));

        return new PageImpl<>(tvList, pageable, tvList.size());
    }

    private TVListDto mapTVToTVListDto(Integer pageNumber, Integer pageSize, Page<TV> pageOfTV) {

        return new TVListDto(
                pageNumber,
                pageSize,
                pageOfTV.getNumberOfElements(),
                (int) pageOfTV.getTotalElements(),

                // Map the tv list to content list of tv DTO.
                pageOfTV.getContent()
                        .stream()
                        .map(tv -> new TVDto(
                                tv.getId(),
                                tv.getBrand_name(),
                                tv.getColor(),
                                tv.getPrice(),
                                tv.getWeight(),
                                tv.getLength(),
                                tv.getQuantity()
                        )).collect(Collectors.toList()));
    }
}
