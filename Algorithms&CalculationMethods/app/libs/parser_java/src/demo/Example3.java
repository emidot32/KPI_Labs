package demo;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.util.Arrays;

import estimator.ExpressionEstimator;

public class Example3 {

  public static void main(String[] args) throws Exception{
    String expression[]={"sin(pi/4)","1+2+"};
    for(String s:expression){
      System.out.print("\""+s+"\"=");
      try {
        System.out.println(ExpressionEstimator.calculate(s));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    ExpressionEstimator estimator=new ExpressionEstimator();
    expression=new String[]{"3*x0+2*x1*x0","6*x0"};
    double[][]values={ {3,6}, {8,6} };
    double[]v;
    int i=0;
    for(String s:expression){
      v=values[i++];
      System.out.print("\""+s+"\""+Arrays.toString(v)+"=");
      try {
        estimator.compile(s);
        System.out.println(estimator.calculate(v));
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

  }

}