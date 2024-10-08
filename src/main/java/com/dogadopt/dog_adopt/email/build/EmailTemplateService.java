package com.dogadopt.dog_adopt.email.build;

public interface EmailTemplateService {

    String buildConfirmationEmail(String name, String link, String text1, String text2, String text3);
}
