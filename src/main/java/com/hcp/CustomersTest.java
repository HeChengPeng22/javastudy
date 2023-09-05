package com.hcp;

import com.hcp.dao.CustomersDao;
import com.hcp.pojo.Customers;
import com.hcp.utils.DBUtils;
import com.hcp.utils.EmailUtils;

import java.sql.Date;
import java.util.List;

public class CustomersTest {


    public static void send(){
        EmailUtils.sendEmail("2273465764@qq.com","主题","内容");
    }

    public static void main(String[] args) {
        CustomersDao customersDao = new CustomersDao();
        List<Customers> list = customersDao.getList(2,10);

        for (Customers customers : list) {
            System.out.println("customers = " + customers);
        }
        Customers byId = customersDao.getById("1");
        System.out.println("byId = " + byId);

        Customers customers = new Customers(null,"Breg","2582721513@qq.com","12321312","中国",Date.valueOf("2023-03-01"));
        customersDao.insert(customers);


        customersDao.delete("3");
        Customers customers1 = new Customers("3","何C","258@qq.com","15221864941","中国",null);
        customersDao.update(customers1);
        DBUtils.closeConnection();

        send();
    }
}
