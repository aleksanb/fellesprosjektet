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

import db.CalendarModel;

public class CalendarPanel extends JPanel implements MouseListener {

	private static final int ROWS = 24;
	private static final int COLLUMNS = 8;

	private final String[] days = {"Tid", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
	private final String[] hours = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", 
			"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", 
			"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
	
	private CalendarModel model;
	
	private JPanel dayLine;
	private JPanel panel;
	private JScrollPane calendarScroller;
	
	private ArrayList<AppointmentView> visibleEvents;
	private AppointmentView selectedEvent;
	
	private JLabel weekLabel;
	private JLabel nextWeek;
	private JLabel previousWeek;
	
	private int gridSizeX;
	private int gridSizeY;

	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			CalendarPanel cp = new CalendarPanel();
			cp.addToCalendar(new AppointmentView(), 2, 2, 1, 2);
			frame.getContentPane().add(cp);
			frame.pack();
			frame.setSize(1024, 576);
			frame.setVisible(true);
		} 
	/**
	 * Create the application.
	 */
	public CalendarPanel() {

		
		setSize(800, 400);
		setLayout(null);
		
		model = new CalendarModel();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
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

		visibleEvents = new ArrayList<AppointmentView>();
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
		nextWeek.setBounds(getWidth() - 80, getHeight() / 2 - 25, 70, 50);
		add(nextWeek);

		previousWeek = new JLabel();
		previousWeek.addMouseListener(this);
		previousWeek.setIcon(new ImageIcon("resources/ArrowLeft.png"));
		previousWeek.setBounds(10, getHeight() / 2 - 25, 70, 50);
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

		try {
			dates = (new SimpleDateFormat("yyyy w u")).parse(model.getYear() + " " + model.getWeek() + " " + 4);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		weekLabel.setText("Uke: " + (new SimpleDateFormat("w")).format(dates));
		
		calendarScroller.setColumnHeaderView(dayLine);
		
		for(int i = 0; i < hours.length; i++) {
			JLabel label = new JLabel(hours[i]);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			addToCalendar(label, 0, i, 1, 1);
		}
		
		visibleEvents = new ArrayList<AppointmentView>();
		
		for(AppointmentView e : model.getEvents()) {
			addEvent(e);
		}
		
		calendarScroller.repaint();
	}
	
	public void addToCalendar(Component comp, double posX, double posY, double width, double height) {
		comp.setBounds((int)(posX * gridSizeX), (int)(posY * gridSizeY), (int)(gridSizeX * width), (int)(gridSizeY * height));
		panel.add(comp);
	}
	
	public void addEvent(AppointmentView appointmentView) {
		Date start = appointmentView.getModel().getStartAsDate();
		Date end = appointmentView.getModel().getEndAsDate();
		int eventWeek = appointmentView.getModel().getWeek();
		if(eventWeek != model.getWeek()) {
			return;
		}
		int day = start.getDay();
		int hour = start.getHours();
		int duration = end.getHours() - start.getHours();
		appointmentView.addMouseListener(this);
		visibleEvents.add(appointmentView);
		addToCalendar(appointmentView, day, hour, 1, duration);
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
		selectedEvent = null;
		for(AppointmentView ev : visibleEvents) {
			ev.setSelected(false);
		}
		if(e.getComponent() == previousWeek) {
			model.setWeek(model.getWeek() - 1);
		}
		else if(e.getComponent() == nextWeek) {
			model.setWeek(model.getWeek() + 1);
		}
		else if(e.getComponent() instanceof AppointmentView) {
			((AppointmentView)e.getComponent()).setSelected(true);
			selectedEvent = (AppointmentView)e.getComponent();
		}
		updateCalendar();
	}

}
