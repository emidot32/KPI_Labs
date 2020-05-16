package graph;

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
public class GraphApplet extends Applet{
	
	public void init(){
		setLayout(new BorderLayout());
		
		String s;
		
		Color background=GraphPanel.defaultBackgroundColor;
		s = getParameter("background");
		if(s!=null){
			try{
				background=new Color(Integer.parseInt(s,16));
			}
			catch(NumberFormatException exception){
			}
		}
		
		add(new GraphPanel(background));
	}

}
