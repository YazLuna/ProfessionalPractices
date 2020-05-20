package test.othertest;

import org.junit.Test;
import org.apache.commons.codec.digest.DigestUtils;

public class TestPassword {
    @Test
    public void passwordEncryption(){
        String textoSinEncriptar="sergio";
        String textoEncriptadoConMD5=DigestUtils.md5Hex(textoSinEncriptar);
        System.out.println("Texto Encriptado con MD5 : "+textoEncriptadoConMD5);

        String textoSinEncripta="sergio";
        String textoEncriptadoConSHA=DigestUtils.sha1Hex(textoSinEncripta);
        System.out.println("Texto Encriptado con SHA : "+textoEncriptadoConSHA);

    }
}
