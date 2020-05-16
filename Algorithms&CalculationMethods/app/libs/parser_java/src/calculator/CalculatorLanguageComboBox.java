package calculator;

/******************************************************
Copyright (c/c++) 2013-doomsday by Alexey Slovesnov 
homepage http://slovesnov.narod.ru/indexe.htm
email slovesnov@yandex.ru
All rights reserved.
******************************************************/
/*
copy from http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
and make changes aslov
*/

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import common.Helper;

@SuppressWarnings("serial")
class CalculatorLanguageComboBox extends JPanel implements ActionListener {
		private ImageIcon[] images;
    private String[] languageName = {"en", "ru"};
    private JComboBox<Integer> combo;
    private CalculatorPanel calculator;

    /*
     * Despite its use of EmptyBorder, this panel makes a fine content
     * pane because the empty border just increases the panel's size
     * and is "painted" on top of the panel's normal background.  In
     * other words, the JPanel fills its entire background if it's
     * opaque (which it is by default); adding a border doesn't change
     * that.
     */
    @SuppressWarnings("unchecked")
		CalculatorLanguageComboBox(CalculatorPanel calculator) {
    	this.calculator=calculator;
			setOpaque(true); //content panes must be opaque
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    	//calculator.setBackgroundColor(this);

      //Load the pet images and create an array of indexes.
      images = new ImageIcon[languageName.length];
      Integer[] intArray = new Integer[languageName.length];
      for (int i = 0; i < languageName.length; i++) {
        intArray[i] = new Integer(i);
        images[i] = Helper.createImageIcon(languageName[i] + ".gif");
        if (images[i] != null) {
         images[i].setDescription(languageName[i]);
        }
      }

      //Create the combo box.
      combo = new JComboBox<>(intArray);
      combo.setRenderer(new ComboBoxRenderer());
      combo.setMaximumRowCount(languageName.length);
      combo.addActionListener(this);
      combo.setSelectedIndex(calculator.getLanguage());
      add(combo);

    }

    @SuppressWarnings("rawtypes")
		private class ComboBoxRenderer extends JLabel
                           implements ListCellRenderer {
        private Font uhOhFont;

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */
        public Component getListCellRendererComponent(
                                           JList list,
                                           Object value,
                                           int index,
                                           boolean isSelected,
                                           boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            int selectedIndex = ((Integer)value).intValue();

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon = images[selectedIndex];
            setIcon(icon);
            if (icon == null) {
              setUhOhText(languageName[selectedIndex] + " (no image available)",
                            list.getFont());
            }
            else{
            	setText(languageName[selectedIndex]);
              setFont(list.getFont());
            }
            
            return this;
        }

        //Set the font and text when no image was found.
        protected void setUhOhText(String uhOhText, Font normalFont) {
            if (uhOhFont == null) { //lazily create this font
                uhOhFont = normalFont.deriveFont(Font.ITALIC);
            }
            setFont(uhOhFont);
            setText(uhOhText);
        }
    }

		public void actionPerformed(ActionEvent arg0) {
			if(combo==arg0.getSource()){
				calculator.changeLanguage(combo.getSelectedIndex());
			}
			
		}
}
