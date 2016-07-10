package com.zlm.audio.util;

import java.text.DecimalFormat;

/**
 * 媒体处理类
 * 
 * @author zhangliangming
 * 
 */
public class MediaUtil {

	public static String getFileExt(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if (pos == -1)
			return "";
		return filePath.substring(pos + 1).toLowerCase();
	}

	/**
	 * 时间格式转换
	 * 
	 * @param progress
	 * @return
	 */
	public static String formatTime(int progress) {
		progress /= 1000;
		int minute = progress / 60;
		// int hour = minute / 60;
		int second = progress % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	/**
	 * 计算文件的大小，返回相关的m字符串
	 * 
	 * @param fileS
	 * @return
	 */
	public static String getFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
