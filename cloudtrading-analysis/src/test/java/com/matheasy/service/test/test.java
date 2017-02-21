package com.matheasy.service.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
public static void main(String[] args) {
//	Arrays.
	int[] n={5,11,6,7,8,9,100,6};
	Arrays.sort(n);
	for(int i:n)
	System.out.println(i);
	Object o=new Object();
	Iterator i=new ArrayList().iterator();
	
	ApplicationContext ctx = new ClassPathXmlApplicationContext("");
}
}
