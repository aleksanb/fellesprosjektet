package gui;

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

public class MeetingPanel extends JPanel implements ActionListener{

	protected JComboBox<MeetingPoint> comboBox;
	protected ParticipantListPanel plp;
	protected ArrayList<User> userList;
	private ArrayList<MeetingPoint> allPlaces = new ArrayList<MeetingPoint>();
	protected CalendarProgram cp;
	
	public MeetingPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{74, 97, 279, 0};
		gridBagLayout.rowHeights = new int[]{16, 145, 16, 27, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Participants");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		plp = new ParticipantListPanel(cp);
		GridBagConstraints gbc_plp = new GridBagConstraints();
		gbc_plp.fill = GridBagConstraints.BOTH;
		gbc_plp.insets = new Insets(0, 0, 5, 0);
		gbc_plp.gridwidth = 3;
		gbc_plp.gridx = 0;
		gbc_plp.gridy = 1;
		add(plp, gbc_plp);
		
		JLabel lblPlace = new JLabel("Place");
		GridBagConstraints gbc_lblPlace = new GridBagConstraints();
		gbc_lblPlace.gridwidth = 2;
		gbc_lblPlace.anchor = GridBagConstraints.WEST;
		gbc_lblPlace.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlace.gridx = 0;
		gbc_lblPlace.gridy = 2;
		add(lblPlace, gbc_lblPlace);
		
		comboBox = new JComboBox<MeetingPoint>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		add(comboBox, gbc_comboBox);
		
		JButton findButton = new JButton("Find Place");
		findButton.addActionListener(this);
		findButton.setActionCommand("Cancel");
		GridBagConstraints gbc_findButton = new GridBagConstraints();
		gbc_findButton.insets = new Insets(0, 0, 5, 0);
		gbc_findButton.gridx = 2;
		gbc_findButton.gridy = 3;
		add(findButton, gbc_findButton);
		
		/*userList = cp.getUsers();
		System.out.println(userList);
		plp.makeCheckListItem(userList.get(1)); */
		
		
	}
	
	public ArrayList<User> getParticipants(){
		return this.plp.getParticipantList();
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
		MeetingPanel mp = new MeetingPanel();
		frame.getContentPane().add(mp);
		frame.pack();
		frame.setSize(300, 100);
		frame.setVisible(true);
	}


	
	public void actionPerformed(ActionEvent e) {
		comboBox.removeAllItems();
		ArrayList<MeetingPoint> filter = filterPlaces(plp.getParticipantList(), allPlaces);
		for(int i = 0; i < filter.size(); i++){
			comboBox.addItem(filter.get(i));
		}
	}

}