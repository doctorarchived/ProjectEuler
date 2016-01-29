package com.brady.euler;

import java.util.Arrays;
import java.util.stream.IntStream;

public class EulerUtils {

	
	public static int[] sieve(int n) {
		final int[] A = new int[n+1];
		Arrays.fill(A, 1);
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (A[i] == 1) {
				for (int j = i*i; j <= n; j += i) {
					A[j] = 0;
				}
			}
		}
		
		return (int[]) IntStream.rangeClosed(2, n).filter(i -> A[i] == 1).toArray();
		
	}
	
	
	public static boolean isPerpendicular(int[] a1, int[] a2) {
		return a1[0]*a2[0] + a1[1]*a2[1] == 0;
	}

}