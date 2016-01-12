package com.u4f.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.u4f.model.ScenerySpot;
import com.u4f.model.ScenerySpotAdapter;
import com.u4f.model.TravelNote;
import com.u4f.model.TravelNoteAdapter;
import com.u4f.util.MyImageLoader;
import com.u4f.util.MyNetWorkUtil;
import com.u4f.widget.NoScrollListview;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScenerySpotInfoActivity extends Activity
{

	ScenerySpot scenerySpot;
	List<TravelNote> travelNoteList;
	
	NoScrollListview travelNoteListView ;
	TravelNoteAdapter travelNoteAdapter;
	
	TextView scenerySpotNameTextView;
	TextView scenerySpotNameAddressTextView;
	ImageView scenerySpotAddressImageView;
	TextView scenerySpotOpenTimeTextView;
	TextView scenerySpotOpenTimeTitleTextView;
	TextView scenerySpotTransTextView;
	TextView scenerySpotTransTitleTextView;
	TextView scenerySpotNameTicketTextView;
	TextView scenerySpotNameTicketTitleTextView;
	TextView travleNoteSizeTextView;
	ImageView scenerySpotPitcureImageView;
	
	MyImageLoader  myImageloader;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenery);
		findViewById();
		getActionBar().setDisplayHomeAsUpEnabled(false);//要箭头
		getActionBar().setDisplayShowHomeEnabled(false);//不要图标 
		getActionBar().setTitle("");
		scenerySpot = getIntent().getParcelableExtra("ss");
		myImageloader = new  MyImageLoader(this);
		if(scenerySpot != null)
		{
			
			Log.d("huang", "scenerySpot="+scenerySpot.toString());
			
			myImageloader.showImage("http://10.0.2.2:8080/u4f/"+scenerySpot.getScenerySpotPicture(), scenerySpotPitcureImageView);
			
			scenerySpotNameTextView.setText(scenerySpot.getScenerySpotName());
			scenerySpotNameAddressTextView.setText(scenerySpot.getScenerySpotAddress());
			String openTime = scenerySpot.getScenerySpotOpenTime();
			String trans = scenerySpot.getScenerySpotTrans();
			String ticket = scenerySpot.getScenerySpotTicket();
			//
			if(openTime == null || openTime.length() == 0 )
			{
				scenerySpotTransTitleTextView.setVisibility(View.GONE);
				scenerySpotTransTextView.setVisibility(View.GONE);
			}
			else
			{
				scenerySpotTransTitleTextView.setVisibility(View.VISIBLE);
				scenerySpotTransTextView.setVisibility(View.VISIBLE);
				scenerySpotOpenTimeTextView.setText(openTime);
			}
			
			
			if(trans == null || trans.length() == 0 )
			{
				scenerySpotTransTitleTextView.setVisibility(View.GONE);
				scenerySpotTransTextView.setVisibility(View.GONE);
			}
			else
			{
				scenerySpotTransTitleTextView.setVisibility(View.VISIBLE);
				scenerySpotTransTextView.setVisibility(View.VISIBLE);
				scenerySpotTransTextView.setText(trans);
			}
			if(ticket == null || ticket.length() == 0 )
			{
				scenerySpotTransTitleTextView.setVisibility(View.GONE);
				scenerySpotTransTextView.setVisibility(View.GONE);
			}
			else
			{
				scenerySpotTransTitleTextView.setVisibility(View.VISIBLE);
				scenerySpotTransTextView.setVisibility(View.VISIBLE);
				scenerySpotNameTicketTextView.setText(ticket);

			}
			
			scenerySpotAddressImageView.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					Intent gotoMapActivity = new Intent(ScenerySpotInfoActivity.this,ScenerySpotMapActivity.class);
					gotoMapActivity.putExtra("ss", scenerySpot);
					startActivity(gotoMapActivity);
					
				}
			});
		}
		
		new getTravelNotesAsync().execute(scenerySpot.getScenerySpotId()+"");
	}
	
	private void findViewById()
	{
		scenerySpotNameTextView = (TextView)findViewById(R.id.ss_name);
		scenerySpotNameAddressTextView = (TextView)findViewById(R.id.ss_address);
		scenerySpotAddressImageView = (ImageView)findViewById(R.id.ss_address_button);
		scenerySpotOpenTimeTextView = (TextView)findViewById(R.id.ss_opentime);
		scenerySpotOpenTimeTitleTextView = (TextView)findViewById(R.id.ss_opentime_title);
		scenerySpotTransTextView = (TextView)findViewById(R.id.ss_trans);
		scenerySpotTransTitleTextView = (TextView)findViewById(R.id.ss_trans_title);
		scenerySpotNameTicketTextView = (TextView)findViewById(R.id.ss_ticket);
		scenerySpotNameTicketTitleTextView = (TextView)findViewById(R.id.ss_ticket_title);

		travleNoteSizeTextView = (TextView)findViewById(R.id.ss_note_size);
		scenerySpotPitcureImageView = (ImageView)findViewById(R.id.ss_picture_imageview);
		
		travelNoteListView = (NoScrollListview)findViewById(R.id.ss_note_listview );
		
	}

	class getTravelNotesAsync extends AsyncTask<String, Void, List<TravelNote>>
	{

		@Override
		protected void onPreExecute()
		{
			travelNoteList = new ArrayList<TravelNote>();
			travelNoteAdapter = new TravelNoteAdapter(ScenerySpotInfoActivity.this,R.layout.travelnote_list_item, travelNoteList);
			travelNoteListView.setAdapter(travelNoteAdapter);
			super.onPreExecute();
		}
		@Override
		protected List<TravelNote> doInBackground(String... params)
		{
			String actionuri="GetTravelNote?scenerySpotId="+params[0]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			if(!TextUtils.isEmpty(result))
			{
				List<TravelNote> cc =  com.alibaba.fastjson.JSON.parseArray(result, TravelNote.class);
				if(cc!=null)
				{
					travelNoteList.clear();
					travelNoteList.addAll(cc);
		
				}
			}
			
			return travelNoteList;
		}
		
		@Override
		protected void onPostExecute(List<TravelNote> result)
		{
		
			if(result != null & result.size() > 0)
			{
				Log.d("huang", result.size()+"=size"+result.get(0).getTravelNoteTitle());
				travleNoteSizeTextView.setVisibility(View.VISIBLE);
				travleNoteSizeTextView.setText("旅游攻略 " + result.size()+ "篇");
				
				
				travelNoteAdapter.notifyDataSetChanged();
			}
			else
			{
				Log.d("huang","攻略 view gone");
				travleNoteSizeTextView.setVisibility(View.GONE);
			}
			
			super.onPostExecute(result);
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.home:
			Intent gotoScenerySpotActivityIntent = new Intent(ScenerySpotInfoActivity.this,MainActivity.class);
			startActivity(gotoScenerySpotActivityIntent);
			Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			return true;
	
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
