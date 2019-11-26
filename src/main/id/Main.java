package main.id;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

public class Main {
	public static void main(String[] args) {
		Executor runner = Executors.newFixedThreadPool(1);
		runner.execute(new Runnable() {

			@Override
			public void run() {
				try {
					FileSystemManager fsManager = VFS.getManager();

					String logFolder = JOptionPane.showInputDialog(
							"Please input path and file name to your apropo log file:\nEx: C:/Users/rdiana2/AppData/Local/Enghouse/Agent/User1449133621/Logs/RDiana2@id0030cim01.txt");
					FileObject listendir = fsManager.resolveFile(logFolder);
//	    					.resolveFile(usersHome + "/Desktop/projectIpt/test/test.txt");
//	    					.resolveFile("C:/Users/Rita Diana/Desktop/projectIpt/test/test.txt");
//	    					.resolveFile("C:/Users/rdiana2/AppData/Local/Enghouse/Agent/User1449133621/Logs/RDiana2@id0030cim01.txt");
					DefaultFileMonitor fm = new DefaultFileMonitor(new CustomFileListener());
					fm.setRecursive(true);
					fm.addFile(listendir);
					fm.start();
				} catch (FileSystemException e) {
					System.out.println("error: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("error: " + e.getMessage());
				}
			}

		});
	}
}
