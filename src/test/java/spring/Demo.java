package spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Demo {

	public static void main(String[] args) {
		
		String s = "vqe653bqjbk7922121be32b@#3$";
		char[] ss = s.toCharArray();
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		List l3 = new ArrayList();
		for (int i = 0; i < ss.length; i++) {
			if ((ss[i] + "").matches("[0-9]")) {
				l1.add(ss[i]);
			} else if ((ss[i] + "").matches("[A-Z]") || (ss[i] + "").matches("[a-z]")) {
				l2.add(ss[i]);
			} else {
				l3.add(ss[i]);
			}
		}
		Collections.sort(l1);
		Collections.sort(l2);
		Collections.sort(l3);
		for (int a = 0; a < l1.size(); a++) {
			System.out.print(l1.get(a));
		}
		System.out.println();
		for (int b = 0; b < l2.size(); b++) {
			System.out.print(l2.get(b));
		}
		System.out.println();
		for (int c = 0; c < l3.size(); c++) {
			System.out.print(l3.get(c));
		}
		System.out.println(formatTime(11111l));
	}
	public static String formatTime(Long ms) {  
	    Integer ss = 1000;  
	    Integer mi = ss * 60;  
	    Integer hh = mi * 60;  
	    Integer dd = hh * 24;  
	  
	    Long day = ms / dd;  
	    Long hour = (ms - day * dd) / hh;  
	    Long minute = (ms - day * dd - hour * hh) / mi;  
	    Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
	    Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
	      
	    StringBuffer sb = new StringBuffer();  
	    if(day > 0) {  
	        sb.append(day+"天");  
	    }  
	    if(hour > 0) {  
	        sb.append(hour+"小时");  
	    }  
	    if(minute > 0) {  
	        sb.append(minute+"分");  
	    }  
	    if(second > 0) {  
	        sb.append(second+"秒");  
	    }  
	    if(milliSecond > 0) {  
	        sb.append(milliSecond+"毫秒");  
	    }  
	    return sb.toString();  
	}  
	/**
	 * 斐波那契数列 java 兔子的规律为数列1,1,2,3,5,8,13,21
	 * 
	 * @param i
	 * @return
	 */
	private static int aa(int i) {
		if (i == 1 || i == 2) {
			return 1;
		} else {
			return aa(i - 1) + aa(i - 2);
		}
	}

	public static void bb() {
		int arr[] = new int[10];
		arr[0] = arr[1] = 1;
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>(new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Integer t1 = (Integer) o1;
				Integer t2 = (Integer) o2;
				return -t1.compareTo(t2);
			}

		});
		for (int i = 2; i < arr.length; i++) {
			arr[i] = arr[i - 1] + arr[i - 2];
			System.out.print(arr[i] + " ");
			tm.put(arr[i], arr[i]);

		}
		System.out.println();
		System.out.println(tm);
	}

	/**
	 * 九九乘法表  
	 */
	public static void cfb() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + "*" + i + "=" + (i * j) + "\t");
			}
			System.out.println();
		}
	}
}
