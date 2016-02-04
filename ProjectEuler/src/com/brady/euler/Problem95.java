package com.brady.euler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Problem95 extends Problem {
	
	
	
	public long solve() {
		
		int N = 1000000;
		int[] table = new int[N+1];
		
		int maxRunLength = 0;
		int minElement = N+1;

		for (int i = 1; i <= N; i++) {
			Map<Integer, Integer> currentRun = new LinkedHashMap<Integer, Integer>();
			int j = i;
			int pos = 0;
			while (j < N+1 && table[j] == 0) {
				currentRun.put(j, pos++);
				j = table[j] = EulerUtils.sumOfDivisors(j);
			}
			if (currentRun.containsKey(j)) {
				int start = currentRun.get(j);
				if (maxRunLength < (pos - start)) {
					maxRunLength = (pos - start);
					minElement = N+1;
					for (Entry<Integer, Integer> entry : currentRun.entrySet()) {
						if (entry.getValue() >= start && entry.getKey() < minElement) {
							minElement = entry.getKey();
						}
					}
				}
			}
		}		
		
		return minElement;
	}
	
	

}
