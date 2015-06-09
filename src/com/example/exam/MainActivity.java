package com.example.exam;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.exam.UploadService.MyBinder;

public class MainActivity extends ActionBarActivity {

	private static final int PICK_CAMERA_IMAGE = 1;
	protected UploadService myService;
	private GridView gridView;
	protected CustomGridAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView) findViewById(R.id.grid_view);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(this,UploadService.class);
		startService(intent);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(connection);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.camera:
	            openCamera();
	            return true;
	        case R.id.upload:
	            if(myService != null) {
	            	myService.uploadToServer();
	            }
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void openCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, PICK_CAMERA_IMAGE);
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PICK_CAMERA_IMAGE) {
				Uri selectedImageUri = data.getData();
				if(selectedImageUri != null) {
					myService.uploadImage(selectedImageUri);
				}
				adapter.notifyDataSetChanged();
			}

		}
	}

	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyBinder binder = (MyBinder) service;
	        myService = binder.getService();
	        adapter = new CustomGridAdapter(MainActivity.this, myService.getAllImages());
	        gridView.setAdapter(adapter);
		}
	};

	
}
