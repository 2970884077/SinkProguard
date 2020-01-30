package com.Sink.ProGuard.utils;
import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.Sink.ProGuard.*;
import java.io.*;
import java.util.*;

public class ProguardThread extends Thread
{
	private Context context;
	private String in,rule,plugin;
	
	private AlertDialog b;

	private TextView tv;

	public ProguardThread(Context context, String in, String rule, String plugin)
	{
		this.context = context;
		this.in = in;
		this.rule = rule;
		this.plugin = plugin;
	}
	Handler mHandler=new Handler(Looper.getMainLooper()){

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if(msg.what==0){
				showDialog(context);
				tv.setText("欢迎使用SinkProguard(混淆过程缓慢请耐心等待).....");
				tv.append("\n正在进行Dex2Jar...");
			}else if(msg.what==1){
				tv.append("\nDex2Jar完毕");
				tv.append("\n正在进行混淆...");
			}else if(msg.what==2){
				tv.append("\n混淆完毕");
				tv.append("\n正在进行Jar2Dex...");
			}else if(msg.what==-1){
				tv.append(((Exception) msg.obj).toString());
			}
			else{ 
				tv.append("\n 混淆结束....");
				Toast.makeText(context,"混淆完成！",3000).show();
				if(b!=null && b.isShowing()){
					b.dismiss();
					
				}
			}
			
			
		}
		
	};
	@Override
	public void run()
	{
	   
		super.run();
	//显示Dilaog
	  mHandler.sendEmptyMessage(0);
		IOUtils.copyFile(Constant.PLUGIN_PATH+plugin,Constant.MAIN_PATH+"sink.dex");
		File f=new File(in);
		try {
			if(f.exists()){
				GuardUtils.startDex2Jar(f);
				mHandler.sendEmptyMessage(1);
				ZipUtils.ZipFolder(Constant.MAIN_PATH+"temp/",Constant.MAIN_PATH+"temp.jar");
				GuardUtils.startProGuard(new String[]{
											 "-libraryjars",Constant.MAIN_PATH+"android.jar",
											 "-ignorewarnings",
											 "-verbose",
											 "-include",rule,
											 "-injars",Constant.MAIN_PATH+"temp.jar",
											 "-outjars",Constant.MAIN_PATH+"my.jar"});
				new File(Constant.MAIN_PATH+"temp.jar").delete();
				Runtime.getRuntime().exec("rm -rf "+Constant.MAIN_PATH+"temp");
				mHandler.sendEmptyMessage(2);
			
				GuardUtils.startJar2Dex(new File(Constant.MAIN_PATH+"my.jar"));
				new File(Constant.MAIN_PATH+"my.jar").delete();
				new File(Constant.MAIN_PATH+"sink.dex").delete();
			    mHandler.sendEmptyMessage(3);
			} 

		}catch(Exception e) {
			mHandler.obtainMessage(-1,e).sendToTarget();
			e.printStackTrace();
			
		}
	}

	
	public void showDialog(Context c){
		View view=LayoutInflater.from(c).inflate(R.layout.dialog,null);
	    tv=view.findViewById(R.id.textView);
		AlertDialog b= new AlertDialog.Builder(c)
			.setTitle("正在混淆....")
			.setView(view)
			.setCancelable(false)
			.setNegativeButton("隐藏",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1,int p2)
				{
					p1.dismiss();
				}
			}).create();
	
	 
	   b.show();
	}
}
