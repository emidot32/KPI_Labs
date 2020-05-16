package graph;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Helper;

import estimator.ExpressionEstimator;

@SuppressWarnings("serial")
public class ElementaryGraphPanel extends JPanel implements ActionListener{
	private final static String typeString[] = { "standard", "polar (a is angle)", "parametrical"};
	private final static String typeLabelString[]={"y(x)"," r(a)","x(t)","y(t)"};
	private JComboBox<String> type=new JComboBox<>(typeString);
	private JLabel label[]=new JLabel[2];
	private JPanel up[]=new JPanel[2];
	private JTextField tf[]=new JTextField[3];
	private GraphPanel graph;
	private final static String startFunctionString[]={"tan(x)","t"};
	private boolean error=false;
	private ExpressionEstimator[] estimator=new ExpressionEstimator[2];
	private int previousType;
	private double startMinMax[]=new double[]{0,100};
	private int startStepValue=1000;
	private int steps;
	private static final String VARIABLE_NAME[]={"x","a","t"};
	private JButton button[]=new JButton[2];
	private Color color;
	private MinMaxPanel parameterMinMaxPanel;
	private JPanel parameterPanel=new JPanel();

	public ElementaryGraphPanel(GraphPanel graph,Color color) {
		int i;
		JPanel p;
		
		this.graph=graph;
		this.color=color;
		setBackground(graph.getBackgroundColor());
		
		for(i=0;i<tf.length;i++){
			tf[i]=new JTextField(i==2 ? startStepValue+"":startFunctionString[i]);
			tf[i].addKeyListener(new KeyL());
		}
		
		parameterMinMaxPanel=new MinMaxPanel(graph,null);
		parameterMinMaxPanel.setValues(startMinMax);
		
		for(i=0;i<button.length;i++){
			button[i]=new JButton( Helper.createImageIcon( (i==0?"plus":"minus")+".png") );
			button[i].addActionListener(this);
		}
		
		type.addActionListener(this);
		type.setMaximumSize(type.getPreferredSize());

		for(i=0;i<up.length;i++){
			label[i]=new JLabel();
			label[i].setForeground(color);
			estimator[i]=new ExpressionEstimator();
			p=up[i]=new JPanel();
			p.setBackground(graph.getBackgroundColor());
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			p.add(label[i]);
			p.add(tf[i]);
			p.setMaximumSize(new Dimension(Integer.MAX_VALUE,tf[i].getPreferredSize().height));
			if(i==1){
				p.setVisible(false);
			}
		}		

		parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.X_AXIS));
		parameterPanel.setBackground(graph.getBackgroundColor());

		parameterPanel.add(parameterMinMaxPanel);
		parameterPanel.add(new JLabel("steps"));
		parameterPanel.add(tf[2]);
		parameterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,tf[2].getPreferredSize().height));
		
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		for(JComponent _c:new JComponent[]{button[0],button[1],up[0],up[1],type,parameterPanel}){
			add(Box.createRigidArea(new Dimension(1, 0)));
			add(_c);
		}
		
		setGraphType(0);
		recalculate();

	}

	public void actionPerformed(ActionEvent e) {
		if(type==e.getSource()){
			setGraphType(type.getSelectedIndex());
			recalculate();
		}
		else if(button[0]==e.getSource()){
			graph.addGraph();
		}
		else if(button[1]==e.getSource()){
			if(graph.getElemenentarySize()!=1){
				graph.removeGraph(this);
			}
		}
	}
	
	private String getExtendedText(int type){
		return " "+typeLabelString[type]+"="; 
	}
	
	private void setGraphType(int type){
		label[0].setText(getExtendedText(type));
		
		tf[0].setText(getMatcher(0).replaceAll(VARIABLE_NAME[type]));//previous type is used
		previousType=type;
		
		if(isParametrical()){
			label[1].setText(getExtendedText(3));
		}

		up[1].setVisible(isParametrical());
		parameterPanel.setVisible(!isStandard());
		
		if(!isStandard()){
			parameterMinMaxPanel.setName(VARIABLE_NAME[type]);
		}
		
		fireKeyPressed();
	}

	public void fireKeyPressed(){
		recalculate();
  	graph.redraw();
	}
	
	private Matcher getMatcher(int i){
		return Pattern.compile("(?<=^|\\W)"+VARIABLE_NAME[previousType]+"(?=\\W|$)",Pattern.CASE_INSENSITIVE).matcher(tf[i].getText());
	}
	
	private void recalculate(){
		int i;
		double v;
		error=parameterMinMaxPanel.isError();
		for(i=0;i<(isParametrical()?2:1);i++){
			JTextField t=tf[i];
			try {
				//check {x,a,t}+digit -> error
				if(Pattern.compile("(?<=^|\\W)"+VARIABLE_NAME[previousType]+"(?=\\d)",Pattern.CASE_INSENSITIVE).matcher(tf[i].getText()).find()){
					throw new Exception();
				}
				
				estimator[i].compile(getMatcher(i).replaceAll("x0"));
				t.setForeground(Color.black);
			} catch (Exception e1) {
				error=true;
				t.setForeground(Color.RED);
			}
		}
		if(!isStandard()){
			JTextField t=tf[2];
			try {
				v=ExpressionEstimator.calculate(t.getText());
				i= (int)v;
				if(v<=0 || v!=i){
					throw new Exception();
				}
				steps=i;
				t.setForeground(MinMaxPanel.okColor);
			} catch (Exception _e1) {
				error=true;
				t.setForeground(MinMaxPanel.errorColor);
			}
		}
		
	}

	private class KeyL implements KeyListener{
	  public void keyTyped(KeyEvent arg0) {
	  }

	  public void keyPressed(KeyEvent arg0) {
	  }
	  
	  public void keyReleased(KeyEvent arg0){
	  	fireKeyPressed();
	  }
	}
	
	public boolean isError(){
		return error;
	}

	public double calculate(int i,double v) throws Exception {//throws if invalida number of arguments
		return estimator[i].calculate(new double[]{v});
	}

	public boolean isStandard() {
		return previousType==0;
	}

	public boolean isPolar() {
		return previousType==1;
	}

	public boolean isParametrical() {
		return previousType==2;
	}

	public double getParameterMin() {
		return parameterMinMaxPanel.getMin();
	}

	public double getParameterMax() {
		return parameterMinMaxPanel.getMax();
	}

	public double getParameterStep() {
		return (getParameterMax()-getParameterMin())/steps;
	}

	public void enableClose(boolean enable) {
		button[1].setEnabled(enable);
	}
	
	public Color getColor(){
		return color;
	}
}
