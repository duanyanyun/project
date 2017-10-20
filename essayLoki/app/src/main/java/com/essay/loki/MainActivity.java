package com.essay.loki;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.essay.baselibrary.ioc.CheckNet;
import com.essay.baselibrary.ioc.OnClick;
import com.essay.baselibrary.ioc.ViewById;
import com.essay.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.textTv)
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        ViewGroup contentView = (ViewGroup)this.findViewById(android.R.id.content);
        View statusBarView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,30);
        statusBarView.setBackgroundColor(Color.RED);
        contentView.addView(statusBarView, lp);

        
        //mTextView.setText("TTTTTT");
        fixDexBug();
    }

    private void fixDexBug() {
    }

    @OnClick(R.id.textTv)
    @CheckNet
    private void textClick(View view){

    }

}
