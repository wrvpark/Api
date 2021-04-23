package com.wrvpark.apiserver.configuration;


import com.wrvpark.apiserver.exception.ItemNotFoundException;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description: Handles the specified exceptions
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    //catch all exceptions when no specific exception has been catched
    @ExceptionHandler(Exception.class)
    //return data
    @ResponseBody
    public ResultEntity error(Exception e)
    {
        e.printStackTrace();
        return ResultEntity.failed(e.getMessage());
    }

    //catch NoSuchElementException when no data found for the id
    @ExceptionHandler(NoSuchElementException.class)
    //return data
    @ResponseBody
    public ResultEntity error(NoSuchElementException e)
    {
        e.printStackTrace();
        return ResultEntity.failed(e.getMessage());
    }

    //catch NoSuchElementException when no data found for the id
    @ExceptionHandler(NullPointerException.class)
    //return data
    @ResponseBody
    public ResultEntity error(NullPointerException e)
    {
        e.printStackTrace();
        return ResultEntity.failed(e.getMessage());
    }

    //catch ItemNotFoundException when no data found for the id
    @ExceptionHandler(ItemNotFoundException.class)
    //return data
    @ResponseBody
    public ResultEntity error(ItemNotFoundException e)
    {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return ResultEntity.failed(e.getMessage());
    }

    //catch MissingServletRequestParameterException when no data found for the id
    @ExceptionHandler(MissingServletRequestParameterException.class)
    //return data
    @ResponseBody
    public ResultEntity error(MissingServletRequestParameterException e)
    {
        return ResultEntity.failed(e.getMessage());
    }
}
