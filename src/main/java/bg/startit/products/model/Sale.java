package bg.startit.products.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sale {

    private Long sale_id;
    private Long product_id;
    private Long nb_sold;
    private LocalDateTime sale_date;
    private Integer product_type;
    private BigDecimal price;

    public Sale() {
    }

    public Sale(Long product_id, Long nb_sold, LocalDateTime sale_date, Integer product_type, BigDecimal price) {
        this.product_id = product_id;
        this.nb_sold = nb_sold;
        this.sale_date = sale_date;
        this.product_type = product_type;
        this.price = price;
    }

    public Long getSale_id() {
        return sale_id;
    }

    public void setSale_id(Long sale_id) {
        this.sale_id = sale_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getNb_sold() {
        return nb_sold;
    }

    public void setNb_sold(Long nb_sold) {
        this.nb_sold = nb_sold;
    }

    public LocalDateTime getSale_date() {
        return sale_date;
    }

    public void setSale_date(LocalDateTime sale_date) {
        this.sale_date = sale_date;
    }

    public Integer getProduct_type() {
        return product_type;
    }

    public void setProduct_type(Integer type) {
        this.product_type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
