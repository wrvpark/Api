package com.wrvpark.apiserver.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:customizes the return data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    //mark if the request is succeed or failed
    private String status;
    //returned message when the request fails
    private String message;
    //return data
    private T data;


    /**
     * success with data
     * @param data
     * @param <E>
     * @return data and the request status
     */
    public static <E> ResultEntity<E> successWithData(E data,String message) {

        return new ResultEntity<E>(SUCCESS, message, data);
    }

    public static <E> ResultEntity<E> successWithOutData(String message) {

        return new ResultEntity<E>(SUCCESS, message, null);
    }
    /**
     * failed with no data
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> failed(String message) {

        return new ResultEntity<E>(FAILED, message, null);
    }

}
