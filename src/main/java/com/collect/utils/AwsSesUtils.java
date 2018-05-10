package com.collect.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import software.amazon.awssdk.services.ses.SESAsyncClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailRequest.Builder;

/**
 * @author Heo, Jin Han
 * @since 2018-05-09
 */
@Component
@AllArgsConstructor
public class AwsSesUtils {

  private final SESAsyncClient sesAsyncClient;
  private final SpringTemplateEngine templateEngine;

  public void singleEmailRequest(String to, String subject, String template, Context context) {
    String html = templateEngine.process(template, context);

    final Builder sendEmailRequestBuilder = SendEmailRequest.builder();
    sendEmailRequestBuilder.destination(Destination.builder().toAddresses(to).build());
    sendEmailRequestBuilder.message(newMessage(subject, html)).source("hjh5488@gmail.com").build();

    sesAsyncClient.sendEmail(sendEmailRequestBuilder.build());
  }

  private Message newMessage(String subject, String html) {
    Content content = Content.builder().data(subject).build();
    Message message = Message.builder().subject(content).body(Body.builder().html(builder -> builder.data(html)).build()).build();

    return message;
  }

}
