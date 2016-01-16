package com.u4f.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * example:
 * sceneryLati":34.257072,
 * "sceneryName":"交大北门",
 * "sceneryDescribe":"",
 * "sceneryLng":108.990022,
 * "sceneryId":1,
 * "scenerySpotId":12
 * 
 */
public class Scenery  implements Parcelable
{

	private int sceneryId;
	private String sceneryName;
	private int scenerySpotId;
	private double sceneryLng;
	private double sceneryLati;
	private String sceneryDescribe;
	public int getSceneryId()
	{
		return sceneryId;
	}
	public void setSceneryId(int sceneryId)
	{
		this.sceneryId = sceneryId;
	}
	public String getSceneryName()
	{
		return sceneryName;
	}
	public void setSceneryName(String sceneryName)
	{
		this.sceneryName = sceneryName;
	}
	public int getScenerySpotId()
	{
		return scenerySpotId;
	}
	public void setScenerySpotId(int scenerySpotId)
	{
		this.scenerySpotId = scenerySpotId;
	}
	public double getSceneryLng()
	{
		return sceneryLng;
	}
	public void setSceneryLng(double sceneryLng)
	{
		this.sceneryLng = sceneryLng;
	}
	public double getSceneryLati()
	{
		return sceneryLati;
	}
	public void setSceneryLati(double sceneryLati)
	{
		this.sceneryLati = sceneryLati;
	}
	public String getSceneryDescribe()
	{
		return sceneryDescribe;
	}
	public void setSceneryDescribe(String sceneryDescribe)
	{
		this.sceneryDescribe = sceneryDescribe;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		
		
		
		dest.writeInt(sceneryId);
		dest.writeString(sceneryName);
		dest.writeInt(scenerySpotId);
		dest.writeDouble(sceneryLng);
		dest.writeDouble(sceneryLati);
		dest.writeString(sceneryDescribe);
	
	
	}

	public static final Parcelable.Creator<Scenery> CREATOR = new Creator<Scenery>() {
		public Scenery createFromParcel(Parcel source) {
			Scenery f = new Scenery();
			f.sceneryId = source.readInt();
			f.sceneryName =source.readString();
			f.scenerySpotId =source.readInt();
			f.sceneryLng = source.readDouble();
			f.sceneryLati =source.readDouble();
		
			f.sceneryDescribe = source.readString();
		
			return f;
		}

		public Scenery[] newArray(int size) {
			return new Scenery[size];
		}
	};

	
	
	
}
