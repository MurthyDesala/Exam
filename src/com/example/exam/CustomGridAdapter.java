package com.example.exam;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CustomGridAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Uri> list;
	
	public CustomGridAdapter(Context context, ArrayList<Uri> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Uri getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view = (ImageView) convertView;
		  if (view == null) {
		    view = new ImageView(context);
		    view.setLayoutParams(new AbsListView.LayoutParams(200, 200));
		  }
		  Uri url = list.get(position);

		  Picasso.with(context).load(url).into(view);
		  return view;
	}

}
