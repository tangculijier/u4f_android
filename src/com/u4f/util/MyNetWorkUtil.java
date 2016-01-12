package com.u4f.util;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.u4f.model.TravelNote;

public class MyNetWorkUtil
{
	
	public static String get(String url)
	{
		String result ="";
		OkHttpClient	client = new OkHttpClient();
		Request request = new Request.Builder().url(MyConst.BASE_URL+url).build();
		Response response = null;
		try
		{
			response = client.newCall(request).execute();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if(response !=null )
		{
			try
			{
				result =  response.body().string();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
