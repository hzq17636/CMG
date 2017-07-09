package com.jz.crm.base.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
	
    public static String toMD5(String s) {
        return DigestUtils.md5Hex(s);
    }

    public static void main(String[] args) {
//        System.out.println("7:\t " + toMD5("7"));
//        System.out.println("1:\t" + toMD5("1"));
//        System.out.println("123456:\t" + toMD5("123456"));
//        System.out.println(":\t" + toMD5(""));
//        System.out.println("null:\t" + toMD5(null));
//        System.out.println("test1:\t" + toMD5("test1"));
//        System.out.println("test2:\t" + toMD5("test2"));
//        System.out.println("ghccing9119:\t" + toMD5("ghccing9119"));
//        System.out.println("ghccing119:\t" + toMD5("ghccing119"));
    	System.out.println("---"+toMD5("123"));
    }
}
