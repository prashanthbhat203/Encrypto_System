package com.example.encrypto.encryptocodes.diffiehellman;

import java.util.Random;

public class PrimeNumberGen {

    private long n;

    public long getPrimeNumber() {
        this.n = (int) (new Random().nextDouble() * 100) + 250;
        long l = 0;
        l = (long) ((this.n) * (Math.log(this.n) + (Math.log(Math.log(this.n)) - 1) + ((Math.log(Math.log(this.n)) - 2) / (Math.log(this.n))) - ((Math.log(Math.log(this.n)) - 21.0 / 10.0) / Math.log(this.n))));
        for (long i = l; ; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private boolean isPrime(long n) {
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
