package com.brady.euler;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Problem124 extends Problem {

	
	public long solve() {
		
		int n = 100000, sqrtN = (int) Math.sqrt(n);
		int k = 10000;		
		int[] primes = sieve(n);

		TreeMap<Integer, TreeSet<Integer>> tree = new TreeMap<>();
		TreeSet<Integer> one = new TreeSet<Integer>(Arrays.asList(1));
		tree.put(1, one);
		for (int p : primes) {
			
			TreeSet<Integer> deque = new TreeSet<>();
			int mp = p;
			do {
				deque.add(mp);
			} while (p <= sqrtN && (mp *= p) < n);

			ArrayDeque<TreeSet<Integer>> toAdd = new ArrayDeque<>();

			for (Entry<Integer, TreeSet<Integer>> entry : tree.entrySet()) {
				if ((p * entry.getKey()) > n) {
					break;
				} else {
					TreeSet<Integer> deque2 = new TreeSet<>();
					for (int multP : deque) {
						for (int multEntry : entry.getValue()) {
							int product2;
							if ((product2 = multP*multEntry) <= n){
								deque2.add(product2);
							} else {
								break;
							}
						}
					}
					toAdd.add(deque2);
				}
			}

			tree.put(p, deque);
			for (TreeSet<Integer> deque2 : toAdd) {
				tree.put(deque2.first(), deque2);
			}

		}
				
		int count = 0;
		TreeSet<Integer> last;
		while (count + (last = tree.pollFirstEntry().getValue()).size() < k) {
			count += last.size();
		}

		int num = 0;
		for (; count < k; count++) {
			num = last.pollFirst();
		}	
		
		return num;
	}
	
	
	private int[] sieve(int n) {
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
	
}
