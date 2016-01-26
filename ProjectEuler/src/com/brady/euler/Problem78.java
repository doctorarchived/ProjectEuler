package com.brady.euler;

public class Problem78 extends Problem {
	
	
	private int[] p = new int[65536];
	
	public long solve() {
		p[0] = 1;
		for (int i = 1; i < 65536; i++) {
			if (partition(i) == 0) {
				return i;
			}
		}
		return -1;
	}	
	
	/*
	 * Use the partition function recurrence found here https://oeis.org/wiki/Partition_function with memoization.
	 * Work in mod 1000000 since we only care about divisibility by 1000000 in this problem
	 */
	public int partition(int n) {
		int sum = 0;
		for (int j = 1; j <= Math.floor((1 + Math.sqrt(1 + 24*n)) / 6.0); j++) {
			sum = (sum + (j % 2 == 0 ? -1 : 1) * p[(int) (n - j*(3*j-1)/2.0)]);
		}
		for (int j = 1; j <= Math.floor((-1 + Math.sqrt(1 + 24*n)) / 6.0); j++) {
			sum = (sum + (j % 2 == 0 ? -1 : 1) * p[(int) (n - j*(3*j+1)/2.0)]);
		}	
		sum = (((sum % 1000000) + 1000000) % 1000000);
		
		p[n] = sum;
		return p[n];
		
	}
	

}
