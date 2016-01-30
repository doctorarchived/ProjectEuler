package com.brady.euler;

import java.util.HashSet;
import java.util.Set;

public class Problem125 extends Problem {

	int n = (int) 1E8;
	long[] squaresUpTo = new long[(int) Math.sqrt(n) + 1];
	
	public long solve() {
		
		Set<Long> seen = new HashSet<Long>();

		for (int i = 1; i < squaresUpTo.length; i++) {
			squaresUpTo[i] = squaresUpTo[i-1] + (i*i);
		}
		long sum = 0;
		for (int i = 1; i < squaresUpTo.length - 1; i++) {
			for (int j = i + 1; j < squaresUpTo.length; j++) {
				long diff = (squaresUpTo[j] - squaresUpTo[i-1]);
				if (diff < n && !seen.contains(diff) && EulerUtils.isPalindrome(String.valueOf(diff))) {
					sum += diff;
					seen.add(diff);
				}
			}
		}

		return sum;
	}
	
}
