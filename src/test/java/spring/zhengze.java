package spring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zhengze {
	public static void main(String[] args) {	
//		String bbb="13612691521";
//		String aaa="^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
//		boolean ccc=bbb.matches(aaa);
//		System.out.println(ccc);
//		
//		String s = "s  tr in  g"; 
//		s = s.replaceAll(" +"," "); 
//		System.out.println(s);	    
//	    Pattern p = Pattern.compile("  +");
//	    Matcher m = p.matcher("aa  foo  aab  foo  ab   foo    b");
//	    String str = m.replaceAll(" ");
//	    System.out.println(str);
	    
	    Integer [] numbers=new Integer[]{6,5,7,0,5,6,3,6,7,7,7,7}; 
	    int count=0;
	    int num=0;
	    List<Integer> list = Arrays.asList(numbers);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (Integer i : list) {
            Integer value = map.get(i);
            if (null == value) {
                value = 0;
            }
            map.put(i, value + 1);
        }
        System.out.println(map);

	}
}
