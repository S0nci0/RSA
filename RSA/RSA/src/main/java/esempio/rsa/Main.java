package esempio.rsa;

import java.math.BigInteger;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
   private static final Logger log = LogManager.getLogger(Main.class);
   private static RSA rsa = new RSA();

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      // Richiedi input all'utente
      log.info("Inserisci una stringa da criptare:");
      String stringa = scanner.nextLine();

      log.info("Stringa originale: " + stringa);
      
      // Cripta la stringa
      BigInteger stringaCriptata = rsa.cripta(stringa);
      log.info("Stringa criptata: " + stringaCriptata);

      // Decripta la stringa
      String stringaDecriptata = rsa.decriptaAStringa(stringaCriptata);
      log.info("Stringa decriptata: " + stringaDecriptata);

      scanner.close();
   }
}
