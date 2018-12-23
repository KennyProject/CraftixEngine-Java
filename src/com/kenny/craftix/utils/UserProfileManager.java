package com.kenny.craftix.utils;

public class UserProfileManager 
{
	private String userProfile;
	
	private String hardDriveCFolder = "C:\\";
	private String hardDriveDFolder = "D:\\";
	private String appdata;
	
	public String getUserAppdataFolder()
	{
		this.setUserFolder("Kenny");
		this.appdata = this.getCFolder() + "\\Users\\" + 
				this.userProfile + "\\AppData\\Roaming\\.craftix\\";
		return this.appdata;
	}
	
	public void setUserFolder(String userProfileIn)
	{ 
		this.userProfile = userProfileIn;
	}
	
	private String getCFolder()
	{
		return hardDriveCFolder;
	}
	
	public String getDFolder()
	{
		return hardDriveDFolder;
	}
}
