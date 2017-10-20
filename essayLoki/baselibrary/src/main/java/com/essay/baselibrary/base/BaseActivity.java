package com.essay.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Loki on 2017年10月10日.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局layout
        setContentView();
        //初始化界面
        initView();
        //初始化数据
        initDate();
    }

    protected abstract void initDate();

    protected abstract void initView();

    protected abstract void setContentView();

    protected void startActivity(Class<?> clas){
        Intent intent=new Intent(this,clas);
        startActivity(intent);
    }

}
