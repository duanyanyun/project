package com.essay.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by Loki on 2017年9月22日.
 *findById辅助类
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;


    public ViewFinder(Activity activity){
        this.mActivity=activity;
    }

    public ViewFinder(View view){
        this.mView=view;
    }

    public View  findViewById(int viewId){
        return  mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }





}
