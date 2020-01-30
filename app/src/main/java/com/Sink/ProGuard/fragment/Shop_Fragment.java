package com.Sink.ProGuard.fragment;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import com.Sink.ProGuard.adapter.*;
import com.Sink.ProGuard.*;
import com.Sink.ProGuard.bean.*;
import com.Sink.ProGuard.utils.*;
import org.json.*;

public class Shop_Fragment extends Fragment
{
	View mView;
	private ShopAdapter mAdapter;
	private ListView listView;
	private List<Shop> mDatas;
	@Nullable
	@Override
	public View onCreateView ( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
	{
		mView = inflater.inflate ( R.layout.shop_main, container, false );
		listView= mView.findViewById(R.id.shop_list);
		mDatas = new ArrayList<Shop>();
		mAdapter = new ShopAdapter(getActivity(),mDatas);
		listView.setAdapter(mAdapter);
        //设置数据
		setList();
		return mView;
	}
	
	
	public void setList(){
		HttpUtils.doGetAsyn(Constant.PLUGINS_ONLINE,new HttpUtils.CallBack(){
				@Override
				public void onRequestComplete(String result)
				{
                    try
					{
						JSONArray mJsonArray=new JSONObject(result).getJSONArray("plugin");
						mDatas.clear();
						for(int i=0;i<mJsonArray.length();i++){
							JSONObject j= (JSONObject) mJsonArray.get(i);
							Shop shop=new Shop();
							shop.setName(j.getString("name"));
							shop.setAuthor(j.getString("author"));
							shop.setHash(j.getString("hash"));
							shop.setUrl(j.getString("url"));
							mDatas.add(shop);
						}
					}
					catch(JSONException e) {
						Toast.makeText(getActivity(),e.toString(),3000).show();
					}
					getActivity().runOnUiThread(new Runnable(){
							@Override
							public void run()
							{
                             	mAdapter = new ShopAdapter(getActivity(),mDatas);
								listView.setAdapter(mAdapter);
							}
						});
				}
			});
	}
}
