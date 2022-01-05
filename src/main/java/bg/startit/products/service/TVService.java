package bg.startit.products.service;

import bg.startit.products.IStaff;
import bg.startit.products.dao.TVDao;
import bg.startit.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
/**
 * Business logic for TV
 */
@Service
public class TVService implements IStaff {

    private final TVDao tvDao;

    public TVService(TVDao tvDao) {
        this.tvDao = tvDao;
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return tvDao.getAll(pageable);
    }

    @Override
    public Product findProduct(Long id) {
        return tvDao.findProduct(id);
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {
        return tvDao.findProduct(color, pageable);
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {
        return tvDao.findProduct(price, pageable);
    }

    @Override
    public String getColor(Long id) {
        return tvDao.getColor(id);
    }

    @Override
    public String getBrandName(Long id) {
        return tvDao.getBrandName(id);
    }

    @Override
    public BigDecimal getPrice(Long id) {
        return tvDao.getPrice(id);
    }

    @Override
    public Long getWeight(Long id) {
        return tvDao.getWeight(id);
    }

    @Override
    public Long getLength(Long id) {
        return tvDao.getLength(id);
    }

    @Override
    public boolean create(Product tv) {
        return tvDao.create(tv);
    }

    @Override
    public boolean update(Product product) {
        return tvDao.update(product);
    }

    @Override
    public boolean delete(Long id) {
        return tvDao.delete(id);
    }
}
