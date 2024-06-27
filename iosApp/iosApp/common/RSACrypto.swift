//
//  RSACrypto.swift
//  iosApp
//
//  Copyright © 2024 orgName. All rights reserved.
//

// RSACrypto.swift

import Foundation
import Security

@objc class RSACrypto: NSObject {
    
    @objc static func encryptString(_ string: String, publicKey: String) -> String? {
        guard let keyData = Data(base64Encoded: publicKey) else {
            return nil
        }
        
        guard let key = try? SecKeyCreateWithData(keyData as CFData, [
            kSecAttrKeyType: kSecAttrKeyTypeRSA,
            kSecAttrKeyClass: kSecAttrKeyClassPublic,
            kSecAttrKeySizeInBits: 2048,
            kSecReturnPersistentRef: true
        ] as CFDictionary, nil) else {
            return nil
        }
        
        guard let data = string.data(using: .utf8) else {
            return nil
        }
        
        var error: Unmanaged<CFError>?
        guard let encryptedData = SecKeyCreateEncryptedData(key, .rsaEncryptionOAEPSHA256, data as CFData, &error) else {
            print("Encryption error: \(String(describing: error?.takeUnretainedValue().localizedDescription))")
            return nil
        }
        
        return encryptedData.base64EncodedString()
    }
}

