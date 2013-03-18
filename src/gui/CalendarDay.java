package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import db.Appointment;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import core.CalendarProgram;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CalendarDay extends JPanel{
	
	GridBagLayout gbl;
	
	public CalendarDay(){
		gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{100};
		gbl.rowHeights = new int[]{50,50,50,50,50,50,50,50,50,50,50,50};
		gbl.columnWeights = new double[]{};
		gbl.rowWeights = new double[]{};
		setLayout(gbl);
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		JPanel p1 = new JPanel();
		p1.setBackground(Color.GREEN);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		add(p1, gbc1);
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		JPanel p2 = new JPanel();
		p2.setSize(100, 50);
		p2.add(new JRadioButton("hore"));
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		add(p2, gbc2);

	}
	
	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			CalendarDay cd = new CalendarDay();
			frame.getContentPane().add(cd);
			frame.pack();
			frame.setSize(100, 600);
			frame.setVisible(true);
		}  
}
