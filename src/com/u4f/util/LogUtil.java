package com.u4f.util;

import android.util.Log;

/**
 * Created by Administrator on 2015/9/7.
 */
public class LogUtil
{


    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;


    public static final int LEVEL = VERBOSE;


    public static void v(String tag,String message)
    {
        if(LEVEL <= VERBOSE)
        {
            Log.v(tag, message);
        }
    }

    public static void d(String tag,String message)
    {
        if(LEVEL <= DEBUG)
        {
            Log.d(tag,message);
        }
    }

    public static void i(String tag,String message)
    {
        if(LEVEL <= INFO)
        {
            Log.i(tag, message);
        }
    }

    public static void w(String tag,String message)
    {
        if(LEVEL <= WARN)
        {
            Log.w(tag, message);
        }
    }

    public static void e(String tag,String message)
    {
        if(LEVEL <= ERROR)
        {
            Log.e(tag,message);
        }
    }



}
