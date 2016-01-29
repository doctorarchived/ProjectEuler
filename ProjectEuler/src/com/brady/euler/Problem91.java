package com.brady.euler;

public class Problem91 extends Problem {
	
	public long solve() {
		int grid = 50;
		int count = 0;
		for (int x1 = 0; x1 <= grid; x1++) {
			for (int y1 = 1 ; y1 <= grid; y1++) {
				int[] a3 = new int[]{0 - x1, 0 - y1};
				for (int x2 = 1; x2 <= grid; x2++) {
					int maxY = x1 == 0 ? grid  : x2 * y1/ x1 - 1;
					for (int y2 = 0; y2 <= maxY; y2++) {
						
						int[] a1 = new int[]{x2,y2};
						int[] a2 = new int[]{x1 - x2, y1 - y2};
						
						if (isPerpendicular(a1,a2) || isPerpendicular(a2,a3) || isPerpendicular(a3,a1)) {
							count++;
						}	
					}
				
				}

			}
		}
		return count;
	}
	
	private boolean isPerpendicular(int[] a1, int[] a2) {
		return a1[0]*a2[0] + a1[1]*a2[1] == 0;
	}
}
