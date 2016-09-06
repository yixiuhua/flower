package pers.flower.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @Description : AES加密解密工具类
 * @Author : flower_ho@126.com
 * @Creation Date : 2014年6月15日 下午8:00:58
 */
public class AESUtil {

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param key
	 * @param ciphertext
	 */
	public static void encryptFile(File plaintext, String key, File ciphertext) {
		try {
			encryptFile(plaintext, key.getBytes("utf-8"), ciphertext);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param key
	 * @param ciphertext
	 */
	public static void encryptFile(String plaintext, String key,
			String ciphertext) {
		try {
			encryptFile(new File(plaintext), key.getBytes("utf-8"), new File(
					ciphertext));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param key
	 * @param ciphertext
	 */
	public static void encryptFile(String plaintext, byte[] key,
			String ciphertext) {
		encryptFile(new File(plaintext), key, new File(ciphertext));
	}

	/**
	 * 加密
	 * 
	 * @param plaintext
	 * @param key
	 * @param ciphertext
	 */
	public static void encryptFile(File plaintext, byte[] key, File ciphertext) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(key));
			// SecureRandom 实现完全隨操作系统本身的內部狀態，除非調用方在調用 getInstance 方法之後又調用了
			// setSeed 方法；该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux
			// 系统上则不同。
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key);
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
			in = new FileInputStream(plaintext);
			System.out.println(plaintext.length());
			out = new FileOutputStream(ciphertext);
			byte[] temp = new byte[(int) plaintext.length()];
			in.read(temp);
			out.write(cipher.doFinal(temp));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @param key
	 * @param plaintext
	 */
	public static void decryptFile(File ciphertext, String key, File plaintext) {
		try {
			decryptFile(ciphertext, key.getBytes("utf-8"), plaintext);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @param key
	 * @param plaintext
	 */
	public static void decryptFile(String ciphertext, String key,
			String plaintext) {
		try {
			decryptFile(new File(ciphertext), key.getBytes("utf-8"), new File(
					plaintext));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @param key
	 * @param plaintext
	 */
	public static void decryptFile(String ciphertext, byte[] key,
			String plaintext) {
		decryptFile(new File(ciphertext), key, new File(plaintext));
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @param key
	 * @param plaintext
	 */
	public static void decryptFile(File ciphertext, byte[] key, File plaintext) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(key));
			// SecureRandom 实现完全隨操作系统本身的內部狀態，除非調用方在調用 getInstance 方法之後又調用了
			// setSeed 方法；该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux
			// 系统上则不同。
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key);
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
			in = new FileInputStream(ciphertext);
			out = new FileOutputStream(plaintext);
			byte[] temp = new byte[(int) ciphertext.length()];
			in.read(temp);
			out.write(cipher.doFinal(temp));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
