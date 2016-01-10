package com.u4f.main;

import com.u4f.model.ScenerySpot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SceneryActivity extends Activity
{

	ScenerySpot scenerySpot;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenery);
		getActionBar().setDisplayHomeAsUpEnabled(true);//Òª¼ýÍ·
		scenerySpot = getIntent().getParcelableExtra("ss");
		if(scenerySpot != null)
		{
			Log.d("huang", "scenerySpot="+scenerySpot.toString());
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.home:
			Intent gotoScenerySpotActivityIntent = new Intent(SceneryActivity.this,MainActivity.class);
			startActivity(gotoScenerySpotActivityIntent);
			Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			return true;
	
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
