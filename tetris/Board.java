// Board.java

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private int[] heights;
	private int[] widths;
	private boolean DEBUG = true;
	boolean committed;
	private boolean[][] tempgrid;
	private int[] tempheights;
	private int[] tempwidths;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		heights = new int[width];
		widths = new int[height];
		tempgrid = new boolean[width][height];
		tempheights = new int[width];
		tempwidths = new int[height];
		// YOUR CODE HERE
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		int res =0;
		for(int i =0; i< heights.length; i++){
			if(res<heights[i]){
				res=heights[i];
			}
		}
		return res;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int[] mywidths = new int[widths.length];
			int[] myheights = new int[heights.length];
			int maxheight = getMaxHeight();
			int mymaxheight = 0;
			int count=0;
			for (int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					if(grid[j][i]){
						count++;
						mymaxheight = i+1;
						myheights[j] = i+1;
					}
				}
				mywidths[i] = count;
				count=0;
			}
			if(!Arrays.equals(mywidths,widths)) throw new RuntimeException("Sanity check failed on widths");
			if(maxheight!=mymaxheight) throw new RuntimeException("Sanity check failed maxheight");
			if(!Arrays.equals(myheights,heights)) throw new RuntimeException("Sanity check failed heights");

		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int res = 0;
		for(int  i=x; i < piece.getWidth()+x; i++)
			if(res < heights[i]-piece.getSkirt()[i-x]) res = heights[i]-piece.getSkirt()[i-x];
		return res;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x<0 || y<0 || x >= width || y >= height|| grid[x][y]) return true;
		return false;
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed = false;
		int result = PLACE_OK;
		System.arraycopy(widths,0,tempwidths,0,widths.length);
		System.arraycopy(heights,0,tempheights,0,heights.length);
		for(int i =0; i< heights.length; i++)
			System.arraycopy(grid[i],0,tempgrid[i],0,widths.length);
		for(int i = 0; i < piece.getBody().length; i++) {
			int xpoint = piece.getBody()[i].x +x;
			int ypoint = piece.getBody()[i].y +y;
			if(xpoint<0 || ypoint<0 || xpoint >= width || ypoint >= height) return PLACE_OUT_BOUNDS;
			if(grid[xpoint][ypoint]) return PLACE_BAD;
			grid[xpoint][ypoint] = true;
			widths[ypoint]++;
			if(getColumnHeight(xpoint) < ypoint+1) heights[xpoint] = ypoint+1;
			if(widths[ypoint] ==width) result =  PLACE_ROW_FILLED;
		}
		sanityCheck();
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		committed = false;
		System.arraycopy(widths,0,tempwidths,0,widths.length);
		System.arraycopy(heights,0,tempheights,0,heights.length);
		for(int i =0; i< heights.length; i++)
			System.arraycopy(grid[i],0,tempgrid[i],0,widths.length);
		boolean[][] temp = new boolean[width][height];
		int[] tempWidths = new int[widths.length];
		int[] count = afterClearings(temp,tempWidths);
		rowsCleared = count[1];
		fillWithFalse(count[0],temp,tempWidths);
		widths = tempWidths;
		grid = temp;
		changeHeights(rowsCleared);
		sanityCheck();
		return rowsCleared;
	}
	private void changeHeights(int clear){
		Arrays.fill(heights,0);
		for (int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(grid[j][i]){
					heights[j] = i+1;
				}
			}
		}

	}
	private void fillWithFalse(int count, boolean[][] temp,int[] tempWidths) {
		for(int i = count; i < height; i++) {
			for(int j = 0; j < width; j++) {
				temp[j][i]=false;
			}
			tempWidths[i] = 0;
		}
	}
	private int[] afterClearings(boolean[][] temp,int[] tempWidths) {
		int[] result = new int[2];
		for (int i = 0; i < height; i++) {
			if(widths[i] != width) {
				for(int j = 0; j < width; j++) {
					temp[j][result[0]]=grid[j][i];
				}
				tempWidths[result[0]] = widths[i];
				result[0]++;
			}else result[1]++;
		}
		return result;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(!committed){
			committed = true;
			boolean[][] tempg = grid;
			grid = tempgrid;
			tempgrid = tempg;
			int[] tempw = widths;
			widths = tempwidths;
			tempwidths = tempw;
			int[] temph = heights;
			heights = tempheights;
			tempheights = temph;
			sanityCheck();
		}
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
*/
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}


}


