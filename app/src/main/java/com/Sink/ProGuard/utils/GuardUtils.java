package com.Sink.ProGuard.utils;
import com.Sink.ProGuard.*;
import com.googlecode.d2j.*;
import com.googlecode.d2j.dex.*;
import com.googlecode.d2j.node.*;
import java.io.*;
import java.nio.file.*;
import org.objectweb.asm.*;
import proguard.*;

public class GuardUtils
{
	public static void startDex2Jar(File inputFile) throws IOException {
		File f=new File(Constant.MAIN_PATH+"temp");
		if(!f.isDirectory()){
			f.mkdir();
		}
		System.out.println("Start Dex2Jar.....");
		Dex2jar d =new Dex2jar();
		d.from(inputFile)
			.optimizeSynchronized()
			.reUseReg()
			.withExceptionHandler(new DexExceptionHandler(){

				@Override
				public void handleFileException(Exception p1)
				{
				    System.out.println(p1);
				}

				@Override
				public void handleMethodTranslateException(Method p1, DexMethodNode p2, MethodVisitor p3, Exception p4)
				{
					System.out.println(p4);

				}
			})
			.to(Paths.get(Constant.MAIN_PATH,new String[]{"temp"}));
		    
		System.out.println("END Dex2Jar.....");
	}
	public static void startJar2Dex(File inputFile) {
		System.out.println("Start Jar2Dex.....");
		com.android.dx.command.Main.main(new String []{"--dex","--no-files", "--no-strict", "--output="+Constant.MAIN_PATH+"classes.dex", inputFile.getPath()});
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
