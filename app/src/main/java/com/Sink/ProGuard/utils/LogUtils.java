package com.Sink.ProGuard.utils;

import android.os.*;
import android.widget.*;
import java.io.*;

public class LogUtils extends OutputStream implements Runnable {
		TextView out;
		ScrollView scroll;
	H h;



		public LogUtils(TextView out, ScrollView scroll) {
				this.out = out;
				this.scroll = scroll;
            
				h = new H();
			}

		@Override
		public void write(int p1) throws IOException {
				h.sendEmptyMessage(p1);
				h.post(this);
			}

		@Override
		public void run() {
				scroll.scrollTo(0, out.getHeight()+5);
			}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
				String s = new String(b, off, len);
			    
				Message msg = Message.obtain();
				msg.obj = s;
				h.sendMessage(msg);
				h.post(this);
			}


		class H extends Handler {

				@Override
				public void handleMessage(Message msg) {
					
						out.append((CharSequence) msg.obj);
						
					   
					}

			}
	}


