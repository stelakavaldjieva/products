package bg.startit.products.service;

import bg.startit.products.IStaff;
import bg.startit.products.dao.CarDao;
import bg.startit.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
/**
 * Business logic for Car
 */
@Service
public class CarService implements IStaff {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return carDao.getAll(pageable);
    }

    @Override
    public Product findProduct(Long id) {
        return carDao.findProduct(id);
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {
        return carDao.findProduct(color, pageable);
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {
        return carDao.findProduct(price, pageable);
    }

    @Override
    public String getColor(Long id) {
        return carDao.getColor(id);
    }

    @Override
    public String getBrandName(Long id) {
        return carDao.getBrandName(id);
    }

    @Override
    public BigDecimal getPrice(Long id) {
        return carDao.getPrice(id);
    }

    @Override
    public Long getWeight(Long id) {
        return carDao.getWeight(id);
    }

    @Override
    public Long getLength(Long id) {
        return carDao.getLength(id);
    }

    @Override
    public boolean create(Product car) {
        return carDao.create(car);
    }

    @Override
    public boolean update(Product product) {
        return carDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }
}
