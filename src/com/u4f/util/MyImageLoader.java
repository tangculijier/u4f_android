package com.u4f.util;

/*
 * ????????????
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.u4f.main.R;

import java.util.HashSet;

public class MyImageLoader {

	Context context;
	DisplayImageOptions options;
	ImageSize mImageSize_big;
	ImageSize mImageSize_samll;
	public MyImageLoader(Context context)
	{
			this.context=context;
			//image config
			ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
			ImageLoader.getInstance().init(configuration);

			options=new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.not_logged) 
			.showImageForEmptyUri(R.drawable.not_logged)
			.cacheInMemory(true)  
            .cacheOnDisk(true).displayer(new SimpleBitmapDisplayer())
			.showImageOnFail(R.drawable.not_logged).build() ;


			
	}
	
	
	
	   
	public void showImage(String userImageUrl,final ImageView target)
	{
		 ImageLoader.getInstance().loadImage(userImageUrl,options, new SimpleImageLoadingListener(){

    	
        @Override  
        public void onLoadingComplete(String imageUri, View view,  
                Bitmap loadedImage) {  
            super.onLoadingComplete(imageUri, view, loadedImage);  
            target.setImageBitmap(loadedImage);  
        }  
    });
	}

	
}
