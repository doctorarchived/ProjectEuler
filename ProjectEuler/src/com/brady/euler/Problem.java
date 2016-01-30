package com.brady.euler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Problem {
	
	
	public long solve() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public int solve(Scanner in) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public static <T extends Problem> void solve(Class<T> problem) {
		solve(problem, null);
	}
	
	public static <T extends Problem> void solve(Class<T> problem, File f) {
		try {
			long timeStamp, result;
			if (f != null) {
				Scanner in = new Scanner(f);
				timeStamp = System.currentTimeMillis();
				result = problem.newInstance().solve(in);
			} else {
				timeStamp = System.currentTimeMillis();
				result = problem.newInstance().solve();
			}
			timeStamp = System.currentTimeMillis() - timeStamp;
			System.out.println("Solved " + problem.getSimpleName() + " with result " + result + " in " + timeStamp + "ms");

		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
