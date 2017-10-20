package com.essay.baselibrary.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Loki on 2017年9月22日.
 */

public class ViewUtils {


    public static  void inject(Activity activity){
        inject(new ViewFinder(activity),activity);
    }

    public static  void inject(View view){
        inject(new ViewFinder(view),view);
    }

    public static  void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }

    private  static  void  inject(ViewFinder finder,Object object){
        injectFiled(finder,object);
        injectEvent(finder,object);
    }

    //注入属性
    private static void injectFiled(ViewFinder finder, Object object) {
        //1获取类里所有的属性
        Class<?> clazz= object.getClass();
        //获取属性集合
        Field[] fields= clazz.getDeclaredFields();
        for (Field mField:fields){
          ViewById viewByid=  mField.getAnnotation(ViewById.class);
            if(viewByid!=null){
                //2获取ViewById里的所有值
                int viewId= viewByid.value();
                //3fendByid找到View
                View view= finder.findViewById(viewId);
                try {
                    //能注入所有属性的修饰符
                    mField.setAccessible(true);
                    //4动态的注入找到的View
                    mField.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //事件注入
    private static void injectEvent(ViewFinder finder, Object object) {
        //1获取内的所有方法
        Class<?> clazz= object.getClass();
        Method[]  methods= clazz.getDeclaredMethods();
        for(Method method:methods){
            OnClick onclick= method.getAnnotation(OnClick.class);
            boolean isCheckNet= method.getAnnotation(CheckNet.class)==null;
            if(onclick!=null){
              //2获取onClick里面的value
              int[] viewIds=  onclick.value();
                for (int viewId:viewIds){
                   View view= finder.findViewById(viewId);
                   view.setOnClickListener(new DeclaredOnClickListener(method,object,isCheckNet));

                }
            }
        }


    }


private static class  DeclaredOnClickListener implements View.OnClickListener {

    private Object mObject;
    private Method mMethod;
    private boolean mIsCheckNet;
    public DeclaredOnClickListener(Method method,Object object,boolean isCheckNet){
        this.mMethod=method;
        this.mObject=object;
        this.mIsCheckNet=isCheckNet;
    }

    @Override
    public void onClick(View v) {
        if(!mIsCheckNet){
            if(!HttpFinder.networkAvailable(v.getContext())){
                //执行无网络方法
                Toast.makeText(v.getContext(),"执行无网络方法",Toast.LENGTH_LONG).show();
                return;
            }
        }
        try {
            mMethod.setAccessible(true);
            mMethod.invoke(mObject,v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




}
