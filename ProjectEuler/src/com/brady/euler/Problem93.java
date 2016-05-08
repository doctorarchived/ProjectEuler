package com.brady.euler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Sets;

/*
 * Strategy: Given 4 distinct digits, we first look at all the possible values of all possible
 * order-independent 2-tuples Recursively we can then use the values from the 2-tuples to generate
 * all possible 3-tuples In the last step of the recursion, we generate all 4-tuples using our
 * 3-tuples or by combining two 2-tuples After we exit the recursion, we iterate over the sorted set
 * for the 4-tuple and find the longest integer sequence starting at 1
 * 
 * This is done for every 4 distinct digits
 */
public class Problem93 extends Problem {

    // Since we only care about "digits", we only want to check 1 to 9.
    private final int max = 10;

    public long solve() {
        Four[] fours = new Four[max];
        for (int d = 0; d < max; d++) {
            fours[d] = new Four(1 << d);
            history.put(fours[d], Sets.newTreeSet(Arrays.asList((double) (d + 1))));
        }

        int longestSeq = 0;
        Four four = null;
        for (int i = 0; i < max; i++) {
            for (int j = i; j < max; j++) {
                for (int k = j; k < max; k++) {
                    for (int l = k; l < max; l++) {
                        magic(Arrays.asList(fours[i], fours[j], fours[k], fours[l]), 2);
                        int seq = longestSeq(
                                history.get(fours[i].add(fours[j].add(fours[k].add(fours[l]))))
                                        .tailSet(1d));
                        if (seq > longestSeq) {
                            longestSeq = seq;
                            four = fours[i].add(fours[j].add(fours[k].add(fours[l])));
                        }
                    }

                }
            }
        }
        return four.set;

    }

    private int longestSeq(SortedSet<Double> set) {
        int last = 0;
        for (Double d : set) {
            if (d == (last + 1)) {
                last++;
            } else if (d > (last + 1)) {
                break;
            }
        }
        return last;

    }

    Map<Four, TreeSet<Double>> history = new HashMap<>();

    private void magic(Collection<Four> seen, int size) {
        Set<Four> seen2 = new HashSet<>();
        Map<Four, TreeSet<Double>> newFours = new HashMap<>();
        for (Four f1 : seen) {
            for (Four f2 : seen) {
                if (f1.valid(f2, size)) {
                    Four fNew = f1.add(f2);
                    if (!history.containsKey(fNew)) {
                        newFours.putIfAbsent(fNew, new TreeSet<>());
                        TreeSet<Double> set = newFours.get(fNew);
                        for (double v1 : history.get(f1)) {
                            for (double v2 : history.get(f2)) {
                                set.add(v1 + v2);
                                set.add(v1 * v2);
                                set.add(v1 - v2);
                                set.add(v1 / v2);
                            }
                        }
                    }
                    seen2.add(fNew);
                }
            }
        }
        history.putAll(newFours);
        if (!seen2.isEmpty() && size < 4) {
            seen2.addAll(seen);
            magic(seen2, ++size);
        }

    }

    static final class Four {

        public final int set;

        public Four(int set) {
            this.set = set;
        }

        public Four set(int n) {
            return new Four(set + (1 << n));
        }

        public Four add(Four f) {
            return new Four(set | f.set);
        }

        public boolean valid(Four f) {
            return (set & f.set) == 0;
        }

        public boolean valid(Four f, int size) {
            return (set & f.set) == 0 && Integer.bitCount(set | f.set) == size;
        }

        public boolean contains(int n) {
            return (set + (1 << n)) != 0;
        }

        @Override
        public int hashCode() {
            return set;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Four) {
                return ((Four) obj).set == this.set;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return String.valueOf(set);
        }
    }
}
