package com.spring.security.spring_security;

import java.util.LinkedList;
import java.util.Queue;

public class FindInfectedServer {
	
	public static void main(String[] args) {
		
		//input matrix where 1 represents infected server, 0 represents clean server and -1 represents empty cell
		int[][] input = new int[][] {{1, 0, 0}, {0, -1, 0}, {0, 0, 1}};
		
		int rows = input.length, columns = input[0].length, minute = 0, cleanServer = 0;
		
		Queue<int[]> infectedcell = new LinkedList<>();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				int curentCell = input[i][j];
				if(curentCell == 1)
					infectedcell.offer(new int[] {i, j});
				else if(curentCell == 0)
					cleanServer++;
			}
		}
		
		int[][] direction = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
		
		while(!infectedcell.isEmpty() && cleanServer > 0) {
			
			boolean isSpreaded = false;
			
			int infectionSize = infectedcell.size();
			
			for(int k = 0; k < infectionSize; k++) {
				int[] infection = infectedcell.poll();
				int row = infection[0], column = infection[1];
				
				for(int[] dir : direction) {
					int rDir = row + dir[0], cDir = column + dir[1];
					if(rDir >= 0 && rDir < rows && cDir >= 0 && cDir < columns && input[rDir][cDir] == 0) {
						input[rDir][cDir] = 1;
						infectedcell.offer(new int[] {rDir, cDir});
						isSpreaded = true;
						cleanServer--;
					}
				}
				
			}
			if (isSpreaded) {
				minute++;
			}
			 
		}
		
		System.out.println(cleanServer == 0 ? minute : -1);
	}
	
}
