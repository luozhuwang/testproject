package com.sy.tool;

import java.util.Comparator;


public class MapKeyComparator implements Comparator<String> {  
	@Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }

	public static void main(String args[]){
		MapKeyComparator compare=new MapKeyComparator();
		String data1="20180914";
		String data2="20180904";
		
		System.out.println(compare.compare(data1, data2));
	}
}
