package com.lgsc.cjbd.book.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtil {
	
	public static String generateUniqueDir(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		return sdf.format(new Date());
	}

	public static String generateDirCode(int i) throws Exception{
		String str_i = "";
		if (i<10) {
			str_i = "00" + i;
		}else if(i<100){
			str_i = "0" + i;
		}else if(i<1000){
			str_i = "" + i;
		}else {
			throw new Exception("章节不能超过1000(包含1000)!");  
		}
		return str_i;
	}
	
	//public static 
//	public static void main(String[] args) throws Exception {
//		System.out.println(generateDirCode(01));
//		System.out.println((int)0001);
//		System.out.println(Integer.parseInt("002001")+1);
//		System.out.println("001012004".substring("001012004".length()-3));
//	}
}
