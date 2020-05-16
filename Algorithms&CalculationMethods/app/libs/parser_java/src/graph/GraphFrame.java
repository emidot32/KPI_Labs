package graph;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import common.Helper;

@SuppressWarnings("serial")
public class GraphFrame extends JFrame{

	public GraphFrame() {
		super("graph");
	  setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	  addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
	    	System.exit(0);
	    }
	  });
	  setIconImage(Helper.createImageIcon("graph.png").getImage());
	  
	  add( new GraphPanel() );
	  setSize(new Dimension(800,600));
	  setLocationRelativeTo(null);
	  setVisible(true);
	}
	
	public static void main(String[] args) {
		new GraphFrame();
	}
}
