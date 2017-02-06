package com.acuo.common.security;

import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.SecurityKey;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.digest.config.DigesterConfig;
import org.jasypt.digest.config.SimpleStringDigesterConfig;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

@Slf4j
public class EncryptionModule extends AbstractModule {

    private final static Logger LOG = LoggerFactory.getLogger(EncryptionModule.class);

    private final PBEStringEncryptor dummyEncryptor = new PBEStringEncryptor() {
        @Override
        public String encrypt(String message) {
            log.warn("No secret key provided, using dummy encryptor to encrypt");
            return message;
        }

        @Override
        public String decrypt(String encryptedMessage) {
            log.warn("No secret key provided, using dummy encryptor to decrypt");
            return encryptedMessage;
        }

        @Override
        public void setPassword(String password) {

        }
    };

    @Override
    protected void configure() {
    }

    @Provides
    DigesterConfig providePasswordConfig() {
        SimpleStringDigesterConfig config = new SimpleStringDigesterConfig();
        config.setAlgorithm("SHA-256");
        config.setIterations(100_101);
        config.setSaltSizeBytes(20);
        config.setPoolSize(10);
        return config;
    }

    @Provides
    PasswordEncryptor providePasswordEncryptor(DigesterConfig config) {
        ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }

    @Provides
    SaltGenerator provideSaltGenerator() {
        return new RandomSaltGenerator();
    }

    @Provides
    PBEStringEncryptor provideStringEncryptor(Configuration configuration) {
        SecurityKey securityKey = configuration.getSecurityKey();
        if (securityKey != null ) {
            StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
            decryptor.setPassword(securityKey.toString());
            decryptor.setAlgorithm("PBEWithMD5AndDES");
            return decryptor;
        }
        return dummyEncryptor;
    }

    public static void main(String[] args) {
        Scanner scanIn = new Scanner(System.in);
        System.out.print("Enter the encryption key: ");
        String key = scanIn.next();
        System.out.print("Enter the text to encrupt: ");
        String password = scanIn.next();

        EncryptionModule module = new EncryptionModule();
        Configuration config = Configuration.builder(AppId.of("common"))
                                            .with(SecurityKey.of(key))
                                            .build();
        PBEStringEncryptor encryptor = module.provideStringEncryptor(config);
        String encrypted = encryptor.encrypt(password);

        System.out.println("The encrypted test [" + encrypted +"]");
    }
}