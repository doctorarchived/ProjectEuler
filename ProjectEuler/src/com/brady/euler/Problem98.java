package com.brady.euler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Problem98 extends Problem {

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

            patterns.put(indicize(i), i);
            for (String anagram : stringAnagrams.get(sort(i))) {
                stringAnagramPatterns.put(i, s2(i, anagram));
                stringAnagramPatterns.put(anagram, s2(anagram, i));
                maxLength = anagram.length() > maxLength ? anagram.length() : maxLength;
            }

            stringAnagrams.put(sort(i), i);
        }


        String sq;
        for (int i = 1; (sq = String.valueOf(i * i)).length() < maxLength; i++) {

            squarePatterns.put(indicize(sq), sq);
            for (String anagram : squareAnagrams.get(sort(sq))) {
                squareAnagramPatterns.put(sq, s2(sq, anagram));
                squareAnagramPatterns.put(anagram, s2(anagram, sq));
            }

            squareAnagrams.put(sort(sq), sq);
        }

        // int bigSquare = 0;
        // for (Entry<String, Collection<String>> entry : stringAnagramPatterns.asMap().entrySet())
        // {
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
        // Collection<String> sqAnagrams = squareAnagramPatterns.get(square);
        // boolean match = sqAnagrams.stream().anyMatch(s -> entry.getValue().contains(s));
        // if (match && Integer.parseInt(square) > bigSquare) {
        // bigSquare = Integer.parseInt(square);
        // }
        // }
        //
        // }

        OptionalInt maxSquare =
                stringAnagramPatterns
                        .entries()
                        .stream()
                        .flatMap(
                                e -> squarePatterns.get(indicize(e.getKey())).stream()
                                        .filter(s -> squareAnagramPatterns.get(s).contains(e.getValue())))
                        .mapToInt(Integer::valueOf).reduce(Integer::max);

        return maxSquare.getAsInt();
    }


    /**
     * Given a string s, replace each character with the index of its first occurence in the string
     * s
     * 
     * @param s
     * @return
     */
    private String indicize(String s) {
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

    /**
     * Given a string 's', replace each character with the index of its first occurence in the
     * string 'base'
     * 
     * @param s
     * @return
     */
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

    private String sort(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
