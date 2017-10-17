//
//  LaunchScreenViewController.m
//  SplashScreen
//
//  Created by Oussama on 16/10/2017.
//  Copyright © 2017 cboy. All rights reserved.
//

#import "LaunchScreenViewController.h"

@interface LaunchScreenViewController ()
@property (unsafe_unretained, nonatomic) IBOutlet UILabel *label;
@property (strong, nonatomic) IBOutlet UIView *launchScreenView;
@property(nonatomic, assign) UIModalTransitionStyle modalTransitionStyle;

@end

@implementation LaunchScreenViewController

- (void)setText: (NSString *)text {
    NSLog(@"##### SPLASHSCREEN LOG : SHOWING TEXT %@", text);
    UILabel *tagLabel =  (UILabel*)self->_label;
    
    [UIView animateWithDuration:0.2
                          delay:0
                        options:0
                     animations:^{
                         tagLabel.alpha = 0.0f;
                     }
                     completion:^(BOOL finished) {
                         // set the new value and fade in
                         [self->_label setText:text];
                         [UIView animateWithDuration:0.2
                                               delay:0
                                             options:0
                                          animations:^{
                                              tagLabel.alpha = 1.0f;
                                          }
                                          completion:nil];
                         
                     }];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
