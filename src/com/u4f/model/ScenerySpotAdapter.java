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
		ViewHolder viewHolder;
		if (convertView == null) 
		{
			convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.scenerySpotPicture = (ImageView) convertView.findViewById(R.id.scenerySpotPicture);
			viewHolder.scenerySpotName = (TextView) convertView.findViewById(R.id.scenerySpotName);

			viewHolder.scenerySpotLab1 = (TextView) convertView.findViewById(R.id.scenerySpotLab1);
			viewHolder.scenerySpotLab2 = (TextView) convertView.findViewById(R.id.scenerySpotLab2);
			viewHolder.scenerySpotLab3 = (TextView) convertView.findViewById(R.id.scenerySpotLab3);
			viewHolder.scenerySpotDistance = (TextView) convertView.findViewById(R.id.scenerySpotDistance);

			convertView.setTag(viewHolder); // ��ViewHolder�洢��View��
		} 
		else
		{
			viewHolder = (ViewHolder) convertView.getTag(); // ���»�ȡViewHolder
		}
			if(scenerySpot.getScenerySpotPicture() != null )
			{
				myImageloader.showImage(MyConst.BASE_URL+scenerySpot.getScenerySpotPicture(), viewHolder.scenerySpotPicture);

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
			viewHolder.scenerySpotDistance.setText("���� "+scenerySpot.getScenerySpotDistance());
			
		
			
		return convertView;
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
