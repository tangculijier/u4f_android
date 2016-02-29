package com.u4f.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.location.service.LocationService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.MediaType;
import com.u4f.model.ScenerySpot;
import com.u4f.model.ScenerySpotAdapter;
import com.u4f.navigationdrawertest.adapter.DrawerItem;
import com.u4f.navigationdrawertest.adapter.DrawerListAdapter;
import com.u4f.navigationdrawertest.fragment.HomeFragment;
import com.u4f.util.MyNetWorkUtil;

public class MainActivity extends ActionBarActivity 
{

	private PullToRefreshListView scenerySpotListView;
	private List<ScenerySpot> scenerySpotList;
	ScenerySpotAdapter scenerySpotadapter ;

	//�����һЩ����
	private String[] mMenuTitles;//����
	private DrawerLayout mDrawerLayout;//����
	private ActionBarDrawerToggle mDrawerToggle;//����
	private ListView mDrawerList;//�б�
	public 	DrawerListAdapter mAdapter;
	private TypedArray mMenuIconArray;//ͼ��
	private List<DrawerItem> mDrawerItemList;
	


	public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
	/**
	 * Baidu ��λ����
	 */
	private LocationService locationService;
	
	FrameLayout content_frame;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		//getActionBar().setDisplayHomeAsUpEnabled(true);//Ҫ��ͷ
		//getActionBar().setDisplayShowHomeEnabled(false);//��Ҫͼ�� 
		findView();
		
		//scenerySpotList = new ArrayList<ScenerySpot>();
		scenerySpotListView = (PullToRefreshListView) findViewById(R.id.scenerySpotListView);

		scenerySpotListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				ScenerySpot scenerySpot = scenerySpotList.get(position-1);
				Intent gotoScenerySpotActivityIntent = new Intent(MainActivity.this,ScenerySpotInfoActivity.class);
				gotoScenerySpotActivityIntent.putExtra("ss", scenerySpot);
				startActivity(gotoScenerySpotActivityIntent);
				
			}
		});
		
		new getNearSSAsync().execute("34.2565","108.9895");
		
		
		scenerySpotListView.setOnRefreshListener(new OnRefreshListener<ListView>() 
		{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				Log.d("huang", "onRefresh");
				new getNearSSAsync().execute("34.2565","108.9895");
				
			}
		});
		


	
		
	}

	@Override
	protected void onStart()
	{
		locationService = ((LocationApplication) getApplication()).locationService; 
		//��ȡlocationserviceʵ��������Ӧ����ֻ��ʼ��1��locationʵ����Ȼ��ʹ�ã����Բο�����ʾ����activity������ͨ�����ַ�ʽ��ȡlocationserviceʵ����
		locationService.registerListener(mListener);
		//ע�����
		int type = getIntent().getIntExtra("from", 0);
		if (type == 0)
		{
			locationService.setLocationOption(locationService
					.getDefaultLocationClientOption());
		} else if (type == 1)
		{
			locationService.setLocationOption(locationService.getOption());
		}
		//locationService.start();// ��λSDK
		super.onStart();
	}
	
	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		locationService.unregisterListener(mListener); //ע��������
		locationService.stop(); //ֹͣ��λ����
		Log.d("huang", "stop location");
		super.onStop();
	}

	private void findView()
	{
		content_frame=(FrameLayout)findViewById(R.id.content_frame);
		initListView();

	}

	private void initListView()
	{
		mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close)
	    {
	            public void onDrawerClosed(View view)
	            {
	            }
	            public void onDrawerOpened(View drawerView)
	            {
	            	//����� 
	            }
	        };
	        
	    mDrawerLayout.setDrawerListener(mDrawerToggle); // Set the drawer toggle as the DrawerListener
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		//list_header
		mDrawerList.addHeaderView(LayoutInflater.from(this).inflate(R.layout.list_header, null), null, false);
		
		mMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		mMenuIconArray = getResources().obtainTypedArray(R.array.nav_drawer_icons);// nav drawer icons from resources
		
		initDrawerList();
		}
	private void  initDrawerList()
	{
			// adding nav drawer items to array
			// Home
		    mDrawerItemList = new ArrayList<DrawerItem>();
			mDrawerItemList.add(new DrawerItem(mMenuTitles[0], mMenuIconArray.getResourceId(0, -1)));
			mDrawerItemList.add(new DrawerItem(mMenuTitles[1], mMenuIconArray.getResourceId(1, -1), true, "120"));
			mDrawerItemList.add(new DrawerItem(mMenuTitles[2], mMenuIconArray.getResourceId(2, -1)));
			mDrawerItemList.add(new DrawerItem(mMenuTitles[3], mMenuIconArray.getResourceId(3, -1)));
			mDrawerItemList.add(new DrawerItem(mMenuTitles[4], mMenuIconArray.getResourceId(4, -1)));
			
		    mAdapter = new DrawerListAdapter(getApplicationContext(),mDrawerItemList);
		 	mDrawerList.setAdapter(mAdapter);
		 	mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
										long id)
				{
					selectItem(position);

				}
			});
		

