package com.u4f.navigationdrawertest.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.u4f.main.R;


public class DrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private List<DrawerItem> navDrawerItems;
	
	public DrawerListAdapter(Context context, List<DrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DrawerItem item=(DrawerItem) getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) 
		{
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            viewHolder=new ViewHolder();
            viewHolder.imgIcon= (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.txtTitle= (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtCount= (TextView) convertView.findViewById(R.id.counter);
            convertView.setTag(viewHolder);
        }
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
	

        
        viewHolder.imgIcon.setImageResource(item.getIcon());
        viewHolder.txtTitle.setText(item.getTitle());
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	viewHolder.txtCount.setText(item.getCount());
        }else{
        	// hide the counter view
        	viewHolder.txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}
	
	
	class ViewHolder
	{
		  	ImageView imgIcon ;
	        TextView txtTitle;
	        TextView txtCount ;
	}
}
