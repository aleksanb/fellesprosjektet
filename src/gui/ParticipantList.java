package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import core.CalendarProgram;

import db.User;
import java.awt.Color;


public class ParticipantList extends JPanel implements ActionListener{
	private JList npList;
	private JList pList;
	protected DefaultListModel<User> nonparticipants;
	protected DefaultListModel<User> participants;

	private ArrayList<User> participantList;
	private ArrayList<User> nonparticipantList;
	
	
	public ParticipantList() {
		setBackground(Color.PINK);
		nonparticipants = new DefaultListModel<User>();
		participants = new DefaultListModel<User>();
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.LIGHT_GRAY);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.LIGHT_GRAY);
		
		JButton btnAdd = new JButton("Add >>");
		btnAdd.addActionListener(this);
		btnAdd.setActionCommand("Add");
		
		JButton buttonRemove = new JButton("<< Remove");
		buttonRemove.addActionListener(this);
		buttonRemove.setActionCommand("Remove");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(buttonRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(leftPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
								.addComponent(rightPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(95)
							.addComponent(btnAdd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRemove)))
					.addContainerGap())
		);
		
		pList = new JList(participants);
		pList.setBackground(Color.LIGHT_GRAY);
		rightPanel.add(pList);
		
		npList = new JList(nonparticipants);
		npList.setBackground(Color.WHITE);
		leftPanel.add(npList);
		setLayout(groupLayout);
		
		
		//Testing purposes
		User u1 = new User(1, "Thea", "@", "kjasd");
		User u2 = new User(2, "Alex", "@", "sdf");
		
		nonparticipants.addElement(u1);
		nonparticipants.addElement(u2);
	}
	
	
	public ArrayList<User> getParticipants(){
		participantList = new ArrayList<User>();
		for(int i = 0; i < participants.getSize(); i++){
			participantList.add(participants.elementAt(i));
		}
		return participantList;
	}
	
	public ArrayList<User> getNonparticipants(){
		nonparticipantList = new ArrayList<User>();
		for(int i = 0; i < nonparticipants.getSize(); i++){
			nonparticipantList.add(nonparticipants.elementAt(i));
		}
		return nonparticipantList;
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		
		//Add-button
		if(event.getActionCommand().equals("Add")){
			User selected = (User) npList.getSelectedValue();
			participants.addElement(selected);
			nonparticipants.removeElement(selected);
		}
		
		//Remove-button
		if(event.getActionCommand().equals("Remove")){
			User selected = (User) pList.getSelectedValue();
			nonparticipants.addElement(selected);
			participants.removeElement(selected);
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ParticipantList());
		frame.pack();
        frame.setSize(500,630);
        frame.setVisible(true);
	}
	
}
