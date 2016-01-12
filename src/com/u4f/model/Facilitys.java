package com.u4f.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * 景点内的服务设置 包括 1：厕所 2：美食 3：购物
 * @author lizheHuang
 *
 */
public class Facilitys implements Parcelable
{
	private int facilityId;
	private String facilityName;
	private Double facilityLng;
	private Double facilityLati;
	private String facilityType;
	private int scenerySpotId;

	public int getScenerySpotId()
	{
		return scenerySpotId;
	}

	public void setScenerySpotId(int scenerySpotId)
	{
		this.scenerySpotId = scenerySpotId;
	}

	public int getFacilityId()
	{
		return facilityId;
	}

	public void setFacilityId(int facilityId)
	{
		this.facilityId = facilityId;
	}

	public String getFacilityName()
	{
		return facilityName;
	}

	public void setFacilityName(String facilityName)
	{
		this.facilityName = facilityName;
	}

	public Double getFacilityLng()
	{
		return facilityLng;
	}

	public void setFacilityLng(Double facilityLng)
	{
		this.facilityLng = facilityLng;
	}

	public Double getFacilityLati()
	{
		return facilityLati;
	}

	public void setFacilityLati(Double facilityLati)
	{
		this.facilityLati = facilityLati;
	}

	public String getFacilityType()
	{
		return facilityType;
	}

	public void setFacilityType(String facilityType)
	{
		this.facilityType = facilityType;
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
		
		
		dest.writeInt(facilityId);
		dest.writeString(facilityName);
		dest.writeDouble(facilityLng);
		dest.writeDouble(facilityLati);
		dest.writeString(facilityType);
		dest.writeInt(scenerySpotId);
	
	}

	public static final Parcelable.Creator<Facilitys> CREATOR = new Creator<Facilitys>() {
		public Facilitys createFromParcel(Parcel source) {
			Facilitys f = new Facilitys();
			f.facilityId = source.readInt();
			f.facilityName =source.readString();
			f.facilityLng = source.readDouble();
			f.facilityLati =source.readDouble();
		
			f.facilityType = source.readString();
			f.scenerySpotId =source.readInt();
			return f;
		}

		public Facilitys[] newArray(int size) {
			return new Facilitys[size];
		}
	};

}
