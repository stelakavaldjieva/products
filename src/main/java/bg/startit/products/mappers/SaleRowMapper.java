package bg.startit.products.mappers;

import bg.startit.products.model.Car;
import bg.startit.products.model.Sale;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Convert db data for sale to Sale entity.
 */
public class SaleRowMapper implements RowMapper<Sale> {

    /**
     * Convert row from table SALE to Sale entity.
     */
    @Override
    public Sale mapRow(ResultSet resultSet, int i) throws SQLException {
        Sale sale = new Sale();

        sale.setSale_id(resultSet.getLong("SALE_ID"));
        sale.setProduct_id(resultSet.getLong("PRODUCT_ID"));
        sale.setNb_sold(resultSet.getLong("NB_SOLD"));
        sale.setSale_date(resultSet.getObject(4, LocalDateTime.class));
        sale.setProduct_type(resultSet.getInt("PRODUCT_TYPE"));
        sale.setPrice(resultSet.getBigDecimal("PRICE"));

        return sale;
    }
}
