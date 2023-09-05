package com.hcp;

import com.hcp.dao.OrdersDao;
import com.hcp.pojo.Orders;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrdersTest {

    public static void main(String[] args) {
        OrdersDao ordersDao = new OrdersDao();
        //分页查询
        System.out.println("============ 分页查询 ============\t\n");
        List<Orders> list = ordersDao.getList(1, 5);
        for (Orders orders : list) {
            System.out.println("orders = " + orders);
        }

        //根据订单ID查询
        System.out.println("============ 根据订单ID查询 ============\t\n");
        Orders byOrderId = ordersDao.getByOrderId("1");
        System.out.println("byOrderId = " + byOrderId);

        //查询用户所有订单
        System.out.println("============ 查询用户所有订单 ============\t\n");
        List<Orders> byCustomerId = ordersDao.getByCustomerId("1");
        for (Orders orders : byCustomerId) {
            System.out.println("orders = " + orders);
        }

        //插入
        Orders insert = new Orders();
        insert.setCustomerId(1);
        insert.setOrderDate(Date.valueOf("2023-03-01"));
        insert.setPaymentStatus("未支付");
        insert.setTotalAmount(BigDecimal.valueOf(200.00));
        insert.setShippingAddress("中国湖南");
        ordersDao.insert(insert);
        //修改订单
        System.out.println("============ 修改订单 ============\t\n");
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setPaymentStatus("已支付");
        orders.setTotalAmount(BigDecimal.valueOf(200.00));
        orders.setShippingAddress("中国湖南");
        ordersDao.update(orders);


    }
}
