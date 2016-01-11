package com.u4f.model;


public class User
{

	private int userId;
	private String username;
	private String password;
	private int level; //用户级别
	private int score;// 用户积分
	private String userAvatar; //用户头像
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getScore()
	{
		return score;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	public String getUserAvatar()
	{
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar)
	{
		this.userAvatar = userAvatar;
	}
	
	
	
	
}
