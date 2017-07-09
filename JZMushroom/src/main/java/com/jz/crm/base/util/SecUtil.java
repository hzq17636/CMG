package com.jz.crm.base.util;

import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具<br>
 * @author: lizeyi<br>
 * @ZTX Holdings Limited
 * @date_time: 2014-4-9 下午09:57:54
 * @EMail: lizeyi01@chinatopcredit.com
 */
public class SecUtil {

    private static byte[] keys = "1234567890abcdff".getBytes();

    /**
     * @param args
     */
    private static BufferedReader in;

	public static void main(String[] args) throws Exception {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String password = "a3d025e4eee1b3e514b52214c45c8b8f";
		System.out.println(decrypt(password));
		String line = "============================================================";
		System.out.println(line);
		while (true) {
			in = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("请输入 : ");
				String str = in.readLine();
				System.out.println("加密后数据  : " + str + " >>" + encrypt(str));
				System.out.println(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 加密
     * 
     * @param value
     * @return
     */
    public static String encrypt(String value) {
        String s = null;
        int mode = Cipher.ENCRYPT_MODE;
        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher.doFinal(value.getBytes());

            s = String.valueOf(Hex.encodeHex(outBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 解密
     * 
     * @param value
     * @return
     */
    public static String decrypt(String value) {
        String s = null;
        int mode = Cipher.DECRYPT_MODE;
        try {
            Cipher cipher = initCipher(mode);
            byte[] outBytes = cipher.doFinal(Hex.decodeHex(value.toCharArray()));

            s = new String(outBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static Cipher initCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(keys, "AES");
        cipher.init(mode, key);
        return cipher;
    }
}
