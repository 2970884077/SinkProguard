package com.Sink.ProGuard.utils;
import android.content.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.Sink.ProGuard.*;
import java.io.*;
import android.os.*;
import android.support.v7.app.AlertDialog.*;

public class ProguardThread extends Thread
{
	private Context context;
	private String in,rule,plugin;
	private PrintStream err, out;
	private AlertDialog b;

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
			}else{
				if(b!=null){
					System.setErr(err);
					System.setOut(out);
					b.dismiss();
					Toast.makeText(context,"混淆完成！",3000).show();
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
				GuardUtils.startProGuard(new String[]{
											 "-libraryjars",Constant.MAIN_PATH+"android.jar",
											 "-include",rule,
											 "-injars",Constant.MAIN_PATH+"temp.jar",
											 "-outjars",Constant.MAIN_PATH+"my.jar"});
				new File(Constant.MAIN_PATH+"temp.jar").delete();
				GuardUtils.startJar2Dex(new File(Constant.MAIN_PATH+"my.jar"));
				new File(Constant.MAIN_PATH+"my.jar").delete();
				new File(Constant.MAIN_PATH+"sink.dex").delete();
			    mHandler.sendEmptyMessage(1);
			} 

		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void setLog(TextView tv,ScrollView sl){
		err=System.err;
		out=System.err;
		LogUtils log = new LogUtils(tv, sl);
        PrintStream ps = new PrintStream(log);
        System.setErr(ps);
        System.setOut(ps);
		System.out.println("欢迎使用Sink ProGuard.....");
		
	}
	
	public void showDialog(Context c){
		View view=LayoutInflater.from(c).inflate(R.layout.dialog,null);
		TextView tv=view.findViewById(R.id.textView);
		ScrollView sl=view.findViewById(R.id.list);
		AlertDialog b= new AlertDialog.Builder(c)
			.setTitle("正在混淆....")
			.setView(view)
			.setCancelable(false)
			.setNegativeButton("隐藏",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1,int p2)
				{
					System.setErr(err);
					System.setOut(out);
					p1.dismiss();
				}
			}).create();
		//设置LOG
	   setLog(tv,sl);
	   b.show();
	}
}
