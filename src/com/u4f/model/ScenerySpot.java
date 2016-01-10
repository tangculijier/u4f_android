package com.u4f.model;

import java.sql.Blob;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ScenerySpot  implements Parcelable
{
	
	private int scenerySpotId;
	private String scenerySpotName;
	private String scenerySpotAddress;
	private double scenerySpotLat;
	private double scenerySpotLong;
	private int belongCityId;
	private double scenerySpotTicket;
	private String scenerySpotTrans;
	private String scenerySpotLab1;
	private String scenerySpotLab2;
	private String scenerySpotLab3;
	private Blob scenerySpotPicture;
	private int scenerySpotDistance;
	public int getScenerySpotDistance()
	{
		return scenerySpotDistance;
	}
	public void setScenerySpotDistance(int scenerySpotDistance)
	{
		this.scenerySpotDistance = scenerySpotDistance;
	}
	public int getScenerySpotId()
	{
		return scenerySpotId;
	}
	public void setScenerySpotId(int scenerySpotId)
	{
		this.scenerySpotId = scenerySpotId;
	}
	public String getScenerySpotName()
	{
		return scenerySpotName;
	}
	public void setScenerySpotName(String scenerySpotName)
	{
		this.scenerySpotName = scenerySpotName;
	}
	public String getScenerySpotAddress()
	{
		return scenerySpotAddress;
	}
	public void setScenerySpotAddress(String scenerySpotAddress)
	{
		this.scenerySpotAddress = scenerySpotAddress;
	}
	public double getScenerySpotLat()
	{
		return scenerySpotLat;
	}
	public void setScenerySpotLat(double scenerySpotLat)
	{
		this.scenerySpotLat = scenerySpotLat;
	}
	public double getScenerySpotLong()
	{
		return scenerySpotLong;
	}
	public void setScenerySpotLong(double scenerySpotLong)
	{
		this.scenerySpotLong = scenerySpotLong;
	}
	public int getBelongCityId()
	{
		return belongCityId;
	}
	public void setBelongCityId(int belongCityId)
	{
		this.belongCityId = belongCityId;
	}
	public double getScenerySpotTicket()
	{
		return scenerySpotTicket;
	}
	public void setScenerySpotTicket(double scenerySpotTicket)
	{
		this.scenerySpotTicket = scenerySpotTicket;
	}
	public String getScenerySpotTrans()
	{
		return scenerySpotTrans;
	}
	public void setScenerySpotTrans(String scenerySpotTrans)
	{
		this.scenerySpotTrans = scenerySpotTrans;
	}
	public String getScenerySpotLab1()
	{
		return scenerySpotLab1;
	}
	public void setScenerySpotLab1(String scenerySpotLab1)
	{
		this.scenerySpotLab1 = scenerySpotLab1;
	}
	public String getScenerySpotLab2()
	{
		return scenerySpotLab2;
	}
	public void setScenerySpotLab2(String scenerySpotLab2)
	{
		this.scenerySpotLab2 = scenerySpotLab2;
	}
	public String getScenerySpotLab3()
	{
		return scenerySpotLab3;
	}
	public void setScenerySpotLab3(String scenerySpotLab3)
	{
		this.scenerySpotLab3 = scenerySpotLab3;
	}
	public Blob getScenerySpotPicture()
	{
		return scenerySpotPicture;
	}
	public void setScenerySpotPicture(Blob scenerySpotPicture)
	{
		this.scenerySpotPicture = scenerySpotPicture;
	}
//	@Override
//	public String toString() {
//		return "ScenerySpot [ScenerySpotId=" + scenerySpotId
//				+ ", ScenerySpotName=" + scenerySpotName
//				+ ", ScenerySpotAddress=" + scenerySpotAddress
//				+ ", ScenerySpotLat=" + scenerySpotLat + ", ScenerySpotLong="
//				+ scenerySpotLong + ", belongCityId=" + belongCityId
//				+ ", ScenerySpotTicket=" + scenerySpotTicket
//				+ ", ScenerySpotTrans=" + scenerySpotTrans
//				+ ", ScenerySpotLab1=" + scenerySpotLab1 + ", ScenerySpotLab2="
//				+ scenerySpotLab2 + ", ScenerySpotLab3=" + scenerySpotLab3
//				+ ", ScenerySpotPicture=" + scenerySpotPicture
//				+ ", getScenerySpotId()=" + getScenerySpotId()
//				+ ", getScenerySpotName()=" + getScenerySpotName()
//				+ ", getScenerySpotAddress()=" + getScenerySpotAddress()
//				+ ", getScenerySpotLat()=" + getScenerySpotLat()
//				+ ", getScenerySpotLong()=" + getScenerySpotLong()
//				+ ", getBelongCityId()=" + getBelongCityId()
//				+ ", getScenerySpotTicket()=" + getScenerySpotTicket()
//				+ ", getScenerySpotTrans()=" + getScenerySpotTrans()
//				+ ", getScenerySpotLab1()=" + getScenerySpotLab1()
//				+ ", getScenerySpotLab2()=" + getScenerySpotLab2()
//				+ ", getScenerySpotLab3()=" + getScenerySpotLab3()
//				+ ", getScenerySpotPicture()=" + getScenerySpotPicture()
//				+  "]";
//	}

	/**
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation.
	 *
	 * @return a bitmask indicating the set of special object types marshalled
	 * by the Parcelable.
	 */
	@Override
	public int describeContents()
	{
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		
		dest.writeInt(scenerySpotId);
		dest.writeString(scenerySpotName);
		dest.writeString(scenerySpotAddress);
		dest.writeDouble(scenerySpotLat);
		dest.writeDouble(scenerySpotLong);
		dest.writeInt(belongCityId);
		dest.writeDouble(scenerySpotTicket);
		dest.writeString(scenerySpotTrans);
		dest.writeString(scenerySpotLab1);
		dest.writeString(scenerySpotLab2);
		dest.writeString(scenerySpotLab3);
		dest.writeInt(scenerySpotDistance);
	}

	public static final Parcelable.Creator<ScenerySpot> CREATOR = new Creator<ScenerySpot>() {
		public ScenerySpot createFromParcel(Parcel source) {
			ScenerySpot c = new ScenerySpot();
			c.scenerySpotId = source.readInt();
			c.scenerySpotName =source.readString();
			c.scenerySpotAddress = source.readString();
			c.scenerySpotLat = source.readDouble();
			c.scenerySpotLong =source.readDouble();
			c.belongCityId =source.readInt();
			c.scenerySpotTicket = source.readDouble();
			c.scenerySpotTrans = source.readString();
			c.scenerySpotLab1 = source.readString();
			c.scenerySpotLab2 = source.readString();
			c.scenerySpotLab3 = source.readString();
			c.scenerySpotDistance = source.readInt();
			return c;
		}

		public ScenerySpot[] newArray(int size) {
			return new ScenerySpot[size];
		}
	};
	
}
