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
import com.u4f.util.MyImageLoader;


public class ScenerySpotAdapter extends ArrayAdapter<ScenerySpot> 
{

	MyImageLoader myImageloader;
	private int resourceId;
	public ScenerySpotAdapter(Context context, int textViewResourceId,List<ScenerySpot> objects) 
	{
			super(context, textViewResourceId, objects);
			myImageloader=new MyImageLoader(context);
			resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ScenerySpot scenerySpot = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) 
		{
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.scenerySpotPicture = (ImageView) view.findViewById(R.id.scenerySpotPicture);
			viewHolder.scenerySpotName = (TextView) view.findViewById(R.id.scenerySpotName);

			viewHolder.scenerySpotLab1 = (TextView) view.findViewById(R.id.scenerySpotLab1);
			viewHolder.scenerySpotLab2 = (TextView) view.findViewById(R.id.scenerySpotLab2);
			viewHolder.scenerySpotLab3 = (TextView) view.findViewById(R.id.scenerySpotLab3);
			viewHolder.scenerySpotDistance = (TextView) view.findViewById(R.id.scenerySpotDistance);

			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} 
		else
		{
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
			if(scenerySpot.getScenerySpotPicture() != null )
			{
				myImageloader.showImage("http://10.0.2.2:8080/u4f/"+scenerySpot.getScenerySpotPicture(), viewHolder.scenerySpotPicture);

			}
			else
			{
				viewHolder.scenerySpotPicture.setImageResource(R.drawable.ic_launcher);

			}
			viewHolder.scenerySpotName.setText(scenerySpot.getScenerySpotName());
			if(scenerySpot.getScenerySpotLab1() != null )
			viewHolder.scenerySpotLab1.setText(scenerySpot.getScenerySpotLab1());
			if(scenerySpot.getScenerySpotLab2() != null )
			viewHolder.scenerySpotLab2.setText(scenerySpot.getScenerySpotLab2());
			if(scenerySpot.getScenerySpotLab3() != null )
			viewHolder.scenerySpotLab3.setText(scenerySpot.getScenerySpotLab3());
			viewHolder.scenerySpotDistance.setText("距离 "+scenerySpot.getScenerySpotDistance());
			
		
			
		return view;
	}
class ViewHolder 
{
	ImageView scenerySpotPicture;
	TextView scenerySpotName;
	TextView scenerySpotLab1;
	TextView scenerySpotLab2;
	TextView scenerySpotLab3;
	TextView scenerySpotDistance;
}
}
