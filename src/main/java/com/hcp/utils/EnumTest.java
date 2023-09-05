package com.hcp.utils;

/**
 * 定义枚举类循序
 * 1.定义枚举
 */
public enum EnumTest {
    //定义枚举类循序
    //1.定义枚举
    SUCCESS("成功","成功","200"),
    FAIL("失败","失败","400"),

    EMAIL_FAIL("邮件发送失败","失败","200"),

    EMAIL_EXIST("邮箱已存在","失败","200");
    //2.定义成员变量
    private final String msg;

    private final String status;

    private final String code;

    //3.定义枚举的构造函数
    EnumTest(String msg,String status,String code){
        this.msg = msg;
        this.status = status;
        this.code = code;
    }

    @Override
    public String toString() {
        return "EnumTest{" +
                "msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