//		myImageLoader=new MyImageLoader(this);
//		myImageLoader.showImage(veinsUser.getUserimageurl_big(), user_image);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_reverse_map).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// Handle action buttons
		switch (item.getItemId())
		{
		case R.id.action_search:
			return true;
		case R.id.action_reverse_map:
	

			//Toast.makeText(this, R.string.action_reverse_map, Toast.LENGTH_SHORT).show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	 public  void selectItem(int position)
	 {
		 	Fragment targetFragment = null;
			 FragmentManager fm=getSupportFragmentManager();
		 switch (position) {
			 case 1:
			 	//clear all fragment and show the acitivity main_xml
				 List<Fragment> fragmentList = fm.getFragments();

				 if(fragmentList!=null&&fragmentList.size()!=0)
				 {
					 for(Fragment fr:fragmentList)
					 {
						 if(fr!=null)
						 fm.beginTransaction().remove(fr).commit();
					 }
				 }

				 if(content_frame.getVisibility()==View.GONE)
					 content_frame.setVisibility(View.VISIBLE);

					 break;
			 case 2:
		 			break;
			 case 3:
				 	//targetFragment = new HomeFragment();
				 	Intent gotomymarkpalceIntent = new Intent(MainActivity.this,MyMarkPlaceListActivity.class);
				 	startActivity(gotomymarkpalceIntent);
		 			break;
			 case 4:
				 targetFragment = new HomeFragment();
		 			break;
		 	 case 5://setting include logout
		 		 targetFragment = new HomeFragment();
		 			break;
		 	 default:
		 		 	break;
		 }

			 if (targetFragment != null)
			 {
				 fm.beginTransaction().replace(R.id.frame_outside, targetFragment).commit();
				 if(content_frame.getVisibility()==View.VISIBLE)
					 content_frame.setVisibility(View.GONE);
			 }


			 mDrawerList.setItemChecked(position, true);
			 mDrawerList.setSelection(position);
			 //setTitle(mMenuTitles[position-1]);
			 mDrawerLayout.closeDrawer(mDrawerList);
		 }


		
	
	class getNearSSAsync extends AsyncTask<String, Void, List<ScenerySpot>>
	{

		@Override
		protected void onPreExecute()
		{
			scenerySpotList = new ArrayList<ScenerySpot>();
			super.onPreExecute();
		}
		@Override
		protected List<ScenerySpot> doInBackground(String... params)
		{
			String actionuri="GetAroundServlet?latitude="+params[0]+"&longtitude="+params[1]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			if(!TextUtils.isEmpty(result))
			{
				List<ScenerySpot> cc = com.alibaba.fastjson.JSON.parseArray(result, ScenerySpot.class);
				if(cc!=null)
				{
					scenerySpotList.addAll(cc);
	
				}
			}
			return scenerySpotList;
		}
		
		@Override
		protected void onPostExecute(List<ScenerySpot> result)
		{
		
			if(result != null & result.size() > 0)
			{
				Log.d("huang", result.size()+"=size"+result.get(0).getScenerySpotName());
				scenerySpotadapter = new ScenerySpotAdapter(MainActivity.this,R.layout.spot_list_item, scenerySpotList);
				scenerySpotListView.setAdapter(scenerySpotadapter);
				scenerySpotadapter.notifyDataSetChanged();
				scenerySpotListView.onRefreshComplete();
			}
			
			super.onPostExecute(result);
		}
		
	}
	
	
	/*****
	 * @see copy funtion to you project
	 * ��λ����ص�����дonReceiveLocation����������ֱ�ӿ������´��뵽�Լ��������޸�
	 *
	 */
	private BDLocationListener mListener = new BDLocationListener()
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			// TODO Auto-generated method stub
			if (null != location
					&& location.getLocType() != BDLocation.TypeServerError)
			{
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * ʱ��Ҳ����ʹ��systemClock.elapsedRealtime()���� ��ȡ�����Դӿ���������ÿ�λص���ʱ�䣻
				 * location.getTime() ��ָ����˳����ν����ʱ�䣬���λ�ò������仯����ʱ�䲻��
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCountryCode());
				sb.append("\nCountry : ");
				sb.append(location.getCountry());
				sb.append("\ncitycode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\nDescribe: ");
				sb.append(location.getLocationDescribe());
				sb.append("\nDirection(not all devices have value): ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");
				if (location.getPoiList() != null
						&& !location.getPoiList().isEmpty())
				{
					for (int i = 0; i < location.getPoiList().size(); i++)
					{
						Poi poi = (Poi) location.getPoiList().get(i);
						sb.append(poi.getName() + ";");
					}
				}
				if (location.getLocType() == BDLocation.TypeGpsLocation)
				{// GPS��λ���
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// ��λ��km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// ��λ����
					sb.append("\ndescribe : ");
					sb.append("gps��λ�ɹ�");
				} 
				else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
				{// ���綨λ���
					// ��Ӫ����Ϣ
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("���綨λ�ɹ�");
				}
				else if (location.getLocType() == BDLocation.TypeOffLineLocation)
				{// ���߶�λ���
					sb.append("\ndescribe : ");
					sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
				}
				else if (location.getLocType() == BDLocation.TypeServerError)
				{
					sb.append("\ndescribe : ");
					sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
				} 
				else if (location.getLocType() == BDLocation.TypeNetWorkException)
				{
					sb.append("\ndescribe : ");
					sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
				} 
				else if (location.getLocType() == BDLocation.TypeCriteriaException)
				{
					sb.append("\ndescribe : ");
					sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
				}
				Log.d("huang", "sb=" + sb.toString());
				if(!TextUtils.isEmpty(location.getCity()))
				{
					getActionBar().setTitle(location.getCity());
				}
			
			}
		}

	};

}
