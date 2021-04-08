
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Main {

    public static void encryptDecryptECB(String key,int cipherMode, File in, File out)
    throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException{
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        DESKeySpec deskeyspec = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = skf.generateSecret(deskeyspec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        if(cipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            write(cis, fos);

        } else if (cipherMode == Cipher.DECRYPT_MODE){

            cipher.init(Cipher.DECRYPT_MODE, secretKey, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            write(fis, cos);
        }
    }

    public static void encryptDecryptCBC(String key,int cipherMode, File in, File out)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, InvalidAlgorithmParameterException {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        DESKeySpec deskeyspec = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = skf.generateSecret(deskeyspec);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        byte [] ivBytes = new byte[8];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        if(cipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            write(cis, fos);

        } else if (cipherMode == Cipher.DECRYPT_MODE){

            cipher.init(Cipher.DECRYPT_MODE, secretKey,iv, SecureRandom.getInstance("SHA1PRNG"));
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            write(fis, cos);
        }
    }

    private static void write (InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[64];
        int numOfBytesRead;
        while ((numOfBytesRead = in.read(buffer)) != -1){
            out.write(buffer,0, numOfBytesRead);
        }
        out.close();
        in.close();
    }

    public static void readfile () {

        try {
            FileInputStream x = new FileInputStream("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/uzkoduotas.txt");
            DataInputStream in = new DataInputStream(x);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null){
                System.out.println(strLine);
            }
            in.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void readfile2 () {

        try {
            FileInputStream x = new FileInputStream("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/atkoduotas.txt");
            DataInputStream in = new DataInputStream(x);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null){
                System.out.println(strLine);
            }
            in.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void irasfile () throws IOException {

        Scanner en = new Scanner(System.in);
        System.out.println("\nĮveskite tekstą:\n");
        String nau = en.nextLine();

        File fil = new File ("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/plain.txt");
        FileWriter fw = new FileWriter(fil, true);
        PrintWriter pw =  new PrintWriter(fw);

        pw.println(nau);
        pw.close();

    }

    public static void pagrindinisECB () throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("1.Užkoduot \n2.Atkoduoti\n\nPasirinkti: ");
        int choice = in.nextInt();
        in.nextLine();

        if (choice == 1) {

            irasfile ();

            System.out.println("\nĮveskite raktą:\n");
            String keyadd= in.nextLine();


            File plaintext = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/plain.txt");
            File encrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/uzkoduotas.txt");

            try {
                encryptDecryptECB(keyadd, Cipher.ENCRYPT_MODE, plaintext, encrypted);
                System.out.println("Tekstas užkoduotas");
                readfile();

            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if (choice == 2){

            System.out.println("\nFaile esantis užkoduotas tekstas: \n");
            readfile();

            System.out.println("\nĮveskite raktą:\n");
            String keya= in.nextLine();

            File encrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/uzkoduotas.txt");
            File decrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/atkoduotas.txt");

            try {
                encryptDecryptECB(keya, Cipher.DECRYPT_MODE,encrypted, decrypted);
                System.out.println("Tekstas atkoduotas");
                readfile2 ();


            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else {
            System.out.println("Neteisingas pasirinkimas");
        }

    }


    public static void pagrindinisCBC () throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("1.Užkoduot \n2.Atkoduoti\n\nPasirinkti: ");
        int choice = in.nextInt();
        in.nextLine();

        if (choice == 1) {

            irasfile ();

            System.out.println("\nĮveskite raktą:\n");
            String keyadd= in.nextLine();


            File plaintext = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/plain.txt");
            File encrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/uzkoduotas.txt");

            try {
                encryptDecryptCBC(keyadd, Cipher.ENCRYPT_MODE, plaintext, encrypted);
                System.out.println("Tekstas užkoduotas");
                readfile();

            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException | InvalidAlgorithmParameterException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if (choice == 2){

            System.out.println("\nFaile esantis užkoduotas tekstas: \n");
            readfile();

            System.out.println("\nĮveskite raktą:\n");
            String keya= in.nextLine();

            File encrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/uzkoduotas.txt");
            File decrypted = new File("/Users/erikabreivyte/IdeaProjects/DES algoritmas/src/atkoduotas.txt");

            try {
                encryptDecryptCBC(keya, Cipher.DECRYPT_MODE,encrypted, decrypted);
                System.out.println("Tekstas atkoduotas");
                readfile2 ();


            } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IOException | InvalidAlgorithmParameterException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else {
            System.out.println("Neteisingas pasirinkimas");
        }

    }





    public static void main (String[] args) throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("1.Modas ECB \n2.Modas CBC \n\nPasirinkti: ");
        int choice = in.nextInt();
        in.nextLine();

        if (choice == 1) {
            pagrindinisECB();

        } else if (choice == 2) {
            pagrindinisCBC();
        } else {
            System.out.println("Neteisingas pasirinkimas");
        }
    }


}
