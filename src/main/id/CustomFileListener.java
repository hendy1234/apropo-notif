package main.id;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;


public class CustomFileListener implements FileListener {

	private long startTime = 0;
	private final StopwatchListener sl = new StopwatchListener();
	private final JLabel tf = new JLabel("00:00:00");
	private final Timer t = new Timer(1000, sl);

	public void fileChanged(FileChangeEvent arg0) throws Exception {

//		File file = new File("/home/hendy/Desktop/ada waktu rapikan/project_apropo/test.txt");
		File file = new File(arg0.getFile().toString().substring(7));
//		File file = new File(
//				"C:/Users/Rita Diana/Desktop/projectIpt/test/test.txt");
//				"C:/Users/rdiana2/AppData/Local/Enghouse/Agent/User1449133621/Logs/RDiana2@id0030cim01.txt");
		String lastLine = Util.tail(file);
		System.out.println("Last Line: " + lastLine);
		String[] splitted = lastLine.split("\\|");

		if (splitted.length >= 18) {
			String code = splitted[12];
			String status = splitted[18];
			if (code.startsWith("AGENT_AVAILABILITY_STATE") && (status.equalsIgnoreCase("PNB")
					|| status.equalsIgnoreCase("Lunch") || status.equalsIgnoreCase("Start of shift"))) {

				startTime = System.currentTimeMillis();
				JFrame frame = new JFrame("Ini Disini");
				frame.setPreferredSize(getInitDimention());
				
				JPanel container = new JPanel();
			    container.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
				container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

//				JPanel panel1 = new JPanel();
//				JPanel panel2 = new JPanel();
				JPanel panel3 = new JPanel();
				JButton button1 = new JButton();
				
				JLabel text1 = new JLabel();
				text1.setAlignmentX(Component.LEFT_ALIGNMENT);
				text1.setText("Status changed to : " + status);
//				panel1.add(text1);

				JLabel text2 = new JLabel();
				text2.setAlignmentX(Component.LEFT_ALIGNMENT);
				text2.setText("Close this window when you want to switch back to Available");
//				panel2.add(text2);
				
				panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
				tf.setAlignmentX(Component.LEFT_ALIGNMENT);
				panel3.add(tf);
				
				button1.setAlignmentX(Component.LEFT_ALIGNMENT);
				button1.setText("OK");
				button1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});

				container.add(text1);
				container.add(text2);
				container.add(panel3);
				container.add(button1);
				
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.add(container);
				frame.setVisible(true);
				t.start();

			}
		}

	}

	public void fileCreated(FileChangeEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	public void fileDeleted(FileChangeEvent arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	private class StopwatchListener implements ActionListener {

		private long count;

		@Override
		public void actionPerformed(ActionEvent e) {
			count = System.currentTimeMillis() - startTime;
			long second = (count / 1000) % 60;
			long minute = (count / (1000 * 60)) % 60;
			long hour = (count / (1000 * 60 * 60)) % 24;

			String time = String.format("%02d:%02d:%02d", hour, minute, second);
			tf.setText(String.valueOf(time));
		}
	}
	
	private Dimension getInitDimention() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int height = screenSize.height * 1 / 6;
		int width = screenSize.width * 1 / 4;
		
		return new Dimension(width, height);
	}
}
