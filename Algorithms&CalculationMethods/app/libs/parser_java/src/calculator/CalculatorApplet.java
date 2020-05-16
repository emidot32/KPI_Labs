package calculator;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;


@SuppressWarnings("serial")
public class CalculatorApplet extends Applet{
	
	public void init(){
		setLayout(new BorderLayout());
		String s;
		
		CalculatorPanel.CalculatorLanguage language=CalculatorPanel.CalculatorLanguage.ENGLISH;
		s = getParameter("language");
		if(s!=null && s.toLowerCase().equals("russian")){
			language=CalculatorPanel.CalculatorLanguage.RUSSIAN;
		}
		
		s = getParameter("useMemory");
		boolean useMemory=(s==null || s.toLowerCase().equals("true"));
		
		s = getParameter("useBuffer");
		boolean useBuffer=(s==null || s.toLowerCase().equals("true"));
		
		s = getParameter("useLanguageSelector");
		boolean useLanguageSelector=(s==null || s.toLowerCase().equals("true"));
		
		
		Color background=CalculatorPanel.defaultBackgroundColor;
		s = getParameter("background");
		if(s!=null){
			try{
				background=new Color(Integer.parseInt(s,16));
			}
			catch(NumberFormatException exception){
			}
		}
		
		CalculatorPanel calculator=new CalculatorPanel(language,useMemory,useBuffer,useLanguageSelector,background);
		add(calculator);
	}

}
