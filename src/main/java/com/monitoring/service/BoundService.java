package com.monitoring.service;

public class BoundService {

    public boolean numberIsInRange(long number, long min, long max) {
        return number >= min && number <= max;
    }
}
