package graph;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import common.Helper;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel implements ActionListener{

	Vector<ElementaryGraphPanel> elementary=new Vector<>();
  private final static int redColor=128;
  private final static int greenColor=128;
  private final static int blueColor=255;
  private final static Color[] graphColor={
    new Color(0,0,0),
    new Color(redColor,0,0),
    new Color(0,greenColor,0),
    new Color(0,0,blueColor),
    new Color(redColor,greenColor,0),
    new Color(redColor,0,blueColor),
    new Color(0,greenColor,blueColor),
    new Color(redColor,greenColor,blueColor),
  };
	
	private JPanel bottom[]=new JPanel[2];
	GraphView view=new GraphView(this);
	private JButton resetButton=new JButton("reset");
	private JButton pictureButton[]=new JButton[3];

	private final static double startLimit[][]={ {-10, 10},{-10,10} };
	private MinMaxPanel axisLimits[]=new MinMaxPanel[2];
	private JPanel mainPanel;
	private final Dimension margin=new Dimension(3, 3);
	private String axisName[]={"x","y"};
	private JTextField textField[]=new JTextField[2];

	public static final Color defaultBackgroundColor = UIManager.getColor ( "Panel.background" );
	private Color backgroundColor;
	private static final String HOMEPAGE="http://javadiagram.sourceforge.net";
	
	public GraphPanel(Color backgroundColor){
		init(backgroundColor);
	}
	
	public GraphPanel(){
		init(defaultBackgroundColor);
	}
	
	private void init(Color backgroundColor){
		int i;
		JPanel p;
		this.backgroundColor=backgroundColor;
		setLayout(new BorderLayout());
		
		mainPanel=new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//mainPanel.setBackground(backgroundColor);
		
		resetButton.addActionListener(this);
		
		for(i=0;i<bottom.length;i++){
			p=bottom[i]=new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			p.setMaximumSize(new Dimension(Integer.MAX_VALUE,resetButton.getPreferredSize().height));
			p.setBackground(backgroundColor);
		}
		for(i=0;i<2;i++){
			axisLimits[i]=new MinMaxPanel(this, axisName[i]);
			bottom[0].add(Box.createRigidArea(margin));
			bottom[0].add(axisLimits[i]);
			view.setAxis(i,axisLimits[i]);
		}
		bottom[0].add(Box.createRigidArea(margin));
		bottom[0].add(resetButton);
		
		for(i=0;i<textField.length;i++){
			if(i!=0){
				bottom[1].add(Box.createRigidArea(margin));
			}
			textField[i]=new JTextField();
			textField[i].setEditable(false);
			textField[i].setBackground(UIManager.getColor("TextField.background"));
			bottom[1].add(new JLabel("current "+axisName[i]));
			bottom[1].add(textField[i]);
		}
		
		final String bname[]={"viewmag+","viewmag-","help"};
		for(i=0;i<3;i++){
			pictureButton[i]=new JButton(Helper.createImageIcon(bname[i]+".png"));
			pictureButton[i].addActionListener(this);
			bottom[1].add(Box.createRigidArea(margin));
			bottom[1].add(pictureButton[i]);
		}
		
		reset();
		add(new JScrollPane(mainPanel));

	}
	
	public Color getBackgroundColor(){
		return backgroundColor;
	}
	
	boolean isError(){
		return axisLimits[0].isError() || axisLimits[1].isError();
	}
	
	public ElementaryGraphPanel getElemenentary(int i){
		return elementary.elementAt(i);
	}
	
	public int getElemenentarySize(){
		return elementary.size();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(resetButton==arg0.getSource()){
			reset();
		}
		else{
			for(int i=0;i<pictureButton.length;i++){
				if(pictureButton[i]==arg0.getSource()){
					if(i==2){
						Helper.openWebpage(HOMEPAGE);
					}
					else{
						zoom(i==1);
					}
					break;
				}
			}
		}
		
	}
	
	public void zoom(boolean increaseArea) {
		double v[]=new double[2];
		int i,j;
		MinMaxPanel m;
		double multiply=increaseArea?1:0.25;
		for(i=0;i<2;i++){
			m=axisLimits[i];
			for(j=0;j<2;j++){
				v[j]=(m.getMin()+m.getMax())/2+(j==0?-1:+1)*multiply*(m.getMax()-m.getMin());
			}
			m.setValues(v);
		}
	}

	private void reset() {
		elementary.clear();
		addGraph();
		
		int i;
		for(i=0;i<2;i++){
			axisLimits[i].setValues(startLimit[i]);
		}
		
	}
	
	void redraw() {
		view.redrawImage();
		view.repaint();
	}

	public void removeGraph(ElementaryGraphPanel elementaryGraphPanel) {
		elementary.remove(elementaryGraphPanel);
		updateMainPanel();
	}
	
	public void addGraph() {
		elementary.add(new ElementaryGraphPanel(this,graphColor[elementary.size() % graphColor.length]));
		updateMainPanel();
	}
	
	private void updateMainPanel(){
		mainPanel.removeAll();
		for(ElementaryGraphPanel e:elementary){
			mainPanel.add(e);
			e.enableClose(true);
		}
		
		mainPanel.add(view);
		for(JPanel p:bottom){
			mainPanel.add(p);
		}
		
		if(elementary.size()==1){
			elementary.elementAt(0).enableClose(false);
		}
		
		revalidate();
		redraw();
		
	}

	public void updateMousePoint(double x, double y) {
		int i;
		for(i=0;i<2;i++){
			textField[i].setText(MinMaxPanel.format(i==0?x:y));
		}
	}
	
}
