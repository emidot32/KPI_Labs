package common;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.Desktop;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Helper {
	
  /** Returns an ImageIcon, or null if the path was invalid. */
  public static ImageIcon createImageIcon(String path) {
  	
  	path="/images/"+path;
  	java.net.URL imgURL =Helper.class.getResource(path);
  	
  	/*path="images/"+path;
  	java.net.URL imgURL =ClassLoader.getSystemResource(path);*/
  	if (imgURL != null) {
  		return new ImageIcon(imgURL);
    } 
  	else{
  		System.err.println("Couldn't find file: " + path);
  		return null;
     }
  }

  
	public static void openWebpage(String address) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    boolean open=false;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	    try {
	      desktop.browse(new URI(address));
	    	open=true;
	    } catch (Exception e) {
	    }
    }
    if(!open){
    	JOptionPane.showMessageDialog(null, "cann't browse "+address, "Error" , JOptionPane.ERROR_MESSAGE);
    }
	}

  
}
