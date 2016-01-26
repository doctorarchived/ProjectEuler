package com.brady.euler;

import java.math.BigInteger;

public class Problem100 extends Problem {

	public long solve() {
		
		long discs = 0;
		long blues = 0;
		int i = 1;
		while ((discs = countDiscs(i)) < 1E12) {
			blues = countBlues(i);
			System.out.println(blues + " / " + discs);
			i++;
		}
		blues = countBlues(i);
		
		System.out.println(blues + " / " + discs);
		
		
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
