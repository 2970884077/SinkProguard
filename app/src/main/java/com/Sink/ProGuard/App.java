package com.Sink.ProGuard;
import android.app.*;
import android.support.multidex.*;

public class App extends Application
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
        MultiDex.install(this);
		CrashHandler.getsInstance().init(this);
	    
	}
	
	
}
