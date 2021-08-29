package com.ark.inflearnback.config.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class JasyptConfig {
    private JasyptProperties jasyptProperties;

    @Bean("jasyptStringEncryptor")
    @Primary
    @Profile("prod")
    public StringEncryptor prodEncryptor() {
        return encryptorSetting(jasyptProperties.getPassword());
    }

    @Bean("jasyptStringEncryptor")
    @Primary
    @Profile("dev")
    public StringEncryptor devEncryptor() {
        return encryptorSetting("ark-back");
    }

    public PooledPBEStringEncryptor encryptorSetting(final String password) {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}