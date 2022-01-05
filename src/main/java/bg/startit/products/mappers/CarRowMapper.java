package bg.startit.products.mappers;

import bg.startit.products.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Convert db data for car to Car entity.
 */
public class CarRowMapper implements RowMapper<Car> {

    /**
     * Convert row from table CAR to Car entity.
     */
    @Override
    public Car mapRow(ResultSet resultSet, int i) throws SQLException {
        Car car = new Car();

        car.setId(resultSet.getLong("ID"));
        car.setBrand_name(resultSet.getString("BRAND_NAME"));
        car.setColor(resultSet.getString("COLOR"));
        car.setPrice(BigDecimal.valueOf(resultSet.getDouble("PRICE")));
        car.setLength(resultSet.getLong("LENGTH"));
        car.setWeight(resultSet.getLong("WEIGHT"));
        car.setQuantity(resultSet.getLong("QUANTITY"));

        return car;
    }
}
