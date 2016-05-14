package com.brady.euler;

/*
 * First we need to note that in a triangle with integer lengths A-B-B, it is necessary that A be
 * even. Since A needs to even, we can split up every "almost equilateral" triangle into two
 * identical pythagorean triangles So we just need to look at every pythagorean triple a<b<c such
 * that abs(2a - c) = 1
 * 
 * Using https://en.wikipedia.org/wiki/Pythagorean_triple#Parent.2Fchild_relationships, note that
 * 3,4,5 satisfies 2*a - c = 1 After checking all 3 transformations, T3, after reordering such that
 * a < b < c, satisfies c - 2*a = 1 GIVEN that 2*a - c = 1 It can be seen that constantly applying
 * T3 and reordering will cycle between 2*a - c = 1 and c - 2*a = 1 So the solution is just a matter
 * of applying T3 until the perimeter exceeds 1 billion.
 */
public class Problem94 extends Problem {


    public long solve() {

        long sum = 0;
        int a = 3, b = 4, c = 5;
        int p = 2 * (a + c);
        while (p < 1e9) {
            sum += p;
            int t1 = -2 * a + b + 2 * c, t2 = -a + 2 * b + 2 * c, t3 = -2 * a + 2 * b + 3 * c;
            a = t1;
            b = t2;
            c = t3;
            p = 2 * (a + c);
        }
        return sum;
    }


}
