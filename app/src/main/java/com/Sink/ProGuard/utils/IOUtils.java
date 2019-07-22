package com.Sink.ProGuard.utils;
import java.io.*;

public class IOUtils
{
	
	/**
     * 复制文件
     *
     * @param srcPath
     *            源文件绝对路径
     * @param destPath
     *            目标文件所在目录
     * @return void
     */
    public static void copyFile(String srcPath, String destPath)
    {
        File destFile = new File(destPath); // 目标文件
        if (destFile.exists() && destFile.isFile())
        {
			destFile.delete();
        }
        try
        {
            FileInputStream fis = new FileInputStream(srcPath);
            copyFile(fis,destPath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
	public static void copyFile(InputStream fis,String destPath){
		try
        {
            
            FileOutputStream fos = new FileOutputStream(destPath);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1)
            {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	
}
