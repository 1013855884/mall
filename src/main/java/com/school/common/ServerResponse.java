package com.school.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 高复用返回对象
 */
//在有null的字段时，去掉该字段
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//课程注释：保证序列化Json的时候，如果是null的对象，key也会消失
public class ServerResponse<T> implements Serializable {
    private Integer status;
    private String message;
    private T data;
    private ServerResponse(Integer status){
        this.status = status;
    }
    private ServerResponse(Integer status, T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponse(Integer status,String message){
        this.status = status;
        this.message = message;
    }
    private ServerResponse(Integer status,String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    //不显示在json中
    @JsonIgnore
    //课程注释：使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getStatus();//0=0
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    //正确
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getStatus());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String message){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getStatus(),message);
    }
    public static <T> ServerResponse<T> createBySuccessMessage(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getStatus(),data);
    }
    public static <T>ServerResponse<T> createBySuccessMessage(String message,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getStatus(),message,data);
    }
    //错误
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getStatus(),ResponseCode.ERROR.getMessage());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getStatus(),errorMessage);
    }
    public static <T> ServerResponse<T> createByErrorStatusMessage(Integer errorStatus,String errorMessage){
        return new ServerResponse<T>(errorStatus,errorMessage);
    }
}
