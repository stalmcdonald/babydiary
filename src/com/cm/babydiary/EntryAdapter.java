package com.cm.babydiary;

import java.util.List;

import com.cm.babydiary.Diary;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryAdapter extends ArrayAdapter<Diary> {
	private Context context;
	private List<Diary> milestoneENTRY;//, dateENTRY;
	
	public EntryAdapter(Context context, List<Diary> objects) {
		super(context, R.layout.milestonelist, objects);
		this.context = context;
		this.milestoneENTRY = objects;
		//this.dateENTRY = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater mLayoutInflater = LayoutInflater.from(context);
			convertView = mLayoutInflater.inflate(R.layout.milestonelist, null);
		}
		
		Diary task = milestoneENTRY.get(position);
		//Diary date = dateENTRY.get(position);
		
		TextView milestoneView = (TextView) convertView.findViewById(R.id.milestone_entry);
		//TextView dateView = (TextView) convertView.findViewById(R.id.date_entry);
		
		milestoneView.setText(task.getDescription());
		//dateView.setText(date.getDescription());
		
		if(task.isCompleted()){
			milestoneView.setPaintFlags(milestoneView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			milestoneView.setPaintFlags(milestoneView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		return convertView;
	}

}

