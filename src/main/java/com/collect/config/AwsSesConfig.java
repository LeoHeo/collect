package com.collect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.auth.AwsCredentials;
import software.amazon.awssdk.core.regions.Region;
import software.amazon.awssdk.services.ses.SESAsyncClient;

/**
 * @author Heo, Jin Han
 * @since 2018-05-02
 */
@Configuration
public class AwsSesConfig {

  @Value("${AWS_ACCESS_KEY_ID}")
  private String AWS_ACCESS_KEY_ID;

  @Value("${AWS_SECRET_KEY}")
  private String AWS_SECRET_KEY;

  @Bean
  public SESAsyncClient sesAsyncClient() {
    return SESAsyncClient.builder()
        .credentialsProvider(() -> AwsCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY))
        .region(Region.US_WEST_2)
        .build();
  }
}
