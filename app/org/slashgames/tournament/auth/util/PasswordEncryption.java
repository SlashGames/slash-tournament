package org.slashgames.tournament.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption
{
    public static String encryptPassword(String unencrypted)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(unencrypted.getBytes());
            byte[] digest = messageDigest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++)
            {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }

            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
