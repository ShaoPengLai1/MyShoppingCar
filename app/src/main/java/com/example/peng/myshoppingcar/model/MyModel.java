package com.example.peng.myshoppingcar.model;

import com.example.peng.myshoppingcar.bean.CartBean;
import com.example.peng.myshoppingcar.okhttp.AbstractUiCallBack;
import com.example.peng.myshoppingcar.okhttp.OkHttpUtil;

public class MyModel {
    public void getData(final ModelCallBack modelCallBack){
        //访问接口
        String path = "http://120.27.23.105/product/getCarts?uid=100";
        OkHttpUtil.getInstance().getAsynchronization(path, CartBean.class, new AbstractUiCallBack<CartBean>() {
            @Override
            public void success(CartBean cartBean) {
                modelCallBack.success(cartBean);
            }

            @Override
            public void fail(Exception e) {
                modelCallBack.failure(e);
            }
        });

    }
}
