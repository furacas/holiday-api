package com.furacas.api.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private static final int SUCCESS_CODE = 0;
    private static final int ERROR_CODE = -1;
    private static Response<String> success = new Response<>(SUCCESS_CODE,"success");

    private static Response<String> fail = new Response<>(ERROR_CODE,"fail");



    private Integer code;

    private T data;

    public static Response<String> ok(){
        return success;
    }

    public static <T> Response<T> ok(T data){
        Response<T> response = new Response<T>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static Response<String> error(){
        return fail;
    }

    public static Response<String> error(String message){
        return new Response<>(ERROR_CODE,message);
    }
}