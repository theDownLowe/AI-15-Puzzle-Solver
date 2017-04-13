package model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A node holding some state of the puzzle with heuristics.
 * 
 * @author Trevor N. Lowe
 * @version 1
 */
public class HPuzzleState implements Comparable<HPuzzleState>{
	
	private final int PUZZLE_SIZE = 16;
	private final String GOAL_STATE = "123456789ABCDEF ";
	private String boardState;
	private int depth;
	private int mode;
	private int h1; // Number of misplaced tiles
	private int h2; // Sum of each tiles M-distance
	
	/**
	 * Initializes the current board state.
	 * 
	 * @param board
	 */
	public HPuzzleState(String board, int newMode) {
		boardState = board;
		mode = newMode;
		depth = 0;
		h1 = 0;
		h2 = 0;
		if (mode == 1) {
			calcH1();
		}
		if (mode == 2) {
			calcH2();
		}
	}
	
	/**
	 * Calculates the number of misplaced tiles.
	 */
	private void calcH1() {
		int misplaced = 0;
		if (boardState.charAt(0) != '1') {
			misplaced++;
		}
		if (boardState.charAt(1) != '2') {
			misplaced++;
		}
		if (boardState.charAt(2) != '3') {
			misplaced++;
		}
		if (boardState.charAt(3) != '4') {
			misplaced++;
		}
		if (boardState.charAt(4) != '5') {
			misplaced++;
		}
		if (boardState.charAt(5) != '6') {
			misplaced++;
		}
		if (boardState.charAt(6) != '7') {
			misplaced++;
		}
		if (boardState.charAt(7) != '8') {
			misplaced++;
		}
		if (boardState.charAt(8) != '9') {
			misplaced++;
		}
		if (boardState.charAt(9) != 'A') {
			misplaced++;
		}
		if (boardState.charAt(10) != 'B') {
			misplaced++;
		}
		if (boardState.charAt(11) != 'C') {
			misplaced++;
		}
		if (boardState.charAt(12) != 'D') {
			misplaced++;
		}
		if (boardState.charAt(13) != 'E') {
			misplaced++;
		}
		if (boardState.charAt(14) != 'F') {
			misplaced++;
		}
		if (boardState.charAt(15) != ' ') {
			misplaced++;
		}
		
		h1 = misplaced;
	}
	
	/**
	 * Calculates the sum of each tiles M-distance.
	 */
	private void calcH2() {
		int index = 0;
		String s = boardState;
		int numArr[] = new int[PUZZLE_SIZE];
		
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			if (s.charAt(i) == 'A') {
				numArr[i] = 10;				
			} else if (s.charAt(i) == 'B'){
				numArr[i] = 11;				
			} else if (s.charAt(i) == 'C'){
				numArr[i] = 12;				
			} else if (s.charAt(i) == 'D'){
				numArr[i] = 13;				
			} else if (s.charAt(i) == 'E'){
				numArr[i] = 14;				
			} else if (s.charAt(i) == 'F'){
				numArr[i] = 15;				
			} else if (s.charAt(i) == ' '){
				numArr[i] = 0;
			} else {
				numArr[i] = Integer.parseInt("" + s.charAt(i));
			}
		}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int num = (numArr[index] - 1);
				if (num != -1) {
					int x = num % 3;
					int y = num / 3;
					h2 += Math.abs(x - i) + Math.abs(y - j);
				}
				index ++;
			}
		}
	}
	
	/**
	 * Returns an ArrayList of successor states of the current state.
	 * 
	 * @return ArrayList of successor states
	 */
	public Queue<HPuzzleState> getSucc() {
		
		Queue<HPuzzleState> succ = new LinkedList<HPuzzleState>();
		
		int blank = boardState.indexOf(' ');
		
		// Slide a piece to the right
		if (blank != 0 && blank != 4 && blank != 8 && blank != 12) {
			addSucc(blank, blank - 1, succ);
		}
		
		// Slide a piece down
		if (blank != 0 && blank != 1 && blank != 2 && blank != 3) {
			addSucc(blank, blank - 4, succ);
		}
		
		// Slide a piece to the left
		if (blank != 3 && blank != 7 && blank != 11 && blank != 15) {
			addSucc(blank, blank + 1, succ);
		}
		
		// Slide a piece up
		if (blank != 12 && blank != 13 && blank != 14 && blank != 15) {
			addSucc(blank, blank + 4, succ);
		}
		
		return succ;
	}
	
	/**
	 * Swaps the given tiles of the current state and adds the new state
	 * 	to the given list.
	 * 
	 * @param blank Location of the blank tile
	 * @param newIndex Location of the moving tile
	 * @param succ The list of the current states successors
	 */
	private void addSucc(int blank, int newIndex, Queue<HPuzzleState> succ) {
		
		// Swap values
		if (blank > newIndex) {
			int tmp = blank;
			blank = newIndex;
			newIndex = tmp;
		}
		
		String s = boardState;
		String s1 = s.substring(0,blank);
		String s2 = s.substring(blank + 1, newIndex);
		String s3 = s.substring(newIndex + 1);
		HPuzzleState newSucc = new HPuzzleState(s1 + s.charAt(newIndex) + s2 + s.charAt(blank) + s3, mode);
		succ.add(newSucc);
	}
	
	/**
	 * Increment the depth by 1
	 */
	public void incDepth() {
		depth++;
	}
	
	/**
	 * Returns the depth.
	 * 
	 * @return depth
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Returns true if the current state is a goal state.
	 * 
	 * @return True if state is goal state
	 */
	public boolean isGoal() {
		return boardState.equals(GOAL_STATE);
	}
	
	/**
	 * Returns the current board state.
	 * @return Current board state
	 */
	public String getBoard() {
		return boardState;
	}
	
	/**
	 * Return the num misplaced tiles.
	 * 
	 * @return num misplaced
	 */
	public int getH1() {
		return h1;
	}
	
	/**
	 * Return the sum of manhattan distance.
	 * 
	 * @return Sum of m-distance
	 */
	public int getH2() {
		return h1;
	}
	
	/**
	 * Compares two puzzle states based on heuristic
	 */
	public int compareTo(HPuzzleState s2) {
		if (mode == 1) { // Heuristic 1
			if (this.getH1() < s2.getH1()) {
				return -1;
			} else if (this.getH1() > s2.getH1()) {
				return 1;
			}
		} else {         // Heuristic 2
			if (this.getH2() < s2.getH2()) {
				return -1;
			} else if (this.getH2() > s2.getH2()) {
				return 1;
			}
		}

		return 0;
	}
}
