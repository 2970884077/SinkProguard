package com.Sink.ProGuard.activities;

import android.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.Sink.ProGuard.*;
import com.Sink.ProGuard.adapter.*;
import com.Sink.ProGuard.fragment.*;
import com.kekstudio.dachshundtablayout.*;
import java.io.*;
import java.util.*;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import com.Sink.ProGuard.R;
import com.Sink.ProGuard.utils.*;

public class MainActivity extends AppCompatActivity 
{
	ProgressDialog progressDialog;
	private ViewPager viewPager;
	private DachshundTabLayout tabLayout;
	private ArrayList<Fragment> fragmentList;
	private MyFragmentAdapter myFragmentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		requestAllPower();		
		//检测配置文件
		if(new File(Constant.MAIN_PATH+"a.txt").isFile()){
			new File(Constant.MAIN_PATH+"a.txt").delete();
		}
		if(!new File(Constant.PLUGIN_PATH).isDirectory()){
			new File(Constant.PLUGIN_PATH).mkdirs();
		}
		if(!new File(Constant.MAIN_PATH+"android.jar").isFile()){
			try
			{
				IOUtils.copyFile(getResources().getAssets().open("android.jar"), Constant.MAIN_PATH + "android.jar");
			}
			catch (IOException e)
			{}
		}
		if(new File(Constant.MAIN_PATH+"temp").isFile()){
			try
			{
				Runtime.getRuntime().exec("rm -rf " + Constant.MAIN_PATH + "temp");
			}
			catch (IOException e)
			{}
		}
		Toast.makeText(this,"配置文件正常.....",3000).show();
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		viewPager=(ViewPager) findViewById(R.id.view_pager);
		tabLayout=(DachshundTabLayout) findViewById(R.id.tab_layout);
		tabLayout.setupWithViewPager(viewPager);		
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(new Shop_Fragment());
		fragmentList.add(new Main_Fragment());
		fragmentList.add(new About_Fragment());
		myFragmentAdapter=new MyFragmentAdapter(
			getSupportFragmentManager(),
			fragmentList);
		viewPager.setAdapter(myFragmentAdapter);

	}

	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode,resultCode,data);
		myFragmentAdapter.getItem(1).onActivityResult(requestCode,resultCode,data);
		
	}
	
	
	@Override
	public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults)
	{
		switch(requestCode)
		{
			case 1:
				if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    
				}else{
					Toast.makeText(this,"哦豁，权限被拒绝了哦~",Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			default:
		}
	}
	
	public void requestAllPower(){
		if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
			if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			}else{
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);		
			}
		}
	}
}

