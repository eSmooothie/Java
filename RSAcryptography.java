
// imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Main
 * 
 */
class RSAcryptography {
    public static void main(String args[]) throws IOException {
        // select two primes p and q, p != q
        int p = 11;
        int q = 13;

        RSA rsa = new RSA(p, q);

        // Choose e such that,
        // 1 < e < phi of n
        // coprime with n and phi of n
        // int e = <something>
        // rsa.setE(e); // manually set e
        rsa.generateE(); // generate e

        // choose d such that
        // e * d mod phi = 1
        // int d = <something>
        // rsa.setD(d); // manually set d
        rsa.computeD();

        System.out.println("RSA Encryption -----------------");

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader rd = new BufferedReader(reader);
        System.out.print("Enter message: ");

        String plainMessage = rd.readLine();

        System.out.println("\nPlain Message: " + plainMessage);
        System.out.print("Encrypted Message: ");
        rsa.encrypt(plainMessage); // C = M^e mod n

        System.out.println();

        System.out.print("Decrypted Message: ");
        String cipherMessage = rsa.getEncryptedMessage().toString();
        rsa.decrypt(cipherMessage); // M = C^d mod n

        System.out.println("\n--------------------------------");
    }
}

class RSA extends Cryptography {

    private HashMap<String, Object> keys = new HashMap<String, Object>();
    private int n; // product of two prime number
    private int phi;
    private int e; // encryption key
    private int d; // decryption key

    RSA(int p, int q) {
        if (p == q) {
            System.err.println("Error p must not be equal to q.");
            System.exit(0);
        }
        if (!isPrime(p) && !isPrime(q)) {
            System.err.println("Error p and q must be prime numbers.");
            System.exit(0);
        }
        // calculate n = p * q
        this.n = p * q;
        // calculate phi of n = (p - 1) * (q - 1)
        this.phi = (p - 1) * (q - 1);
    }

