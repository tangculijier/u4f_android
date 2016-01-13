package com.u4f.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;
import com.u4f.model.ScenerySpot;
import com.u4f.model.TravelNote;
import com.u4f.util.MyConst;
import com.u4f.util.MyNetWorkUtil;
import com.u4f.util.UploadUtil;

/**
 * 上传游记 activity
 *
 */
public class UpTravleNoteActivity extends Activity
{
	ScenerySpot scenerySpot;
	EditText travelNoteTitleEditText;
	EditText travelNoteEditText;
	Button travelNoteSendButton;
	ImageView addPictureButton;
	List<File> addPhotoFileList;
	LinearLayout travelAddPictureLinearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_travle_note);
		getActionBar().setDisplayHomeAsUpEnabled(true);//要箭头
		//getActionBar().setDisplayShowHomeEnabled(false);//不要图标 
		getActionBar().setTitle("写游记");
		travelNoteTitleEditText = (EditText)findViewById(R.id.travel_note_title);
		travelNoteEditText = (EditText)findViewById(R.id.travel_note_content);
		travelNoteSendButton = (Button)findViewById(R.id.travel_note_send);
		addPictureButton = (ImageView)findViewById(R.id.add_picture_button);
		travelAddPictureLinearLayout = (LinearLayout)findViewById(R.id.travel_add_picture_linearLayout);
		scenerySpot = getIntent().getParcelableExtra("ss");
		travelNoteSendButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				String title = travelNoteTitleEditText.getText().toString();
				String content = travelNoteEditText.getText().toString();
				if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content))
				{
					new upLoadTravleNoteContentAsync().execute();
				}
				else
				{
					Toast.makeText(UpTravleNoteActivity.this, "请填写内容", Toast.LENGTH_SHORT).show();

				}
			}
		});
		addPictureButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{  
                    Intent addPictureintent = new Intent(Intent.ACTION_GET_CONTENT);
                    addPictureintent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(addPictureintent, 1);
				}
					
			}
		});
		addPhotoFileList = new ArrayList<File>();
	}

	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{  
        if(requestCode==1) 
        {  
            if(resultCode==Activity.RESULT_OK)  
            {  
            	
            	Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
    
                try {
                   Uri originalUri = data.getData(); // 获得图片的uri
                   Log.d("huang", "uri="+originalUri);
                   bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
    
                   // 这里开始的第二部分，获取图片的路径：
    
                   String[] proj = { MediaStore.Images.Media.DATA };
    
                   // 好像是android多媒体数据库的封装接口，具体的看Android文档
                   @SuppressWarnings("deprecation")
                   Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                   // 按我个人理解 这个是获得用户选择的图片的索引值
                   int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                   // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                   cursor.moveToFirst();
                   // 最后根据索引值获取图片路径
                   final String path = cursor.getString(column_index);
                   File file = new File(path); //这里的path就是那个地址的全局变量
                   addPhotoFileList.add(file);
                   
                   ImageView image = new ImageView(UpTravleNoteActivity.this);
                   image.setScaleType(ScaleType.CENTER_CROP);
                   image.setLayoutParams(new LayoutParams(150, 150));
                   image.setPadding(5, 5, 5, 5);
                   image.setImageURI(originalUri);
                   
                   travelAddPictureLinearLayout.addView(image);
                   
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
      }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.up_travle_note, menu);
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
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	class upLoadTravleNoteContentAsync extends AsyncTask<String, Integer, String>
	{

		
		@Override
		protected String doInBackground(String... params)
		{
			String res = "false";
			TravelNote travle = new TravelNote();
			RequestBody formBody = new FormEncodingBuilder()
		 	.add("scenerySpotId", scenerySpot.getScenerySpotId()+"")
		 	.add("userId", "1")
		 	.add("travelNoteTitle", travelNoteEditText.getText().toString())
		 	.add("travelNoteContent", travelNoteTitleEditText.getText().toString())
		 	.build();
			String postUrl ="UploadTravelNote";
			//res = MyNetWorkUtil.post(MyConst.BASE_URL+postUrl, formBody);
			for(int i = 0 ;i < addPhotoFileList.size() ;i++)
			{
				File file = addPhotoFileList.get(i);
				Log.d("huang", file.getAbsolutePath()+"");
	            String resultUpPhtoto = UploadUtil.uploadFile(file, MyConst.BASE_URL+"UploadTravelNote");
	            Log.d("huang", "resultUpPhtoto="+resultUpPhtoto);
	            if(TextUtils.equals(resultUpPhtoto, "true"))
	            {
	            	publishProgress(i+1);
	            }
			}

			return res;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			Toast.makeText(UpTravleNoteActivity.this, "第"+values[0]+"个图片上传成功!", Toast.LENGTH_SHORT).show();
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(String result)
		{
//			if(TextUtils.equals(result, "true"))
//			{
//				Toast.makeText(UpTravleNoteActivity.this, "攻略发布成功!", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//			else 
//			{	
//				Toast.makeText(UpTravleNoteActivity.this, "攻略发布失败...", Toast.LENGTH_SHORT).show();
//			}
			super.onPostExecute(result);
		}
		
		
	}
}
