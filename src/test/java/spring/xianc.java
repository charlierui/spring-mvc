package spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class xianc implements Runnable {

	 
	private static ThreadLocal<Integer> th = new ThreadLocal<Integer>();
	Integer   count =  5;
	@Override
	public synchronized void  run() {

		 count = th.get();
		for (int i = 0; i < 5; i++) {

			if (count == null) {
				count = 5;
			}
			System.out.println(Thread.currentThread().getName() + "运行  count= " + count--);
			th.set(count);			
		}

	}

	

	public static void main(String[] args) {
		xianc my = new xianc();
		for(int i=0;i<3;i++){
		new Thread(my, "C"+i).start();// 同一个mt，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
		new Thread(my, "D"+i).start();
		new Thread(my, "E"+i).start();
		}
//		Map<Object, Object> ma= Collections.synchronizedMap(new HashMap());
//		Map<Object, Object> ma1=new Hashtable<>();
//		Hashtable ha=new Hashtable<>();
//		ConcurrentHashMap cm=new ConcurrentHashMap();
//		ma.put("1", "aaa");
//		cm.put("2", "nnn");
//		cm.put(1, 2);
//		List list=new ArrayList<>();
//		ma.containsKey("1");
//		Iterator iterator = cm.entrySet().iterator();
//	    while (iterator.hasNext()) {
//	    	Map.Entry<Object, Object> entry1=(Map.Entry<Object, Object>)iterator.next();    
//	        System.out.println(entry1.getKey()+"=="+entry1.getValue());    
//	    }
//		//System.out.println(ma.get("1").hashCode()+" --- "+ha.get("2"));
	}
}
