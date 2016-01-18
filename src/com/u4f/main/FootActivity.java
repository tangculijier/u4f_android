package com.u4f.main;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.u4f.model.Facilitys;
import com.u4f.model.Scenery;
import com.u4f.util.MyNetWorkUtil;

public class FootActivity extends ActionBarActivity
{

	
	private String scenerySpotId ;
	private String userId;
	
	private List<Scenery> littlesceneryList;
	
	
	BitmapDescriptor ss_bitmap;
	MapView mMapView;
	BaiduMap mBaiduMap;
	float maxZoomLevel = 17;//放大级别 越大越精确20最大
	float minZoomLevel = 6;//放大级别 越大越精确20最大
    Polyline mPolyline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_foot);
		scenerySpotId = getIntent().getStringExtra("scenerySpotId");
		userId = getIntent().getStringExtra("userId");
		initBaiduMap();
		
		Log.d("huang", "scenerySpotId="+scenerySpotId+"userId="+userId);
		Log.d("huang", "scenerySpotId="+getIntent().getStringExtra("scenerySpotId"));
	
		
		
	}

	private void initBaiduMap()
	{
		ss_bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView_foot);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMaxAndMinZoomLevel(maxZoomLevel, minZoomLevel);
		//initScenerySpotlay();
		// 开启定位图层
		//mBaiduMap.setMyLocationEnabled(false);
		if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(scenerySpotId))
		{
			new getFootPrintAsync().execute(userId,scenerySpotId);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foot, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class getFootPrintAsync extends AsyncTask<String, Integer, List<Scenery>>
	{

		@Override
		protected void onPreExecute()
		{
			littlesceneryList = new ArrayList<Scenery>();
			super.onPreExecute();
		}
		@Override
		protected List<Scenery> doInBackground(String... params)
		{
			String actionuri="GetMySignedScenery?userId="+params[0]+"&scenerySpotId="+params[1]; 
			Log.d("huang", "get actionuri"+actionuri);
			String result = MyNetWorkUtil.get(actionuri);
			Log.d("huang", "result="+result);
			if(!TextUtils.isEmpty(result))
			{
				List<Scenery> cc =  com.alibaba.fastjson.JSON.parseArray(result, Scenery.class);
				if(cc!=null)
				{
					littlesceneryList.clear();
					littlesceneryList.addAll(cc);
					publishProgress(1);
				}
			}
			else
			{
				publishProgress(0);
			}
			return littlesceneryList;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			if(values[0] == 1)
			{
				Toast.makeText(getApplicationContext(), "足迹图绘制成功", Toast.LENGTH_SHORT).show();

			}
			else if(values[0] ==  0)
			{
				Toast.makeText(getApplicationContext(), "没有更多足迹", Toast.LENGTH_SHORT).show();

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
			if(result != null && result.size() != 0)
			{
				mBaiduMap.clear();
				//初始化地图开始位置
				
				LatLng ll = new LatLng(result.get(0).getSceneryLati(),result.get(0).getSceneryLng());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, maxZoomLevel);
				mBaiduMap.animateMapStatus(u);
				Log.d("huang", "签到路径个数"+result.size());
				List<LatLng> points = new ArrayList<LatLng>();
				//添加路径
				for (int i = 0 ; i < result.size();i++)
				{
					Scenery s =result.get(i);
					LatLng point = new LatLng(s.getSceneryLati(), s.getSceneryLng());
					points.add(point);
					
			        
				}
				OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(points);
			    mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
			}
			super.onPostExecute(result);
		}
		
	}
	
	
	 /**
     * 添加点、线、多边形、圆、文字
     */
    public void addCustomElementsDemo()
    {
        // 添加普通折线绘制
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        
//        // 添加多颜色分段的折线绘制
//        LatLng p11 = new LatLng(39.965, 116.444);
//        LatLng p21 = new LatLng(39.925, 116.494);
//        LatLng p31 = new LatLng(39.955, 116.534);
//        LatLng p41 = new LatLng(39.905, 116.594);
//        LatLng p51 = new LatLng(39.965, 116.644);
//        List<LatLng> points1 = new ArrayList<LatLng>();
//        points1.add(p11);
//        points1.add(p21);
//        points1.add(p31);
//        points1.add(p41);
//        points1.add(p51);
//        List<Integer> colorValue = new ArrayList<Integer>();
//        colorValue.add(0xAAFF0000);
//        colorValue.add(0xAA00FF00);
//        colorValue.add(0xAA0000FF);
//        OverlayOptions ooPolyline1 = new PolylineOptions().width(10)
//                .color(0xAAFF0000).points(points1).colorsValues(colorValue);
//        mColorfulPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline1);
//        
//        // 添加多纹理分段的折线绘制
//        LatLng p111 = new LatLng(39.865, 116.444);
//        LatLng p211 = new LatLng(39.825, 116.494);
//        LatLng p311 = new LatLng(39.855, 116.534);
//        LatLng p411 = new LatLng(39.805, 116.594);
//        List<LatLng> points11 = new ArrayList<LatLng>();
//        points11.add(p111);
//        points11.add(p211);
//        points11.add(p311);
//        points11.add(p411);
//        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
//        textureList.add(mRedTexture);
//        textureList.add(mBlueTexture);
//        textureList.add(mGreenTexture);
//        List<Integer> textureIndexs = new ArrayList<Integer>();
//        textureIndexs.add(0);
//        textureIndexs.add(1);
//        textureIndexs.add(2);
//        OverlayOptions ooPolyline11 = new PolylineOptions().width(20)
//                .points(points11).dottedLine(true).customTextureList(textureList).textureIndex(textureIndexs);
//        mTexturePolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline11);
//        
//        // 添加弧线
//        OverlayOptions ooArc = new ArcOptions().color(0xAA00FF00).width(4)
//                .points(p1, p2, p3);
//        mBaiduMap.addOverlay(ooArc);
//        // 添加圆
//        LatLng llCircle = new LatLng(39.90923, 116.447428);
//        OverlayOptions ooCircle = new CircleOptions().fillColor(0x000000FF)
//                .center(llCircle).stroke(new Stroke(5, 0xAA000000))
//                .radius(1400);
//        mBaiduMap.addOverlay(ooCircle);
//
//        LatLng llDot = new LatLng(39.98923, 116.397428);
//        OverlayOptions ooDot = new DotOptions().center(llDot).radius(6)
//                .color(0xFF0000FF);
//        mBaiduMap.addOverlay(ooDot);
//        // 添加多边形
//        LatLng pt1 = new LatLng(39.93923, 116.357428);
//        LatLng pt2 = new LatLng(39.91923, 116.327428);
//        LatLng pt3 = new LatLng(39.89923, 116.347428);
//        LatLng pt4 = new LatLng(39.89923, 116.367428);
//        LatLng pt5 = new LatLng(39.91923, 116.387428);
//        List<LatLng> pts = new ArrayList<LatLng>();
//        pts.add(pt1);
//        pts.add(pt2);
//        pts.add(pt3);
//        pts.add(pt4);
//        pts.add(pt5);
//        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
//                .stroke(new Stroke(5, 0xAA00FF00)).fillColor(0xAAFFFF00);
//        mBaiduMap.addOverlay(ooPolygon);
//        // 添加文字
//        LatLng llText = new LatLng(39.86923, 116.397428);
//        OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
//                .fontSize(24).fontColor(0xFFFF00FF).text("百度地图SDK").rotate(-30)
//                .position(llText);
//        mBaiduMap.addOverlay(ooText);
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
		// 关闭定位图层
		//mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
}
