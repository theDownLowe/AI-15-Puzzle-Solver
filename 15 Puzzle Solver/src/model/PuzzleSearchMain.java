package model;
// Trevor N. Lowe
// TCSS 435 - AI
// Programming Assignment 1

/** This program finds the solution to a 15 piece
 * 	slide puzzle using several different search algorithms
 *  as decided by the user through the console.
 *  The search algorithms are BFS, DFS, GBFS, A*, DLS/ID.
 * 
 *  @author Trevor N. Lowe
 *  @version 1
 */
public class PuzzleSearchMain {
	
	public static void main(String [] args) {
		
		String inState = "";
		String method = "";
		String option = "";

		
		// CMD input retrieval 
		if (args.length != 0 && args[0] != null && args[1] != null
				&& args.length <= 3) {
			
			inState = args[0];
			method = args[1];
			
			// Confirms A-F, 1-9, ' ' input
			if (!(inState.matches(".*[A-F 1-9].*") 
					&& !inState.matches(".*[a-zG-Z].*"))) {
				System.out.println("Incorrect input ... Exiting");
				System.exit(0);
			}
			if (args.length == 3) {
				option = args[2];
			}
			
		} else {
			System.out.println("Incorrect input ... Exiting");
			System.exit(0);
		}
		
		
		// Run Search Method
		if (method.equals("BFS")) {
			// Breadth-First Search
			useBFS(inState);
			
		} else if (method.equals("DFS")) {
			// Depth-First Search
			useDFS(inState);
			
		} else if (method.equals("GBFS")) {
			// Greedy Best-First Search
			if (option.equals("") || option.charAt(0) != 'h' || option.length() < 2) {
				System.out.println("Incorrect input ... Exiting");
				System.exit(0);
			}
			String h = "" + option.charAt(1);
			int heuristic = Integer.parseInt(h);
			
			useGBFS(inState, heuristic);
			
		} else if (method.equals("A*")) {
			// AStar
			if (option.equals("") || option.charAt(0) != 'h' || option.length() < 2) {
				System.out.println("Incorrect input ... Exiting");
				System.exit(0);
			}
			String h = "" + option.charAt(1);
			int heuristic = Integer.parseInt(h);
			
			useAStar(inState, heuristic);
			
		} else if (method.equals("DLS")) {
			// Depth-Limited Search
			if (option.equals("")) {
				System.out.println("Incorrect input ... Exiting");
				System.exit(0);
			}
			String d = "" + option.charAt(0);
			int depth = Integer.parseInt(d);
			
			useDLS(inState, depth);
			
		} else if (method.equals("ID")) {
			// Iterative Deepening - NOT IMPLEMENTED
			System.out.println("This algorithm is not implemented ... exiting");
			System.exit(0);
		} else {
			System.out.println("Incorrect input ... Exiting");
			System.exit(0);
		}
	}
	
	/**
	 * This solves the 15-piece slide puzzle using BFS.
	 * 
	 * @param state The initial state of the puzzle
	 */
	public static void useBFS(String state) {
		new BFSSolver(state);

	}
	
	/**
	 * This solves the 15-piece slide puzzle using DFS.
	 * 
	 * @param state The initial state of the puzzle
	 */
	public static void useDFS(String state) {
		new DFSSolver(state);
	}
	
	/**
	 * This solves the 15-piece slide puzzle using GBFS.
	 * 
	 * @param state The initial state of the puzzle
	 * @param heuristic The heuristic selected
	 */
	public static void useGBFS(String state, int heuristic) {
		new GBFSSolver(state, heuristic);
		

	}
	
	/**
	 * This solves the 15-piece slide puzzle using A*.
	 * 
	 * @param state The initial state of the puzzle
	 * @param heuristic The heuristic selected
	 */
	public static void useAStar(String state, int heuristic) {
		new AStarSolver(state, heuristic);
	}
	
	/**
	 * This solves the 15-piece slide puzzle using DLS.
	 * 
	 * @param state The initial state of the puzzle
	 * @param depth The max depth of the search
	 */
	public static void useDLS(String state, int depth) {
		new DLSSolver(state, depth);
	}
}

