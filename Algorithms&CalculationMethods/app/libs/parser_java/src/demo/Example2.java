package demo;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import estimator.ExpressionEstimator;

public class Example2 {

  public static void main(String[] args){
    ExpressionEstimator estimator=new ExpressionEstimator();
    try {
      estimator.compile("x0+x1");
      double[][]values={ {3,6}, {8,6} };
      for(double[]v:values){
        System.out.println( estimator.calculate(v) );
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}