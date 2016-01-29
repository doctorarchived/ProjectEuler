package com.brady.euler;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Problem124 extends Problem {

	
	public long solve() {
		
		int n = 100000, sqrtN = (int) Math.sqrt(n);
		int k = 10000;		
		int[] primes = EulerUtils.sieve(n);

		TreeMap<Integer, TreeSet<Integer>> tree = new TreeMap<>();
		TreeSet<Integer> one = new TreeSet<Integer>(Arrays.asList(1));
		tree.put(1, one);
		
		for (int p : primes) {

			TreeSet<Integer> powersOfP = new TreeSet<>();
			int pExp = p;
			do {
				powersOfP.add(pExp);
			} while (p <= sqrtN && (pExp *= p) < n);

			ArrayDeque<TreeSet<Integer>> toAdd = new ArrayDeque<>();

			for (Entry<Integer, TreeSet<Integer>> prevPowers : tree.entrySet()) {
				if ((p * prevPowers.getKey()) > n) {
					break;
				} else {
					TreeSet<Integer> powersOfNewBase = new TreeSet<>();
					for (int powerOfP : powersOfP) {
						for (int prevPower : prevPowers.getValue()) {
							int product;
							if ((product = powerOfP*prevPower) <= n){
								powersOfNewBase.add(product);
							} else {
								break;
							}
						}
					}
					toAdd.add(powersOfNewBase);
				}
			}

			tree.put(p, powersOfP);
			for (TreeSet<Integer> powersOfNewBase : toAdd) {
				tree.put(powersOfNewBase.first(), powersOfNewBase);
			}

		}
		
		//Find first radical that brings us over k
		int count = 0;
		TreeSet<Integer> nextRadical;
		while (count + (nextRadical = tree.pollFirstEntry().getValue()).size() < k) {
			count += nextRadical.size();
		}

		//Increment through the radical until we find the k'th number
		int num = 0;
		for (; count < k; count++) {
			num = nextRadical.pollFirst();
		}	
		
		return num;
	}
	
}
