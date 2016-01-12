package com.u4f.model;


public class Travelphoto
{

	private int travelPhotoId;
	private int travelNoteId;
	private String imageURL;
	public int getTravelPhotoId()
	{
		return travelPhotoId;
	}
	public void setTravelPhotoId(int travelPhotoId)
	{
		this.travelPhotoId = travelPhotoId;
	}
	public int getTravelNoteId()
	{
		return travelNoteId;
	}
	public void setTravelNoteId(int travelNoteId)
	{
		this.travelNoteId = travelNoteId;
	}
	public String getImage()
	{
		return imageURL;
	}
	public void setImage(String image)
	{
		this.imageURL = image;
	}
	
	
}
