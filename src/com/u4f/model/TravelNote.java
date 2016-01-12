package com.u4f.model;

import java.sql.Timestamp;
import java.util.List;

public class TravelNote {

	private int travelNoteId;
	private User user;
	private int ScenerySpotId;
	private String travelNoteTitle;
	private String travelNoteContent;
	private String publicTime;
	private List<String>  travelPhotosURL;
	
	public List<String> getTravelPhotos()
	{
		return travelPhotosURL;
	}
	public void setTravelPhotos(List<String> travelPhotos)
	{
		this.travelPhotosURL = travelPhotos;
	}
	public int getTravelNoteId() {
		return travelNoteId;
	}
	public void setTravelNoteId(int travelNoteId) {
		this.travelNoteId = travelNoteId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
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
	public String getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
	
	
	
	
}
