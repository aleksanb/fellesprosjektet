package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import core.CalendarProgram;

import db.Appointment;
import db.AppointmentType;
import db.CalendarModel;
import db.NotificationType;

public class CalendarPanel extends JPanel implements MouseListener {

	private static final int ROWS = 24;
	private static final int COLLUMNS = 8;

	private final String[] days = {"Tid", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	private final String[] hours = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", 
			"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", 
			"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
	
	private CalendarModel model;
	private CalendarProgram cp;
	
	private JPanel dayLine;
	private JPanel panel;
	private JScrollPane calendarScroller;
	
	private ArrayList<AppointmentView> visibleAppointments;
	private AppointmentView selectedEvent;
	
	private JLabel weekLabel;
	private JLabel nextWeek;
	private JLabel previousWeek;
	
	private int gridSizeX;
	private int gridSizeY;

	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			CalendarPanel cp = new CalendarPanel(null);
			GregorianCalendar gc = new GregorianCalendar();
			GregorianCalendar gc1 = new GregorianCalendar();
			gc1.set(GregorianCalendar.HOUR_OF_DAY, 23);
			Appointment a = new Appointment(1, 2, "halla", gc,
					gc1, "halla", true);
			cp.addAppointmentToModel(a);
;			frame.getContentPane().add(cp);
			frame.pack();
			frame.setSize(1024, 576);
			frame.setVisible(true);
		} 
	/**
	 * Create the application.
	 */
	public CalendarPanel(CalendarProgram cp) {

		this.cp = cp;
		
		setSize(800, 400);
		setLayout(null);
		
		model = new CalendarModel();
		
		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int j = 1; j < ROWS + 1; j++) {
					if(j > 0) {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.drawLine(0, j * (getHeight() / ROWS), getWidth(), j * (getHeight() / ROWS));
				}
				g.setColor(Color.BLACK);
				for(int i = 1; i < COLLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLLUMNS), 0, i * (getWidth() / COLLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(600, 800));
		panel.setSize(panel.getPreferredSize());
		panel.setLayout(null);

		gridSizeX = panel.getWidth() / COLLUMNS;
		gridSizeY = panel.getHeight() / ROWS;
		
		JViewport viewPort = new JViewport();
		viewPort.setView(panel);
		viewPort.setViewPosition(new Point(0, 8 * gridSizeY));
		
		calendarScroller = new JScrollPane();
		calendarScroller.setSize(panel.getWidth() + 20, 350);
		calendarScroller.setLocation(getWidth() / 2 - calendarScroller.getWidth() / 2, getHeight() - calendarScroller.getHeight());
		calendarScroller.setViewport(viewPort);
		add(calendarScroller);

		visibleAppointments = new ArrayList<AppointmentView>();
		selectedEvent = null;
		
		weekLabel = new JLabel("Uke: " + model.getWeek());
		weekLabel.setFont(new Font(null, Font.BOLD, 20));
		weekLabel.setBounds(getWidth() / 2 - calendarScroller.getWidth() / 2, getHeight() - calendarScroller.getHeight() - 40, 100, 50);
		add(weekLabel);
		
		dayLine = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for(int i = 1; i < COLLUMNS; i++) {
					g.drawLine(i * (getWidth() / COLLUMNS), 0, i * (getWidth() / COLLUMNS), getHeight());
				}
				super.paintChildren(g);
			}
		};
		dayLine.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		dayLine.setPreferredSize(new Dimension(panel.getWidth(), (int)(1.3 * gridSizeY)));
		dayLine.setSize(dayLine.getPreferredSize());
		dayLine.setLayout(null);
		
		nextWeek = new JLabel();
		nextWeek.addMouseListener(this);
		nextWeek.setIcon(new ImageIcon("resources/ArrowRight.png"));
		nextWeek.setVisible(true);
		nextWeek.setBounds(getWidth() - 80, getHeight() / 3 - 25, 70, 50);
		add(nextWeek);

		previousWeek = new JLabel();
		previousWeek.addMouseListener(this);
		previousWeek.setIcon(new ImageIcon("resources/ArrowLeft.png"));
		previousWeek.setBounds(10, getHeight() / 3 - 25, 70, 50);
		add(previousWeek);

		updateCalendar();
	}
	
	public void updateCalendar() {

		dayLine.removeAll();
		panel.removeAll();
		
		Date dates = null;
		DateFormat df = new SimpleDateFormat("yyyy w u");
		
		int date = 0;
		int month = 0;
		
		for(int i = 0; i < days.length; i++) {
			JLabel weekDay = new JLabel(days[i]);
			weekDay.setBounds(i * gridSizeX, 0, gridSizeX, gridSizeY);
			weekDay.setHorizontalAlignment(SwingConstants.CENTER);
			dayLine.add(weekDay);
			if(i > 0) {
				try {
					if(i != 7) {
						dates = df.parse(model.getYear() + " " + model.getWeek() + " " + (i % 8));
					}
					else {
						dates = df.parse(model.getYear() + " " + (model.getWeek() + 1) + " " + (i % 8));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
//				date = Integer.parseInt((new SimpleDateFormat("d")).format(dates));
//				month = Integer.parseInt((new SimpleDateFormat("M")).format(dates));
				JLabel dateLabel = new JLabel(new SimpleDateFormat("d/M yy").format(dates));
				dateLabel.setBounds(i * gridSizeX, dateLabel.getFont().getSize(), gridSizeX, gridSizeY);
				dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
				dayLine.add(dateLabel);
			}
		}
		weekLabel.setText("Uke: " + (new SimpleDateFormat("w")).format(dates));
		calendarScroller.setColumnHeaderView(dayLine);
		
		for(int i = 0; i < hours.length; i++) {
			JLabel label = new JLabel(hours[i]);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			addToCalendar(label, 0, i, 1, 1);
		}
		
		visibleAppointments = new ArrayList<AppointmentView>();
		ArrayList<AppointmentView> modelevents = model.getEvents();
		for(AppointmentView e : modelevents) {
			addAppointment(e);
		}
		
		calendarScroller.repaint();
	}
	
	public void addToCalendar(Component comp, double posX, double posY, double width, double height) {
		comp.setBounds((int)(posX * gridSizeX), (int)(posY * gridSizeY), (int)(gridSizeX * width), (int)(gridSizeY * height));
		panel.add(comp);
	}
	
	public void addAppointment(AppointmentView appointmentView) {
		Date start = appointmentView.getModel().getStartAsDate();
		Date end = appointmentView.getModel().getEndAsDate();
		int eventWeek = appointmentView.getModel().getWeek();
		AppointmentType appointmentType = appointmentView.getModel().getAppointmentType();
		if(appointmentType == AppointmentType.OK ){
			appointmentView.setColor(Color.GREEN);
		}
		else if(appointmentType == AppointmentType.NEEDSATTENTION){
			appointmentView.setColor(Color.ORANGE);
		}
		else{
			appointmentView.setColor(Color.RED);
		}
		if(appointmentView.getHasListener()== false){
			appointmentView.addMouseListener(this);
			appointmentView.setHasListener(true);
		}
		if(eventWeek != model.getWeek()) {
			return;
		}
		int day = start.getDay();
		int hour = start.getHours();
		int duration = end.getHours() - start.getHours();
		visibleAppointments.add(appointmentView);
		addToCalendar(appointmentView, day, hour, 1, duration);
	}
	public void addAppointmentToModel(Appointment appointment){
		AppointmentView appointmentView = new AppointmentView(appointment);
		model.addAppointmentView(appointmentView);
		appointmentView.addMouseListener(this);
		appointmentView.setHasListener(true);
		updateCalendar();
		addAppointment(appointmentView);
	}

	public CalendarModel getModel() {
		return model;
	}

	public void setModel(CalendarModel model) {
		this.model = model;
	}

	public AppointmentView getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(AppointmentView selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		cp.setEditButtonDisabled();
		selectedEvent = null;
		for(AppointmentView ev : visibleAppointments) {
			ev.setSelected(false);
		}
		if(e.getComponent() == previousWeek) {
			model.setWeek(model.getWeek() - 1);
		}
		else if(e.getComponent() == nextWeek) {
			model.setWeek(model.getWeek() + 1);
		}
		else if(e.getComponent() instanceof AppointmentView) {
			selectedEvent = (AppointmentView)e.getComponent();
			((AppointmentView)e.getComponent()).setSelected(true);
			AppointmentType appointmentType = selectedEvent.getModel().getAppointmentType();

			if(appointmentType == AppointmentType.OK ){
				cp.setEditButtonEnabled();
				System.out.println("Ok");
			}
			else if(appointmentType == AppointmentType.NEEDSATTENTION){
				cp.setEditButtonEnabled();
				System.out.println("Attention");
			}
			else{
				System.out.println("DEleted");
				model.removeAppointment(selectedEvent.getModel());
			}
			//TODO skrive kode for behandling av hva som skjer når man klikker på et event delegering til menupanel
		}
		updateCalendar();
	}
	public void setFocusToAppointment(Appointment app){
		ArrayList<Appointment> appointments = getAppointmentList();
		if(appointments.contains(app)){
			model.setWeek(app.getWeek());
			model.setYear(app.getYear());
		}
		updateCalendar();
		for(AppointmentView ev : visibleAppointments) {
			if(ev.getModel() == app){
				ev.setSelected(true);
			}
			else{
				ev.setSelected(false);
			}
		}
	}
	private ArrayList<Appointment> getAppointmentList() {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		ArrayList<AppointmentView> appointmentView = model.getEvents();
		for(int i = 0; i < appointmentView.size(); i++){
			Appointment a = appointmentView.get(i).getModel();
			appointments.add(a);
		}
		return appointments;
	}
}