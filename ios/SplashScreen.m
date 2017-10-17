/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */

#import "SplashScreen.h"
#import <React/RCTBridge.h>
#import "LaunchScreenViewController.h"

static bool waiting = true;
static bool showing = false;
static bool addedJsLoadErrorObserver = false;
static LaunchScreenViewController *launchScreenViewController = nil;

@implementation SplashScreen
- (dispatch_queue_t)methodQueue{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

+ (void)show {
    if (!addedJsLoadErrorObserver) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(jsLoadError:) name:RCTJavaScriptDidFailToLoadNotification object:nil];
        addedJsLoadErrorObserver = true;
    }

    /*while (waiting) {
        NSDate* later = [NSDate dateWithTimeIntervalSinceNow:0.1];
        [[NSRunLoop mainRunLoop] runUntilDate:later];
    }*/
    
    UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    
    if(launchScreenViewController == nil) {
        launchScreenViewController = [[LaunchScreenViewController alloc] initWithNibName:@"LaunchScreenViewController" bundle:nil];
    }

    [root presentViewController:launchScreenViewController animated:false completion:nil];
    
    showing = true;
    
    NSDate* later = [NSDate dateWithTimeIntervalSinceNow:0.5];
    [[NSRunLoop mainRunLoop] runUntilDate:later];
}

+ (void)hide {
    dispatch_async(dispatch_get_main_queue(),
                   ^{
                       [launchScreenViewController dismissViewControllerAnimated:YES completion:nil];
                       waiting = false;
                       showing = false;
                       launchScreenViewController = nil;
                   });
}

+ (void)setCustomText:(NSString* )text {
    if(launchScreenViewController != nil) {
        [launchScreenViewController setText:text];
    }
}

+ (void) jsLoadError:(NSNotification*)notification
{
    // If there was an error loading javascript, hide the splash screen so it can be shown.  Otherwise the splash screen will remain forever, which is a hassle to debug.
    [SplashScreen hide];
}

RCT_EXPORT_METHOD(  isShowing:resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  
                  )
{
    if(launchScreenViewController == nil || !showing) {
        resolve(@"");
    } else {
        resolve(@"ok");
    }
}

RCT_EXPORT_METHOD(hide){
    [SplashScreen hide];

}

RCT_EXPORT_METHOD(setText:test) {
    [SplashScreen setCustomText:test];
}

@end
