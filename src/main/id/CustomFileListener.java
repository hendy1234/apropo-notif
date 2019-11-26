package main.id;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;

public class CustomFileListener implements FileListener {

	private long startTime = 0;
	private final StopwatchListener sl = new StopwatchListener();
	private final JTextField tf = new JTextField(3);
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

//				JFrame frame = new JFrame("Ini Disini");
//				frame.pack();
//				Component parent = null;
//				frame.setLocationRelativeTo(parent);
//				frame.setVisible(true);
//				frame.setAlwaysOnTop(true);
//
//				startTime = System.currentTimeMillis();
//				JPanel panel1 = new JPanel();
//				tf.setHorizontalAlignment(JTextField.RIGHT);
//				tf.setEditable(false);
//				t.start();
//				panel1.add(tf);
//				frame.add(panel1);
//
//				JOptionPane.showMessageDialog(frame, "Status changed to : " + status
//						+ "\nClose this window when you want to switch back to Available");
//				frame.setVisible(false);

				String message = "Status changed to : " + status
						+ "\nClose this window when you want to switch back to Available";

				final JOptionPane msg = new JOptionPane(message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION);
				msg.
				final JDialog dlg = msg.createDialog("Ini yang ini");

				msg.setInitialSelectionValue(JOptionPane.OK_OPTION);
				dlg.setAlwaysOnTop(true);
				dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dlg.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentShown(ComponentEvent e) {
						super.componentShown(e);
						final Timer t = new Timer(1000, new ActionListener() {

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

						});
						t.start();
					}
				});
				dlg.setVisible(true);

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
}
