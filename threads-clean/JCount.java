// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private JButton start,end;
	private JLabel label;
	private JTextField textField;
	private Worker worker ;
	public JCount() {
		// Set the JCount to use Box layout
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		textField = new JTextField();
		label = new JLabel("0");
		start = new JButton("start");
		end = new JButton("stop");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
				}
				worker = new Worker();
				worker.start();
			}
		});
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
					worker = null;
				}
			}
		});
		add(textField);
		add(label);
		add(start);
		add(end);
		add(Box.createRigidArea(new Dimension(0, 40)));
		// YOUR CODE HERE
	}
	public class Worker extends Thread{
		@Override
		public void run() {
			int target = Integer.parseInt(textField.getText());
			int current = 0;
			while (current <= target && !isInterrupted()) {
				int finalCurrent = current;
				SwingUtilities.invokeLater(() -> label.setText(String.valueOf(finalCurrent)));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
				current += 1000;
			}
		}
	}
	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

