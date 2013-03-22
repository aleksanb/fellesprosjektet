package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import core.CalendarProgram;

import db.Status;
import db.User;
import java.awt.Color;
import java.awt.Component;


public class ParticipantList extends JPanel implements ActionListener{
	private JList<User> npList;
	private JList<User> pList;
	protected DefaultListModel<User> nonparticipants;
	protected DefaultListModel<User> participants;
	JButton btnAdd;
	JButton buttonRemove;
	
	public ParticipantList() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		
		btnAdd = new JButton("Add >>");
		btnAdd.addActionListener(this);
		btnAdd.setActionCommand("Add");
		
		buttonRemove = new JButton("<< Remove");
		buttonRemove.addActionListener(this);
		buttonRemove.setActionCommand("Remove");
		
		setBackground(Color.LIGHT_GRAY);
		nonparticipants = new DefaultListModel<User>();
		participants = new DefaultListModel<User>();
		
		pList = new JList<User>(participants);
		pList.setBackground(Color.LIGHT_GRAY);
		rightPanel.add(pList);
		
		npList = new JList<User>(nonparticipants);
		npList.setBackground(Color.WHITE);
		leftPanel.add(npList);
		
		JLabel lblAllUsers = new JLabel("All users");
		
		JLabel lblParticipating = new JLabel("Participating");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(buttonRemove, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addComponent(lblAllUsers)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblParticipating)
							.addGap(48))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(89)
							.addComponent(btnAdd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRemove))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAllUsers)
								.addComponent(lblParticipating))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(leftPanel, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
								.addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))))
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	
	
	public void populateParticipants(ArrayList<User> allUsers, HashMap<User, Status> hashMap){
		allUsers.removeAll(hashMap.keySet());
		for(int i = 0; i < allUsers.size(); i++){
			this.nonparticipants.addElement(allUsers.get(i));
		}
		User tmp;
		for (Map.Entry<User, Status> entry : hashMap.entrySet()) {
			tmp = entry.getKey().getCopy();
			tmp.setName(tmp.getName() + entry.getValue());
			System.out.println("created haxxorUser with name " + tmp.getName());
			this.participants.addElement(tmp);
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
	
	public void setEditable(Boolean bool) {
		this.btnAdd.setEnabled(bool);
		this.buttonRemove.setEnabled(bool);
	}
	
}

class ListRenderer implements ListCellRenderer{
		
		@Override
		public Component getListCellRendererComponent(JList participantList, Object user, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = new JLabel();
			User u = (User) user;
			label.setText(u.getName() + u.getEmail());
			return label;
			
		}
}
