package com.brady.euler;

public class Problem100 extends Problem {

	public long solve() {
		
		long blues = 0;
		int i = 1;
		while (countDiscs(i) < 1E12) {
			blues = countBlues(i);
			i++;
		}
		blues = countBlues(i);
				
		return blues;
		
	}
	
	private long countDiscs(double m) {
		
		double sqrt2 = Math.sqrt(2);
		double dif = Math.pow(3 - 2*sqrt2, m);
		double sum = Math.pow(3 + 2*sqrt2, m);
		return (long) ((sqrt2*sum + 2 - sum - sqrt2*dif - dif) / 4L + 0.5);
		
	}
	
	private long countBlues(double m) {
		
		double sqrt2 = Math.sqrt(2);
		double dif = Math.pow(3 - 2*sqrt2, m);
		double sum = Math.pow(3 + 2*sqrt2, m);
		return (long) ((2*dif + sqrt2*dif + 2*sum - sqrt2*sum + 4) / 8L + 0.5);
		
	}
	
}
