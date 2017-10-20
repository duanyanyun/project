package com.essay.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by Loki on 2017年10月16日.
 */

public class AlertController {

    private  AlertDialog mDialog;
    private Window mWindow;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog=dialog;
        this.mWindow=window;
    }

    public AlertDialog getDialog(){
        return this.mDialog;
    }

    public Window getWindow(){
        return this.mWindow;
    }

    public static class AlertParams{

        public Context mContext;
        public int mThemeResId;
        //点击空白是否可以取消
        public boolean mCancelable=true;
        //Dialog Cance监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //Dialog Dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //Dialog Key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局的layout Id
        public int mViewLayoutResId=0;
        //存放字体的修改
        public SparseArray<CharSequence> mTextArray=new SparseArray<>();
        //存放控件的事件
        public SparseArray<WeakReference<View.OnClickListener>> mClickArray=new SparseArray<>();
        //默认宽度
        public int mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;
        //显示位置
        public int mGravity= Gravity.CENTER;
        //动画
        public int mAnimations=0;
        //默认高度
        public int mHeight=ViewGroup.LayoutParams.WRAP_CONTENT;


        public AlertParams(Context context, int themeResId) {
            this.mContext=context;
            this.mThemeResId=themeResId;
        }

        //绑定和设置参数
        public void apply(AlertController mAlert) {

            DialogViewHelper viewHelper=null;
            //1.设置布局
            if(mViewLayoutResId!=0){
                viewHelper=new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if (mView!=null){
                viewHelper=new DialogViewHelper();
                viewHelper.setContentView(mView);
            }
            if(viewHelper==null){
                throw new IllegalArgumentException("");
            }
            //给Dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //2.设置文本
            int textCount=  mTextArray.size();
            for (int i=0;i<textCount;i++){
                viewHelper.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }
            //3.设置事件
            int clickCount=  mClickArray.size();
            for (int i=0;i<clickCount;i++){
                viewHelper.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            //4.显示方式
            Window window=  mAlert.getWindow();
            window.setGravity(mGravity);
            //设置动画
            if(mAnimations!=0){
                window.setWindowAnimations(mAnimations);
            }
            //设置宽高
            WindowManager.LayoutParams  params=window.getAttributes();
            params.width=mWidth;
            params.height=mHeight;
            window.setAttributes(params);


        }


    }

}
