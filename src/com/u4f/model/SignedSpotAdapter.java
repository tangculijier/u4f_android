package com.u4f.model;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.u4f.main.R;
import com.u4f.main.R.drawable;
import com.u4f.main.R.id;
import com.u4f.util.MyConst;
import com.u4f.util.MyImageLoader;


public class SignedSpotAdapter extends ArrayAdapter<SignedSpot> 
{

	MyImageLoader myImageloader;
	private int resourceId;
	public SignedSpotAdapter(Context context, int textViewResourceId,List<SignedSpot> objects) 
	{
			super(context, textViewResourceId, objects);
			myImageloader=new MyImageLoader(context);
			resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		SignedSpot signedSpot = getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) 
		{
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.beenPicture = (ImageView) convertView.findViewById(R.id.been_Picture_imageview);

			viewHolder.beenName = (TextView) convertView.findViewById(R.id.been_name_textview);
			viewHolder.beenSignCounts = (TextView) convertView.findViewById(R.id.been_signtimes_textview);
			viewHolder.beenTime = (TextView) convertView.findViewById(R.id.been_time_textview);

			convertView.setTag(viewHolder); // 将ViewHolder存储在View中
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag(); // 重新获取ViewHolder
		}
			if(signedSpot.getSpotAvatar() != null )
			{
				myImageloader.showImage(MyConst.BASE_URL+signedSpot.getSpotAvatar(), viewHolder.beenPicture);

			}
			else
			{
				viewHolder.beenPicture.setImageResource(R.drawable.ic_launcher);

			}
			viewHolder.beenName.setText(signedSpot.getSpotName());
			if(signedSpot.getSignedCounts() != null )
			{
				String counts = viewHolder.beenSignCounts.getText().toString();
				counts = counts.replace("%s",signedSpot.getSignedCounts()+"");
				viewHolder.beenSignCounts.setText(counts);

			}
			if(signedSpot.getSignedTime() != null )
			viewHolder.beenTime.setText(signedSpot.getSignedTime());
			
		
			
		return convertView;
	}
class ViewHolder 
{
	ImageView beenPicture;
	TextView beenName;
	TextView beenSignCounts;
	TextView beenTime;
}
}
