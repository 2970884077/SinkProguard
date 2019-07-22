package com.Sink.ProGuard.fragment;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.Sink.ProGuard.*;
import com.Sink.ProGuard.utils.*;
import com.nbsp.materialfilepicker.*;
import com.nbsp.materialfilepicker.ui.*;
import java.io.*;
import java.util.regex.*;

import android.support.v4.app.Fragment;
import com.Sink.ProGuard.R;

public class Main_Fragment extends Fragment implements OnClickListener
{

	
	View mView;
	private EditText dex,rule,plug;
	private Button dex_btn,rule_btn,plug_btn,start;
	

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		mView = inflater.inflate(R.layout.main_main, container, false);
		dex = mView.findViewById(R.id.dex);
		rule =  mView.findViewById(R.id.rule);
		plug = mView.findViewById(R.id.plug);
		
		//初始化控件
		dex_btn =mView.findViewById(R.id.dex_btn);
		rule_btn =  mView.findViewById(R.id.rule_btn);
		plug_btn =  mView.findViewById(R.id.plug_btn);
		start=mView.findViewById(R.id.start);
		//设置监听事件
		dex_btn.setOnClickListener(this);
		rule_btn.setOnClickListener(this);
		plug_btn.setOnClickListener(this);
		start.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(plug.getText().toString().isEmpty()||rule.getText().toString().isEmpty()||dex.getText().toString().isEmpty()){
						Toast.makeText(getActivity(),"请输入完整信息",3000).show();
						return;
					}
					if(new File(Constant.MAIN_PATH+"sink.dex").isFile()){
						Toast.makeText(getActivity(),"已经在混淆了，请稍后再试",3000).show();
						return;
					}
				   //启动
					new ProguardThread(getActivity(),dex.getText().toString(),rule.getText().toString(),plug.getText().toString()).start();
				}
			});
		
		
		
		return mView;
	}
	

	
	@Override
	public void onClick(View p1)
	{
		MaterialFilePicker m= new MaterialFilePicker()
			.withActivity(getActivity())
			.withFilterDirectories(false) 
			.withHiddenFiles(true) ;
		switch(p1.getId()){
			case R.id.dex_btn:
				m.withRequestCode(1)
				 .withFilter(Pattern.compile(".*\\.dex"))
				 .start();
				break;
			case R.id.rule_btn:
				m.withRequestCode(2)
				 .withFilter(Pattern.compile(".*\\.(p|P)(R|r)(O|o)"))
				 .start();
				break;
			case R.id.plug_btn:
				m.withRequestCode(3)
				 .withRootPath("/"+Constant.PLUGIN_PATH)
				 .withFilter(Pattern.compile(".*\\.jar"))
				 .start();
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode,resultCode,data);
		
		if(resultCode==getActivity().RESULT_OK){
			String path= data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
			switch(requestCode){
				case 1:
					dex.setText(path);
				    break;
				case 2:
					rule.setText(path);
					break;
				case 3:
					plug.setText(path.substring(path.lastIndexOf("/")+1,path.length()));
					break;
			}
		}
	}
	
	
}

