package com.u4f.model;



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
	private String scenerySpotOpenTime;
	private String scenerySpotTicket;
	private String scenerySpotTrans;
	private String scenerySpotLab1;
	private String scenerySpotLab2;
	private String scenerySpotLab3;
	private String scenerySpotPicture;
	private String scenerySpotDistance;
	public String getScenerySpotDistance()
	{
		return scenerySpotDistance;
	}
	public void setScenerySpotDistance(String scenerySpotDistance)
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
	public String getScenerySpotPicture()
	{
		return scenerySpotPicture;
	}
	public void setScenerySpotPicture(String scenerySpotPicture)
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

	public String getScenerySpotOpenTime()
	{
		return scenerySpotOpenTime;
	}
	public void setScenerySpotOpenTime(String scenerySpotOpenTime)
	{
		this.scenerySpotOpenTime = scenerySpotOpenTime;
	}
	public String getScenerySpotTicket()
	{
		return scenerySpotTicket;
	}
	public void setScenerySpotTicket(String scenerySpotTicket)
	{
		this.scenerySpotTicket = scenerySpotTicket;
	}
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
		dest.writeString(scenerySpotOpenTime);
		dest.writeString(scenerySpotTicket);
		dest.writeString(scenerySpotTrans);
		dest.writeString(scenerySpotLab1);
		dest.writeString(scenerySpotLab2);
		dest.writeString(scenerySpotLab3);
		dest.writeString(scenerySpotDistance);
		dest.writeString(scenerySpotPicture);
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
			c.scenerySpotOpenTime = source.readString();
			c.scenerySpotTicket = source.readString();
			c.scenerySpotTrans = source.readString();
			c.scenerySpotLab1 = source.readString();
			c.scenerySpotLab2 = source.readString();
			c.scenerySpotLab3 = source.readString();
			c.scenerySpotDistance = source.readString();
			c.scenerySpotPicture = source.readString();
			return c;
		}

		public ScenerySpot[] newArray(int size) {
			return new ScenerySpot[size];
		}
	};
	
}
