package demo;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import estimator.ExpressionEstimator;
import estimator.SlowExpressionEstimator;

public class ExpressionEstimatorsTest {
	
	static String fromBoolean(boolean b){
		return b?"ok":"error";
	}
	
	public static void main(String[] args) {
		String[] expression={
			"+1*2+3","+(+2)","-(-2)","-max(+1,-2)","ceil(5.6)","ceil(-5.6)",
			"1+2*3","1*2*sin(+3)","1*2+(","1*2-(","sin(pow(pi,e))","sin(1+3*4))","sin()","sin(","sin)","+-2",
			"2/+2","+2","1+-2"
		};
		
		int i;
		double v[]=new double[2];
		boolean ok[]=new boolean[2];
		
		final int FAST=0;
		final int SLOW=1;
		boolean error=false;
		
		for(String s:expression){
			for(i=0;i<2;i++){
				try {
					v[i]= (i==FAST?ExpressionEstimator.calculate(s):SlowExpressionEstimator.estimate(s));
					ok[i]=true;
				} catch (Exception e) {
					//System.err.println(e.getMessage());
					ok[i]=false;
				}
			}
			if(ok[FAST]!=ok[SLOW]){
				System.err.println(s+" FAST="+fromBoolean(ok[FAST])+" SLOW="+fromBoolean(ok[SLOW]) );
				error=true;
				break;
			}
			else if(ok[FAST] && ok[SLOW] && v[FAST]!=v[SLOW]){
				System.err.println(s+" v[FAST]="+v[FAST]+" v[SLOW]="+v[SLOW]);
				error=true;
				break;
			}
		}
		if(!error){
			System.out.println("the end");
		}
	}

}
