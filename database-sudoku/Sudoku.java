import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	private int[][] sudokuGrid;
	private int[][] sudokuGridSolution;
	private long duration;
	private ArrayList<Spot> spots;
	private int solutionsNumber;
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	/*public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku.toString(sudoku.sudokuGrid)); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}*/
	
	


	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		sudokuGrid = ints;
		sudokuGridSolution = new int[SIZE][SIZE];

	}
	public Sudoku(String text) {
		this(textToGrid(text));
	}
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		sortSpots();
		long start = System.currentTimeMillis();
		recursivesolve(0);
		long end  = System.currentTimeMillis();
		duration = end  - start;
		return solutionsNumber;
	}

	private void recursivesolve(int index){
		if(solutionsNumber == MAX_SOLUTIONS) return;
		if(index == spots.size()){
			if(solutionsNumber == 0)
				saveFirstSol();
			solutionsNumber++;
			return;
		}

		Spot tempSpot = spots.get(index);

		for(int i : tempSpot.getPossibleOptions()){
			tempSpot.set(i);
			recursivesolve(index+1);
			tempSpot.set(0);
		}
	}

	private void saveFirstSol(){
		for(int i=0; i<SIZE; i++){
			System.arraycopy(sudokuGrid[i], 0, sudokuGridSolution[i], 0, SIZE);
		}
	}

	private void sortSpots() {
		spots = new ArrayList<Spot>();
		for (int row=0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				if(sudokuGrid[row][col] == 0) {
					spots.add(new Spot(row, col));
				}
			}
		}
		Collections.sort(spots);

	}

	public String getSolutionText() {
		return toString(sudokuGridSolution); // YOUR CODE HERE
	}
	//@Override
	public String toString(int[][]grid){
		StringBuilder myString  = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				myString.append(grid[i][j]);
				myString.append(" ");
			}
			myString.append("\n");
		}

		return myString.toString();
	}

	public long getElapsed() {
		return duration;
	}


	private class Spot implements Comparable<Spot> {
		private int x,y;
		private HashSet<Integer> possibleOptions;
		public Spot(int row, int col){
			x = row;
			y = col;
			possibleOptions = getPossibleOptions();
		}
		public HashSet<Integer> getPossibleOptions() {
			HashSet<Integer> result = new HashSet<>();
			HashSet<Integer> badchoice = new HashSet<>();
			for (int i=1; i<=SIZE; i++)
				result.add(i);
			for(int j=0; j<SIZE; j++){
				badchoice.add(sudokuGrid[x][j]);
				badchoice.add(sudokuGrid[j][y]);
				badchoice.add(sudokuGrid[(x/PART) * PART + j%PART][(y/PART) * PART + j/PART]);
			}
			result.removeAll(badchoice);
			return result;
		}
		public void set(int value){
			sudokuGrid[x][y] = value;
		}

		 @Override
		 public int compareTo(Spot o) {
			return possibleOptions.size() - o.possibleOptions.size();
		 }
	 }
}
