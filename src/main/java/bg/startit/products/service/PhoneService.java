package bg.startit.products.service;

import bg.startit.products.IStaff;
import bg.startit.products.dao.PhoneDao;
import bg.startit.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Business logic for Phone
 */
@Service
public class PhoneService implements IStaff {

    private final PhoneDao phoneDao;

    public PhoneService(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return phoneDao.getAll(pageable);
    }

    @Override
    public Product findProduct(Long id) {
        return phoneDao.findProduct(id);
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {
        return phoneDao.findProduct(color, pageable);
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {
        return phoneDao.findProduct(price, pageable);
    }

    @Override
    public String getColor(Long id) {
        return phoneDao.getColor(id);
    }

    @Override
    public String getBrandName(Long id) {
        return phoneDao.getBrandName(id);
    }

    @Override
    public BigDecimal getPrice(Long id) {
        return phoneDao.getPrice(id);
    }

    @Override
    public Long getWeight(Long id) {
        return phoneDao.getWeight(id);
    }

    @Override
    public Long getLength(Long id) {
        return phoneDao.getLength(id);
    }

    @Override
    public boolean create(Product phone) {
        return phoneDao.create(phone);
    }

    @Override
    public boolean update(Product product) {
        return phoneDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return phoneDao.delete(id);
    }
}
