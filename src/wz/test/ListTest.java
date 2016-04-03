package wz.test;

import java.util.LinkedList;
import java.util.List;

public class ListTest {
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int count=0;
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		while(!list.isEmpty()){
//			Integer i = list.
			Integer i = list.pollFirst();
			if(i < 4){
				list.addLast(i*6);
			}else{
				System.out.println(i);
			}
		}
//		for(Integer i:list){
//			if(i<4){
//				list.remove(i);
//				list.add(i*6);
//			}else{
//				System.out.println(i);
//			}
//		}
	}
}
