package spring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;

public class testweb {
	public static void main(String[] args) throws Exception {

		sendSms();
	}

	public static void sendSms() throws Exception {
		String qqCode = "12344";// qq号码
		String urlString = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx";
		String xml = testweb.class.getClassLoader().getResource("SendInstantSms.xml").getFile();
		String xmlFile = replace(xml, "qqCodeTmp", qqCode).getPath();
		String soapActionString = "http://WebXml.com.cn/qqCheckOnline";
		URL url = new URL(urlString);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		File fileToSend = new File(xmlFile);
		byte[] buf = new byte[(int) fileToSend.length()];
		new FileInputStream(xmlFile).read(buf);
		httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("soapActionString", soapActionString);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		out.write(buf);
		out.close();

		byte[] datas = readInputStream(httpConn.getInputStream());
		String result = new String(datas);
		// 打印返回结果
		System.out.println("result:" + result);
	}

	/**
	 * 文件内容替换
	 * 
	 * @param inFileName
	 *            源文件
	 * @param from
	 * @param to
	 * @return 返回替换后文件
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static File replace(String inFileName, String from, String to)
			throws IOException, UnsupportedEncodingException {
		File inFile = new File(inFileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "utf-8"));
		File outFile = new File(inFile + ".tmp");
		PrintWriter out = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8")));
		String reading;
		while ((reading = in.readLine()) != null) {
			out.println(reading.replaceAll(from, to));
		}
		out.close();
		in.close();
		// infile.delete(); //删除源文件
		// outfile.renameTo(infile); //对临时文件重命名
		return outFile;
	}

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
}
