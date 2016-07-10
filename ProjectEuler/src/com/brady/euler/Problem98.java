package com.brady.euler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class Problem98 extends Problem {

    public static void main(String[] args) {
        System.out.println(normalize("MICHAELBRADY", 16));



    }

    public long solve(Scanner in) {
        in.useDelimiter("\",?\"?");
        int maxLength = 0;
        Multimap<String, String> stringAnagrams = ArrayListMultimap.create();
        Multimap<String, String> patterns = ArrayListMultimap.create();
        Multimap<String, String> stringAnagramPatterns = ArrayListMultimap.create();

        Multimap<String, String> squareAnagrams = ArrayListMultimap.create();
        Multimap<String, String> squareAnagramPatterns = ArrayListMultimap.create();
        Multimap<String, String> squarePatterns = ArrayListMultimap.create();



        while (in.hasNext()) {
            String i = in.next();
            // freq.add(sort(i));
            // map.put(sort(i), i);
            // charNormalizedStrings.put(normalizeCharacters(i), i);
            // freqNormalizedStrings.put(normalizeFrequency(i), i);

            patterns.put(s2(i), i);
            for (String anagram : stringAnagrams.get(sort(i))) {
                stringAnagramPatterns.put(i, s2(i, anagram));
                stringAnagramPatterns.put(anagram, s2(anagram, i));
                maxLength = anagram.length() > maxLength ? anagram.length() : maxLength;
            }

            stringAnagrams.put(sort(i), i);
        }
        // System.out.println("MAX LENGTH: " + maxLength);
        // System.out.println("ANAGRAMS: " + stringAnagrams);
        // System.out.println("PATTERNS: " + patterns);
        // System.out.println("PATTERNS OF ANAGRAMS: " + stringAnagramPatterns);
        //
        // System.out.println("------------");

        String sq;
        for (int i = 1; (sq = String.valueOf(i * i)).length() < maxLength; i++) {


            squarePatterns.put(s2(sq), sq);
            for (String anagram : squareAnagrams.get(sort(sq))) {
                squareAnagramPatterns.put(sq, s2(sq, anagram));
                squareAnagramPatterns.put(anagram, s2(anagram, sq));
                maxLength = anagram.length() > maxLength ? anagram.length() : maxLength;
            }

            squareAnagrams.put(sort(sq), sq);
        }
        // System.out.println(squareAnagrams);
        // System.out.println(squarePatterns);
        // System.out.println(squareAnagramPatterns);


        int bigSquare = 0;



        // for (Entry<String, Collection<String>> entry : stringAnagramPatterns.asMap().entrySet())
        // {
        // //Get pattern of string
        // String pattern = s2(entry.getKey());
        //
        //
        // //Get possible squares the string can map to
        // Collection<String> squares = squarePatterns.get(pattern);
        //
        //
        // // squarePatterns.get(pattern).stream().filter(sq)
        //
        // //For each possible square, look at its anagrams
        // for (String square : squares) {
        // Collection<String> sqAnagrams = squareAnagramPatterns.get(square);
        // boolean match = sqAnagrams.stream().anyMatch(s -> entry.getValue().contains(s));
        // if (match && Integer.parseInt(square) > bigSquare) {
        // bigSquare = Integer.parseInt(square);
        // }
        // }
        //
        // }

        // stringAnagramPatterns.entries().stream().flatMap(e ->
        // squarePatterns.get(e.getKey()).stream().flatMap(s ->
        // squareAnagramPatterns.get(s).stream().filter(p -> )));


        // System.out.println("TEST: " + test.get());
        OptionalInt t =
                stringAnagramPatterns
                        .entries()
                        .stream()
                        .flatMap(
                                e -> squarePatterns.get(s2(e.getKey())).stream()
                                        .filter(s -> squareAnagramPatterns.get(s).contains(e.getValue())))
                        .mapToInt(Integer::valueOf).reduce(Integer::max);
        System.out.println(t.getAsInt());
        // for (Entry<String, String> entry : stringAnagramPatterns.entries()) {
        // // Get pattern of string
        // String pattern = s2(entry.getKey());
        //
        //
        // // Get possible squares the string can map to
        // Collection<String> squares = squarePatterns.get(pattern);
        //
        //
        // // squarePatterns.get(pattern).stream().filter(sq)
        //
        // // For each possible square, look at its anagrams
        // for (String square : squares) {
        // boolean match = squareAnagramPatterns.get(square).stream().anyMatch(s ->
        // entry.getValue().equals(s));
        // if (match && Integer.parseInt(square) > bigSquare) {
        // bigSquare = Integer.parseInt(square);
        // break;
        // }
        // }
        //
        // }
        // System.out.println(bigSquare);



        // getValidSquares(maxLength, anagramMap, patternMap);


        // System.out.println(freq);
        // System.out.println(map.asMap());
        // System.out.println(charNormalizedStrings.asMap());
        // System.out.println(anagramStrings.asMap());
        // System.out.println(patterns.asMap());

        // System.out.println(anagramMap.asMap());
        // System.out.println(patternMap.asMap());
        //
        // System.out.println(sqAnagramMap.asMap());
        // System.out.println(sqPatternMap.asMap());

        // Map<String, Collection<String>> anagrams =
        // anagramStrings.asMap().entrySet().stream().filter(e -> e.getValue().size() > 1)
        // .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        // // System.out.println(anagrams);
        //
        // Set<String> test1 = freq.stream().filter(f -> freq.count(f) >
        // 1).collect(Collectors.toSet());
        // // System.out.println(test1);
        // Set<String> test = freq.stream().filter(f -> freq.count(f) > 1).map(f ->
        // toFreq(f)).collect(Collectors.toSet());
        // for (String t : test) {
        // maxLength = t.length() > maxLength ? t.length() : maxLength;
        // }

        // System.out.println(squares(maxLength, test));
        // test(10E9);

        // squares(16);
        // test(5);
        return 0;
    }

    private String s(String s) {
        String sorted = sort(s);
        Map<Character, Character> map = new HashMap<>();
        char last = 'A';
        for (char c : sorted.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, last);
                last++;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(map.get(c));
        }

        return sb.toString();

    }

    private String s2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
            }
            sb.append(map.get(s.charAt(i)));

        }

        return sb.toString();

    }

    private String s2(String base, String s) {
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < base.length(); i++) {
            if (!map.containsKey(base.charAt(i))) {
                map.put(base.charAt(i), i);
            }
        }

        for (int i = 0; i < s.length(); i++) {
            sb.append(map.get(s.charAt(i)));
        }

        return sb.toString();

    }

    private Multimap<String, Entry<String, Long>> test(double maxSize) {
        long s;
        Multimap<String, Map.Entry<String, Long>> map = HashMultimap.create();

        Multimap<String, Long> temp = HashMultimap.create();
        for (long i = 1; (s = i * i) < maxSize; i++) {
            char[] c = String.valueOf(s).toCharArray();
            Arrays.sort(c);
            temp.put(new String(c), s);
        }
        for (Map.Entry<String, Long> entry : temp.entries()) {
            map.put(normalize(entry.getKey(), 2), entry);
        }
        System.out.println(map);

        return map;

    }

    private Map<String, Integer> squares(int maxSize, Set<String> patterns) {

        int i = 1;
        String s = "1";
        Map<Integer, List<String>> map = new HashMap<>();
        Map<String, Integer> patternToMax = new HashMap<>();

        while (s.length() < maxSize) {
            String pattern = normalize(s, 2);
            int square = i * i;
            if (patterns.contains(pattern)) {
                patternToMax.put(pattern, square);
            }


            i++;
            s = String.valueOf(i * i);
        }
        return patternToMax;

    }

    private String sort(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private String normalizeCharacters(String s) {
        Map<Character, Character> names = new HashMap<>();
        char cur = 'A';
        char[] charNormalized = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (!names.containsKey(s.charAt(i))) {
                names.put(s.charAt(i), cur++);
            }
            charNormalized[i] = names.get(s.charAt(i));

        }
        return new String(charNormalized);
    }

    private String normalizeFrequency(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        int[] freqNormalized = new int[s.length()];

        for (char c : s.toCharArray()) {
            if (counts.containsKey(c)) {
                freqNormalized[counts.get(c)]--;
            }
            freqNormalized[counts.compute(c, (k, v) -> v == null ? 0 : v + 1)]++;

        }
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < freqNormalized.length && count < s.length(); i++) {
            count += freqNormalized[i] * (i + 1);
            sb.append(freqNormalized[i]);
        }

        return sb.toString();
    }



    private void getValidSquares(int maxLength, Multimap<String, String> anagramMap, Multimap<String, String> patternMap) {
        String s;
        for (int i = 1; (s = String.valueOf(i * i)).length() < maxLength; i++) {
            anagramMap.put(sort(s), s);
            patternMap.put(normalizeCharacters(s), s);
        }
    }



    private String toFreq(String s) {
        char[] names = new char[26];
        char cur = 'A' - 1;
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (names[s.charAt(i) - 'A'] == 0) {
                names[s.charAt(i) - 'A'] = ++cur;
            }
            sb.append(names[s.charAt(i) - 'A']);
        }
        return sb.toString();
    }

    private String normalize2(String s) {
        Map<Character, Character> names = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        char a = 'A';

        for (char c : s.toCharArray()) {
            if (!names.containsKey(c)) {
                names.put(c, a++);
            }

            sb.append(names.get(c).charValue());

        }

        // System.out.println(s + "=>" + sb.toString());
        return sb.toString();
    }

    private static String normalize(String s, int maxLength) {
        Map<Character, Integer> count = new HashMap<>();
        int[] freq = new int[maxLength];
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (count.containsKey(c)) {
                freq[count.get(c)]--;
                freq[count.computeIfPresent(c, (a, b) -> b + 1)]++;
            } else {
                freq[0]++;
                count.put(c, 0);
            }
        }
        System.out.println(Arrays.toString(freq));
        for (int i : freq) {
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    private String toFreq(int n) {
        String s = String.valueOf(n);
        char[] names = new char[26];
        char cur = 'A' - 1;
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (names[s.charAt(i) - 'A'] == 0) {
                names[s.charAt(i) - 'A'] = ++cur;
            }
            sb.append(names[s.charAt(i) - 'A']);
        }
        return sb.toString();
    }

    private static class BradyString {
        public final String string;

        public final Map<Character, Integer> map;

        public BradyString(String s) {
            this.string = s;

            map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                if (!map.containsKey(s.charAt(i))) {
                    map.put(s.charAt(i), i);
                }
            }

        }


    }

}
