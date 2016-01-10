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


public class ScenerySpotAdapter extends ArrayAdapter<ScenerySpot> 
{

	private int resourceId;
	public ScenerySpotAdapter(Context context, int textViewResourceId,List<ScenerySpot> objects) 
	{
			super(context, textViewResourceId, objects);
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
			viewHolder.scenerySpotPicture.setImageResource(R.drawable.ic_launcher);
			viewHolder.scenerySpotName.setText(scenerySpot.getScenerySpotName());
			viewHolder.scenerySpotLab1.setText(scenerySpot.getScenerySpotLab1());
			viewHolder.scenerySpotLab2.setText(scenerySpot.getScenerySpotLab2());
			viewHolder.scenerySpotLab3.setText(scenerySpot.getScenerySpotLab3());
			viewHolder.scenerySpotDistance.setText("距离 100m");
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
