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

public class MeetingPanel extends JPanel implements ActionListener{

	protected JComboBox<MeetingPoint> comboBox;
	protected ParticipantListPanel plp;
	private ArrayList<MeetingPoint> allPlaces = new ArrayList<MeetingPoint>();
	
	public MeetingPanel() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Participants");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		plp = new ParticipantListPanel();
		GridBagConstraints gbc_participantListPanel = new GridBagConstraints();
		gbc_participantListPanel.insets = new Insets(0, 0, 5, 0);
		gbc_participantListPanel.fill = GridBagConstraints.BOTH;
		gbc_participantListPanel.gridx = 0;
		gbc_participantListPanel.gridy = 1;
		add(plp, gbc_participantListPanel);
		
		JLabel lblPlace = new JLabel("Place");
		GridBagConstraints gbc_lblPlace = new GridBagConstraints();
		gbc_lblPlace.anchor = GridBagConstraints.WEST;
		gbc_lblPlace.insets = new Insets(0, 0, 5, 0);
		gbc_lblPlace.gridx = 0;
		gbc_lblPlace.gridy = 2;
		add(lblPlace, gbc_lblPlace);
		
		comboBox = new JComboBox<MeetingPoint>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		add(comboBox, gbc_comboBox);
		
		JButton findButton = new JButton("Find Place");
		findButton.addActionListener(this);
		findButton.setActionCommand("Cancel");
		GridBagConstraints gbc_btnFindPlace = new GridBagConstraints();
		gbc_btnFindPlace.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindPlace.gridx = 0;
		gbc_btnFindPlace.gridy = 4;
		add(findButton, gbc_btnFindPlace);
		
		
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