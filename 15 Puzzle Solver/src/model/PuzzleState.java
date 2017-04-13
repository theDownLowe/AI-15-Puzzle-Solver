package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A node holding some state of the puzzle.
 * 
 * @author Trevor N. Lowe
 * @version 1
 */
public class PuzzleState {
	
	private final int PUZZLE_SIZE = 16;
	private final String GOAL_STATE = "123456789ABCDEF ";
	private String boardState;
	private int depth;
	
	/**
	 * Initializes the current board state.
	 * 
	 * @param board
	 */
	public PuzzleState(String board) {
		boardState = board;
		depth = 0;
	}
	
	/**
	 * Initializes chiild nodes.
	 * 
	 * @param board board state
	 * @param newDepth the new depth
	 */
	public PuzzleState(String board, int newDepth) {
		boardState = board;
		depth = newDepth;
	}
	
	/**
	 * Returns an ArrayList of successor states of the current state.
	 * 
	 * @return ArrayList of successor states
	 */
	public Queue<PuzzleState> getSucc() {
		
		Queue<PuzzleState> succ = new LinkedList<PuzzleState>();
		
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
	private void addSucc(int blank, int newIndex, Queue<PuzzleState> succ) {
		
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
		PuzzleState newSucc = new PuzzleState(s1 + s.charAt(newIndex) + s2 + s.charAt(blank) + s3, depth);
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
}
