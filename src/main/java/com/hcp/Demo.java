package com.hcp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    /**
     * 查询全部
     * @return
     */
    public static List<Customers> getList() {
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
        DBUtils.closeConnection();
        return customers;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public static Customers getById(String id){

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
        DBUtils.closeConnection();
        return customers;
    }

    /**
     * 新增
     * @param customers
     */
    public static void insert(Customers customers){
        if (isExist(customers.getEmail())){

        }
        int i = DBUtils.executeUpdate("insert into Customers (customer_name,email,phone_number,address,date_registered) value(?,?,?,?,?)",
                customers.getCustomerName(),customers.getEmail(),customers.getPhoneNumber(),customers.getAddress(),customers.getDate());
        if (i<0){
            System.out.println("添加失败");
        }else{
            System.out.println("添加成功");
        }
        DBUtils.closeConnection();
    }

    /**
     * 删除
     * @param id
     */
    public static void delete(String id){
        Customers byId = getById(id);
        if (byId!=null){
            int i = DBUtils.executeUpdate("delete from Customers where customer_id = ?", id);
            if (i>0){
                System.out.println("删除成功");
                return;
            }
        }
        DBUtils.closeConnection();
        System.out.println("数据不存在");
    }
    /**
     * 判断邮箱是否创建
     * @param email
     * @return
     */
    public static boolean isExist(String email){

        try {
            ResultSet resultSet = DBUtils.executeQuery("select * from Customers where email = ?", email);
            if (resultSet.next()){
                System.out.println("邮箱被占用");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.closeConnection();
        return true;
    }

    /**
     * 修改
     * @param customers
     */
    public static void update(Customers customers){
        if(getById(customers.getCustomerId()) == null){
            System.out.println("需要修改的数据不存在");
            return;
        }
        if (isExist(customers.getEmail())){
            int rowsAffected  = DBUtils.executeUpdate("update Customers set customer_name = ?,email = ?,phone_number = ?,address = ? where customer_id = ?",
                    customers.getCustomerName(), customers.getEmail(), customers.getPhoneNumber(), customers.getAddress(), customers.getCustomerId());
            if (rowsAffected >0){
                System.out.println("修改成功");
                Customers customerInfo = getById(customers.getCustomerId());
                System.out.println("customerInfo = " + customerInfo);
                return;
            }
        }
        DBUtils.closeConnection();
        System.out.println("修改失败,邮箱已存在");

    }

    public static void send(){
        EmailUtils.sendEmail("2273465764@qq.com","主题","内容");
    }

    public static void main(String[] args) {
        List<Customers> list = getList();

        for (Customers customers : list) {
            System.out.println("customers = " + customers);
        }
        Customers byId = getById("1");
        System.out.println("byId = " + byId);

        if (isExist("2582721513@qq.com")){
            Customers customers = new Customers(null,"Breg","2582721513@qq.com","12321312","中国",Date.valueOf("2023-03-01"));
            insert(customers);
        }

        delete("3");
        Customers customers = new Customers("3","何C","258@qq.com","15221864941","中国",null);
        update(customers);

        send();
    }
}
