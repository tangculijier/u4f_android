package com.u4f.navigationdrawertest.adapter;
/*
 * 左方导航抽屉的自定义
 */
import android.graphics.Bitmap;
import android.widget.TextView;

public class DrawerItem {
	
	private String title;
	private int icon;
	private String count = "0";//红色通知
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	
	public DrawerItem(){}

	public DrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public DrawerItem(String title, int icon, boolean isCounterVisible, String count){
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	public DrawerItem(String title,String imageUri,Bitmap loadedImage)
	{
		
	}
	

	
	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.isCounterVisible;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisibility(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}
}
