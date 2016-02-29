package com.u4f.main;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.u4f.main.MainActivity.getNearSSAsync;
import com.u4f.model.ScenerySpot;
import com.u4f.model.ScenerySpotAdapter;
import com.u4f.model.SignedSpot;
import com.u4f.model.SignedSpotAdapter;
import com.u4f.util.MyConst;
import com.u4f.util.MyNetWorkUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyMarkPlaceListActivity extends ActionBarActivity
{

	private PullToRefreshListView myBeenPlace_ListView;
	private List<SignedSpot> myBennPlace_List;
	SignedSpotAdapter myBennPlace_Adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mark_place_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);//要箭头
		getActionBar().setDisplayShowHomeEnabled(false);//不要图标 
		
		myBeenPlace_ListView = (PullToRefreshListView) findViewById(R.id.my_been_mark_place_listview);
		myBeenPlace_ListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
		
				SignedSpot scenerySpot = myBennPlace_List.get(position-1);
				Log.d("huang", "点击到过的地方"+scenerySpot.getSpotName()+scenerySpot.getSpotId());
				Intent gotoFootActivityIntent = new Intent(MyMarkPlaceListActivity.this,FootActivity.class);
				gotoFootActivityIntent.putExtra("scenerySpotId", scenerySpot.getSpotId()+"");
				gotoFootActivityIntent.putExtra("userId", MyConst.USERID_DEFAULT+"");

				startActivity(gotoFootActivityIntent);
				
			}
		});
		myBeenPlace_ListView.setOnRefreshListener(new OnRefreshListener<ListView>() 
				{
					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView)
					{
						Log.d("huang", "onRefresh");
						new getBeenPlacesAsync().execute("2");
						
					}
				});
				
		
		new getBeenPlacesAsync().execute("2");
	}

	class getBeenPlacesAsync extends AsyncTask<String, Void, List<SignedSpot>>
	{

		@Override
		protected void onPreExecute()
		{
			myBennPlace_List = new ArrayList<SignedSpot>();
			super.onPreExecute();
		}
		@Override
		protected List<SignedSpot> doInBackground(String... params)
		{
			String actionuri="GetMySignedScenerySpot?userId="+params[0]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			if(!TextUtils.isEmpty(result))
			{
				List<SignedSpot> cc = com.alibaba.fastjson.JSON.parseArray(result, SignedSpot.class);
				if(cc!=null)
				{
					myBennPlace_List.addAll(cc);
	
				}
			}
			return myBennPlace_List;
		}
		
		@Override
		protected void onPostExecute(List<SignedSpot> result)
		{
		
			if(result != null & result.size() > 0)
			{
				Log.d("huang", result.size()+"=size"+result.get(0).getSpotName());
				myBennPlace_Adapter = new SignedSpotAdapter(MyMarkPlaceListActivity.this,R.layout.been_spot_list_item, myBennPlace_List);
				myBeenPlace_ListView.setAdapter(myBennPlace_Adapter);
				myBennPlace_Adapter.notifyDataSetChanged();
				myBeenPlace_ListView.onRefreshComplete();
			}
			
			super.onPostExecute(result);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_mark_place_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.home)
		{
			Toast.makeText(MyMarkPlaceListActivity.this, "home", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
