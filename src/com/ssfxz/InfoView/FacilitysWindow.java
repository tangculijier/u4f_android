package com.ssfxz.InfoView;

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
public class FacilitysWindow extends FrameLayout implements InfoWindow.OnInfoWindowClickListener
{
    TextView facilitys_title_TextView;

    public FacilitysWindow(Context context,String facilitysTitle)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.message_window_facilitys, this);

        facilitys_title_TextView=(TextView)findViewById(R.id.facilitys_title);
        facilitys_title_TextView.setText(facilitysTitle);

    }



    @Override
    public void onInfoWindowClick()
    {
    }
}
