/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.vtion.health.pay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088001573350477";

	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "liuna3058@163.com";

	// 商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ12G8tVVwejQkj+SMFeaR4S9/iRgH6vGCPJhwiDJhmL4y8SIIzMry8PRQUs2+j9pQp6/WgoTi8AjftHxutcj02JhMws6dfuTuJ74bt5eAqvwB+v/wNA76cEPC6q4knkhInW2MIddU7FnrzYcpDHNt9yzGmJ9iiaWs+eFQHENZrDAgMBAAECgYAXMye1NcW04mEJ0C14ECj8vV67yPRdtf6ghzjWEDy01t+KN+mD1USacm2A7bMZCQYgHS7pAcdDNZm1xEWSt/iFAOT1wcsBfHE4kak0+nb5wnGvduNEeUjy0bkQSxk1s9+cRzsS/zDbNYR6iO/XdUqkRTo92SsiJAZ/IZs/TMxagQJBAM8PhPvQytTJC6Ues7mLztSGR6rCgvGtnsqX2GHaQwRPcguLVcyqYRE4qN7Ja3zF+RZAGFBTYvr/wNyu0msITfkCQQDCrYTx33x2/ESg16PBTXlTmu7pYRrOlX07PFtD5rTfN9iex4gKRk4KGAZ4OBaFIs6cN2W1QwdlAyqJjgj1Ls2bAkAl/GviIpjDVqq7PRmtpXdpR8UoD40kNzzVX+wDb54x0T1T3tj7thoepa1dSchdWJhsu496VQHomDjSg4P4vMpJAkEAp/2mMDYDKIXuDsGmPINHQ/75VQiHYmcbRseBlel6gPinA/T4jq02YH10776uqDpuAd3H9Y+ezOrbhRG85x55KwJAXZi8xaWoiF5xOQ6ma5zIDYYx6EILksDnppIRk0wi69KmrihoboZR74QY0IVrnnpRYM5b8xuII9FbF7BYPv+seQ==";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
