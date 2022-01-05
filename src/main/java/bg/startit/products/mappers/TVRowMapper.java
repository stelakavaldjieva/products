package bg.startit.products.mappers;

import bg.startit.products.model.TV;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Convert db data for tv to TV entity.
 */
public class TVRowMapper implements RowMapper<TV> {

    /**
     * Convert row from table TV to Car entity.
     */
    @Override
    public TV mapRow(ResultSet resultSet, int i) throws SQLException {
        TV tv = new TV();

        tv.setId(resultSet.getLong("ID"));
        tv.setBrand_name(resultSet.getString("BRAND_NAME"));
        tv.setColor(resultSet.getString("COLOR"));
        tv.setPrice(BigDecimal.valueOf(resultSet.getDouble("PRICE")));
        tv.setLength(resultSet.getLong("LENGTH"));
        tv.setWeight(resultSet.getLong("WEIGHT"));
        tv.setQuantity(resultSet.getLong("QUANTITY"));

        return tv;
    }
}
