package bg.startit.products.dao;

import bg.startit.products.mappers.CarRowMapper;
import bg.startit.products.mappers.SaleRowMapper;
import bg.startit.products.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Access Sale data via JDBC.
 */
@Repository
public class SaleDao {
    /*
     *  Use basic JDBC operations using named parameters instead of '?' placeholders
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean create(Sale sale) {

        final String query = "INSERT INTO SALE (PRODUCT_ID, NB_SOLD, SALE_DATE, PRODUCT_TYPE, PRICE)" +
                " VALUES (:product_id, :nb_sold, :sale_date, :product_type, :price)";

        try {
            // If query has updated one row, return true
            if (namedParameterJdbcTemplate.update(
                    query,
                    new BeanPropertySqlParameterSource(sale)) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Long getCountInDateRange(LocalDateTime start, LocalDateTime end) {

        final String query = "SELECT SUM(NB_SOLD) FROM SALE WHERE SALE.SALE_DATE BETWEEN :start AND :end";
        // Use map to store query parameters
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("start", start);
        mapSqlParameterSource.addValue("end", end);

        Long countAllInDateRange = 0L;

        try {
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/namedparam/NamedParameterJdbcTemplate.html#queryForObject-java.lang.String-java.util.Map-java.lang.Class-
            countAllInDateRange = namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return countAllInDateRange;
    }

    public BigDecimal getSumAllMoneyInDateRange(LocalDateTime start, LocalDateTime end) {

        final String query = "SELECT SUM(PRICE) FROM SALE WHERE SALE.SALE_DATE BETWEEN :start AND :end";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("start", start);
        mapSqlParameterSource.addValue("end", end);

        BigDecimal sumAllMoneyInDateRange = BigDecimal.ZERO;

        try {
            sumAllMoneyInDateRange = namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, BigDecimal.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sumAllMoneyInDateRange;
    }

    // Required for testing
    public Sale findByDate(LocalDateTime sale_date) {

        final String query = "SELECT * FROM SALE WHERE SALE_DATE = :sale_date";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("sale_date", sale_date);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, mapSqlParameterSource, new SaleRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Required for testing
    public boolean delete(Long sale_date) {

        final String query = "SELECT * FROM SALE WHERE SALE_DATE = :sale_date";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("sale_date", sale_date);

        try {
            if (namedParameterJdbcTemplate.update(query, mapSqlParameterSource) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
