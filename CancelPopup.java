import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class CancelPopup extends JFrame {

	private JPanel contentPane;
	private CancelPopup window; 
	private TestFrame ogTest;
	private CompetitionWindow oldComp;

	public CancelPopup(TestFrame test, CompetitionWindow compWindow) {

		window = this;
		ogTest = test;
		oldComp = compWindow;


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 308, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel CancelLAbel = new JLabel("Are you sure you want to cancel?");
		CancelLAbel.setBounds(59, 32, 200, 46);
		contentPane.add(CancelLAbel);

		JButton YesButton = new JButton("Yes");
		YesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				window.setVisible(false); 
				ogTest.setVisible(true);
				oldComp.setVisible(false);

			}
		});
		YesButton.setBounds(38, 89, 89, 23);
		contentPane.add(YesButton);

		JButton NoButton = new JButton("No");
		NoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				window.setVisible(false); 
			}
		});
		NoButton.setBounds(151, 89, 89, 23);
		contentPane.add(NoButton);
	}

}

