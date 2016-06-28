package com.acuo.common.security;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
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

import javax.inject.Inject;
import javax.inject.Named;

public class EncryptionModule extends AbstractModule {

    private final static Logger LOG = LoggerFactory.getLogger(EncryptionModule.class);

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
    @Inject
    PBEStringEncryptor provideStringEncryptor(@Named("acuo.security.key") String securityKey) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(securityKey);
        decryptor.setAlgorithm("PBEWithMD5AndDES");
        return decryptor;
    }

}