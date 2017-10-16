package com.cboy.rn.splashscreen;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;
    public static ReactContext mReactContext;
    private final static String SS_HIDDEN = "SS_HIDDEN";
    private static String mText = "";

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen, final String mainComponentName) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        Log.e("SPLASHSCREEN", " CALLED THE SHOW METHOD ");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("SPLASHSCREEN", " RUNNING ON UI THREAD ");
                if (!activity.isFinishing()) {

                    ArrayList<String> runningActivities = new ArrayList<String>();

                    ActivityManager activityManager = (ActivityManager)activity.getBaseContext().getSystemService (Context.ACTIVITY_SERVICE);

                    List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);

                    for (int i1 = 0; i1 < services.size(); i1++) {
                        runningActivities.add(0,services.get(i1).topActivity.toString());
                    }

                    if(mSplashDialog != null && runningActivities.contains("ComponentInfo{"+ mainComponentName +"/"+ mainComponentName +".MainActivity}")) {
                        Log.e("SPLASHSCREEN", " CLEANING PAST REFERENCE TO SPLASH SCREEN DIALOG AS IT IS HOOKED TO A NON EXISTANT ACTIVITY");
                        if (mSplashDialog.isShowing()) {
                            mSplashDialog.dismiss();
                        }
                        mSplashDialog = null;
                    }

                    if(mSplashDialog == null) {
                        Log.e("SPLASHSCREEN", " INITIALIZING SPLASHSCREEN DIALOG ");
                        mSplashDialog = new Dialog(activity,fullScreen? R.style.SplashScreen_Fullscreen:R.style.SplashScreen_SplashTheme);
                        mSplashDialog.setContentView(R.layout.launch_screen);
                        TextView tv1 = (TextView)mSplashDialog.findViewById(R.id.splashTextView);
                        tv1.setText(mText);
                        mSplashDialog.setCancelable(false);
                    } else {
                        Log.e("SPLASHSCREEN", " WE ALREADY HAVE A REFERENCE TO SPLASHSCREEN DIALOG ");
                    }
                    
                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                        Log.e("SPLASHSCREEN", " SPLASHSCREEN IS SHOWING NOW ");
                    }else {
                        Log.e("SPLASHSCREEN", " SPLASHSCREEN IS ALREADY SHOWING ");
                    }
                } else {
                    Log.e("SPLASHSCREEN", " ACTIVITY IS BUSY NOTHING WILL BE SHOWN ");
                }
            }
        });
    }
    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity,false, "app");
    }
    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, String mainComponentName) {
        show(activity,false, mainComponentName);
    }

    public static void setText(final String text, final Promise promise) {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mSplashDialog.isShowing()) {
                    mText = text;

                    final TextView tv1 = (TextView)mSplashDialog.findViewById(R.id.splashTextView);

                    // Start from 0.1f if you desire 90% fade animation
                    final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                    fadeIn.setDuration(200);
                    fadeIn.setStartOffset(0);
                    // End to 0.1f if you desire 90% fade animation
                    final Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    fadeOut.setDuration(200);
                    fadeOut.setStartOffset(0);

                    fadeOut.setAnimationListener(new Animation.AnimationListener(){
                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            // start fadeIn when fadeOut ends
                            tv1.setText(mText);
                            tv1.startAnimation(fadeIn);
                        }

                        @Override
                        public void onAnimationRepeat(Animation arg0) {
                        }

                        @Override
                        public void onAnimationStart(Animation arg0) {
                        }
                    });

                    tv1.startAnimation(fadeOut);

                    promise.resolve(true);
                } else {
                    promise.resolve(false);
                }
            }
        });
    }

    public static void isShowing(Promise promise) {
        if(mSplashDialog == null) {
            promise.resolve(false);
        } else {
            promise.resolve(mSplashDialog.isShowing());
        }
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        Log.e("SPLASHSCREEN", " CALL TO SPLASH SCREEN HIDE ");
        if (activity == null) activity = mActivity.get();
        if (activity == null) {

            sendEvent(SS_HIDDEN, null);
            return;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    Log.e("SPLASHSCREEN", " SPLASH SCREEN IS HIDING NOW ");
                    mSplashDialog.dismiss();

                    sendEvent(SS_HIDDEN, null);
                }
            }
        });
    }

    protected static void sendEvent(String eventName,
                             @Nullable Object params) {
        mReactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
        //Log.wtf("EVENT : ", "TRIGGERED EVENT " + eventName);
    }
}
