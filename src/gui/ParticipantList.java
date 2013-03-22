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
	private JList<User> npList;
	private JList<User> pList;
	protected DefaultListModel<User> nonparticipants;
	protected DefaultListModel<User> participants;
	
	public ParticipantList() {
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
		
		setBackground(Color.PINK);
		nonparticipants = new DefaultListModel<User>();
		participants = new DefaultListModel<User>();
		
		pList = new JList<User>(participants);
		pList.setBackground(Color.LIGHT_GRAY);
		rightPanel.add(pList);
		
		npList = new JList<User>(nonparticipants);
		npList.setBackground(Color.WHITE);
		leftPanel.add(npList);
		
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
		setLayout(groupLayout);
	}
	
	
	public void populateParticipants(ArrayList<User> allUsers, ArrayList<User> participants){
		allUsers.removeAll(participants);
		for(int i = 0; i < allUsers.size(); i++){
			this.nonparticipants.addElement(allUsers.get(i));
		}
		for (int i = 0; i < participants.size(); i++ ) {
			this.participants.addElement(participants.get(i));
		}
	}
	
	public ArrayList<User> getParticipants() {
		ArrayList<User> participating = new ArrayList<User>();
		for (int i = 0; i < this.participants.size(); i++ ) {
			participating.add(this.participants.get(i));
		}
		return participating;
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
		//frame.getContentPane().add(new ParticipantList(new CalendarProgram()));
		frame.pack();
        frame.setSize(500,630);
        frame.setVisible(true);
	}
	
}
