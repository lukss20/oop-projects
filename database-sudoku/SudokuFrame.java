import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	 private JTextArea solution, puzzle;
	 private JCheckBox checkBox;
	 private JButton solveButton;
	 private Box southBox;

	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
		setLayout((new BorderLayout(4,4)));
		solution = new JTextArea(15,20);
		puzzle = new JTextArea(15,20);
		solution.setBorder(new TitledBorder("Solution"));
		puzzle.setBorder(new TitledBorder("Puzzle"));
		checkBox = new JCheckBox("auto check");
		solveButton = new JButton("check");
		checkBox.setSelected(true);
		southBox = Box.createHorizontalBox();
		southBox.add(checkBox);
		southBox.add(solveButton);
		add(solution, BorderLayout.EAST);
		add(puzzle, BorderLayout.CENTER);
		add(southBox, BorderLayout.SOUTH);
		solveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solveSudoku();
			}
		});
		puzzle.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (checkBox.isSelected())
					solveSudoku();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (checkBox.isSelected())
					solveSudoku();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (checkBox.isSelected())
					solveSudoku();
			}
		});



		// Could do this:
		setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	private void solveSudoku() {
		Sudoku mySudoku;
		String result = "";
		try{
			mySudoku = new Sudoku(puzzle.getText());
		} catch (Exception e) {
			solution.setText("Parsing Problem");
			return;
		}
		int solutionNumber = mySudoku.solve();
		if(solutionNumber == 0) {
			solution.setText(result);
			return;
		}
		result = mySudoku.getSolutionText();
		result += "solutions:" + solutionNumber + "\n";
		result += "elapsed:" + mySudoku.getElapsed() + "ms" + "\n";
		solution.setText(result);


	}
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
