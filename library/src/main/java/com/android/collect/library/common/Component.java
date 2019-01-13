package com.android.collect.library.common;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Component {
    public static int TOP = 10;//组件间距�?
    public static int BOTTOM = 10;//组件间距�?
    public static int ZERO = 0;//组件间距�?
    public static int TEXT_PADDING = 5;//文字离边框距�?

    /**
     * 3DES 解密(String).
     *
     * @param keyData
     * @param crypt   byte[]
     * @return byte[]
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static String desDecrypt(String keyData, String crypt) throws Exception {
        SecretKey key = new SecretKeySpec(keyData.substring(0, 24).getBytes("UTF-8")
                , "DESede");
        String Algorithm = "DESede";
        SecureRandom sr = new SecureRandom();
        byte[] plainByte = null;
        try {
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, key, sr);
            plainByte = c1.doFinal(Base64.decode(crypt));
            return new String(plainByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //keybyte为加密密钥，长度�?24字节
    //src为被加密的数据缓冲区（源�?
    public static String encryptMode(String key, String src) {
        if (key.length() < 24)
            key = prefixZero(key, 24);
        String Algorithm = "DESede";
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(key.substring(0, 24).getBytes("UTF-8"), Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.encode(c1.doFinal(src.getBytes("UTF-8")));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static String prefixZero(String paramString, int paramInt) {
        StringBuffer localStringBuffer = new StringBuffer(paramInt);
        for (int i = 0; i < paramInt - paramString.length(); ++i)
            localStringBuffer.append('0');
        return paramString + localStringBuffer.toString();
    }


    @SuppressLint("TrulyRandom")
    public static String desDecrypt(String crypt) throws Exception {
        if (TextUtils.isEmpty(crypt)) {
            return "";
        }

        SecretKey key = new SecretKeySpec(Constant.spu.substring(0, 24).getBytes("UTF-8")
                , "DESede");
        String Algorithm = "DESede";
        SecureRandom sr = new SecureRandom();
        byte[] plainByte = null;
        try {
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, key, sr);
            plainByte = c1.doFinal(Base64.decode(crypt));
            return new String(plainByte, "UTF-8");
        } catch (Exception e) {
//			e.printStackTrace();
            return "";
        }
    }

    //keybyte为加密密钥，长度�?24字节
    //src为被加密的数据缓冲区
    public static String encryptMode(String src) {
        String key = Constant.spu;
        if (key.length() < 24)
            key = prefixZero(key, 24);
        String Algorithm = "DESede";
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(key.substring(0, 24).getBytes("UTF-8"), Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.encode(c1.doFinal(src.getBytes("UTF-8")));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
