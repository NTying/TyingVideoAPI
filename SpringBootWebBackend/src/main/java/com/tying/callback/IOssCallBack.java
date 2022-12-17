package com.tying.callback;

/**
 * @author Tying
 * @version 1.0
 */
public interface IOssCallBack {

    void onSuccess(String result);
    void onFailure(Exception e);
}
