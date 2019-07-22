package com.Sink.ProGuard.utils;
import com.googlecode.dex2jar.tools.*;
import java.io.*;
import proguard.*;
import com.Sink.ProGuard.*;

public class GuardUtils
{
	public static void startDex2Jar(File inputFile) {
		System.out.println("Start Dex2Jar.....");
		Dex2jarCmd.main(new String[]{"-os","-v","-f","-d","-o",Constant.MAIN_PATH+"temp.jar",inputFile.getAbsolutePath()});
		System.out.println("END Dex2Jar.....");
	}
	public static void startJar2Dex(File inputFile) {
		System.out.println("Start Jar2Dex.....");
		com.android.dx.command.Main.main(new String []{"--dex", "--verbose", "--no-strict", "--output="+Constant.MAIN_PATH+"classes.dex", inputFile.getAbsolutePath()});
		System.out.println("END Jar2Dex.....");
	}

	public static void startProGuard(String[] s)
    {
        Configuration configuration = new Configuration();
        try
        {
            ConfigurationParser parser = new ConfigurationParser(s,System.getProperties());
            try
            {
                parser.parse(configuration);
            }
            finally
            {
                parser.close();
            }
            new ProGuard(configuration).execute();
        }
        catch (Exception ex)
        {
            if (configuration.verbose)
            {
                ex.printStackTrace();
            }
            else
            {
                System.err.println("Error: "+ex.getMessage());
            }
        }
	}
}
