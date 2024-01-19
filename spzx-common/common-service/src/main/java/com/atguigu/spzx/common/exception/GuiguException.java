package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @ClassName GuiguException
 * @Description
 * @Author Richard
 * @Date 2023-12-08 23:10
 **/
@Data
public class GuiguException extends RuntimeException{

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public GuiguException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public GuiguException(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
