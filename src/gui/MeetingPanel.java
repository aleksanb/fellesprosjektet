package gui;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;

import db.Action;
import db.MeetingPoint;
import db.User;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

import core.CalendarProgram;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class MeetingPanel extends JPanel implements ActionListener{

	protected JComboBox<MeetingPoint> comboBox;
	protected ParticipantList pl;
	protected JTextField hostField;
	private JButton findButton;
	private JButton btnUpdateStatus;
	ButtonGroup group;
	EditAppointmentPanel eap;
	
	public MeetingPanel(ArrayList<User> allUsers, ArrayList<User> participatingUsers, EditAppointmentPanel eap) {
		setBackground(Color.LIGHT_GRAY);
		this.eap = eap;
		//setBackground(Color.PINK);
		
		JLabel lblNewLabel = new JLabel("Participants:");
		JLabel lblPlace = new JLabel("Place:");
		
		this.pl = new ParticipantList();		
		this.pl.populateParticipants(allUsers, participatingUsers);
		
		comboBox = new JComboBox<MeetingPoint>();
		
		findButton = new JButton("Find Place");
		findButton.addActionListener(this);
		findButton.setActionCommand("Find");
		
		JLabel lblStatus = new JLabel("Status:");
		
		
		JRadioButton rdbtnAttending = new JRadioButton("Attending");
		rdbtnAttending.setActionCommand("Attending");
		
		JRadioButton rdbtnNotAttending = new JRadioButton("Not attending");
		rdbtnNotAttending.setActionCommand("Not attending");
		
		//Group radiobuttons
		group = new ButtonGroup();
		group.add(rdbtnAttending);
		group.add(rdbtnNotAttending);
		
		
		JLabel lblHost = new JLabel("Host:");
		
		hostField = new JTextField("the internets");
		hostField.setEditable(false);
		hostField.setColumns(10);
		
		btnUpdateStatus = new JButton("Update status");
		btnUpdateStatus.addActionListener(this);
		btnUpdateStatus.setActionCommand("UpdateStatus");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(pl, GroupLayout.PREFERRED_SIZE, 479, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStatus)
								.addComponent(lblPlace, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
							.addGap(39)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(rdbtnAttending)
									.addGap(18)
									.addComponent(rdbtnNotAttending)))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUpdateStatus, GroupLayout.PREFERRED_SIZE, 126, Short.MAX_VALUE)
								.addComponent(findButton, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblHost)
							.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
							.addComponent(hostField, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(hostField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHost))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel)
					.addGap(8)
					.addComponent(pl, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(findButton)
						.addComponent(lblPlace))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnAttending)
						.addComponent(rdbtnNotAttending)
						.addComponent(lblStatus)
						.addComponent(btnUpdateStatus))
					.addGap(12))
		);
		setLayout(groupLayout);
		
		/*userList = cp.getUsers();
		System.out.println(userList);
		plp.makeCheckListItem(userList.get(1)); */
		
		
	}
	
	public ArrayList<User> getParticipants(){
		return this.pl.getParticipants();
	}
	
	public void filterPlaces(){
		this.comboBox.removeAllItems();
		ArrayList<User> participants = pl.getParticipants();
		ArrayList<MeetingPoint> allPlaces = eap.getMeetingPoints();
		for (int i = 0; i < allPlaces.size(); i++){
			if(allPlaces.get(i).getCapacity() >= participants.size()-1){
				comboBox.addItem(allPlaces.get(i));
			}
		}
	}
	
	public MeetingPoint getMeetingPoint(){
		return (MeetingPoint) comboBox.getSelectedItem();
	}
	
	public static void main(String[] args){

		JFrame frame = new JFrame();
		frame.pack();
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	public void toggleEditable(Boolean bool) {
		this.pl.setEditable(bool);
		this.comboBox.setEnabled(bool);
		this.findButton.setEnabled(bool);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("UpdateStatus")) {
			ButtonModel tmp = group.getSelection();
			if (tmp != null) {
				eap.setStatus(tmp.getActionCommand());
			} else {
				System.out.println("action is null");
			}
		} else if (e.getActionCommand().equals("Find")) {
			// TODO: pass på at klokkeslett er satt før man søker
			System.out.println("attempting to find rooms");
			filterPlaces();
		}
	}
}