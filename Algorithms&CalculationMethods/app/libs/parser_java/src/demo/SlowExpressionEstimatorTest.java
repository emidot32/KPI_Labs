package demo;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import estimator.SlowExpressionEstimator;

public class SlowExpressionEstimatorTest {
	public static void main(String[] args){
		String expression[]={"sin(pi/4)","1+2+"};
		for(String s:expression){
			System.out.print("\""+s+"\"=");
			try {
				System.out.println(SlowExpressionEstimator.estimate(s));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
