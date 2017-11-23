package com.acuo.common.security;

import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.SecurityKey;
import com.acuo.common.util.GuiceJUnitRunner;
import com.google.inject.AbstractModule;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({
        EncryptionModuleTest.SystemPropertyModule.class,
        EncryptionModule.class })
public class EncryptionModuleTest {

    public static class SystemPropertyModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Configuration.class).toInstance(Configuration.builder(AppId.of("common"))
                                                              .with(SecurityKey.of("mysecuritykey"))
                                                              .build());
        }
    }

    @Inject
    PBEStringEncryptor encryptor;

    @Test
    public void testEncryptor() {
        String message = "this is the text I want to encrypt";
        String encrypted = encryptor.encrypt(message);
        assertThat(encrypted).isNotEqualTo(message);
        String decrypt = encryptor.decrypt(encrypted);
        assertThat(decrypt).isEqualTo(message);
    }

}