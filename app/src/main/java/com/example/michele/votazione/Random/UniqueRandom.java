package com.example.michele.votazione.Random;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Michele on 11/04/2020.
 */

public class UniqueRandom {
    private Random random = new Random();
    private Set<Integer> usedNumbers = new HashSet<>();


    public int nextInt(int n) {
        int potentialRandom = this.random.nextInt(n);

        while (this.usedNumbers.contains(potentialRandom)) {
            potentialRandom = this.random.nextInt(n);
        }

        this.usedNumbers.add(potentialRandom);

        return potentialRandom;
    }

}

