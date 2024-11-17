package esempio.rsa;

import java.math.BigInteger;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RSA {
   private static final Logger log = LogManager.getLogger(RSA.class);
   private static BigInteger n, d, e;
   private final int LUNGHEZZA_BIT = 1024;

 
   //genero chiavi
   public RSA() {
      Random random = new Random();

      // Genera due numeri primi grandi, p e q
      BigInteger p = BigInteger.probablePrime(LUNGHEZZA_BIT / 2, random);
      log.debug("Generato il numero primo 'p': " + p);
      BigInteger q = BigInteger.probablePrime(LUNGHEZZA_BIT / 2, random);
      log.debug("Generato il numero primo 'q': " + q);

      // Calcolo n = p * q
      n = p.multiply(q);
      log.debug("Calcolato 'n': " + n);

      // Calcolo phi(n) = (p-1)*(q-1)
      BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
      log.debug("Calcolato 'phi': " + phi);

      
      e = BigInteger.probablePrime(LUNGHEZZA_BIT / 2, random);
      while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
         e = e.add(BigInteger.ONE);
      }
      log.debug("Generato 'e': " + e);

      
      d = e.modInverse(phi);
      log.debug("Calcolato l'inverso moltiplicativo 'd': " + d);
   }

 
   public static BigInteger cripta(String messaggio) {
      BigInteger messaggioBigInt = new BigInteger(messaggio.getBytes());
      log.info("Stringa criptata: " + messaggio + ", BigInteger: " + messaggioBigInt);
      return messaggioBigInt.modPow(e, n); 
   }

   
   public static BigInteger cripta(BigInteger messaggio) {
      log.info("BigInteger criptato: " + messaggio + ", Risultato cifrato: " + messaggio.modPow(e, n));
      return messaggio.modPow(e, n); 
   }

  
   public static BigInteger decripta(BigInteger criptato) {
      log.info("BigInteger decriptato: " + criptato + ", Risultato decriptato: " + criptato.modPow(d, n));
      return criptato.modPow(d, n); 
   }

   public static String decriptaAStringa(BigInteger criptato) {
      String risultato = new String(criptato.modPow(d, n).toByteArray());
      log.info("Stringa decriptata: " + criptato + ", Risultato decifrato: " + risultato);
      return risultato; 
   }
}
