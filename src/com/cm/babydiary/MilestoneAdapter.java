package com.cm.babydiary;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MilestoneAdapter extends ArrayAdapter<Diary> {
  @SuppressWarnings("unused")
private Context context;
  private List<Diary> diaryLogs;
  private LayoutInflater li;

  public MilestoneAdapter(Context context, List<Diary> diaryEntries ) {
	  super( context, R.layout.entry_layout, diaryEntries );
	  this.context = context;
	  this.diaryLogs = diaryEntries;
	  li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View diaryrow = convertView!=null ? convertView : li.inflate(R.layout.entry_layout, parent, false);
    
    TextView mainEntry = (TextView) diaryrow.findViewById(R.id.mainEntry);
    TextView moreDetails = (TextView) diaryrow.findViewById(R.id.babyDetails);
    
    Diary item = diaryLogs.get(position);
    diaryrow.findViewById(R.id.deleteMilestoneBttn).setTag(item);
    mainEntry.setText(item.getHeader());
    moreDetails.setText(item.getDetails());
    
    if(item.getRelevance()){
    	mainEntry.setPaintFlags(mainEntry.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    	moreDetails.setPaintFlags(mainEntry.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	}else{
		mainEntry.setPaintFlags(mainEntry.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		moreDetails.setPaintFlags(mainEntry.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
	}

    return diaryrow;
  }
}