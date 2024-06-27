// RSACryptoBridge.h
#import <Foundation/Foundation.h>

@interface RSACryptoBridge : NSObject
+ (NSString *)encryptString:(NSString *)string publicKey:(NSString *)publicKey;
@end
