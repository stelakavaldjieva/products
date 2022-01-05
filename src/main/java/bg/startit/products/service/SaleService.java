package bg.startit.products.service;

import bg.startit.products.dao.SaleDao;
import bg.startit.products.model.Sale;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Business logic for Sale
 */
@Service
public class SaleService {

    private final SaleDao saleDao;

    public SaleService(SaleDao saleDao) {

        this.saleDao = saleDao;
    }

    public boolean create(Sale sale) {

        return saleDao.create(sale);
    }

    public Long getCountAllInDateRange(LocalDateTime start, LocalDateTime end) {

        return saleDao.getCountInDateRange(start, end);
    }

    public BigDecimal getSumAllMoneyInDateRange(LocalDateTime start, LocalDateTime end) {

        return saleDao.getSumAllMoneyInDateRange(start, end);
    }

    public Sale findByDate(LocalDateTime sale_date) {

        return saleDao.findByDate(sale_date);
    }

    public boolean delete(Long sale_id) {
        return saleDao.delete(sale_id);
    }
}
