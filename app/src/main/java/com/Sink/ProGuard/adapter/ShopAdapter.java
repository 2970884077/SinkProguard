package com.Sink.ProGuard.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.Sink.ProGuard.*;
import java.util.*;
import com.Sink.ProGuard.bean.*;
import android.view.View.*;
import com.Sink.ProGuard.utils.*;

public class ShopAdapter extends BaseAdapter
 {
    private LayoutInflater mInflater;
    private List<Shop> mDatas;
	Context context;
    public ShopAdapter(Context context, List<Shop> datas) {

        mInflater = LayoutInflater.from(context);
		this.context=context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view, parent, false); 
            holder = new ViewHolder();
          	convertView.setTag(holder);
			holder.tv_name= convertView.findViewById(R.id.name);
			holder.bt= convertView.findViewById(R.id.donw);
			holder.tv_author=convertView.findViewById(R.id.author);
        } else {   
            holder = (ViewHolder) convertView.getTag();
        }

        final Shop shop = mDatas.get(position);
		holder.tv_name.setText(shop.getName());
		holder.tv_author.setText(shop.getAuthor());
		holder.bt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
				 //下载..
		         new DownloadThread(context,shop.getUrl(),Constant.PLUGIN_PATH+shop.getName()+".jar",true).start();
				}
			});
		
     	return convertView;
    }

    private class ViewHolder {
        TextView tv_name,tv_author;
		Button bt;
    }

}

