package bg.startit.products.dao;

import bg.startit.products.IStaff;
import bg.startit.products.mappers.CarRowMapper;
import bg.startit.products.model.Car;
import bg.startit.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Access Car data via JDBC.
 */
@Repository
public class CarDao implements IStaff {

    /**
     * Use basic JDBC operations using named parameters instead of '?' placeholders
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {

        final String query = "SELECT * FROM CAR OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
        // Add values via MapSqlParameterSource
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("limit", pageable.getPageSize());
        mapSqlParameterSource.addValue("offset", pageable.getPageNumber());

        List<Car> carList = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, new CarRowMapper());

        List<Product> productList = new ArrayList<>(carList);

        return new PageImpl<>(productList, pageable, productList.size());
    }

    @Override
    public Product findProduct(Long id) {

        final String query = "SELECT * FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, new CarRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Page<Product> findProduct(String color, Pageable pageable) {
        // TODO implement for large lists https://www.baeldung.com/spring-jdbctemplate-in-list#large

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("color", "%" + color + "%");
        mapSqlParameterSource.addValue("limit", pageable.getPageSize());
        mapSqlParameterSource.addValue("offset", pageable.getPageNumber());

        final String query = "SELECT * FROM CAR WHERE COLOR LIKE :color ORDER BY PRICE DESC OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

        List<Car> carList = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, new CarRowMapper());

        final List<Product> productList = new ArrayList<>(carList);

        return new PageImpl<>(productList, pageable, productList.size());
    }

    @Override
    public Page<Product> findProduct(BigDecimal price, Pageable pageable) {
        // TODO implement for large lists https://www.baeldung.com/spring-jdbctemplate-in-list#large

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("price", price);
        mapSqlParameterSource.addValue("limit", pageable.getPageSize());
        mapSqlParameterSource.addValue("offset", pageable.getPageNumber());

        final String query = "SELECT * FROM CAR WHERE PRICE <= :price ORDER BY PRICE DESC OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

        List<Car> carList = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, new CarRowMapper());

        final List<Product> productList = new ArrayList<>(carList);

        return new PageImpl<>(productList, pageable, productList.size());
    }

    @Override
    public String getColor(Long id) {

        final String query = "SELECT COLOR FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String getBrandName(Long id) {

        final String query = "SELECT BRAND_NAME FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public BigDecimal getPrice(Long id) {

        final String query = "SELECT PRICE FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, BigDecimal.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    @Override
    public Long getWeight(Long id) {

        final String query = "SELECT WEIGHT FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }

    @Override
    public Long getLength(Long id) {

        final String query = "SELECT LENGTH FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }

    @Override
    public boolean create(Product product) {

        final Car car = (Car) product;

        String query = "INSERT INTO CAR (BRAND_NAME, COLOR, PRICE, WEIGHT, LENGTH, QUANTITY)" +
                " VALUES (:brand_name, :color, :price, :weight, :length, :quantity)";

        try {
            if (namedParameterJdbcTemplate.update(
                    query,
                    new BeanPropertySqlParameterSource(car)) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Product product) {

        final Car car = (Car) product;

        String query = "UPDATE CAR SET BRAND_NAME = :brand_name, COLOR = :color, PRICE = :price, " +
                "WEIGHT = :weight, LENGTH = :length, QUANTITY = :quantity WHERE ID = :id";

        try {
            if (namedParameterJdbcTemplate.update(
                    query,
                    new BeanPropertySqlParameterSource(car)) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {

        String query = "DELETE FROM CAR WHERE ID = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        try {
            if (namedParameterJdbcTemplate.update(
                    query,
                    mapSqlParameterSource) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
