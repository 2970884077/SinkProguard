package com.Sink.ProGuard.adapter;

import android.support.v4.app.*;
import java.util.*;

public class MyFragmentAdapter extends FragmentPagerAdapter
	{
	private static final String DOG_BREEDS[] = {"Shop", "Main","About"};

		@Override
		public CharSequence getPageTitle(int position)
			{
				return DOG_BREEDS[position];
			}
		private List<Fragment> fragmentList;

		public MyFragmentAdapter(FragmentManager fm, 
								 List<Fragment> fragmentList)
			{
				super(fm);
				this.fragmentList = fragmentList;
			}

		@Override
		public Fragment getItem(int position)
			{
				return fragmentList.get(position);
			}

		@Override
		public int getCount()
			{
				return fragmentList.size();
			}
	}

