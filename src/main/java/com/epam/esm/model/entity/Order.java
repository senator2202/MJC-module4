package com.epam.esm.model.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entity class, representing order for a certificate from a user .
 */
@javax.persistence.Entity
@Table(name = "`order`")
public class Order extends Entity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "certificate_id")
    private GiftCertificate giftCertificate;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "cost")
    private BigDecimal cost;

    public Order() {
    }

    public Order(Long id, User user, GiftCertificate giftCertificate, String orderDate, BigDecimal cost) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) {
            return false;
        }
        if (!Objects.equals(user, order.user)) {
            return false;
        }
        if (!Objects.equals(giftCertificate, order.giftCertificate)) {
            return false;
        }
        return Objects.equals(cost, order.cost);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
