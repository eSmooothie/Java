/**
 * Cryptography 
 * 
 */

package Exercise1; // folder name

// imports

/**
 * Main
 * 
 */
class Main {
    public static void main(String args[]) {
        // instantiate
        Vigenere vigenereCipher = new Vigenere();

        // agreed key
        String key = "lemon";

        // encryption
        String plainText = "attack at dawn";
        vigenereCipher.encrypt(plainText, key);
        System.out.println("Plain Message: " + vigenereCipher.getPlainMessage());
        System.out.println("Key: " + vigenereCipher.getKey());
        System.out.println("Encrypted Message: " + vigenereCipher.getEncryptedMessage());
        System.out.println();

        // decryption
        String cipherText = "lxfopvefrnhr";
        vigenereCipher.decrypt(cipherText, key);
        System.out.println("Encrypted Message: " + vigenereCipher.getEncryptedMessage());
        System.out.println("Key: " + vigenereCipher.getKey());
        System.out.println("Plain Message: " + vigenereCipher.getPlainMessage());

    }
}

/**
 * Vigenere cipher text
 * 
 * 
 */
class Vigenere extends Cryptography {
    /**
     * Evaluate if the object is a string
     * 
     * @param obj
     * @return true if string otherwise false
     */
    private boolean isString(Object obj) {
        return obj instanceof String;
    }

    /**
     * Remove white spaces in a string
     * 
     * Example input " ABC DEF "
     * 
     * @param plainMessage
     * @return A no whitespace string, example output: "ABCDEF"
     */
    private String removeWhitespace(String message) {
        // remove space
        return message.replace(" ", "");
    }

    /**
     * Convert a string to decimal
     * 
     * @param string
     * @return a list of integer from [0-25], where 0 = A ... 25 = Z
     */
    private int[] stringToDecimal(String str) {
        str = str.toUpperCase();

        int n = str.length(); // string size

        int[] dec = new int[n];

        char[] characters = str.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            // read each character in a string
            // and get its corresponding integer value

            char c = characters[i];

            int d = Integer.valueOf(c) - 65; // 65 = A

            // check for non alphabet character
            if (d < 0 || d > 25) {
                // terminate
                System.err.println("Error: Non alphabet charcater");
                System.exit(0);
            } else {

                dec[i] = d;

            }
        }

        return dec;
    }

    /**
     * Convert list of decimal into a string
     * 
     * Ex. input: [0,1,2]
     * 
     * @param decimal[]
     * @return converted string, ex. output: ABC
     */
    private String decimalToString(int[] decimal) {
        String str = "";
        for (int i = 0; i < decimal.length; i++) {
            int d = decimal[i] + 65;
            if (d < 0 || d > 25) {
                // terminate
                System.err.println("Error: Invalid character.");
                System.exit(0);
            } else {
                String c = Character.toString((char) d);
                str = str + c;
            }

        }
        return str;
    }

    @Override
    void encrypt(Object obj, Object key) {
        // check obj or key is an instance of a String
        if (!isString(obj) || !isString(key)) {
            // terminate
            System.err.println("Error: Invalid data type.");
            System.exit(0);
        }
        // remove white spaces
        String plainMessage = removeWhitespace(obj.toString());
        String strKey = removeWhitespace(key.toString());

        this.setPlainMessage(plainMessage.toUpperCase());
        this.setKey(strKey.toUpperCase());

        // convert each char into decimal
        int[] decPlaintext = stringToDecimal(plainMessage);
        int[] decKey = stringToDecimal(strKey);

        // Encryption
        int[] encryptedDec = new int[decPlaintext.length];

        // start encrypting

        // Eq. C[i] = (M[i] + K[i mod K.len]) mod 26
        for (int i = 0; i < encryptedDec.length; i++) {
            int M = decPlaintext[i]; // plain message
            int K = decKey[(i % decKey.length)]; // repeated key
            int C = (M + K) % 26; // cipher message
            encryptedDec[i] = C;
        }

        String encryptedMessage = decimalToString(encryptedDec);

        this.setEncryptedMessage(encryptedMessage);
    }

    @Override
    void decrypt(Object obj, Object key) {
        // check obj or key is an instance of a String
        if (!isString(obj) || !isString(key)) {
            // terminate
            System.err.println("Error: Invalid data type.");
            System.exit(0);
        }
        // remove white spaces
        String encryptedMessage = removeWhitespace(obj.toString());
        String strKey = removeWhitespace(key.toString());

        this.setEncryptedMessage(encryptedMessage.toUpperCase());
        this.setKey(strKey.toUpperCase());

        // convert each char into decimal
        int[] decCipher = stringToDecimal(encryptedMessage);
        int[] decKey = stringToDecimal(strKey);

        // Decryption
        int[] decryptedDec = new int[decCipher.length];

        // start decrypting

        // Eq. M[i] = (C[i] - K[i mod K.len] + 26) mod 26
        for (int i = 0; i < decryptedDec.length; i++) {
            int C = decCipher[i]; // cipher message
            int K = decKey[(i % decKey.length)]; // repeated key
            int M = (C - K + 26) % 26; // plain message
            decryptedDec[i] = M;
        }

        String plainMessage = decimalToString(decryptedDec);

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
     * @param obj // to encrypt
     * @param key
     */
    abstract void encrypt(Object obj, Object key);

    /**
     * 
     * Decryption
     * 
     * @param obj // to decrypt
     * @param key
     */
    abstract void decrypt(Object obj, Object key);
}
