package com.or;

import java.util.Random;

    public class NetworkUtil {

        private static final int MAX_SPEED = 1000;
        private static final int MAX_FREQ = 3000;

        public static int getCurrentSpeed() {
            return new Random().nextInt(MAX_SPEED);
        }

        public static int getCurrentFrequency() {
            return new Random().nextInt(MAX_FREQ);
        }

    }
