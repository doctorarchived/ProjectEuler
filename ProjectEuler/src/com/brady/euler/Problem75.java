package com.brady.euler;

import java.util.Arrays;

public class Problem75 extends Problem {

	final int L = 1500000;
	final int[] count = new int[L+1];
	
	@Override
	public int solve() {
				
		farey(2048); //2048 is more than enough for euclid's formula since 2048^2 + 1 > L
		
		return (int) Arrays.stream(count).filter(a -> a == 1).count();
		}
	
	/*
	 * Given m > n and m and n coprime, find all pythagorean triples with sum less than L
	 * Using Euclid's formula https://en.wikipedia.org/wiki/Pythagorean_triple
	 */
	private void findTriples(int m, int n) {
		if ((m - n) % 2 == 1 && m > n) {
			int a = m*m - n*n, b = 2*m*n, c = m*m + n*n;
			int sum = a + b + c;
			int increment = sum;
			//Only care about sums below L
			while (sum <= L) {
				count[sum]++;
				sum += increment;
			}
		}
	}
	
	/*
	 * https://en.wikipedia.org/wiki/Farey_sequence used to generate coprime pairs 
	 */
	private void farey(int n) {
	    int a = 0, b = 1, c = 1 , d = n;
	    while (c <= n) {
	        int k = (n + b) / d;
	        int ta = a, tb = b;
	        a = c; b = d; c = k*c - ta; d = k*d - tb;
	        
	        findTriples(b, a);     
	        //System.out.println(a + "/" + b);
	    }
	}
}
