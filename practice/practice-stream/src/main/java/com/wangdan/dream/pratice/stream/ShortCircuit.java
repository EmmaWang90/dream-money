package com.wangdan.dream.pratice.stream;

import com.wangdan.dream.framework.ServiceBase;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ShortCircuit extends ServiceBase {
    public ShortCircuit(ServiceBase parent) {
        super(parent);
    }

    public void practice() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {
                            System.out.println("filtering " + n);
                            return n % 2 == 0;
                        })
                        .map(n -> {
                            System.out.println("mapping " + n);
                            return n * n;
                        })
                        .limit(2)
                        .collect(toList());
    }
}
