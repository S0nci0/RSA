package com.example.rsa;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RSA {

    // Logger di Log4j
    private static final Logger logger = LogManager.getLogger(RSA.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // chiavi 
        BigInteger p = generaPrimo();
        BigInteger q = generaPrimo();
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = trovaCoprimo(phi);
        BigInteger d = e.modInverse(phi);

        logger.info("Chiave pubblica: e=" + e + ", n=" + n);
        logger.info("Chiave privata: d=" + d + ", n=" + n);

      
        System.out.print("Inserisci il messaggio da cifrare: ");
        String messaggio = scanner.nextLine();

        String messaggioCifrato = cripta(messaggio, e, n);
        logger.info("Messaggio cifrato: " + messaggioCifrato);

       
        String messaggioDecifrato = decripta(messaggioCifrato, d, n);
        logger.info("Messaggio decifrato: " + messaggioDecifrato);

        scanner.close();
    }

    
    private static BigInteger generaPrimo() {
        return BigInteger.probablePrime(16, new Random());
    }

    // Trova un numero 'e' coprimo con 'phi'
    private static BigInteger trovaCoprimo(BigInteger phi) {
        BigInteger e = BigInteger.valueOf(3);
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.valueOf(2));
        }
        return e;
    }

    // Cifra il messaggio
    private static String cripta(String messaggio, BigInteger e, BigInteger n) {
        StringBuilder cifrato = new StringBuilder();
        for (char c : messaggio.toCharArray()) {
            BigInteger carattere = BigInteger.valueOf((int) c);
            BigInteger cifratoCarattere = carattere.modPow(e, n);
            cifrato.append(cifratoCarattere).append(";");
        }
        return cifrato.toString();
    }

    // Decifra il messaggio
    private static String decripta(String messaggioCifrato, BigInteger d, BigInteger n) {
        StringBuilder decifrato = new StringBuilder();
        String[] parti = messaggioCifrato.split(";");
        for (String parte : parti) {
            if (!parte.isEmpty()) {
                BigInteger carattereCifrato = new BigInteger(parte);
                BigInteger carattereOriginale = carattereCifrato.modPow(d, n);
                decifrato.append((char) carattereOriginale.intValue());
            }
        }
        return decifrato.toString();
    }
}
