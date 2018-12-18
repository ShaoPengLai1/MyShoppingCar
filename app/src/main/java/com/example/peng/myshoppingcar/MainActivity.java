package com.example.peng.myshoppingcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.myshoppingcar.adapter.RecyAdapter;
import com.example.peng.myshoppingcar.bean.CartBean;
import com.example.peng.myshoppingcar.presenter.MyPresenter;
import com.example.peng.myshoppingcar.view.ViewCallBack;

public class MainActivity extends AppCompatActivity implements ViewCallBack {

    private RecyclerView recyclerView;
    private TextView total_price;
    private TextView total_num;
    private CheckBox quanxuan;
    private MyPresenter myPresenter;
    private RecyAdapter recyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView=findViewById(R.id.recycler_view);
        total_price=findViewById(R.id.total_price);
        total_num=findViewById(R.id.total_num);
        quanxuan=findViewById(R.id.quanxuan);
        //1为不选中
        quanxuan.setTag(1);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyAdapter=new RecyAdapter(this);
        //myPresenter=new IPresenterImpl(this);
        myPresenter=new MyPresenter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyAdapter);
        myPresenter.getData();
        //myPresenter.requestDataGet(Apis.URL_PRODUCTS_POST,null,CartBean.class);
        //调用recyAdapter里面的接口,设置 全选按钮 总价 总数量
        recyAdapter.setUpdateListener(new RecyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                //设置ui的改变
                total_num.setText("共"+num+"件商品");//总数量
                total_price.setText("总价 :¥"+total+"元");//总价
                if (allCheck){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用adapter里面的方法 ,,把当前quanxuan状态传递过去
                int tag = (int) quanxuan.getTag();
                if(tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else{
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                recyAdapter.quanXuan(quanxuan.isChecked());

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.detach();
    }

    @Override
    public void success(CartBean cartBean) {
        recyAdapter.add(cartBean);
    }

    @Override
    public void failure(Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
