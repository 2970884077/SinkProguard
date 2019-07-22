package com.Sink.ProGuard;
import android.app.*;

public class App extends Application
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		CrashHandler.getsInstance().init(this);
	}
	
	
}
