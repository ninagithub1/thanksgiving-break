import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class CompInfoDisplay extends JFrame {

	private JPanel contentPane;
	private CompetitionsDatabase data;
	private Integer rowLoc;
	private Competition myCompetition;
	private DefaultTableModel myEventsModel;

	public CompInfoDisplay(CompetitionsDatabase compData, Integer location) {

		data = compData;

		rowLoc = location;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(242, 137, 125));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		ArrayList<Competition> temp = compData.getCompetitionData();
		System.out.println("the size of this competition list is: " + temp.size());
		Competition ourComp = temp.get(location);
		myCompetition = ourComp;
		System.out.println("the size of this competition list is: " + ourComp.getEventList().size());

		String referenceName = ourComp.getReferenceName();
		System.out.println("this point has been reached");

		myEventsModel = new DefaultTableModel(new String[] { "Event", "Students" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {

				return false;

			}
		};

		JTable table = new JTable(myEventsModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(217, 58, 292, 253);
		contentPane.add(scrollPane);

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					int row = table.getSelectedRow();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								AddStudentToEvent frame = new AddStudentToEvent(myCompetition.getEventList().get(row),
										data, myCompetition);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		table.setModel(myEventsModel);

		JLabel CompetitionName = new JLabel(referenceName);
		CompetitionName.setBounds(132, 23, 175, 14);
		contentPane.add(CompetitionName);

		JButton AddEvent = new JButton("Add New Event");
		AddEvent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AddEvent frame = new AddEvent(data, rowLoc);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		AddEvent.setBounds(52, 172, 133, 49);
		contentPane.add(AddEvent);

		JLabel CompEvents = new JLabel("Competition Events:");
		CompEvents.setBounds(62, 59, 275, 14);
		contentPane.add(CompEvents);

		if (myCompetition.getEventList().size() > 0) {
			String eventNames = "";
			for (int i = 0; i < myCompetition.getEventList().size(); i++) {

				eventNames += myCompetition.getEventList().get(i).getName();
			}
			JLabel FirstEvent = new JLabel(eventNames);
			FirstEvent.setBounds(232, 99, 101, 14);
			contentPane.add(FirstEvent);
		} else {
			String eventNames = "No events added yet";
			JLabel FirstEvent = new JLabel(eventNames);
			FirstEvent.setBounds(232, 99, 101, 14);
			contentPane.add(FirstEvent);
		}
		for (int i = 0; i < myCompetition.getEventList().size(); i++) {

			System.out.println("number: " + i);
			myEventsModel.insertRow(i, myCompetition.getEventList().get(i).getEventFormattedInfo());
		}
	}

}