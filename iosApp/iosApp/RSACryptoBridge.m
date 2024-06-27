//
//  RSACryptoBridge.m
//  iosApp
//
//  Created by 高鑫 on 2024/6/26.
//  Copyright © 2024 orgName. All rights reserved.
//
// RSACryptoBridge.m
#import "RSACryptoBridge.h" // 替换为你的项目名 
#import <IOSurface/IOSurfaceBase.h>
 
@implementation RSACryptoBridge

+ (NSString *)encryptString:(NSString *)string publicKey:(NSString *)publicKey {
    return [RSACrypto encryptString:string publicKey:publicKey];
}

@end
