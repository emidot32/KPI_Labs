package graph;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import estimator.ExpressionEstimator;

@SuppressWarnings("serial")
public class MinMaxPanel extends JPanel{
	private JTextField t[]=new JTextField[2];
	private double v[]=new double[t.length];
	private JLabel label=new JLabel();
	private boolean error;
	static Color errorColor=Color.red;
	static Color okColor=Color.black;
	private GraphView view;
	
	public MinMaxPanel(GraphPanel graph,String name) {
		int i;
		this.view=graph.view;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(graph.getBackgroundColor());
		
		if(name!=null){
			setName(name);
		}
		
		for(i=0;i<t.length;i++){
			t[i]=new JTextField();
			t[i].addKeyListener(new KeyL());
		}
		
		for(Component c:new Component[]{label,t[0],new JLabel("-"),t[1]} ){
			add(c);
		}
		

	}
	
	public MinMaxPanel(GraphView view) {
	}

	public void setName(String name){
		label.setText(name);
	}
	
	public double getMin(){
		return v[0];
	}
	
	public double getMax(){
		return v[1];
	}

	public static String format(double v){
		return String.format(Locale.US,"%.8f",v);
	}
	
	private void setValuesNoRecount(double[]value){
		int i;
		for(i=0;i<t.length;i++){
			t[i].setText(format(value[i]));
		}
	}
	
	public void setValues(double[]value){
		setValuesNoRecount(value);
		recount();
	}

	public boolean isError(){
		return error;
	}
	
	private void recount(){
		int i;
		error=false;
		double tv[]=new double[v.length];
		for(i=0;i<t.length;i++){
			try{
				tv[i]=ExpressionEstimator.calculate(t[i].getText());
				t[i].setForeground(okColor);
			}
			catch(Exception _ex){
				t[i].setForeground(errorColor);
				error=true;
			}
		}
		
		if( tv[0]>=tv[1] ){
			for(i=0;i<t.length;i++){
				t[i].setForeground(errorColor);
			}
			error=true;
		}
		
		if(!error){
			for(i=0;i<v.length;i++){
				v[i]=tv[i];
			}
			view.redrawImage();
		}
	}
	
	private class KeyL implements KeyListener{
	  public void keyTyped(KeyEvent arg0) {
	  }

	  public void keyPressed(KeyEvent arg0) {
	  }
	  
	  public void keyReleased(KeyEvent arg0){
	  	recount();
	  }
	}

	public void setValues(double a, double b) {
		setValues(new double[]{a,b});
	}

}
