package com.wrvpark.apiserver.exception;

/**
 * @author Isabel Ke
 * Defines a exception for no items found
 */
public class ItemNotFoundException extends RuntimeException{
    //exception message
    private String message;

    public ItemNotFoundException()
    {
        super();
    }


    public ItemNotFoundException(String message)
    {
        super(message);
    }
}
