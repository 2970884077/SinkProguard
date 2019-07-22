package com.Sink.ProGuard.fragment;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import com.Sink.ProGuard.*;
import com.Sink.view.*;
import com.bumptech.glide.*;
import de.hdodenhof.circleimageview.CircleImageView;

public class About_Fragment extends Fragment
	{
		View mView; 
	    String Url_zxs="http://q1.qlogo.cn/g?b=qq&nk=2814432475&s=640";
	    String Url_sink="http://q1.qlogo.cn/g?b=qq&nk=468766131&s=640";
		CircleImageView sink,zxs;
		@Nullable
		@Override
		public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
			{
				mView = inflater.inflate ( R.layout.about_main, container, false );
				sink=mView.findViewById(R.id.sink);
				zxs=mView.findViewById(R.id.zxs);
				Glide.with(getActivity()).load(Url_sink).into(sink);
				Glide.with(getActivity()).load(Url_zxs).into(zxs);
				return mView;
			}
	}

