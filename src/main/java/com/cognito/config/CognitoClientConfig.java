package com.cognito.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 921452
 *
 */
@Configuration
public class CognitoClientConfig {

    @Value("${aws.access-key}")
    private String access_key;
    @Value("${aws.secret-key}")
    private String secret_key;

    Region region= Region.getRegion(Regions.AP_SOUTH_1);

    @Bean
    public AWSCognitoIdentityProvider createCognitoClient() {

        AWSCredentials cred = new BasicAWSCredentials(access_key, secret_key);

        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                        .withCredentials(credProvider)
                        .withRegion(String.valueOf(region))
                        .build();
    }
}
