package com.cboy.rn.splashscreen;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreenModule extends ReactContextBaseJavaModule{
    public SplashScreenModule(ReactApplicationContext reactContext) {
        super(reactContext);

        SplashScreen.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "SplashScreen";
    }

    @ReactMethod
    public void show() {
        SplashScreen.show(getCurrentActivity());
    }

    @ReactMethod
    public void hide() {
        SplashScreen.hide(getCurrentActivity());
    }

    @ReactMethod
    public void isShowing(Promise promise) {
        SplashScreen.isShowing(promise);
    }

    @ReactMethod
    public void setText(String text, Promise promise) {
        SplashScreen.setText(text, promise);
    }
}