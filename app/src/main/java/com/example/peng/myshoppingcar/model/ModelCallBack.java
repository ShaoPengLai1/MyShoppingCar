package com.example.peng.myshoppingcar.model;

import com.example.peng.myshoppingcar.bean.CartBean;

public interface ModelCallBack {
    public void success(CartBean cartBean);
    public void failure(Exception e);
}
