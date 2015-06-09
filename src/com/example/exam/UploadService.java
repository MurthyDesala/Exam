package com.example.exam;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;


public class UploadService extends Service {

	private final IBinder binder = new MyBinder();
	private ArrayList<Uri> queue = new ArrayList<Uri>();
	private Random random = new Random(10);
	private int i = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public class MyBinder extends Binder {
		public UploadService getService() {
			return UploadService.this;
		}
	}
	
	public void uploadImage(Uri uri) {
		queue.add(uri);
	}
	
	public ArrayList<Uri> getAllImages() {
		return queue;
	}
	
	public void uploadToServer() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (queue.size() > 0) {
					try {
						uploadImage(i);
					} catch (Exception e) {
						System.out.println("Exception");
					}
					
				}
				
			}
		}).start();
	}
	
	private synchronized void uploadImage(int i) {
	    try {
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	    if (random.nextInt() < 5) {
	    	
	    	if(queue.size() > 0 && i == queue.size()-1) {
	    		i = 0;
	    	}
	       throw new RuntimeException("Faking upload failure exception");
	    }

	    queue.remove(i);
	    System.out.println("Success");
	} 

}
