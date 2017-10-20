package com.essay.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Loki on 2017年10月11日.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionCrashHandler mExceptionCrashHandler;

    public static ExceptionCrashHandler getInstance(){
        if(mExceptionCrashHandler==null){
            //同步锁（并发出现多个实例）
            synchronized (ExceptionCrashHandler.class){
                if(mExceptionCrashHandler==null) {
                    mExceptionCrashHandler = new ExceptionCrashHandler();
                }
            }
        }
        return mExceptionCrashHandler;
    }

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    private static final String TAG="ExceptionCrashHandler";

    public void init(Context context){
        this.mContext=context;
        //设置全局的异常为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        this.mDefaultExceptionHandler=Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常
        Log.e(TAG,"出现错误");
        //写入本地文件 (手机信息)
            //1,奔溃详细信息
            //2,应用信息 包名 版本信息
            //3,手机信息
        String crashFileName= saveInfoToSD(e);
        //缓存日志名称到本地
        casheCrashFile(crashFileName);
        //保存日志文件 等待应用再次启动将上传服务器

        //让系统默认处理异常（起到不拦截异常）
        mDefaultExceptionHandler.uncaughtException(t,e);

    }

    //缓存日志文件路径
    private void casheCrashFile(String fileName) {
        SharedPreferences sp=mContext.getSharedPreferences("crash",Context.MODE_PRIVATE);
        sp.edit().putString("crash_file_name",fileName).commit();
    }

    //获取应用环境信息
    private HashMap<String,String> obtainSimpleInfo(Context content){
        HashMap<String,String> map=new HashMap<String,String>();
        PackageManager mPackageManager=content.getPackageManager();
        PackageInfo mPackageInfo=null;
        try {
            mPackageInfo= mPackageManager.getPackageInfo(content.getPackageName(),PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName",mPackageInfo.versionName);
        map.put("versionCode",""+mPackageInfo.versionCode);
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT",Build.VERSION.SDK_INT+"");
        map.put("PRODUCT",Build.PRODUCT+"");
        map.put("Mobile_Info",getMobileInfo());

        return map;
    }

    //获取手机信息
    public static String getMobileInfo(){
        StringBuffer sb=new StringBuffer();
        //利用反射获取类的所有属性 Build（设备类）
        Field[] fields=Build.class.getDeclaredFields();
        try {
            for (Field field:fields){
                field.setAccessible(true);
                String name= field.getName();
                String value= field.get(null).toString();
                sb.append(name+"="+value);
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //获取异常错误消息
    public static String obtainExceptionInfo(Throwable ex){
        StringWriter stringWriter=new StringWriter();
        PrintWriter printWriter=new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    //将错误消息和手机信息应用信息写入本地
    private String saveInfoToSD(Throwable ex){

        String fileName=null;
        StringBuffer sb=new StringBuffer();
        //拼接手机版本信息和应用信息
        for (Map.Entry<String,String> entry:obtainSimpleInfo(mContext).entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        //拼接错误消息
        sb.append(obtainExceptionInfo(ex));
        //保存文件
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File dir=new File(mContext.getFilesDir()+File.separator+"crash"+File.separator);
            //如果目录存在 则删除目录下所有文件
            if(dir.exists()){
                deleteDir(dir);
            }
            //如果不存在就创建
            if(!dir.exists()){
                dir.mkdir();
            }
            //拼接文件路径
            fileName=dir.toString()+File.separator+getAssignTime("yyyy_MM_dd_HH_mm")+".txt";

            try {
                FileOutputStream fos=new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return fileName;
    }

    //获取时间
    private String getAssignTime(String dateFormatStr){
        DateFormat dateFormat=new SimpleDateFormat(dateFormatStr);
        return  dateFormat.format(System.currentTimeMillis());
    }

    //删除目录下所有文件
    private boolean deleteDir(File dir){
        if(dir.isDirectory()){
            File[] files=  dir.listFiles();
            for (File file: files){
                file.delete();
            }
        }
        return true;
    }



}
