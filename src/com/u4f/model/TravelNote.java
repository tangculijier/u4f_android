package com.u4f.model;

import java.sql.Timestamp;
import java.util.List;

public class TravelNote {

	private int travelNoteId;
	private User user;
	private int ScenerySpotId;
	private String travelNoteTitle;
	private String travelNoteContent;
	private Timestamp publicTime;
	private List<String>  travelPhotos;
	
	public List<String> getTravelPhotos()
	{
		return travelPhotos;
	}
	public void setTravelPhotos(List<String> travelPhotos)
	{
		this.travelPhotos = travelPhotos;
	}
	public int getTravelNoteId() {
		return travelNoteId;
	}
	public void setTravelNoteId(int travelNoteId) {
		this.travelNoteId = travelNoteId;
	}
	public User getUserId() {
		return user;
	}
	public void setUserId(User user) {
		this.user = user;
	}
	public int getScenerySpotId() {
		return ScenerySpotId;
	}
	public void setScenerySpotId(int scenerySpotId) {
		ScenerySpotId = scenerySpotId;
	}
	public String getTravelNoteTitle() {
		return travelNoteTitle;
	}
	public void setTravelNoteTitle(String travelNoteTitle) {
		this.travelNoteTitle = travelNoteTitle;
	}
	public String getTravelNoteContent() {
		return travelNoteContent;
	}
	public void setTravelNoteContent(String travelNoteContent) {
		this.travelNoteContent = travelNoteContent;
	}
	public Timestamp getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(Timestamp publicTime) {
		this.publicTime = publicTime;
	}
	@Override
	public String toString()
	{
		return "TravelNote [travelNoteId=" + travelNoteId + ", user="
				+ user + ", ScenerySpotId=" + ScenerySpotId
				+ ", travelNoteTitle=" + travelNoteTitle
				+ ", travelNoteContent=" + travelNoteContent + ", publicTime="
				+ publicTime + ", travelPhotos=" + travelPhotos + "]";
	}
	
	
	
	
	
}
