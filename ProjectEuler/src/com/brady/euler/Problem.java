package com.brady.euler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Problem {
	
	public int solve() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public int solve(Scanner in) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public static <T extends Problem> void solve(Class<T> problem) {
		try {
			System.out.println(problem.getName() + " " + problem.newInstance().solve());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static <T extends Problem> void solve(Class<T> problem, File f) {
		try {
			Scanner in = new Scanner(f);
			System.out.println(problem.getName() + " " + problem.newInstance().solve(in));
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
