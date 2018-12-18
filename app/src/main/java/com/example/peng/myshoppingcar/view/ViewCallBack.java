package com.example.peng.myshoppingcar.view;

import com.example.peng.myshoppingcar.bean.CartBean;

public interface ViewCallBack {
    public void success(CartBean cartBean);
    public void failure(Exception e);
}
