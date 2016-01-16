package com.u4f.InfoView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.InfoWindow;
import com.u4f.main.R;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2015/4/18.
 */
public class LitteSceneryWindow extends FrameLayout implements InfoWindow.OnInfoWindowClickListener
{
    TextView little_scenery_title_TextView;

    public LitteSceneryWindow(Context context,String littleSceneryName)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.message_window_little_scenery, this);

        little_scenery_title_TextView=(TextView)findViewById(R.id.little_scenery_title);
        little_scenery_title_TextView.setText(littleSceneryName);

    }



    @Override
    public void onInfoWindowClick()
    {
    }
}
