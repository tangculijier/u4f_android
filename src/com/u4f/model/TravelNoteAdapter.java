package com.u4f.model;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u4f.main.R;
import com.u4f.main.R.drawable;
import com.u4f.main.R.id;
import com.u4f.util.MyConst;
import com.u4f.util.MyImageLoader;


public class TravelNoteAdapter extends ArrayAdapter<TravelNote> 
{
	MyImageLoader myImageloader;
	private int resourceId;
	Context ctx;
	boolean isAdd = false;//getiview只添加一次 暂定
	public TravelNoteAdapter(Context context, int textViewResourceId,List<TravelNote> objects) 
	{
			super(context, textViewResourceId, objects);
			myImageloader=new MyImageLoader(context);
			resourceId = textViewResourceId;
			this.ctx = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		TravelNote TravelNote = getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) 
		{
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.UserName = (TextView) convertView.findViewById(R.id.user_name);

			viewHolder.UserLevel = (TextView) convertView.findViewById(R.id.user_level);
			viewHolder.TravelTime = (TextView) convertView.findViewById(R.id.travel_time);
			viewHolder.TravelContent = (TextView) convertView.findViewById(R.id.travel_note_content_textview);
			viewHolder.TravleTitle = (TextView) convertView.findViewById(R.id.travel_note_title_textview);
			viewHolder.TravlePictureLinearLayout = (LinearLayout) convertView.findViewById(R.id.travel_note_picture_linearLayout);
			convertView.setTag(viewHolder); // 将ViewHolder存储在View中
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag(); // 重新获取ViewHolder
		}
		
			viewHolder.UserName.setText(TravelNote.getUser().getUsername());
			viewHolder.UserLevel.setText("Lv"+TravelNote.getUser().getLevel());
			viewHolder.TravelTime.setText(TravelNote.getPublicTime()+"");
			
			if(TravelNote.getTravelNoteContent() != null )
			viewHolder.TravelContent.setText(TravelNote.getTravelNoteContent());
			
			viewHolder.TravleTitle.setText("来自攻略 《"+TravelNote.getTravelNoteTitle()+"》");
			
			List<String> travelPhotosUrl = TravelNote.getTravelPhotos();
			if( travelPhotosUrl!= null && travelPhotosUrl.size() != 0 )
			{
				if(isAdd == false)
				{
					for(String url : travelPhotosUrl)
					{
						//Log.d("huang", "travelPhotosUrl="+url);
						ImageView image = new ImageView(ctx);
						image.setScaleType(ScaleType.CENTER_CROP);
						image.setLayoutParams(new LayoutParams(150, 150));
						image.setPadding(5, 5, 5, 5);
						myImageloader.showImage(MyConst.BASE_URL+url, image);
						viewHolder.TravlePictureLinearLayout.addView(image);
						isAdd = true;
					}
				}
				

			}
			
			return convertView;
	}
class ViewHolder 
{

	TextView UserName;
	TextView UserLevel;
	TextView TravelTime;
	TextView TravelContent;
	TextView TravleTitle;
	LinearLayout TravlePictureLinearLayout;
}
}
