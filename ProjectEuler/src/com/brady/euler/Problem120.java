package com.brady.euler;

public class Problem120 extends Problem {
	
	public long solve() {
			
		int rMaxSum = 0;
		for (int a = 3; a <= 1000; a++) {
			rMaxSum += rMax(a);
		}
		return rMaxSum;
	}

	
	private int rMax(int a) {
		int max = 0;
		for (int n = 0; n < a*a; n++) {
			if (n % 2 == 0) {
				if (2 > max) {
					max = 2;
				}
			} else {
				int rem = 2*n*a % (a*a);
				if (rem > max) {
					max = rem;
				}
			}
		}
		return max;
	}
}