    /**
     * Evaluate if n is a prime number.
     * 
     * @param n
     * @return true if n is prime otherwise false
     */
    public static boolean isPrime(int n) {

        // Check if number is less than
        // equal to 1
        if (n <= 1)
            return false;

        // Check if number is 2
        else if (n == 2)
            return true;

        // Check if n is a multiple of 2
        else if (n % 2 == 0)
            return false;

        // If not, then just check the odds
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Generate a prime numbers between 2 to n
     * 
     * @param n
     * @return random prime numbers between 2 to n
     */
    public static Integer generatePrimeNumbers(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        // randomize
        Random rand = new Random();
        int index = rand.nextInt(primeNumbers.size());
        return primeNumbers.get(index);
    }

    /**
     * Get all coprimes of n
     * 
     * @param n
     * @return coprimes of n
     */
    public static List<Integer> getCoprimes(int n) {
        LinkedList<Integer> coprimes = new LinkedList<Integer>();
        for (int i = 2; i < n; i++) {
            boolean isCoprime = isRelativePrime(i, n);
            if (isCoprime) {
                coprimes.add(i);
            }
        }
        return coprimes;
    }

    /**
     * Evaluate if integer a is ralative prime to integer b
     * 
     * @param a
     * @param b
     * @return true if relative prime otherwise false
     */
    public static boolean isRelativePrime(int a, int b) {
        int gcd = 1;
        int min = a;
        int max = b;
        while (max > min) {
            int r = max % min;
            if (r == 0) {
                gcd = min;
                break;
            } else {
                max = min;
                min = r;
            }
        }
        if (gcd == 1) {
            return true;
        }
        return false;
    }

    /**
     * compute the d(decryption number), using the formula ed mod n = 1
     */
    public void computeD() {
        if (this.phi == 0 || this.e == 0) {
            System.out.println("Error: phi and e not set.");
            System.exit(0);
        }
        int d = -1;
        int i = 1;
        while (d <= this.e) {
            int x = ((this.phi * i) + 1) % this.e;
            if (x == 0) {
                d = ((this.phi * i) + 1) / this.e;
            }

            i++;
        }
        this.d = d;
        int[] privateKey = { this.d, this.n };
        this.keys.put("privateKey", privateKey);
        this.setKey(keys);
    }

    /**
     * Generate e(encryption number) by checking the co primes of phi and n
     */
    public void generateE() {
        if (this.phi == 0 && this.n == 0) {
            System.err.println("Error phi and n is not set");
            System.exit(0);
        }

        int e = 0; // encryption key
        List<Integer> coprimesOfPhi = RSA.getCoprimes(this.phi); // get coprimes of phi
        List<Integer> coprimesOfN = RSA.getCoprimes(this.n); // get coprimes of n
        HashMap<Integer, Boolean> coPrimes = new HashMap<Integer, Boolean>();

        // map coprimesOfPhi
        for (int i = 0; i < coprimesOfPhi.size(); i++) {
            coPrimes.put(coprimesOfPhi.get(i), true);
        }
        // search for matching coprimes of phi and n
        for (int i = 0; i < coprimesOfN.size(); i++) {
            int prime = coprimesOfN.get(i);
            if (coPrimes.containsKey(prime)) {
                e = prime;
                break;
            }
        }

        this.e = e;
        int[] publicKey = { this.e, this.n };
        this.keys.put("publicKey", publicKey);
        this.setKey(keys);
    }

    /**
     * Manually set e(encryption number)
     * 
     * @param value
     */
    public void setE(int value) {
        if (isRelativePrime(value, this.phi) && isRelativePrime(value, this.n) && (value > 1 && value < phi)) {
            this.e = value;
            int[] publicKey = { this.e, this.n }; // set public key
            this.keys.put("publicKey", publicKey);
            this.setKey(keys);
        } else {
            System.err.println("Error: value must be a relative prime of phi and n or 1 < value < phi");
            System.exit(0);
        }
    }

    public void setD(int value) {
        if ((this.e * value) % this.phi == 1 && value != this.e) {
            this.d = value;
            int[] privateKey = { this.d, this.n };
            this.keys.put("privateKey", privateKey);
            this.setKey(keys);
        } else {
            System.err.println("Error: ed mod phi = 1 and value != e");
        }
    }

    @Override
    void encrypt(String plainText) {

        if (this.n == 0 && this.e == 0) {
            System.out.println("Public key is not set");
        }

        String message = plainText;

        this.setPlainMessage(message);

        // get byte data of each characters
        byte[] rawData = message.getBytes();

        BigInteger[] encrypted = new BigInteger[rawData.length];
        BigInteger e = new BigInteger(String.valueOf(this.e));
        BigInteger n = new BigInteger(String.valueOf(this.n));

        // C = M^e mod n, M < n
        for (int i = 0; i < rawData.length; i++) {
            BigInteger m = new BigInteger(String.valueOf(rawData[i]));
            BigInteger C = m.modPow(e, n);
            encrypted[i] = C;
        }
        String cipherText = "";
        // transform to byte data
        for (int i = 0; i < encrypted.length; i++) {
            BigInteger data = encrypted[i];
            byte transformData = (byte) (data.intValue());
            char correspondingChar = (char) transformData;
            System.out.print(correspondingChar);
            cipherText += correspondingChar;
        }

        this.setEncryptedMessage(cipherText);
    }

    @Override
    void decrypt(String cipherText) {

        if (this.d == 0 && this.n == 0) {
            System.out.println("Private key is not set");
        }

        String message = cipherText;

        // get byte data of each characters
        byte[] rawData = message.getBytes();

        LinkedList<Integer> tData = new LinkedList<Integer>();
        // inspect raw data
        // this block examine the actual encrypted value of character
        for (int i = 0; i < rawData.length; i++) {
            byte x = rawData[i];
            if (x != -17 && x != -66) {
                if (x < 0) {
                    int y = 129 + x; // 129 - x
                    int actualData = 127 + y; // 127 + y
                    tData.add(actualData);
                } else if (x >= 0) {
                    tData.add((int) x);
                }
            }
        }
        BigInteger[] decrypt = new BigInteger[tData.size()];
        BigInteger d = new BigInteger(String.valueOf(this.d));
        BigInteger n = new BigInteger(String.valueOf(this.n));

        // M = C^d mod n, C < n
        for (int i = 0; i < tData.size(); i++) {
            BigInteger c = new BigInteger(String.valueOf(tData.get(i)));
            BigInteger M = c.modPow(d, n);
            decrypt[i] = M;
        }

        String plainMessage = "";
        // transform to byte data
        for (int i = 0; i < decrypt.length; i++) {
            BigInteger data = decrypt[i];
            byte transformData = (byte) (data.intValue());
            char correspondingChar = (char) transformData;
            System.out.print(correspondingChar);
            plainMessage += correspondingChar;
        }

        this.setPlainMessage(plainMessage);
    }

}

/**
 * Abstract class
 * 
 */
abstract class Cryptography {

    private Object plainMessage;
    private Object encryptedMessage;
    private Object key;

    // setter
    protected void setPlainMessage(Object plainMessage) {
        this.plainMessage = plainMessage;
    }

    protected void setKey(Object key) {
        this.key = key;
    }

    protected void setEncryptedMessage(Object encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    // getter
    public Object getKey() {
        return this.key;
    }

    public Object getPlainMessage() {
        return this.plainMessage;
    }

    public Object getEncryptedMessage() {
        return this.encryptedMessage;
    }

    // abstract methods
    /**
     * Encryption
     * 
     * @param plainText // to encrypt
     */
    abstract void encrypt(String plainText);

    /**
     * 
     * Decryption
     * 
     * @param cipherText // to decrypt
     */
    abstract void decrypt(String cipherText);
}
