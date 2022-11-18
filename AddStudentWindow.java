import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;

public class AddStudentWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldLastName;
	private JTextField textFieldFirstName;
	private AddStudentWindow StudentWindow;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private DefaultTableModel model;
	private DatabaseFolder data;
	private TestFrame myTest;

	
	public AddStudentWindow(ArrayList<Student> list, DefaultTableModel mod, TestFrame test, DatabaseFolder dataList) {
		data = dataList;
		model = mod;
		myTest = test;
		StudentWindow = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 407, 283);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(158, 207, 207));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(47, 59, 81, 13);
		contentPane.add(lblLastName);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(47, 22, 81, 13);
		contentPane.add(lblFirstName);

		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(123, 55, 96, 19);
		contentPane.add(textFieldLastName);
		textFieldLastName.setColumns(10);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(123, 22, 96, 19);
		contentPane.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setBounds(47, 123, 45, 13);
		contentPane.add(lblGrade);

		JRadioButton rdbtn9th = new JRadioButton("9th");
		rdbtn9th.setBounds(98, 119, 45, 21);
		contentPane.add(rdbtn9th);

		JRadioButton rdbtn10th = new JRadioButton("10th");
		rdbtn10th.setBounds(143, 119, 51, 21);
		contentPane.add(rdbtn10th);

		JRadioButton rdbtn11th = new JRadioButton("11th");
		rdbtn11th.setBounds(190, 119, 59, 21);
		contentPane.add(rdbtn11th);

		JRadioButton rdbtn12th = new JRadioButton("12th");
		rdbtn12th.setBounds(249, 119, 61, 21);
		contentPane.add(rdbtn12th);

		ButtonGroup grades = new ButtonGroup();
		grades.add(rdbtn12th);
		grades.add(rdbtn11th);
		grades.add(rdbtn10th);
		grades.add(rdbtn9th);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(210, 165, 85, 21);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String last = textFieldLastName.getText();
				String first = textFieldFirstName.getText();
				String grade = "";
				Enumeration<AbstractButton> buttons = grades.getElements();
				while (buttons.hasMoreElements()) {
					AbstractButton button = buttons.nextElement();
					if (button.isSelected()) {
						grade = button.getText();
					}
				}
				String errorMessage = "";
				if (last.equals("")) {
					errorMessage += "Please enter a last name";
				}
				if (first.equals("")) {
					errorMessage += "\nPlease enter a first name";
				}
				if (grade.equals("")) {
					errorMessage += "\nPlease choose a grade level";
				}
				if (!errorMessage.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if (!(last.equals("") || first.equals("") || grade.equals(""))) 
				{
					Student stu = new Student(last, first, grade); 
					data.addStudent(stu); 
					textFieldLastName.setText("");
					textFieldFirstName.setText("");
					grades.clearSelection();
					StudentWindow.setVisible(false);
					test.loadList(); 
					test.setVisible(true);
				}

			}
		});
		contentPane.add(btnSave);
	}
}