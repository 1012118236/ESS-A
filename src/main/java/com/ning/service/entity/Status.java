package com.ning.service.entity;

/**
 * @author shenjiang
 * @Description: 系统状态表
 * @Date: 2019/12/13 14:40
 */
public  class Status {
    public static final Integer _200 = 200;//正常
    public static final Integer _500 = 500;//账号异常
    public static final Integer _501 = 501;//当前用户已在其它设备登陆
    public static final Integer _502 = 502;
    public static final Integer _599 = 599;//token失效或无权限
}
