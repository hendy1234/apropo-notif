package main.id;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/** @see https://stackoverflow.com/questions/5528939 */
class StopWatch extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int N = 60;
	private static final String stop = "Stop";
	private static final String start = "Start";
	private final ClockListener cl = new ClockListener();
	private final Timer t = new Timer(1000, cl);
	private final JTextField tf = new JTextField(3);
	private static long startTime = 0;

	public StopWatch(String status) {
		t.setInitialDelay(0);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		//panel1.set[Preferred/Maximum/Minimum]Size()

		container.add(panel1);
		container.add(panel2);
		JTextField tfMessage = new JTextField();
		tfMessage.setText("Status changed to : " + status + "\nClose this window when you want to switch back to Available");
		panel1.add(tfMessage);
		
		tf.setHorizontalAlignment(JTextField.RIGHT);
		tf.setEditable(false);
		panel2.add(tf);
		container.add(panel1);
		container.add(panel2);

//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(container);
		this.setTitle("Timer");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void start() {
		t.start();
	}
	public void stop() {
		t.stop();
		this.setVisible(false);
	}

	private class ClockListener implements ActionListener {

		private long count;

		@Override
		public void actionPerformed(ActionEvent e) {
			count = System.currentTimeMillis() - startTime;
			long millis = count % 1000;
			long second = (count / 1000) % 60;
			long minute = (count / (1000 * 60)) % 60;
			long hour = (count / (1000 * 60 * 60)) % 24;

			String time = String.format("%02d:%02d:%02d", hour, minute, second);
			tf.setText(String.valueOf(time));
		}
	}

	public static void startTimer(String status) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				startTime = System.currentTimeMillis();
				StopWatch timer = new StopWatch(status);
				timer.start();
			}
		});
	}
	
	public static void main(String[] args) {
		startTimer("test");
	}
	
}