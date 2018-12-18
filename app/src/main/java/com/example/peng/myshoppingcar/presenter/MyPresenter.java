package com.example.peng.myshoppingcar.presenter;

import com.example.peng.myshoppingcar.bean.CartBean;
import com.example.peng.myshoppingcar.model.ModelCallBack;
import com.example.peng.myshoppingcar.model.MyModel;
import com.example.peng.myshoppingcar.view.ViewCallBack;

public class MyPresenter {
    MyModel myModel = new MyModel();
    ViewCallBack viewCallBack;
    public MyPresenter(ViewCallBack viewCallBack) {
        this.viewCallBack = viewCallBack;
    }
    //调用model 层的请求数据
    public void getData(){
        myModel.getData(new ModelCallBack() {
            @Override
            public void success(CartBean cartBean) {
                if(viewCallBack!=null) {
                    viewCallBack.success(cartBean);
                }
            }
            @Override
            public void failure(Exception e) {
                if(viewCallBack!=null) {
                    viewCallBack.failure(e);
                }
            }
        });
    }
    public void detach(){
        viewCallBack=null;
    }
}
