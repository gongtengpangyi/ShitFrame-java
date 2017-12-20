package shit.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 一个简单的文件操作工具类
 * 
 * @author GongTengPangYi
 *
 */
public class ShitFileHelper {
	/**
	 * 根据文件的URL来读取文件
	 * 
	 * @param url
	 *            文件的地址
	 * @return 文件的字节
	 */
	public static byte[] read(String url) {
		int i;
		byte[] buff = null;
		try {
			FileInputStream inputStream = new FileInputStream(url);
			i = inputStream.available();
			buff = new byte[i];
			inputStream.read(buff);
			// 记得关闭输入流
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// byte数组用于存放图片字节数据
		return buff;
	}

	/**
	 * 根据URL将字节写入文件
	 * 
	 * @param buff
	 *            字节
	 * @param url
	 *            储存路径
	 * @return
	 */
	public static boolean write(byte[] buff, String url) {
		try {
			File file = new File(url);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outPutStream = new FileOutputStream(file);
			outPutStream.write(buff);
			outPutStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * base64解码
	 * 
	 * @param str
	 *            base64字符串
	 * @return 字节流
	 */
	@SuppressWarnings("restriction")
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bt;
	}

	/**
	 * base64编码
	 * 
	 * @param b
	 *            字节流
	 * @return base64字符串
	 */
	@SuppressWarnings("restriction")
	public static String encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return (new sun.misc.BASE64Encoder()).encode(b);
	}
}
