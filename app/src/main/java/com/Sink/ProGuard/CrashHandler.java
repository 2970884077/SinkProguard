package com.Sink.ProGuard;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：sparkhuu
 * email:sparkhuu@gmail.com
 */
public class CrashHandler implements UncaughtExceptionHandler {
   
    private static final String FILE_NAME = "LOG:";

    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();

    private UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;

    private CrashHandler(){}

    public static CrashHandler getsInstance(){
        return sInstance;
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context;
    }

    /**
     * 当程序中有未捕获的异常，系统会自动调用此方法
     * @param thread 出现未捕获异常的线程
     * @param ex 未捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息导SD卡
            dumpExceptionToSDCard(ex);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ex.printStackTrace();
        if (null != mDefaultCrashHandler){
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
          
        }
        File dir = new File(Constant.MAIN_PATH+".error");
        if (!dir.exists()){
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(Constant.MAIN_PATH+".error/" + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println();
            dumpPhoneInfo(pw);
            ex.printStackTrace(pw);
            pw.close();
        }catch (Exception e) {

        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App version:");
        pw.print(pi.versionName);
        pw.print("-");
        pw.println(pi.versionCode);

        //Android版本号
        pw.print("OS version:");
        pw.print(Build.VERSION.RELEASE);
        pw.print("-");
        pw.println(Build.VERSION.SDK_INT);

        // 手机制造商
        pw.print("Vendor:");
        pw.println(Build.MANUFACTURER);

        // 手机型号
        pw.print("Model:");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI:");
        pw.println(Build.CPU_ABI);
    }

}


