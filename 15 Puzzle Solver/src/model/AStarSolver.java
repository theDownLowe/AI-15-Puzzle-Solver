package model;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A tree used for solving the 15-puzzle problem.
 * 
 * @author Trevor N. Lowe
 * @version 1
 */
public class AStarSolver {
	
	private HPuzzleState root;
	private PriorityQueue<HPuzzleState> fringe;
	private LinkedList<String> visited;
	
	private int depth; // Depth when solution is found. 0 if no solution found.
	private int numCreated; // Number of nodes created. 0 if depth == -1
	private int numExpanded; // Number of nodes pulled of fringe and not solution. 0 if depth == -1
	private int maxFringe; //size of the fringe at any point. 0 if depth == -1
	
	/**
	 * Initializes and solves a 15-puzzle using BFS.
	 * 
	 * @param board The starting game board
	 */
	public AStarSolver(String board, int mode) {
		root = new HPuzzleState(board, mode);
		fringe = new PriorityQueue<HPuzzleState>();
		visited = new LinkedList<String>();
		fringe.add(root);
		visited.add(root.toString());
		
		depth = 0;
		numCreated = 0;
		numExpanded = 0;
		maxFringe = 0;
		
		solveAStar();
	}
	
	private void solveAStar() {
		
		boolean goalFound = false;
		
		if (isUnsolvable()) {
			fringe.poll();
		}
		
		while (!fringe.isEmpty()) {
			HPuzzleState next = fringe.poll();
			
			if (next.isGoal()) {
				depth = next.getDepth();
				output();
				goalFound = true;
				fringe.clear();
			} else {
				numExpanded++;
				Queue<HPuzzleState> succ = next.getSucc();
				while(!succ.isEmpty()) {
					HPuzzleState nextSucc = succ.poll();
					if (!visited.contains(nextSucc.getBoard())) {

						nextSucc.incDepth();
						fringe.add(nextSucc);
						visited.add(nextSucc.getBoard());
						numCreated++;
						if (fringe.size() > maxFringe) {
							maxFringe = fringe.size();
						}
					}
				}	
			}
		}
		if (!goalFound) {
			depth = 0;
			System.out.println("0, 0, 0, 0");
		}
	}
	
	/** 
	 * Outputs information from the search to the console.
	 */
	private void output() {
		System.out.println(depth + ", " + numCreated
							+ ", " + numExpanded + ", " + maxFringe);
	}

	/**
	 * Returns true if the root is unsolvable.
	 * 
	 * @return true if unsolvable
	 */
	private boolean isUnsolvable() {
		
		int blank = root.getBoard().indexOf(' ');
		int row = 3 -(blank / 4);
		int inversions = 0;
		
		for (int i = 0; i < 16; i++) {
			char piece = root.getBoard().charAt(i);
			if (piece != ' ') {
				for (int j = i; j < 16; j++ ) {
					if (root.getBoard().charAt(j) != ' ' &&
							root.getBoard().charAt(i) > root.getBoard().charAt(j)) {
						inversions++;
					}
				}
			}
		}
		

		if (row == 0 || row == 2) { 		// Blank in odd row
			// Inversions in solvable solution is even
			
			return inversions % 2 == 1;
			
		} else {							// Blank in even row
			// Inversions in solvable solution is odd
			
			return inversions % 2 == 0;
			
		}
	}
}
