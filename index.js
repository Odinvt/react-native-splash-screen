/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 * @flow
 */
'use strict';

import { NativeModules, DeviceEventEmitter } from 'react-native';
const RNSplash = NativeModules.SplashScreen;

export default class SplashScreen {
  static subscriptions = [];

  static show() {
    RNSplash.show();
  }

  static hide() {
    RNSplash.hide();
  }

  static onHide(callback) {
    SplashScreen.subscriptions.push(DeviceEventEmitter.addListener('SS_HIDDEN', callback));
  }

  static offHide() {
    SplashScreen.subscriptions.forEach((subscription) => {
      subscription && subscription.remove();
    });

    SplashScreen.subscriptions = [];
  }

  static isShowing() {
    return RNSplash.isShowing();
  }

  static setText(text) {
    return RNSplash.setText(text);
  }

}

