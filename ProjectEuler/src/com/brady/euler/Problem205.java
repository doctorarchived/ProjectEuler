package com.brady.euler;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Problem205 extends Problem {
	
	public long solve() {
		
		int[] peterPDF = getPDF(4,9);
		int[] colinCDF = getCDF(6,6);
		
		long sum = 0L;
		long total = (long) Arrays.stream(peterPDF).sum()*colinCDF[colinCDF.length - 1];
		
		for (int i = 1; i < peterPDF.length; i++) {
			sum += (long) peterPDF[i] * colinCDF[i-1];
		}
		return (int) ((double) sum / total * 1e7 + 0.5);
	}
	
	private int[] getPDF(int numSides, int numRolls) {

		TreeMap<Integer, Integer> distribution = new TreeMap<Integer, Integer>();
		distribution.put(0, 1);
		
		for (int i = 0; i < numRolls; i++) {
			TreeMap<Integer, Integer> newDistribution = new TreeMap<Integer, Integer>();
			for (int j = 1; j <= numSides; j++) {
				for (Map.Entry<Integer, Integer> entry : distribution.entrySet()) {
					newDistribution.compute(entry.getKey() + j, (k,v) -> v == null ? entry.getValue() : v + entry.getValue());
				}
			}
			distribution = newDistribution;
		}
		
		int[] pdf = new int[numSides*numRolls + 1];
		
		for (Map.Entry<Integer, Integer> entry: distribution.entrySet()) {
			pdf[entry.getKey()] = entry.getValue();
		}
		return pdf;
	}
	
	private int[] getCDF(int numSides, int numRolls) {
		int sum = 0;
		int[] pdf = getPDF(numSides, numRolls);
		int[] cdf = new int[numSides*numRolls + 1];
		for (int i = 0; i < pdf.length; i++) {
			sum += pdf[i];
			cdf[i] = sum;
		}
		return cdf;
	}
	

}
