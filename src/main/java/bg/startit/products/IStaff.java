package bg.startit.products;

import bg.startit.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Methods to be implemented in Car, Phone, Tv Controller, Service and Repository
 */
public interface IStaff
{
  Page<Product> getAll(Pageable pageable);

  Product findProduct(Long id);

  Page<Product> findProduct(String color, Pageable pageable);

  Page<Product> findProduct(BigDecimal price, Pageable pageable);

  String getColor(Long id);

  String getBrandName(Long id);

  BigDecimal getPrice(Long id);

  Long getWeight(Long id);

  Long getLength(Long id);

  boolean create(Product product);

  boolean update(Product product);

  boolean delete(Long id);
}
