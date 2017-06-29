package cn.six.sec.keystore;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Created by songzhw on 2016/1/16.
 */
public class ManyKeysTest {

    public static void main(String[] args) throws Exception {
        String keystorePwd = "szwjavac";

        final String keystoreFilePath = "out/myks.keystore";
        KeyStore keyStore = SaveKeyStoreTest.createKeyStore(keystoreFilePath, keystorePwd);

         // I saved 4 keys in KeyStore (with SaveKeyStoreTest class)
        // and a cert in KeyStore (with SavePaireKeyStoreTest class)
        Enumeration<String> aliases = keyStore.aliases();
        while(aliases.hasMoreElements()) {
            String aalias = aliases.nextElement();
            boolean isCE = keyStore.isCertificateEntry(aalias);
            boolean isKE = keyStore.isKeyEntry(aalias);
            System.out.println("KeyStore alias = "+aalias+" ; isCe  = " + isCE + " ; isKe = " + isKE);
        }

        // ----------------------------------------------------------
//        System.out.println("-----------------------------------");
//
//        Certificate cert3 =  keyStore.getCertificate("certAlias");
//        X509Certificate cert3_2 = (X509Certificate) cert3;
//        System.out.println("SubjectDN = " + cert3_2.getSubjectDN().getName());

    }
}

/*
the result is :
KeyStore alias = mykey03 ; isCe = false ; isKe = true
KeyStore alias = mykey02 ; isCe = false ; isKe = true
KeyStore alias = mykey01 ; isCe = false ; isKe = true
KeyStore alias = mykey04 ; isCe = false ; isKe = true
KeyStore alias = certalias ; isCe  = false ; isKe = true
 */
