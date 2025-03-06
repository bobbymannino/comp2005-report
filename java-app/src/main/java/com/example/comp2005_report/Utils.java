package com.example.comp2005_report;

import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Utils {
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
    static double differenceInDays(@NonNull Date date1, @NonNull Date date2) {
        long diff = date1.getTime() - date2.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
