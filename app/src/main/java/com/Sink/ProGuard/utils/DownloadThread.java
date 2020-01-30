package com.Sink.ProGuard.utils;

import android.content.*;
import android.app.*;
import android.os.*;
import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import android.widget.*;

public class DownloadThread extends Thread
{
    Context context;
    String url;
	String path;
    boolean isShowDialog;
	ProgressDialog dialog;
	
	public DownloadThread(Context context,String url,String path,boolean isShowDialog)
	{
		this.context=context;
		this.url=url;
		this.path=path;
		this.isShowDialog=isShowDialog;
		if(isShowDialog){
			dialog=new ProgressDialog(context);
			dialog.setTitle("下载中");
			dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
			dialog.setMax(100);
			dialog.setMessage("正在下载.....");
		}
	}
	
	Handler mHandler=new Handler(Looper.getMainLooper()){

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch(msg.what){
			   case 0:
				   dialog.show();
				  break;
			 
				 
			  case -1:
				  Exception e=  (Exception) msg.obj;
				  Toast.makeText(context,"下载失败:"+e,3000).show();
				  dialog.dismiss();
				  break;
			  case 2:
				   dialog.dismiss();
				   Toast.makeText(context,"下载完成",3000).show();
				  break;
			}
			
		}
		
	};
	@Override
	public void run()
	{
		super.run();
		try
		{
			mHandler.sendEmptyMessage(0);
			HttpsURLConnection con=(HttpsURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
		    con.setReadTimeout(8000);
			con.setConnectTimeout(8000);
			
			InputStream is= con.getInputStream();
			FileOutputStream os=new FileOutputStream(path);
			byte[] bs=new byte[1024];
			int i=0;
			while((i=is.read(bs))>0){
				os.write(bs,0,i);
				os.flush();
			}
		 
			is.close();
			os.close();
			mHandler.sendEmptyMessage(2);
			
		}
		catch(Exception e) {
			Message mssage=mHandler.obtainMessage();
			mssage.what=-1;
			mssage.obj=e;
			mHandler.sendMessage(mssage);
		}

		
	}
	
}
