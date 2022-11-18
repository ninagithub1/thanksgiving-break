import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

public class AddEvent extends JFrame {

	private JPanel contentPane;
	private JTextField EventName;
	private Integer rowLoc; 
	private CompetitionsDatabase myCompetition;


	public AddEvent(CompetitionsDatabase compData, Integer location) {
		
		myCompetition = compData;
		rowLoc = location; 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(111, 183, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel AddNewEvent = new JLabel("Add New Event");
		AddNewEvent.setFont(new Font("Georgia", Font.PLAIN, 20));
		AddNewEvent.setBounds(174, 26, 194, 34);
		contentPane.add(AddNewEvent);
		
		EventName = new JTextField();
		EventName.setBounds(193, 91, 86, 20);
		contentPane.add(EventName);
		EventName.setColumns(10);
		
		JLabel EventLabel = new JLabel("Event Name:");
		EventLabel.setFont(new Font("Georgia", Font.PLAIN, 20));
		EventLabel.setBounds(37, 94, 146, 14);
		contentPane.add(EventLabel);
		
		JLabel EventSlot = new JLabel("Event Time Block:");
		EventSlot.setFont(new Font("Georgia", Font.PLAIN, 20));
		EventSlot.setBounds(37, 130, 194, 14);
		contentPane.add(EventSlot);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(219, 130, 43, 20);
		contentPane.add(spinner);
		
		JButton SaveButton = new JButton("Save");
		SaveButton.setFont(new Font("Georgia", Font.PLAIN, 20));
		SaveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = EventName.getText();
				int timeBlock = (Integer)spinner.getValue();
				Event temp = new Event(name, timeBlock);
				myCompetition.addEvent(rowLoc, temp);  // parameters = COMPETITON INDEX, event
				dispose();
			}
		});
		SaveButton.setBounds(57, 188, 106, 41);
		contentPane.add(SaveButton);
	}
}