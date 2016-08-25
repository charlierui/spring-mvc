package spring;

import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

public class zhengze {
	public static void main(String[] args) {	
		String bbb="logout";
		String aaa="(add.*)|(create.*)|(save.*)|(insert.*)|(update.*)|(del.*)|(delete.*)|(remove.*)";
		boolean ccc=bbb.matches(aaa);
		System.out.println(ccc);
		System.out.println(2<<2);
		
	}
}
