package spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {
	static Socket server;  
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		server = new Socket(InetAddress.getLocalHost(), 8001);  
        BufferedReader in = new BufferedReader(new InputStreamReader(  
                server.getInputStream()));  
        PrintWriter out = new PrintWriter(server.getOutputStream());  
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
       // while (true) {  
            String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><note><heading>Reminder</heading><body>数据测试</body></note> ";  
            out.println(str);  
            out.flush();  
       //     if (str.equals("end")) {  
       //         break;  
       //     }  
       //     System.out.println(in.readLine());  
       // }  
        server.close();  
	}

}
