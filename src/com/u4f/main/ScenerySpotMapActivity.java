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
import com.ssfxz.InfoView.FacilitysWindow;
import com.u4f.model.Facilitys;
import com.u4f.model.ScenerySpot;
import com.u4f.model.TravelNote;
import com.u4f.util.MyNetWorkUtil;

public class ScenerySpotMapActivity extends Activity
{
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private InfoWindow mInfoWindow;
	MapView mMapView;
	BaiduMap mBaiduMap;
	int FACILITYWinOffSET=-70;
	
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
	List<Facilitys> facilityList;
	
	TextView scenerySpotNameTextView;
	RadioGroup radioGroup;
	RadioButton radio_wc_Button;
	RadioButton radio_food_Button;
	RadioButton radio_shopping_Button;
	
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
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		radio_wc_Button= (RadioButton) findViewById(R.id.radioButton_wc);
		radio_food_Button= (RadioButton) findViewById(R.id.radioButton_food);
		radio_shopping_Button= (RadioButton) findViewById(R.id.radioButton_shopping);
		
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				String type = "1";
			    RadioButton rb = (RadioButton)findViewById(checkedId);
				String check =rb.getText().toString();
			    if(TextUtils.equals(check,"����"))
			    {
			    	 type = "1";
			    }
			    else if(TextUtils.equals(check,"��ʳ"))
			    {
			    	 type = "2";
			    }
			    else if(TextUtils.equals(check,"����"))
			    {
			    	type = "3";
			    }
			    new getSpotFacilityAsync().execute(scenerySpot.getScenerySpotId()+"",type);
			    //���ݲ����������ݻ�ȡ
				Toast.makeText(ScenerySpotMapActivity.this,"id="+checkedId+" "+rb.getText().toString(),Toast.LENGTH_SHORT).show();

			}
		});
		
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
		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker arg0) {
				LatLng latLng = arg0.getPosition();
				if(arg0.getExtraInfo().getParcelable("facilitys") instanceof Facilitys)
				{
					//LogUtil.d("huang","arg0.getExtraInfo().getParcelable(content) instanceof Content");
					final Facilitys f = arg0.getExtraInfo().getParcelable("facilitys");
					FacilitysWindow messageInfoWindow=new FacilitysWindow(getApplicationContext(),f.getFacilityName());
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

	class getSpotFacilityAsync extends AsyncTask<String, Boolean, List<Facilitys>>
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
			String actionuri="?scenerySpotId="+params[0]+"&type="+params[1]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			if(!TextUtils.isEmpty(result))
			{
				List<Facilitys> cc =  com.alibaba.fastjson.JSON.parseArray(result, Facilitys.class);
				if(cc!=null)
				{
					facilityList.clear();
					facilityList.addAll(cc);
					publishProgress(true);
				}
			}
			return facilityList;
		}
		
		@Override
		protected void onProgressUpdate(Boolean... values)
		{
			if(values[0] == true)
			{
				Toast.makeText(getApplicationContext(), "������Ϣ�Ѹ���", Toast.LENGTH_SHORT).show();

			}
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(List<Facilitys> result)
		{
			OverlayOptions option = null;	
			for (Facilitys f : result)
			{
				LatLng point = new LatLng(f.getFacilityLati(), f.getFacilityLng());
				//����Markerͼ��
				Bundle bundle = new Bundle();
				bundle.putParcelable("facilitys", f);
				option = new MarkerOptions().position(point).draggable(true).icon(mCurrentMarker).extraInfo(bundle);
				mBaiduMap.addOverlay(option);
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
		// �˳�ʱ���ٶ�λ
		mLocClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
