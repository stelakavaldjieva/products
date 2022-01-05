package bg.startit.products.mappers;

import bg.startit.products.model.Phone;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Convert db data for phone to Phone entity.
 */
public class PhoneRowMapper implements RowMapper<Phone> {

    /**
     * Convert row from table PHONE to Car entity.
     */
    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new Phone();

        phone.setId(resultSet.getLong("ID"));
        phone.setBrand_name(resultSet.getString("BRAND_NAME"));
        phone.setColor(resultSet.getString("COLOR"));
        phone.setPrice(BigDecimal.valueOf(resultSet.getDouble("PRICE")));
        phone.setLength(resultSet.getLong("LENGTH"));
        phone.setWeight(resultSet.getLong("WEIGHT"));
        phone.setQuantity(resultSet.getLong("QUANTITY"));

        return phone;
    }
}
