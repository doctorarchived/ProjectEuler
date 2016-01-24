package com.brady.euler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Problem83 extends Problem {

	int size = 80;
	int[] grid = new int[size*size];
	
	private int[] getNewNeighbours(int cur, Set<Integer> closedSet) {
		int[] neighbours = new int[4];
		int col = cur % size, row = cur / size, num = 0;
		
		if (col < size-1 && !closedSet.contains(cur+1)) {
			neighbours[num] = cur+1;
			num++;
		} 
		
		if (col > 0 && !closedSet.contains(cur-1)) {
			neighbours[num] = cur-1;
			num++;
		}
		
		if (row > 0 && !closedSet.contains(cur-size)) {
			neighbours[num] = cur-size;
			num++;
		}
		
		if (row < size-1 && !closedSet.contains(cur+size)) {
			neighbours[num] = cur+size;
			num++;
		}
		return num < 4 ? Arrays.copyOf(neighbours, num) : neighbours;
			
	}	
	private int aStar(int start, int end, int[] h) {
		final int[] f = new int[end-start+1];
		final int[] g = new int[end-start+1];
		Arrays.fill(f, Integer.MAX_VALUE);
		Arrays.fill(g, Integer.MAX_VALUE);
		g[0] = grid[0];
		f[0] = g[0] + h[0];
		
		PriorityQueue<Integer> openSet = new PriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return f[o1] - f[o2];
			}
		});
		Set<Integer> closedSet = new HashSet<>();
		openSet.add(0);
		while (!openSet.isEmpty()) {
			int cur = openSet.poll();
			if (cur == end) {
				return f[cur];
			}
			closedSet.add(cur);
			int[] newNeighbours = getNewNeighbours(cur, closedSet);
			for (int n : newNeighbours) {
				int tempG = g[cur] + grid[n];
				if (tempG < g[n]) {
					if (openSet.contains(n)) openSet.remove(n);
					g[n] = tempG;
					f[n] = g[n] + h[(n % size) + (n / size)];
					openSet.add(n);
				}
			}
		}
		return -1;
		
	}
	
	@Override
	public int solve(Scanner in) {
		in.useDelimiter(",|\n");
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		size = 80;
		grid[0] = Integer.parseInt(in.next());
		for (int i = 1; i < size*size; i++) {
			grid[i] = Integer.parseInt(in.next());
			pq.offer(grid[i]);
		}

		int[] h = new int[size+size - 1];
		for (int i = h.length - 2; i >= 0; i--) {
			h[i] = pq.poll() + h[i+1];
		}
		return aStar(0, grid.length-1, h);
	}

}
