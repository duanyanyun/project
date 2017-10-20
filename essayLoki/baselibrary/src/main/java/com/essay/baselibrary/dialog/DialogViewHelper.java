package com.essay.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Loki on 2017年10月16日.
 */

class DialogViewHelper {

    private View mContentView=null;
    private SparseArray<WeakReference<View>> mViews;


    public DialogViewHelper(Context mContext, int mViewLayoutResId) {
        this();
        this.mContentView= LayoutInflater.from(mContext).inflate(mViewLayoutResId,null);

    }

    public DialogViewHelper() {
        mViews=new SparseArray<>();
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    //设置文本
    public void setText(int viewId, CharSequence charSequence) {
        TextView textView=getView(viewId);

        if(textView!=null){
            textView.setText(charSequence);
        }
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference=mViews.get(viewId);
        View view=null;
        if(viewWeakReference!=null) {
            view = viewWeakReference.get();
        }
        if(view==null){
            view=mContentView.findViewById(viewId);
            if(view!=null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T)view;
    }

    //设置点击事件
    public void setOnClickListener(int viewId, WeakReference<View.OnClickListener> clickListener) {
        View view=getView(viewId);
        if(view!=null){
            view.setOnClickListener(clickListener.get());
        }
    }


    public View getContentView() {
        return this.mContentView;
    }
}
