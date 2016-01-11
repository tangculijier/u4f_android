package com.u4f.main;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.u4f.model.ScenerySpot;

public class ScenerySpotMapActivity extends Activity
{
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;

	// UI���
	Button requestLocButton;
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	float maxZoomLevel = 17;//�Ŵ󼶱� Խ��Խ��ȷ20���
	float minZoomLevel = 6;//�Ŵ󼶱� Խ��Խ��ȷ20���
	// ��ʼ��ȫ�� bitmap ��Ϣ������ʱ��ʱ recycle
	BitmapDescriptor ss_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	private Marker scenerySpotMarker;
	LatLng scenerySpotLatLng;
	ScenerySpot scenerySpot;
	
	
	TextView scenerySpotNameTextView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scenery_spot_map);
		scenerySpot = getIntent().getParcelableExtra("ss");
		Log.d("huang",scenerySpot.getScenerySpotName()+ "��ͼ����"+scenerySpot.getScenerySpotLat()+" ����+"+scenerySpot.getScenerySpotLong());
		scenerySpotLatLng = new LatLng(scenerySpot.getScenerySpotLat(),scenerySpot.getScenerySpotLong());
		
		scenerySpotNameTextView = (TextView) findViewById(R.id.ss_name_text_view);
		scenerySpotNameTextView.setText(scenerySpot.getScenerySpotName());
		
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("��ͨ");
		OnClickListener btnClickListener = new OnClickListener()
		{
			public void onClick(View v)
			{
				switch (mCurrentMode)
				{
				case NORMAL:
					requestLocButton.setText("����");
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case COMPASS:
					requestLocButton.setText("��ͨ");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case FOLLOWING:
					requestLocButton.setText("����");
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
	}

	private void initBaiduMap()
	{
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMaxAndMinZoomLevel(maxZoomLevel, minZoomLevel);
		initScenerySpotlay();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	
	public void initScenerySpotlay() 
	{

		MarkerOptions ooA = new MarkerOptions().position(scenerySpotLatLng).icon(ss_bitmap)
				.zIndex(9).draggable(true);
		scenerySpotMarker = (Marker) (mBaiduMap.addOverlay(ooA));
	}
	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			Log.d("huang", "��λһ��"+location.getLatitude()+" :"+location.getLongitude());
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
			{
				return;
			}
//			MyLocationData locData = new MyLocationData.Builder()
//					.accuracy(location.getRadius())
//					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
//					.direction(100).latitude(scenerySpot.getScenerySpotLat())
//					.longitude(scenerySpot.getScenerySpotLong()).build();
//			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc)
			{	Log.d("huang", "isFirstLoc");
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
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
