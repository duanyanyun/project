package com.essay.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.essay.baselibrary.R;

import java.lang.ref.WeakReference;

/**
 * Created by Loki on 2017年10月16日.
 */

public class AlertDialog extends Dialog {

    private AlertController mAlert;

    public AlertDialog(Context context,int themeResId) {
        super(context, themeResId);
        mAlert=new AlertController(this,getWindow());
    }



    public static class Builder{

        public AlertController.AlertParams P;

        public Builder(Context context){
            this(context, R.style.dialog);
        }

        public Builder(Context context,int themeResId){
            P=new AlertController.AlertParams(context,themeResId);
        }


        public AlertDialog create() {
            final AlertDialog dialog=new AlertDialog(P.mContext,P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if(P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if(P.mOnKeyListener!=null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            AlertDialog dialog= create();
            dialog.show();

            return dialog;
        }

        public Builder setContentView(View view){
            P.mView=view;
            P.mViewLayoutResId=0;

            return this;
        }

        public Builder setContentView(int themeResId){
            P.mView=null;
            P.mViewLayoutResId=themeResId;

            return this;
        }


        public Builder setText(int viewId,CharSequence text){
            P.mTextArray.put(viewId,text);

            return this;
        }

        public Builder setOnClickListener(int viewId,View.OnClickListener clickListener){
            P.mClickArray.put(viewId,new WeakReference<>(clickListener));
            return this;
        }
        //设置全屏
        public Builder fullWidth(){
            P.mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;
            return this;
        }
        //从底部显示 是否带动画
        public Builder formButtom(boolean isAnimation){
            if(isAnimation){
                P.mAnimations=R.style.dialog_form_buttom_anim;
            }
            P.mGravity= Gravity.BOTTOM;

            return this;
        }
        //设置宽高
        public Builder setWidthAndHeight(int width,int height){
            P.mWidth=width;
            P.mHeight=height;
            return this;
        }
        //添加默认动画
        public  Builder addDefaulAnimation(){
            //P.mAnimations=
            return this;
        }
        //设置动画
        public  Builder setAnimation(int styleAnimations){
            P.mAnimations=styleAnimations;
            return this;
        }



    }


}
