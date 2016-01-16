package com.u4f.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.u4f.util.UoLoadUtil2;
import com.u4f.util.UploadUtil;

/**
 * �ϴ��μ� activity
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
		getActionBar().setDisplayHomeAsUpEnabled(true);//Ҫ��ͷ
		//getActionBar().setDisplayShowHomeEnabled(false);//��Ҫͼ�� 
		getActionBar().setTitle("д�μ�");
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
					Toast.makeText(UpTravleNoteActivity.this, "����д����", Toast.LENGTH_SHORT).show();

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
                // ���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�
                ContentResolver resolver = getContentResolver();
    
                try {
                   Uri originalUri = data.getData(); // ���ͼƬ��uri
                   Log.d("huang", "uri="+originalUri);
                   bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // �Եõ�bitmapͼƬ
    
                   // ���￪ʼ�ĵڶ����֣���ȡͼƬ��·����
    
                   String[] proj = { MediaStore.Images.Media.DATA };
    
                   // ������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
                   @SuppressWarnings("deprecation")
                   Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                   // ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
                   int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                   // �����������ͷ ���������Ҫ����С�ĺ���������Խ��
                   cursor.moveToFirst();
                   // ����������ֵ��ȡͼƬ·��
                   final String path = cursor.getString(column_index);
                   File file = new File(path); //�����path�����Ǹ���ַ��ȫ�ֱ���
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
			String postUrl ="UploadTravelPhotos";
			//res = MyNetWorkUtil.post(MyConst.BASE_URL+postUrl, formBody);
			for(int i = 0 ;i < addPhotoFileList.size() ;i++)
			{
				File file = addPhotoFileList.get(i);
				Log.d("huang","MyConst.BASE_URL+postUrl="+MyConst.BASE_URL+postUrl);
	            //String resultUpPhtoto = UploadUtil.uploadFile(file, MyConst.BASE_URL+postUrl);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("travelNoteId", "128");
				 UoLoadUtil2.toUploadFile(file,"img", MyConst.BASE_URL+postUrl,map);
				 try
				{
					UoLoadUtil2.post(file, MyConst.BASE_URL+postUrl, map);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//	            Log.d("huang", "resultUpPhtoto="+resultUpPhtoto);
//	            if(TextUtils.equals(resultUpPhtoto, "true"))
//	            {
//	            	publishProgress(i+1);
//	            }
			}

			return res;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			Toast.makeText(UpTravleNoteActivity.this, "��"+values[0]+"��ͼƬ�ϴ��ɹ�!", Toast.LENGTH_SHORT).show();
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(String result)
		{
//			if(TextUtils.equals(result, "true"))
//			{
//				Toast.makeText(UpTravleNoteActivity.this, "���Է����ɹ�!", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//			else 
//			{	
//				Toast.makeText(UpTravleNoteActivity.this, "���Է���ʧ��...", Toast.LENGTH_SHORT).show();
//			}
			super.onPostExecute(result);
		}
		
		
	}
}
