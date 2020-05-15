package com.demos.demoOtp1.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class NblOtp extends Thread {      
     
//    private static byte[] hmac_sha1(String crypto, byte[] keyBytes,
//        byte[] text)
    public byte[] hmac_sha1(String crypto, byte[] keyBytes,
        byte[] text)
    {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey =
                new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }


    /**
     * This method converts HEX string to Byte[]
     *
     * @param hex   the HEX string
     *
     * @return      A byte array
     */
    // Arreglo del tipo Byte
//    private static byte[] hexStr2Bytes(String hex){
    public byte[] hexStr2Bytes(String hex){
        // Adding one byte to get the right conversion
        // values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex,16).toByteArray(); // validar Hex -> Byte

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];//copia un arreglo en otro -1 -> elimino el ultimo valor
        for (int i = 0; i < ret.length ; i++)
            ret[i] = bArray[i+1];
        return ret;
    }


//    private static final int[] DIGITS_POWER
    public final int[] DIGITS_POWER
    // 0 1  2   3    4     5      6       7        8
  = {1,10,100,1000,10000,100000,1000000,10000000,100000000 };


    /**
     * This method generates an TOTP value for the given
     * set of parameters.
     *
     * @param key   the shared secret, HEX encoded
     * @param time     a value that reflects a time
     * @param returnDigits     number of digits to return
     *
     * @return      A numeric String in base 10 that includes
     *              {@link truncationDigits} digits
     */
//    public static String generateTOTP(String key,
//            String time,
//            String returnDigits)
    
        public String generateTOTP(String key,
            String time,
            String returnDigits)
    {
                    
        return generateTOTP(key, time, returnDigits, "HmacSHA1");
        
    }


    /**
     * This method generates an TOTP value for the given
     * set of parameters.
     *
     * @param key   the shared secret, HEX encoded
     * @param time     a value that reflects a time
     * @param returnDigits     number of digits to return
     *
     * @return      A numeric String in base 10 that includes
     *              {@link truncationDigits} digits
     */
//    public static String generateTOTP256(String key,
//            String time,
//            String returnDigits)
            
            
    public String generateTOTP256(String key,
            String time,
            String returnDigits)
    {
        return generateTOTP(key, time, returnDigits, "HmacSHA256");
    }


    /**
     * This method generates an TOTP value for the given
     * set of parameters.
     *
     * @param key   the shared secret, HEX encoded
     * @param time     a value that reflects a time
     * @param returnDigits     number of digits to return
     *
     * @return      A numeric String in base 10 that includes
     *              {@link truncationDigits} digits
     */
    public String generateTOTP512(String key,
            String time,
            String returnDigits)
    {
        return generateTOTP(key, time, returnDigits, "HmacSHA512");
    }


    /**
     * This method generates an TOTP value for the given
     * set of parameters.
     *
     * @param key   the shared secret, HEX encoded
     * @param time     a value that reflects a time
     * @param returnDigits     number of digits to return
     * @param crypto    the crypto function to use
     *
     * @return      A numeric String in base 10 that includes
     *              {@link truncationDigits} digits
     */
    public String generateTOTP(String key,
            String time,
            String returnDigits,
            String crypto)
    {
        int codeDigits = Integer.decode(returnDigits).intValue();
        String result = null;
        byte[] hash;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Complaint with base RFC 4226 (HOTP)
        while(time.length() < 16 )
            time = "0" + time;

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);

        // Adding one byte to get the right conversion
        byte[] k = hexStr2Bytes(key);

        hash = hmac_sha1(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        //24
        // 16
        //8
        int binary = ((hash[offset] & 0x7f) << 24) |((hash[offset + 1] & 0xff) << 16) |((hash[offset + 2] & 0xff) << 8)|(hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }
    
    @Override
    public void run(){
    
    
        
    
    }   
    
        
     public String OtpBod(String longNewOtp) {
          
       long start = System.currentTimeMillis();
       Random aleatorio = new Random(System.currentTimeMillis());
       int intAletorio = aleatorio.nextInt(100);
       String longOTP =  longNewOtp;
       String Otp = "";
         
       Double  numero = Math.random();
       Double  numoero2 = Math.random(); 
       String seed = numero.toString() + numoero2.toString();
       seed = seed.replace('.', '7');
                
         Random rnd = new Random();
        long T0 = 0;
        long X = 30;
        long P = 0;
        int mmin = 1;
        Random rand = null;
        int max = 5;
        long testTime = 0;
        numero = Math.random();
        numoero2 = Math.random(); 
        seed = numero.toString() + numoero2.toString();
        seed = seed.replace('.', '1');
       
        String steps = "0";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

         try{
                testTime = ((int)(rnd.nextDouble() * 10000));
                seed = seed + String.valueOf(start);
          
                long T = (testTime - T0)/X;
                steps = Long.toHexString(T).toUpperCase(); //convierte el numero a hexadecimal
                while(steps.length() < 16) steps = "0" + steps; //coloca a 16 digitos
                String fmtTime = String.format("%1$-10s", testTime);//formatea el texto
                String utcTime = df.format(new Date(testTime*1000));//finalmente registra la fecha en formato AAMMDDHHMMSS YA NO SE USA
                       
                Otp = generateTOTP(seed, steps, longOTP,"HmacSHA512");
         //       System.out.println(generateTOTP(seed, steps, "4","HmacSHA1") + "| SHA1   |");
         //       System.out.println(generateTOTP(seed, steps, "4","HmacSHA256") + "| SHA256 |");
            //pasa la cadena de que varia, valor Hexadecimal, y el metodo
            
                
                
        }catch (final Exception e){
            System.out.println("Error Generando la OTP : " + e);
        }
         
       return ((Otp));
         
    }
    
    public static void main(String longOtp) {
        
        
    }
     
    
    
    
    
}
