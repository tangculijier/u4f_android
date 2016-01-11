package com.u4f.model;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.u4f.main.R;
import com.u4f.main.R.drawable;
import com.u4f.main.R.id;


public class TravelNoteAdapter extends ArrayAdapter<TravelNote> 
{

	private int resourceId;
	public TravelNoteAdapter(Context context, int textViewResourceId,List<TravelNote> objects) 
	{
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		TravelNote TravelNote = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) 
		{
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.UserName = (TextView) view.findViewById(R.id.user_name);

			viewHolder.UserLevel = (TextView) view.findViewById(R.id.user_level);
			viewHolder.TravelTime = (TextView) view.findViewById(R.id.travel_time);
			viewHolder.TravelContent = (TextView) view.findViewById(R.id.travel_note_content_textview);
			viewHolder.TravleTitle = (TextView) view.findViewById(R.id.travel_note_title_textview);

			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} 
		else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
			viewHolder.UserName.setText(TravelNote.getUserId().getUsername());
			viewHolder.UserLevel.setText("Lv"+TravelNote.getUserId().getLevel());
			viewHolder.TravelTime.setText(TravelNote.getPublicTime()+"");
			if(TravelNote.getTravelNoteContent() != null )
			viewHolder.TravelContent.setText(TravelNote.getTravelNoteContent());
			viewHolder.TravleTitle.setText("来自攻略 《"+TravelNote.getTravelNoteTitle()+"》");
		return view;
	}
class ViewHolder 
{

	TextView UserName;
	TextView UserLevel;
	TextView TravelTime;
	TextView TravelContent;
	TextView TravleTitle;
}
}
