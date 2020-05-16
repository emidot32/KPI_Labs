package demo;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import estimator.ExpressionEstimator;

public class Example1 {

  public static void main(String[] args){

    try {
      double v=ExpressionEstimator.calculate("sin(pi/4)");
      System.out.println(v);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}