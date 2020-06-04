package com.ning.service.vo;

/**
 * @author shenjiang
 * @Description: 返回封装类
 * @Date: 2019/6/10 11:09
 */
public class ResData {
    public Integer code;
    public Object data;

    public ResData() {
    }

    public ResData(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String message;
}
