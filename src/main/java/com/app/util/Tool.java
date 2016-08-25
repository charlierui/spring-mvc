/*
 * Tool.java
 *
 * Created on 2006年9月12日, 下午10:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.app.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import sun.misc.BASE64Encoder;

/**
 * 工具类，包含许多工具方法。
 * 
 * @author
 */

public class Tool {

	/** Creates a new instance of Tool */
	public Tool() {
	}

	/**
	 * 截取 “,”,返回List<Long>集合
	 */
	public static List<Long> splitdh(String navids) {
		List<Long> list = new ArrayList<Long>();
		//navids = "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26";
		if (navids.length() > 0) {

			String[] aa = navids.split("\\,");
			Long[] nav = null;
			for (int i = 0; i < aa.length; i++) {
				list.add(Long.parseLong(aa[i].toString()));
			}
		}
		return list;
	}

	/**
	 * 获得当前日期
	 * 
	 */
	public static String nowDate() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String a = sdf1.format(date);
		return a;
	}

	/**
	 * 起止日期比较
	 * 
	 * @param s
	 *            描述整型数据的字符串
	 * @return 返回转换好的int，如果转换时出错则返回0。
	 */
	public static int compare_date(Date dt1, Date dt2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// Date dt1 = df.parse(DATE1);
			// Date dt2 = df.parse(DATE2);
			if (dt2.getTime() >= dt1.getTime()) {
				return 1;
			} else {

				return 0;
			}
		} catch (Exception exception) {

			exception.printStackTrace();
			return 0;
		}

	}

	/**
	 * 将字符串表示的整型数据转化为int型。
	 * 
	 * @param s
	 *            描述整型数据的字符串
	 * @return 返回转换好的int，如果转换时出错则返回0。
	 */
	public static int sToI(String s) {
		return sToI(s, 0);
	}

	/**
	 * 将字符串表示的整型数据转化为int型。
	 * 
	 * @param s
	 *            描述整型数据的字符串
	 * @param defaultValue
	 *            转换失败时默认返回的值。
	 * @return 返回转换好的int，如果转换时出错则返回defaultValue。
	 */
	public static int sToI(String s, int defaultValue) {
		int i = defaultValue;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
		}
		return i;
	}

	/**
	 * 将字符串表示的整型数据转化为long型。
	 * 
	 * @param s
	 *            描述整型数据的字符串
	 * @param defaultValue
	 *            转换失败时默认返回的值。
	 * @return 返回转换好的int，如果转换时出错则返回defaultValue。
	 */
	public static long sToL(String s, long defaultValue) {
		long i = defaultValue;
		try {
			i = Long.parseLong(s);
		} catch (Exception e) {
		}
		return i;
	}

	/**
	 * 将字符串表示的整型数据转化为float型。
	 * 
	 * @param s
	 *            描述float数据的字符串
	 * @param defaultValue
	 *            转换失败时默认返回的值。
	 * @return 返回转换好的float，如果转换时出错则返回defaultValue。
	 */
	public static float sToF(String s, float defaultValue) {
		float f = defaultValue;
		try {
			f = Float.parseFloat(s);
		} catch (Exception e) {
		}
		return f;
	}

	/**
	 * 将字符串表示的整型数据转化为float型。
	 * 
	 * @param s
	 *            描述float数据的字符串
	 * @param defaultValue
	 *            转换失败时默认返回的值。
	 * @return 返回转换好的float，如果转换时出错则返回defaultValue。
	 */
	public static double sToD(String s, double defaultValue) {
		double f = defaultValue;
		try {
			f = Double.parseDouble(s);
		} catch (Exception e) {
		}
		return f;
	}

	/**
	 * 将字符串格式化为给定长度（length）的字符串。<BR>
	 * 长度不足时，在字符串前面补充给定字符（filling）。<BR>
	 * 
	 * @param source
	 *            需要格式化的字符串。
	 * @param length
	 *            目标字符串的长度。
	 * @param filling
	 *            字符串长度不足时补充的字符。
	 * @return 返回格式化好的字符串。
	 */
	public static String format(String source, int length, char filling) {
		while (source.length() < length) {
			source = filling + source;
		}
		return source;
	}

	/**
	 * 将整型数字格式化为给定长度（length）的字符串。<BR>
	 * 长度不足时，在字符串前面补充给定字符（filling）。<BR>
	 * 
	 * @param source
	 *            需要格式化的整型数字。
	 * @param length
	 *            目标字符串的长度。
	 * @param filling
	 *            字符串长度不足时补充的字符。
	 * @return 返回格式化好的字符串。
	 */
	public static String format(int source, int length, char filling) {
		return format("" + source, length, filling);
	}

	/**
	 * 将日期（date）转换为给定模式（format）的字符串。<BR>
	 * 如果给定日期为null返回默认字符串（defaultV）。<BR>
	 * 给定模式为null或为空字符串时使用“yyyy年MM月dd日 HH:mm:ss”模式。<BR>
	 * 
	 * @param date
	 *            需要转换的日期型对象。为java.util.Date类或其子类的对象。
	 * @param format
	 *            转换的模式，默认为yyyy年MM月dd日 HH:mm:ss
	 * @param defaultV
	 *            默认字符串，给定日期为null返回。
	 * @return 返回格式化好的字符串，如果给定日期为null返回默认字符串。
	 */
	public static String formatTime(java.util.Date date, String format, String defaultV) {
		if (date == null)
			return defaultV;
		if (format == null || format.trim().equals(""))
			format = "yyyy年MM月dd日 HH:mm:ss";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		String dateS = sdf.format(date);
		return dateS;
	}

	/**
	 * 截取日期的年月 格式：2012-01-01 字符串格式
	 */
	public static String formatTime(java.util.Date date) {
		if (date == null) {
			date = new Date();
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 字符串日期格式，转换成Date
	 * 
	 * @param String
	 * @return yyyy--MM-dd
	 */
	public static Date formatDate(String date) {

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			if (date != null && date.length() > 0) {
				d = df.parse(date);
			} else {
				d = null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 将毫秒数表示的日期（time）转换为给定模式（format）的字符串。<BR>
	 * 如果给定日期为0返回默认字符串（defaultV）。<BR>
	 * 给定模式为null或为空字符串时使用“yyyy年MM月dd日 HH:mm:ss”模式。<BR>
	 * 
	 * @param time
	 *            需要转换的日期（毫秒数）。
	 * @param format
	 *            转换的模式，默认为yyyy年MM月dd日 HH:mm:ss
	 * @param defaultV
	 *            默认字符串，给定日期毫秒数为0返回。
	 * @return 返回格式化好的字符串，如果给定日期为null返回默认字符串。
	 */
	public static String formatTime(long time, String format, String defaultV) {
		if (time == 0L)
			return defaultV;
		java.util.Date date = new java.util.Date(time);
		return formatTime(date, format, defaultV);
	}

	/**
	 * 将字符串表示的日期转换为日期的毫秒数。<BR>
	 * 字符串必须按"yyyy-MM-dd HH:mm:ss"模式。<BR>
	 * 转换失败时返回0。<BR>
	 * 
	 * @param timeS
	 *            表示日期的字符串
	 * @return 返回日期的毫秒数。转换出错时返回0。
	 */
	public static long parseTime(String timeS) {
		return parseTime(timeS, "yyyy-MM-dd HH:mm:ss").getTime();
	}

	/**
	 * 将字符串表示的日期根据给定模式（format）转换为日期的毫秒数。<BR>
	 * 转换失败时返回0。<BR>
	 * 
	 * @param timeS
	 *            表示日期的字符串
	 * @param format
	 *            表示转换的模式
	 * @return 返回日期的毫秒数。转换出错时返回0。
	 */
	public static Date parseTime(String timeS, String format) {
		if (format == null || format.trim().equals(""))
			format = "yyyy-MM-dd HH:mm:ss";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(timeS);
		} catch (Exception ex) {
			// ex.printStackTrace();
			date = null;
		}
		return date;
	}

	/**
	 * 修剪字符串。将字符串修剪为给定长度（length）的字符串。字符串以filling结束。<BR>
	 * 源字符串长度不大于length时，不修剪。<BR>
	 * 源字符串长度大于length时，将字符串修剪为以filling结束 总长度为length的字符串。<BR>
	 * 例：<BR>
	 * shear("1234567890",10,"a") 返回1234567890；<BR>
	 * shear("123456",10,"a") 返回123456；<BR>
	 * shear("1234567890",6,"a") 返回12345a；<BR>
	 * shear("1234567890",6,"ab") 返回1234ab；<BR>
	 * 
	 * @param source
	 *            需要修剪的字符串
	 * @param length
	 *            修剪后字符串的最大长度。
	 * @param filling
	 *            字符串长度大于length时，修剪后的字符串以filling结尾。
	 * @return 修剪后的字符串。
	 */
	public static String shear(String source, int length, String filling) {
		if (source.length() > length) {
			source = source.substring(0, length - filling.length()) + filling;
		}
		return source;
	}

	/**
	 * 模糊字符串<BR>
	 * 用于sql模糊查询<BR>
	 * 将字符串中的空格替换为“%”<BR>
	 * 字符串两头添加“%” <BR>
	 * 
	 * @param strSource
	 *            需要模糊的字符串
	 * @return 模糊后的字符串
	 */
	public static String fuzzy(String strSource) {
		String strResult = null;
		if (strSource == null || strSource.trim().length() == 0) {
			strResult = "%";
		} else {
			strResult = "%" + strSource.replace(' ', '%') + "%";
			strResult = strResult.replaceAll("%+", "%");
		}
		return strResult;
	}

	/**
	 * 利用MD5进行加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encoderByMd5(String str) {
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			String newstr = base64en.encode(md5.digest(str.getBytes()));
			return newstr;
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 去掉HTML中的script代码
	 * 
	 */
	public static String slipScript(String str) {

		str = str.replaceAll("(?i)<script(.|\\s)*>(.|\\s)*</script>", "");
		// str = str.replaceAll("(?i)<script[^>]*/>","");
		return str;
	}

	/**
	 * 去掉HTML中的iframe代码
	 * 
	 */
	public static String slipIframe(String str) {

		str = str.replaceAll("(?i)<iframe(.|\\s)*>(.|\\s)*</iframe>", "");
		// str = str.replaceAll("(?i)<script[^>]*/>","");
		return str;
	}

	/**
	 * 去掉HTML中的html代码
	 * 
	 */
	public static String slipHtml(String str) {

		str = str.replaceAll("<[^>]+>", "");
		str = str.replaceAll("\r\n", "\n");
		// str = Replace(str, "\r\n", "\n");
		// str = str.replaceAll("(?i)<script[^>]*/>","");
		return str;
	}

	/**
	 * 将整型表示的级别转换为星级（★，☆）表示。<BR>
	 * 例：<BR>
	 * starLevel(5,2) 返回★★☆☆☆<BR>
	 * starLevel(5,3) 返回★★★☆☆<BR>
	 * 
	 * @param totle
	 *            最高级别
	 * @param level
	 *            当前级别
	 * @return 级别的星级表示
	 */
	public static String starLevel(int totle, int level) {
		StringBuffer stars = new StringBuffer();
		for (int i = 0; i < level; i++) {
			stars.append("★");
		}
		for (int i = level; i < totle; i++) {
			stars.append("☆");
		}
		return stars.toString();
	}

	/**
	 * 
	 * @param arg
	 */
	public static void main(String arg[]) {
		/*
		 * java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		 * java.sql.Timestamp t = new java.sql.Timestamp(new
		 * java.util.Date().getTime());
		 * System.out.println("Date:"+formatTime(d,null,"none"));
		 * System.out.println("Timestamp:"+formatTime(d,null,"none"));
		 */
		// String str = "<tr><td id=\"mainBody\"> " +
		// "<iframe scrolling=\"yes\" width=\100%\" height=\"100%\"" +
		// " frameborder=\"0\" id=\"mainFrame\" src=\"${ctx}/index.jsp\" >
		// </iframe> </td></tr>";
		// String ss="<div id='mini_nav_qq'><li><a target='_top' " +
		// "href='http:// lady.qq.com/emo/emotio.shtml'>情感</a></li><li>" +
		// "<a target='_top'
		// href='http://lady.qq.com/beauty/beauty.shtml'>美容</a></li></div>";
		//
		//
		// System.out.println(slipHtml(str));
		// float defaultValue = 0;
		// String a="2015-01-25";
		// String b="2015-01-23";
		// System.out.println(compare_date(formatDate(a),formatDate(b)));
		// DecimalFormat df1 = new DecimalFormat("0.0");
		//
		// DecimalFormat df2 = new DecimalFormat("#.#");
		//
		// DecimalFormat df3 = new DecimalFormat("000.000");
		//
		// DecimalFormat df4 = new DecimalFormat("###.###");
		//
		// System.out.println(df1.format(12.34));
		//
		// System.out.println(df2.format(1.5));
		//
		// System.out.println(df3.format(12.34));
		//
		// System.out.println(df4.format(12.34));

		// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//
		// Date d=new Date();
		// System.out.println(sdf1.format(d));
		// System.out.println(nowDate());
		System.out.println(getUUID());
	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		// +s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)
		return s.substring(0, 8);
	}
}
