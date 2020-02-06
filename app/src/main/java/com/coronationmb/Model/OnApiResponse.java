package com.coronationmb.Model;

/**
 * Created by UEke on 2/21/2019.
 */

public interface OnApiResponse<T> {

    void onSuccess(T data);
    void onFailed(String message);

}
