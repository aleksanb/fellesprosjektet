package gui;

import javax.swing.ButtonGroup;
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

public class MeetingPanel extends JPanel implements ActionListener{

	protected JComboBox<MeetingPoint> comboBox;
	protected ParticipantList pl;

	protected ArrayList<MeetingPoint> allPlaces = new ArrayList<MeetingPoint>();
	private JTextField textField;
	
	public MeetingPanel(ArrayList<User> allUsers, ArrayList<User> participatingUsers) {
		setBackground(Color.LIGHT_GRAY);
		//setBackground(Color.PINK);
		
		JLabel lblNewLabel = new JLabel("Participants:");
		JLabel lblPlace = new JLabel("Place:");
		
		this.pl = new ParticipantList();		
		this.pl.populateParticipants(allUsers, participatingUsers);
		
		comboBox = new JComboBox<MeetingPoint>();
		
		JButton findButton = new JButton("Find Place");
		findButton.addActionListener(this);
		findButton.setActionCommand("Cancel");
		
		JLabel lblStatus = new JLabel("Status:");
		
		
		JRadioButton rdbtnAttending = new JRadioButton("Attending");
		
		JRadioButton rdbtnNotAttending = new JRadioButton("Not attending");
		
		//Group radiobuttons
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAttending);
		group.add(rdbtnNotAttending);
		
		
		JLabel lblHost = new JLabel("Host:");
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton btnUpdateStatus = new JButton("Update status");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(pl, GroupLayout.PREFERRED_SIZE, 479, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
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
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblHost)
							.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHost))
					.addGap(14)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
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
	
	public ArrayList<MeetingPoint> filterPlaces(ArrayList<User> participants, ArrayList<MeetingPoint> allPlaces){
		ArrayList<MeetingPoint> places = new ArrayList<MeetingPoint>();
		for (int i = 0; i < allPlaces.size(); i++){
			if(allPlaces.get(i).getCapacity() >= participants.size()-1){
				places.add(allPlaces.get(i));
			}
		}
		return places;
	}
	
	public MeetingPoint getMeetingPoint(){
		return (MeetingPoint) comboBox.getSelectedItem();
	}
	
	public static void main(String[] args){

		JFrame frame = new JFrame();
		//MeetingPanel mp = new MeetingPanel();
		//frame.getContentPane().add(mp);
		frame.pack();
		frame.setSize(300, 100);
		frame.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		comboBox.removeAllItems();
		ArrayList<MeetingPoint> filter = filterPlaces(pl.getParticipants(), allPlaces);
		for(int i = 0; i < filter.size(); i++){
			comboBox.addItem(filter.get(i));
		}
	}
}