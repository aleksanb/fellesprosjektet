package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import db.User;

@SuppressWarnings("serial")
public class ParticipantList extends JList<db.User> {
	
	protected JList<User> participantList;
	protected DefaultListModel listModel;
	
	public ParticipantList() {
		
		listModel = new DefaultListModel();
		participantList = new JList(listModel);
		participantList.setVisibleRowCount(100);
		participantList.setFixedCellHeight(15);
		participantList.setFixedCellWidth(200);
		participantList.setMinimumSize(new Dimension(100,100));
		participantList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		participantList.setLayoutOrientation(JList.VERTICAL);
		participantList.setSelectedIndex(0);
		participantList.setName("Participants");
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		this.add(participantList, c);
	}
	

	
	public static void main(String args[]) {
		ParticipantList participantList = new ParticipantList();
		DefaultListModel model = new DefaultListModel();
		participantList.setModel(model);
		participantList.setOpaque(true);
		JFrame frame = new JFrame();
		frame.setContentPane(participantList);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
}
