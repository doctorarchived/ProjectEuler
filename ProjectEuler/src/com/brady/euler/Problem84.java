package com.brady.euler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Problem84 extends Problem {

	private final int numSides = 4;
		
	private int chance = 0;
	private int communityChest = 0;
	
	public long solve() {		
		int[] sim = sim(100000000);
		
		Integer[] positions = new Integer[40];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = i;
		}
		
		Arrays.sort(positions, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				int diff = sim[o1] - sim[o2];
				
				return diff > 0 ? -1 : (diff == 0 ? 0 :  1);
			}
		});
		
		return positions[0]*10000 + positions[1]*100 + positions[2];
	}
	
	private int chance(Random rand, int pos) {
		int draw = chance++ % 16;
		
		switch (draw) {
		case 0:
			return 0;
		case 1:
			return 10;
		default:
			return pos;
		}
	}
	
	private int communityChest(Random rand, int pos) {
		int draw = communityChest++ % 16;
		switch (draw) {
		case 0:
			return 0;
		case 1:
			return 10;
		case 2:
			return 11;
		case 3:
			return 24;
		case 4:
			return 39;
		case 5:
			return 5;
		case 6:
		case 7:
			return pos + (5 - pos % 10);
		case 8:
			return pos > 12 && pos < 28 ? 28 : 12; 
		case 9:
			return pos - 3;
		default:
			return pos;
		}
	}
	
	private int[] sim(int numRolls) {
		int[] count = new int[40];
		int doublesStreak = 0, pos = 0;
		Random rand = new Random();
		while (numRolls-- > 0) {
			int roll1 = rand.nextInt(numSides) + 1, roll2 = rand.nextInt(numSides) + 1;
			
			doublesStreak = roll1 == roll2 ? doublesStreak + 1 : 0;
	
			if (doublesStreak == 3) {
				pos = 10;
				doublesStreak = 0;
			} else {
			
				pos = (pos + roll1 + roll2) % 40;
				
				switch (pos) {
				case 2:
				case 17:
				case 33:
					pos = chance(rand, pos);
					break;
				case 7:
				case 22:
				case 36:
					pos = communityChest(rand, pos);
					break;
				case 30:
					pos = 10;
					break;
				}
			}
			count[pos]++;
			
		}
		return count;
	}
}
