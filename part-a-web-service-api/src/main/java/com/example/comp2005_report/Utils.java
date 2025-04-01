package com.example.comp2005_report;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.springframework.lang.NonNull;

public class Utils {

    private static final double msInDay = 86_400_000.0;

    static Integer[] removeDuplicates(Integer[] array) {
        Set<Integer> set = new HashSet<>();

        for (Integer i : array) {
            if (!set.contains(i)) {
                set.add(i);
            }
        }

        return set.toArray(Integer[]::new);
    }

    /// This will return all items in array1 that are not in array2
    static Integer[] difference(Integer[] array1, Integer[] array2) {
        Set<Integer> setA = new HashSet<>();
        Set<Integer> setB = new HashSet<>();

        for (Integer i : array2) {
            setB.add(i);
        }

        for (Integer i : array1) {
            if (!setB.contains(i)) {
                setA.add(i);
            }
        }

        return setA.toArray(Integer[]::new);
    }

    /// This returns the difference in days from date1 compared to date2
    /// @example
    /// if date1 is 22nd December and date2 is December 20th, you will get 2.0, it does count ms so you will get a float
    static double differenceInDays(
        @NonNull Calendar date1,
        @NonNull Calendar date2
    ) {
        long diffInMs = date1.getTime().getTime() - date2.getTime().getTime();
        double diffInDays = diffInMs / msInDay;

        return diffInDays;
    }
}
