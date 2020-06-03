import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Panel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel seg_start_label, seg_end_label, seg_gain_label;
	private JTextField seg_start_text, seg_end_text, seg_gain_text;
	private JButton solve_button, next_button;
	private int width, height;

	Panel() {
		initialize();
	}

	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth() - 120;
		height = (int) screenSize.getHeight() - 120;
		Data.width = width;
		Data.height = height;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(120, 120, 1000, 650);
		setTitle("Signal FLow Graph Solver");
		setLayout(null);
		setResizable(true);

		seg_start_label = new JLabel("from node #");
		seg_start_label.setBounds(60, 10, 160, 50);
		seg_start_text = new JTextField();
		seg_start_text.setBounds(60, 80, 160, 50);

		seg_end_label = new JLabel("to node #");
		seg_end_label.setBounds(240, 10, 160, 50);
		seg_end_text = new JTextField();
		seg_end_text.setBounds(220, 80, 160, 50);

		seg_gain_label = new JLabel("gain");
		seg_gain_label.setBounds(420, 10, 160, 50);
		seg_gain_text = new JTextField();
		seg_gain_text.setBounds(380, 80, 160, 50);


		next_button = new JButton("next");
		next_button.setBounds(560, 80, 140, 50);
		solve_button = new JButton("solve");
		solve_button.setBounds(750, 80, 100, 50);



		solve_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Mason mason = new Mason();
				mason.setSFG(Data.segmentsGains);
				Data.forwardPaths = mason.getForwardPaths();
				Data.loops = mason.getLoops();
				Data.nonTouchingloops = mason.getNonTouchingLoops();
				Data.overAllTF = mason.getOvalAllTF();
				Data.loopsGain = mason.getLoopGains();
				Data.forwardPathsGain = mason.getForwardPathGains();
				Data.nonTouchingloopsGain = mason.getNonTouchingLoopGains();
				ResultView result = new ResultView();
				result.setVisible(true);
			}
		});
		next_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Valid.isValidInt(seg_start_text.getText())) {
					ErrorView errView = new ErrorView(
							"from node, invalid numeric value!");
					errView.setVisible(true);
				} else if (!Valid.isValidInt(seg_end_text.getText())) {
					ErrorView errView = new ErrorView(
							"to node, invalid numeric value!");
					errView.setVisible(true);
				} else if (!Valid.isValidDouble(seg_gain_text.getText())) {
					ErrorView errView = new ErrorView(
							"segment gain, invalid numeric value!");
					errView.setVisible(true);
				} else {
					int n1 = Integer.parseInt(seg_start_text.getText()), n2 = Integer
							.parseInt(seg_end_text.getText());
					if (n1 > Data.numOfNodes || n2 > Data.numOfNodes) {
						ErrorView errView = new ErrorView(
								"node number exceeded max number of nodes!");
						errView.setVisible(true);
					} else if (n1 < 1 || n2 < 1) {
						ErrorView errView = new ErrorView(
								"invalid node number!");
						errView.setVisible(true);
					} else if (n1 == Data.numOfNodes) {
						ErrorView errView = new ErrorView(
								"no feedback allowded from node # "
										+ Data.numOfNodes);
						errView.setVisible(true);
					} else if (n2 == 1) {
						ErrorView errView = new ErrorView(
								"no feedback allowded to node # 1");
						errView.setVisible(true);
					} else {
						double g = Double.parseDouble(seg_gain_text.getText());
						Data.segmentsGains[n1 - 1][n2 - 1] = g;
						seg_end_text.setText("");
						seg_start_text.setText("");
						seg_gain_text.setText("");
					}
				}
			}
		});

		

		Font font = new Font("Serif", Font.PLAIN, 24);
		seg_start_label.setFont(font);
		seg_end_label.setFont(font);
		seg_gain_label.setFont(font);
		seg_start_text.setFont(font);
		seg_end_text.setFont(font);
		seg_gain_text.setFont(font);
		next_button.setFont(font);
		solve_button.setFont(font);


		getContentPane().add(seg_start_label);
		getContentPane().add(seg_end_label);
		getContentPane().add(seg_gain_label);
		getContentPane().add(seg_start_text);
		getContentPane().add(seg_end_text);
		getContentPane().add(seg_gain_text);
		getContentPane().add(solve_button);
		getContentPane().add(next_button);

	}
}