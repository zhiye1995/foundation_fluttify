//
// Created by Yohom Bao on 2019/11/29.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>

NS_ASSUME_NONNULL_BEGIN

@interface CGPointHandler : NSObject
- (instancetype)initWith:(NSString*) method :(NSDictionary*)args :(FlutterResult)methodResult;
@end

NS_ASSUME_NONNULL_END