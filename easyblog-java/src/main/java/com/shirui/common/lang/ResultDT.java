package com.shirui.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDT<T> implements Serializable {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 附加数据
     */
    private T addData;

    public ResultDT() {
    }
}