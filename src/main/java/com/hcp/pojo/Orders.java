package com.hcp.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Orders implements Serializable {

    private Integer orderId;

    private Integer customerId;

    private Date orderDate;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private String shippingAddress;

    public Orders() {
    }

    public Orders(Integer orderId, Integer customerId, Date orderDate, BigDecimal totalAmount, String paymentStatus, String shippingAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
