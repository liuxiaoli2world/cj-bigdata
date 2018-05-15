package com.lgsc.cjbd.book.util;

import java.util.ArrayList;
import java.util.List;

public class TagList {
	
	private List<String> list ;
	
	public TagList() {
		this.list = new ArrayList<>();
	}
	
	public boolean remove(String tag){
		if (!list.isEmpty()) {
			int lastIndex = list.size()-1;
			String last =  list.get(lastIndex);
			if (last.equals(tag)) {
				list.remove(lastIndex);
				return true;
			}
		}
		return false;
	}
	
	public void add(String tag){
		list.add(tag);
	}
	
	public int size(){
		return list.size();
	}
	
	public String get(int index){
		return list.get(index);
	}
}
