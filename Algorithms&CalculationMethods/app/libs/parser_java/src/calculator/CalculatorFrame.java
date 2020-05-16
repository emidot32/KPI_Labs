package calculator;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import common.Helper;


@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame{

	public CalculatorFrame() {
		super("calculator");
	  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	  addWindowListener(new WindowAdapter(){
	    public void windowClosing(WindowEvent e){
	    	System.exit(0);
	    }
	  });
	  
	  setIconImage(Helper.createImageIcon("calculator.png").getImage());
	  
	  //Color background=new Color(Integer.parseInt("e808e8",16));
	  //add( new CalculatorPanel(CalculatorLanguage.ENGLISH,false,true,false,background) );
	  //add( new CalculatorPanel(CalculatorLanguage.ENGLISH,false,true,true,background) );
	  //add( new CalculatorPanel(CalculatorLanguage.ENGLISH,false,false,true,background) );
	  //add( new CalculatorPanel(CalculatorLanguage.ENGLISH,false,false,false,background) );
	  
	  add(new CalculatorPanel());
	  
	  pack();
	  setLocationRelativeTo(null);
	  setVisible(true);
	}
	
	public static void main(String[] args) {
		new CalculatorFrame();
	}
}