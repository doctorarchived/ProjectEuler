package com.brady.euler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class Problem96 extends Problem {



    public long solve(Scanner in) {
        int sum = 0;
        while (in.hasNext()) {
            int[] grid = new int[81];
            in.nextLine();
            for (int i = 0; i < 9; i++) {
                char[] s = in.nextLine().toCharArray();
                for (int j = 0; j < 9; j++) {
                    grid[9 * i + j] = s[j] - '0';
                }
            }
            sum += solvePuzzle(grid);
        }
        return sum;
    }

    private int solvePuzzle(int[] grid) {
        List<AllDiff> constraints = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            constraints.add(new AllDiff());
        }

        List<Var> vars = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            int row = i / 9, col = i % 9;
            Var v = grid[i] != 0 ? new Var(i, grid[i]) : new Var(i);
            vars.add(v);
            constraints.get(row).addVar(v);
            constraints.get(9 + col).addVar(v);
            constraints.get(18 + 3 * (row / 3) + (col / 3)).addVar(v);
        }

        GAC(new PriorityQueue<Var>(vars));
        return vars.get(0).value * 100 + vars.get(1).value * 10 + vars.get(2).value;

    }

    /**
     * Given a list of variables, iterate over them and after checking for promising values, enforce
     * GAC on the variable's constraints
     * 
     * @param queue The variables in the grid
     * @return If it is possible to solve the puzzle from the given state
     */
    private boolean GAC(PriorityQueue<Var> queue) {

        Var var = queue.poll();

        if (var == null) {
            return true;
        }

        for (int i = var.currentDomain.nextSetBit(0); i != -1; i = var.currentDomain.nextSetBit(i + 1)) {

            if (var.valid(i + 1)) {
                var.set(i + 1);
                List<Var> modifiedVars = new ArrayList<>();
                boolean success = enforceGAC(new TreeSet<>(var.constraints), modifiedVars);
                if (success && GAC(queue)) {
                    return true;
                }

                for (Var v : modifiedVars) {
                    v.reset();
                }
                var.unset(i + 1);
            }

        }
        queue.add(var);
        return false;

    }

    /**
     * Enforce GAC on the given constraints. This means that for every constraint given, we iterate
     * over all the variables of every constraint pruning invalid values from the domain of the
     * variables. If a variable has had its domain pruned and is still not empty, we mark it as
     * modified and add the remaining constraints to the list to check them as well. Pruning the
     * value from the domain may affect the other constraints associated with the variable in
     * general. Given the way I solved this, the constraints do not care about the pruned values of
     * the relevant variable domains(only when a variable has been set) so it is actually a waste of
     * time to add these constraints. We fill up modifiedVars to allow for easy cleanup when
     * backtracking.
     * 
     * 
     * 
     * @param constraints Initial constraints to enforce GAC on
     * @param modifiedVars Variables that have had their domains pruned during the GAC process
     * @return
     */
    private boolean enforceGAC(TreeSet<AllDiff> constraints, List<Var> modifiedVars) {
        AllDiff constraint;
        while ((constraint = constraints.pollFirst()) != null) {
            for (Var var : constraint.vars) {
                if (var.value == 0) {
                    boolean modified = false;
                    for (int i = var.currentDomain.nextSetBit(0); i != -1; i = var.currentDomain.nextSetBit(i + 1)) {
                        if (!var.valid(i + 1)) {
                            var.prune(i + 1);
                            modified = true;
                            modifiedVars.add(var);
                        }
                    }
                    if (var.currentDomain.isEmpty()) {
                        modifiedVars.forEach(v -> v.clean());
                        modifiedVars.clear();
                        return false;
                    }

                    if (modified) {
                        for (AllDiff c : var.constraints) {
                            if (c != constraint) {
                                constraints.add(c);
                            }
                        }
                    }
                }
            }
        }
        for (Var v : modifiedVars) {
            v.save();
        }
        return true;
    }

    /**
     * Variable to represent a cell in the sudoku grid. Use a bitset to represent the valid domain
     * and unset bits as we prune values. Keep a history of the domains, so that we can easily undo
     * changes when we need to backtrack in the algorithm
     * 
     * @author Michael
     *
     */
    static class Var implements Comparable<Var> {

        static final Map<Integer, Character> map = new HashMap<Integer, Character>();

        static {
            map.put(0, 'A');
            map.put(1, 'B');
            map.put(2, 'C');
            map.put(3, 'D');
            map.put(4, 'E');
            map.put(5, 'F');
            map.put(6, 'G');
            map.put(7, 'H');
            map.put(8, 'I');

        }

        private BitSet currentDomain = new BitSet(9);
        private final Deque<BitSet> domains = new ArrayDeque<>();
        private int value;
        private final int id;
        List<AllDiff> constraints = new ArrayList<>();

        public Var(int id, int val) {
            currentDomain.set(val - 1);
            domains.push((BitSet) currentDomain.clone());
            this.id = id;
        }

        public Var(int id) {
            currentDomain.set(0, 9);
            domains.push((BitSet) currentDomain.clone());
            this.id = id;
        }

        public boolean valid(int n) {
            for (AllDiff c : constraints) {
                if (!c.valid(n)) {
                    return false;
                }
            }
            return true;
        }

        public void set(int n) {
            this.value = n;
            for (AllDiff c : constraints) {
                c.set(n);
            }
        }

        public void unset(int n) {
            this.value = 0;
            for (AllDiff c : constraints) {
                c.pop(n);
            }
        }

        public void prune(int n) {
            this.currentDomain.clear(n - 1);
        }

        public void save() {
            this.domains.push((BitSet) currentDomain.clone());
        }

        public void clean() {
            this.currentDomain = (BitSet) domains.peek().clone();
        }

        public void reset() {
            domains.pop();
            this.currentDomain = (BitSet) domains.peek().clone();
        }

        public int value() {
            return this.value;
        }

        @Override
        public int compareTo(Var v) {
            return currentDomain.cardinality() - v.currentDomain.cardinality();
        }

        @Override
        public String toString() {
            String id2 = map.get(id / 9) + "" + (id % 9 + 1);
            return id2 + "=" + value + "|" + currentDomain;// + ": " + currentDomain.toString();
        }

    }

    /**
     * Object to represent an "All Different" constraint. Whenever a variable is set to n we unset
     * n-1 in the bitset to allow us to enforce uniqueness among variables in the constraint
     * 
     * @author Michael
     *
     */
    static class AllDiff implements Comparable<AllDiff> {

        final private BitSet set;
        final private List<Var> vars = new ArrayList<>();

        public AllDiff(List<Var> vars) {
            this();
            this.vars.addAll(vars);
            this.vars.forEach(v -> v.constraints.add(this));
        }

        public AllDiff() {
            this.set = new BitSet();
            this.set.set(0, 9);
        }

        public boolean satisfied() {
            return set.isEmpty();
        }

        public boolean valid(int n) {
            return set.get(n - 1);
        }

        public void set(int n) {
            set.set(n - 1, false);
        }

        public void pop(int n) {
            set.set(n - 1, true);
        }

        public void addVar(Var var) {
            vars.add(var);
            var.constraints.add(this);
        }

        @Override
        public String toString() {
            return set + "|" + vars.toString();
        }

        @Override
        public int compareTo(AllDiff o) {
            return hashCode() - o.hashCode();
        }


    }
}
