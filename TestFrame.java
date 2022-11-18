import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

public class TestFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private JTextField textFieldID;
	private TestFrame testFr;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private DefaultTableModel model = new DefaultTableModel(new String[] { "Last Name", "First Name", "Grade" }, 0);
	private DefaultTableModel globalStudentModel;
	private DefaultTableModel globalCompetitionsModel;

	private DatabaseFolder data = new DatabaseFolder();
	private CompetitionsDatabase compData = new CompetitionsDatabase();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * TASK LIST: Complete Roster of Students - Use array list to add individual
	 * students - when a student is added, they should immediately show up on the
	 * complete roster of students table - use a text file to save the student data
	 * (might need to clear the array every time) COMPLETE!!!! YES!!!! 9/6/22
	 * 
	 * COMPLETE Add Competition - create competition class, make an array of
	 * competition type - add to text file and write to text file every time
	 * COMPLETE (((: 9/26- (check to make sure this function works both for adding
	 * and altering previous competitions) - ensure that once a competition is
	 * added, it pops up effectively on the homepage table-
	 * 
	 * recolor windows
	 * make sure all windows have an escape button
	 * remove spaces and add comments to code
	 * idiot-proof windows which code for adding a student/competition to event
	 */

	public TestFrame() {

		testFr = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 748, 695);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(222, 207, 226));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel myStudentModel = new DefaultTableModel(new String[] { "Last Name", "First Name", "Grade" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;

			}
		};

		JTable table = new JTable(myStudentModel); // this is what tests is.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(396, 72, 292, 253);
		contentPane.add(scrollPane);

		table.setModel(myStudentModel);
		globalStudentModel = myStudentModel;

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
				}
			}
		});

		AddStudentWindow StudentAddFrame = new AddStudentWindow(studentList, model, testFr, data);
		StudentAddFrame.setVisible(false);

		JButton AddStudentWindow = new JButton("Add Student Window");
		AddStudentWindow.setFont(new Font("Georgia", Font.PLAIN, 20));
		AddStudentWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							StudentAddFrame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
						testFr.setVisible(false);
					}
				});

			}

		});

		DefaultTableModel myCompetitionsModel = new DefaultTableModel(new String[] { "Name", "Location", "Date" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;

			}
		};

		JTable competitionsTable = new JTable(myCompetitionsModel);
		JScrollPane CompScrollPane = new JScrollPane(competitionsTable);
		CompScrollPane.setBounds(128, 362, 441, 253);
		contentPane.add(CompScrollPane);

		competitionsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					int row = competitionsTable.getSelectedRow();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								CompInfoDisplay frame = new CompInfoDisplay(compData, row);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		competitionsTable.setModel(myCompetitionsModel);
		globalCompetitionsModel = myCompetitionsModel;

		CompetitionWindow CompetitionAddFrame = new CompetitionWindow(testFr, compData);
		CompetitionAddFrame.setVisible(false);

		loadList();

		AddStudentWindow.setBounds(64, 156, 246, 40);
		contentPane.add(AddStudentWindow);

		JButton AddCompButton = new JButton("Add Competition");
		AddCompButton.setFont(new Font("Georgia", Font.PLAIN, 20));
		AddCompButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							CompetitionAddFrame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				testFr.setVisible(false);
			}
		});

		AddCompButton.setBounds(64, 72, 246, 40);
		contentPane.add(AddCompButton);

		JButton reset = new JButton("Reset");
		reset.setFont(new Font("Georgia", Font.PLAIN, 20));
		reset.setBounds(64, 241, 246, 40);
		contentPane.add(reset);
	}

	public void loadList() {
		while (globalStudentModel.getRowCount() > 0) {
			globalStudentModel.removeRow(0);
		}
		Scanner infile;
		ArrayList<Student> temp = new ArrayList<Student>();
		try {
			infile = new Scanner(new File("students.txt"));
			while (infile.hasNextLine()) {
				String last = infile.nextLine();
				String first = infile.nextLine();
				String grade = infile.nextLine();
				Student stu = new Student(last, first, grade);
				int index = stu.findLoc(stu, temp);
				temp.add(index, stu);
			}
		} catch (Exception error) {
			System.out.println(error.getMessage());
		}

		for (int i = 0; i < temp.size(); i++) {
			Student current = temp.get(i);
			globalStudentModel.insertRow(i, current.getStudentInfo());
		}
		while (globalCompetitionsModel.getRowCount() > 0) {
			globalCompetitionsModel.removeRow(0);
		}
		ArrayList<Competition> compList = new ArrayList<Competition>();
		Scanner infile2;
		try {
			infile2 = new Scanner(new File("competitions.txt"));

			while (infile2.hasNextLine()) {

				String CompetitionSingleNumberDate = infile2.nextLine();
				String myCompetititonName = infile2.nextLine();
				String myCompetitionLocation = infile2.nextLine();
				String year = CompetitionSingleNumberDate.substring(0, 4);
				String month = CompetitionSingleNumberDate.substring(4, 6);
				String day = CompetitionSingleNumberDate.substring(6, 8);
				String monthSeg = month.substring(0, 1);
				if (monthSeg.equals("0")) {
					month = month.substring(1);
				}
				String daySeg = day.substring(0, 1);
				if (daySeg.equals("0")) {
					day = day.substring(1);
				}
				Competition comp = new Competition(myCompetititonName, Integer.valueOf(month), Integer.valueOf(day),
						Integer.valueOf(year), myCompetitionLocation);
				int index = comp.findLoc(comp, compList);
				compList.add(index, comp);

				while (infile2.hasNextLine()) {
					String read = infile2.nextLine();
					if (read.equals("EVENT LIST COMPLETE") || read.equals("STUDENT LIST COMPLETE")) {
						break;
					}
					String eventName = read.substring(0, read.length() - 1);
					String timeBlock = read.substring(read.length() - 1, read.length());
					Integer block = Integer.valueOf(timeBlock);
					Event tempEvent = new Event(eventName, block);
					comp.addEvent(tempEvent);
					ArrayList<Student> tempStudentList = new ArrayList<Student>();

					while (infile2.hasNextLine()) {
						String studentLastName = infile2.nextLine();
						if (studentLastName.equals("STUDENT LIST COMPLETE")) {
							break;
						}

						String first = infile2.nextLine();
						System.out.print(first);
						String grade = infile2.nextLine();
						Student stu = new Student(studentLastName, first, grade);
						int index1 = stu.findLoc(stu, tempStudentList);

						tempStudentList.add(index1, stu);

						tempEvent.setNewStudentList(tempStudentList);

						comp.addStudentToSetBlock(stu, block);

					}

				}
				System.out.println("NOT REACHING HERE");

				compData.addCompetition(comp);

				System.out.println("the competition has been added");
			}
		} catch (Exception error) {
			System.out.println(error.getMessage());
		}
		for (int i = 0; i < compList.size(); i++) {
			Competition current = compList.get(i);
			globalCompetitionsModel.insertRow(i, current.getCompetitionFormattedInfo());
		}
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}