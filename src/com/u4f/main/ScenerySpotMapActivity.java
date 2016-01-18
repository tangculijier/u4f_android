package com.u4f.main;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.u4f.InfoView.FacilitysWindow;
import com.u4f.model.Facilitys;
import com.u4f.model.Scenery;
import com.u4f.model.ScenerySpot;
import com.u4f.model.TravelNote;
import com.u4f.util.MyNetWorkUtil;

public class ScenerySpotMapActivity extends Activity
{
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker_shopping = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding1);
	BitmapDescriptor mCurrentMarker_food = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding2);
	BitmapDescriptor mCurrentMarker_wc = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding3);
	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

	private InfoWindow mInfoWindow;
	MapView mMapView;
	BaiduMap mBaiduMap;
	int FACILITYWinOffSET=-70;
	
	// UI相关
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	float maxZoomLevel = 17;//放大级别 越大越精确20最大
	float minZoomLevel = 6;//放大级别 越大越精确20最大
	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor ss_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	private Marker scenerySpotMarker;
	LatLng scenerySpotLatLng;
	ScenerySpot scenerySpot;
	List<Facilitys> facilityList;
	List<Scenery> littleSceneryList;
	Scenery signSceneryTarget;
	
	Button signButton;
	TextView scenerySpotNameTextView;
	RadioGroup radioGroup;
	RadioButton radio_wc_Button;
	RadioButton radio_food_Button;
	RadioButton radio_shopping_Button;
	RadioButton radio_little_scenery;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scenery_spot_map);
		scenerySpot = getIntent().getParcelableExtra("ss");
		Log.d("huang",scenerySpot.getScenerySpotName()+ "地图坐标"+scenerySpot.getScenerySpotLat()+" 经度+"+scenerySpot.getScenerySpotLong());
		scenerySpotLatLng = new LatLng(scenerySpot.getScenerySpotLat(),scenerySpot.getScenerySpotLong());
		
		scenerySpotNameTextView = (TextView) findViewById(R.id.ss_name_text_view);
		scenerySpotNameTextView.setText(scenerySpot.getScenerySpotName());
		requestLocButton = (Button) findViewById(R.id.button1);
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		radio_wc_Button= (RadioButton) findViewById(R.id.radioButton_wc);
		radio_food_Button= (RadioButton) findViewById(R.id.radioButton_food);
		radio_shopping_Button= (RadioButton) findViewById(R.id.radioButton_shopping);
		radio_little_scenery= (RadioButton) findViewById(R.id.little_scenery);
		signButton = (Button) findViewById(R.id.signButton);
		signButton.setVisibility(View.GONE);
    	new getlittleSceneryInsideAsync().execute(scenerySpot.getScenerySpotId()+"");

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				Log.d("huang", "onCheckedChanged");
				String type = "1";
			    RadioButton rb = (RadioButton)findViewById(checkedId);
				String check =rb.getText().toString();
			    if(TextUtils.equals(check,"厕所"))
			    {
			    	 type = "1";
			    }
			    else if(TextUtils.equals(check,"美食"))
			    {
			    	 type = "2";
			    }
			    else if(TextUtils.equals(check,"购物"))
			    {
			    	type = "3";
			    }
			    else if(TextUtils.equals(check,"景点"))
			    {
			    	type = "4";
			    }
			    if(TextUtils.equals(check,"景点"))
			    {
			    	new getlittleSceneryInsideAsync().execute(scenerySpot.getScenerySpotId()+"");
			    }
			    else
			    {
				    new getSpotFacilityAsync().execute(scenerySpot.getScenerySpotId()+"",type);

			    }
			    //根据参数进行数据获取
				//Toast.makeText(ScenerySpotMapActivity.this,"id="+checkedId+" "+rb.getText().toString(),Toast.LENGTH_SHORT).show();

			}
		});
		
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		OnClickListener btnClickListener = new OnClickListener()
		{
			public void onClick(View v)
			{
				switch (mCurrentMode)
				{
				case NORMAL:
					requestLocButton.setText("跟随");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("普通");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("罗盘");
					mCurrentMode = LocationMode.COMPASS;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				default:
					break;
				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);

		initBaiduMap();
		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker arg0) {
				LatLng latLng = arg0.getPosition();
				if(arg0.getExtraInfo().getParcelable("facilitys") instanceof Facilitys)
				{
					//LogUtil.d("huang","arg0.getExtraInfo().getParcelable(content) instanceof Content");
					final Facilitys f = arg0.getExtraInfo().getParcelable("facilitys");
					signButton.setVisibility(View.GONE);
					FacilitysWindow messageInfoWindow=new FacilitysWindow(getApplicationContext(),f.getFacilityName());
					InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
						public void onInfoWindowClick()
						{
							Toast.makeText(ScenerySpotMapActivity.this,"listener~",Toast.LENGTH_SHORT).show();

						}
					};

					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(messageInfoWindow), latLng, FACILITYWinOffSET, listener);

				}
				else if(arg0.getExtraInfo().getParcelable("scenery") instanceof Scenery)
				{
					//LogUtil.d("huang","arg0.getExtraInfo().getParcelable(content) instanceof Content");
					final Scenery littleScenery = arg0.getExtraInfo().getParcelable("scenery");
					signSceneryTarget = littleScenery;
					scenerySpotNameTextView.setText(signSceneryTarget.getSceneryName());
					signButton.setVisibility(View.VISIBLE);
					FacilitysWindow messageInfoWindow=new FacilitysWindow(getApplicationContext(),littleScenery.getSceneryName());
					InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
						public void onInfoWindowClick()
						{
							Toast.makeText(ScenerySpotMapActivity.this,"listener~",Toast.LENGTH_SHORT).show();

						}
					};

					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(messageInfoWindow), latLng, FACILITYWinOffSET, listener);

				}
				
				

				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}});
		signButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Log.d("huang", "签到"+signSceneryTarget.getSceneryId());
				new signAsync().execute();
				
			}
		});
	}

	private void initBaiduMap()
	{
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMaxAndMinZoomLevel(maxZoomLevel, minZoomLevel);
		initScenerySpotlay();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(false);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	
	public void initScenerySpotlay() 
	{

		//MarkerOptions ooA = new MarkerOptions().position(scenerySpotLatLng).icon(ss_bitmap)
		//		.zIndex(9).draggable(true);
		//scenerySpotMarker = (Marker) (mBaiduMap.addOverlay(ooA));
	}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			//Log.d("huang", "定位一次"+location.getLatitude()+" :"+location.getLongitude());
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
			{
				return;
			}
//			MyLocationData locData = new MyLocationData.Builder()
//					.accuracy(location.getRadius())
//					// 此处设置开发者获取到的方向信息，顺时针0-360
//					.direction(100).latitude(scenerySpot.getScenerySpotLat())
//					.longitude(scenerySpot.getScenerySpotLong()).build();
//			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc)
			{	
				Log.d("huang", "isFirstLoc");
				isFirstLoc = false;
				LatLng ll = new LatLng(scenerySpot.getScenerySpotLat(),
						scenerySpot.getScenerySpotLong());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, maxZoomLevel);

				mBaiduMap.animateMapStatus(u);
				
			}
		}

		public void onReceivePoi(BDLocation poiLocation)
		{
		}
	}

	class getSpotFacilityAsync extends AsyncTask<String, Integer, List<Facilitys>>
	{
		@Override
		protected void onPreExecute()
		{
			facilityList = new ArrayList<Facilitys>();
			super.onPreExecute();
		}

		@Override
		protected List<Facilitys> doInBackground(String... params)
		{
			String actionuri="FindFacility?scenerySpotId="+params[0]+"&facilityType="+params[1]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			if(!TextUtils.isEmpty(result))
			{
				List<Facilitys> cc =  com.alibaba.fastjson.JSON.parseArray(result, Facilitys.class);
				if(cc!=null)
				{
					facilityList.clear();
					facilityList.addAll(cc);
					publishProgress(1);
				}

			}
			else
			{
				publishProgress(0);
			}
			return facilityList;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			if(values[0] == 1)
			{
				Toast.makeText(getApplicationContext(), "附近信息已更新", Toast.LENGTH_SHORT).show();

			}
			else if(values[0] ==  0)
			{
				Toast.makeText(getApplicationContext(), "附近没有更多信息", Toast.LENGTH_SHORT).show();

			}
			else
			{
				Toast.makeText(getApplicationContext(), "附近信息更新失败", Toast.LENGTH_SHORT).show();

			}
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(List<Facilitys> result)
		{
			OverlayOptions option = null;	
			if(result != null && result.size() != 0)
			{
				mBaiduMap.clear();
				LatLng ll = new LatLng(scenerySpot.getScenerySpotLat(),
						scenerySpot.getScenerySpotLong());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, maxZoomLevel);

				mBaiduMap.animateMapStatus(u);
				for (Facilitys f : result)
				{
					LatLng point = new LatLng(f.getFacilityLati(), f.getFacilityLng());
					//构建Marker图标
					Bundle bundle = new Bundle();
					bundle.putParcelable("facilitys", f);
					if(TextUtils.equals("1", f.getFacilityType()))
					{
						option = new MarkerOptions().position(point).draggable(true).icon(mCurrentMarker_wc).extraInfo(bundle);

					}
					else if(TextUtils.equals("2", f.getFacilityType()))
					{
						option = new MarkerOptions().position(point).draggable(true).icon(mCurrentMarker_food).extraInfo(bundle);

					}
					else if(TextUtils.equals("3", f.getFacilityType()))
					{
						option = new MarkerOptions().position(point).draggable(true).icon(mCurrentMarker_shopping).extraInfo(bundle);

					}
					mBaiduMap.addOverlay(option);
				}
			}
			super.onPostExecute(result);
		}
		
	}
	
	class getlittleSceneryInsideAsync extends AsyncTask<String, Integer, List<Scenery>>
	{
		@Override
		protected void onPreExecute()
		{
			littleSceneryList = new ArrayList<Scenery>();
			super.onPreExecute();
		}

		@Override
		protected List<Scenery> doInBackground(String... params)
		{
			String actionuri="GetAllSceneryByScenerySpotId?scenerySpotId="+params[0]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			Log.d("huang", "result="+result);
			if(!TextUtils.isEmpty(result))
			{
				List<Scenery> cc =  com.alibaba.fastjson.JSON.parseArray(result, Scenery.class);
				if(cc!=null)
				{
					littleSceneryList.clear();
					littleSceneryList.addAll(cc);
					publishProgress(1);
				}
			}
			else
			{
				publishProgress(0);
			}
			return littleSceneryList;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			if(values[0] == 1)
			{
				Toast.makeText(getApplicationContext(), "附近信息已更新", Toast.LENGTH_SHORT).show();

			}
			else if(values[0] ==  0)
			{
				Toast.makeText(getApplicationContext(), "附近没有更多信息", Toast.LENGTH_SHORT).show();

			}
			else
			{
				Toast.makeText(getApplicationContext(), "附近信息更新失败", Toast.LENGTH_SHORT).show();

			}
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(List<Scenery> result)
		{
			OverlayOptions option = null;	
			if(result != null && result.size() != 0)
			{
				mBaiduMap.clear();
				LatLng ll = new LatLng(scenerySpot.getScenerySpotLat(),
						scenerySpot.getScenerySpotLong());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, maxZoomLevel);

				mBaiduMap.animateMapStatus(u);
				for (Scenery littleScenery : result)
				{
					LatLng point = new LatLng(littleScenery.getSceneryLati(), littleScenery.getSceneryLng());
					//构建Marker图标
					Bundle bundle = new Bundle();
					bundle.putParcelable("scenery", littleScenery);
					option = new MarkerOptions().position(point).draggable(true).icon(mCurrentMarker).extraInfo(bundle);

					mBaiduMap.addOverlay(option);
				}
			}
			super.onPostExecute(result);
		}
		
	}
	
	
	class signAsync extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			String res ="";
		
			if(signSceneryTarget != null)
			{
				String userId = "1";
				String longtutide = "108.990058";
				String latutide = "34.24745";
				String sceneryId = signSceneryTarget.getSceneryId()+"";
				RequestBody formBody = new FormEncodingBuilder()
			 	.add("userId", "1")
			 	.add("sceneryId",sceneryId)
			 	.add("latitude", latutide)
			 	.add("longtitude", longtutide)
			 	.build();
				String postUrl ="SignInServlet";
				res = MyNetWorkUtil.post(postUrl, formBody);
			}
			return res;
		}
		@Override
		protected void onPostExecute(String result)
		{
			if(TextUtils.equals(result, "0"))
			{
				Toast.makeText(ScenerySpotMapActivity.this,"已签到过", Toast.LENGTH_SHORT).show();

			}
			else if(TextUtils.equals(result, "1"))
			{
				Toast.makeText(ScenerySpotMapActivity.this,"签到成功!", Toast.LENGTH_SHORT).show();

			}
			else if(TextUtils.equals(result, "2"))
			{
				Toast.makeText(ScenerySpotMapActivity.this,"签到失败!", Toast.LENGTH_SHORT).show();

			}
			else if(TextUtils.equals(result, "3"))
			{
				Toast.makeText(ScenerySpotMapActivity.this,"距离大于100m,签到失败!", Toast.LENGTH_SHORT).show();

			}
			super.onPostExecute(result);
		}
		
	}
	
	
	
	@Override
	protected void onPause()
	{
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
