package com.hcp.dao;

import com.hcp.pojo.Customers;
import com.hcp.pojo.Orders;
import com.hcp.utils.DBUtils;
import com.hcp.utils.EnumTest;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {

    /**
     * 查询全部
     * @return
     */
    public List<Orders> getList(){
        String sql = "select * from orders";
        List<Orders> ordersList = new ArrayList<>();
        try(ResultSet resultSet = DBUtils.executeQuery(sql)) {
            // Orders orders = new Orders 如果要保证迭代时不覆盖 1.将对象创建到while外
            // Orders orders = null; 将此代码写在while外虽然不会一直创建新的对象也可以节省内存分配和垃圾回收的开销
            //如果一直不创建新的对象，而是重复使用一个对象，则每次迭代会修改同一个对象的属性值
            //可能会导致ordersList中所有orders对象都拥有相同的属性值
            while (resultSet.next()){
                // 2.orders.setOrderId(resultSet.getInt("order_id")); 在循环中，从数据源（例如 ResultSet）中检索当前迭代的属性值。
                // 3.使用每次迭代的检索值更新现有对象的属性。例如以上写法
                Integer orderId = resultSet.getInt(1);
                Integer customerId = resultSet.getInt(2);
                Date orderDate = resultSet.getDate(3);
                BigDecimal totalAmount = resultSet.getBigDecimal(4);
                String paymentStatus = resultSet.getString(5);
                String shippingAddress = resultSet.getString(6);
                Orders orders = new Orders(orderId,customerId,orderDate,totalAmount,paymentStatus,shippingAddress);
                ordersList.add(orders);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    /**
     * 分页查询(优化: 避免add()出现属性覆盖的可能)
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Orders> getList(Integer pageNum,Integer pageSize){
        int startIndex = (pageNum-1)*pageSize;
        List<Orders> ordersList = new ArrayList<>();
        String sql = "select * from orders limit ?,?";
        try {
            ResultSet resultSet = DBUtils.executeQuery(sql, startIndex, pageSize);
            while (resultSet.next()){
                Orders orders = new Orders();
                orders.setOrderId(resultSet.getInt(1));
                orders.setCustomerId(resultSet.getInt(2));
                orders.setOrderDate(resultSet.getDate(3));
                orders.setTotalAmount(resultSet.getBigDecimal(4));
                orders.setPaymentStatus(resultSet.getString(5));
                orders.setShippingAddress(resultSet.getString(6));
                ordersList.add(orders);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    /**
     * 根据订单ID查询
     * @param orderId
     * @return
     */
    public Orders getByOrderId(String orderId){
        String sql = "select * from orders where order_id = ?";
        Orders orders = new Orders();
        try {
            ResultSet resultSet = DBUtils.executeQuery(sql, orderId);
            while (resultSet.next()){
                orders.setOrderId(resultSet.getInt(1));
                orders.setCustomerId(resultSet.getInt(2));
                orders.setOrderDate(resultSet.getDate(3));
                orders.setTotalAmount(resultSet.getBigDecimal(4));
                orders.setPaymentStatus(resultSet.getString(5));
                orders.setShippingAddress(resultSet.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    /**
     * 查询用户订单
     * @param customerId
     * @return
     */
    public List<Orders> getByCustomerId(String customerId){
        List<Orders> ordersList = new ArrayList<>();
        String sql = "select * from orders where customer_id = ?";
        try (ResultSet resultSet = DBUtils.executeQuery(sql, customerId);){
            while (resultSet.next()){
                Orders orders = new Orders();
                orders.setOrderId(resultSet.getInt(1));
                orders.setCustomerId(resultSet.getInt(2));
                orders.setOrderDate(resultSet.getDate(3));
                orders.setTotalAmount(resultSet.getBigDecimal(4));
                orders.setPaymentStatus(resultSet.getString(5));
                orders.setShippingAddress(resultSet.getString(6));
                ordersList.add(orders);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }
    /**
     * 新增
     * @param orders
     */
    public void insert(Orders orders){
        String sql = "insert into orders(customer_id,order_date,total_amount,payment_status,shipping_address) value" +
                "(?,?,?,?,?)";
        int i = DBUtils.executeUpdate(sql, orders.getCustomerId(),orders.getOrderDate(),orders.getTotalAmount(),orders.getPaymentStatus(),orders.getShippingAddress());
        if (i>0){
            System.out.println(EnumTest.SUCCESS);
            return;
        }
        System.out.println(EnumTest.FAIL);
    }

    /**
     * 修改金额，支付状态，地址
     * @param orders
     */
    public void update(Orders orders){
        String sql = "update orders set total_amount = ? ,  payment_status = ? , shipping_address = ? where order_id = ?";
        int i = DBUtils.executeUpdate(sql, orders.getTotalAmount(), orders.getPaymentStatus(),
                orders.getShippingAddress(), orders.getOrderId());
        if (i>0){
            System.out.println(EnumTest.SUCCESS);
            return;
        }
        System.out.println(EnumTest.FAIL);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Integer id){
        String sql = "delete from orders where order_id = ?";
        int i = DBUtils.executeUpdate(sql, id);
        if (i>0){
            System.out.println(EnumTest.SUCCESS);
            return;
        }
        System.out.println(EnumTest.FAIL);
    }
}
