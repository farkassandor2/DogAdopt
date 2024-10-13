package com.dogadopt.dog_adopt.email.build;

public interface EmailTemplateService {

    String buildConfirmationEmail(String letterTitle,
                                  String greetedReference,
                                  String text1,
                                  String text2,
                                  String text3,
                                  String link);
}
