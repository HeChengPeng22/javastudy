package com.hcp.dao;

import com.hcp.pojo.Customers;
import com.hcp.utils.DBUtils;
import com.hcp.utils.EnumTest;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDao {
    /**
     * 查询全部
     * @return
     */
    public List<Customers> getList() {
        List<Customers> customers = new ArrayList<>();
        try {
            ResultSet resultSet = DBUtils.executeQuery("select * from customers");
            Customers customers1 = null;
            while (resultSet.next()){
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date_registered");
                customers1 = new Customers(customerId,customerName,email,phoneNumber,address,date);
                customers.add(customers1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public List<Customers> getList(Integer pageNumber,Integer pageSize){
        List<Customers> customers = new ArrayList<>();
        int startIndex = (pageNumber - 1) * pageSize;
        try {
            ResultSet resultSet = DBUtils.executeQuery("select * from customers limit ?,?", startIndex, pageSize);
            while (resultSet.next()){
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date_registered");
                Customers customers1 = new Customers(customerId,customerName,email,phoneNumber,address,date);
                customers.add(customers1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public List<Customers> getList(Integer pageNum,Integer pageSize,String customerName){
        List<Customers> customers = new ArrayList<>();
        Integer startIndex = (pageNum-1)*pageSize;
        String sql = "select * from customers where 1=1";
        if (customerName != null){
            sql += " and  customer_name like '%"+customerName+"%'";
        }
        sql += " limit ?,?";
        try {
            ResultSet resultSet = DBUtils.executeQuery(sql, startIndex, pageSize);
            while (resultSet.next()){
                String customerId = resultSet.getString("customer_id");
                String customerNames = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date_registered");
                Customers customers1 = new Customers(customerId,customerNames,email,phoneNumber,address,date);
                customers.add(customers1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Customers getById(String id){

        Customers customers = null;
        try {
            ResultSet resultSet = DBUtils.executeQuery("select * from customers where customer_id = ?", id);
            while (resultSet.next()){
                String customerId = resultSet.getString("customer_id");
                String customerName = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");
                Date date = resultSet.getDate("date_registered");

                customers = new Customers(customerId,customerName,email,phoneNumber,address,date);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    /**
     * 新增
     * @param customers
     */
    public void insert(Customers customers){
        if (!isExist(customers.getEmail())){
            return;
        }
        int i = DBUtils.executeUpdate("insert into Customers (customer_name,email,phone_number,address,date_registered) value(?,?,?,?,?)",
                customers.getCustomerName(),customers.getEmail(),customers.getPhoneNumber(),customers.getAddress(),customers.getDate());
        if (i<0){
            System.out.println("添加失败");
        }else{
            System.out.println("添加成功");
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        Customers byId = getById(id);
        if (byId!=null){
            int i = DBUtils.executeUpdate("delete from Customers where customer_id = ?", id);
            if (i>0){
                System.out.println("删除成功");
                return;
            }
        }
        System.out.println("数据不存在");
    }
    /**
     * 判断邮箱是否创建
     * @param email
     * @return
     */
    public boolean isExist(String email){

        try {
            ResultSet resultSet = DBUtils.executeQuery("select * from Customers where email = ?", email);
            if (resultSet.next()){
                System.out.println(EnumTest.EMAIL_EXIST);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 修改
     * @param customers
     */
    public void update(Customers customers){
        if(getById(customers.getCustomerId()) == null){
            System.out.println("需要修改的数据不存在");
            System.out.println(EnumTest.FAIL);
            return;
        }
        if (isExist(customers.getEmail())){
            int rowsAffected  = DBUtils.executeUpdate("update Customers set customer_name = ?,email = ?,phone_number = ?,address = ? where customer_id = ?",
                    customers.getCustomerName(), customers.getEmail(), customers.getPhoneNumber(), customers.getAddress(), customers.getCustomerId());
            if (rowsAffected >0){
                System.out.println(EnumTest.SUCCESS);
                return;
            }
        }
        System.out.println(EnumTest.EMAIL_EXIST);

    }
}
