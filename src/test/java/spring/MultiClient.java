package spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClient extends Thread {
	 private Socket client;  
	  
	    public MultiClient(Socket c) {  
	        this.client = c;  
	    }  
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server = new ServerSocket(8001);  
        while (true) {  
            // transfer location change Single User or Multi User  
  
            MultiClient mc = new MultiClient(server.accept());  
            mc.start();  
        }  
	}
	
	  
	    public void run() {  
	        try {  
	            BufferedReader in = new BufferedReader(new InputStreamReader(  
	                    client.getInputStream()));  
	            PrintWriter out = new PrintWriter(client.getOutputStream());  	  
	           // while (true) {  
	                String str = in.readLine();  
	                System.out.println("我是服务器，接收到："+str);  
	                out.println("has receive....");  
	                out.flush();  
	          //      if (str.equals("end"))  
	           //         break;  
	           // }  
	            client.close();  
	        } catch (IOException ex) {  
	        } finally {  
	        }  
	    }  
}
