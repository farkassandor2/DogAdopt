package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.notification.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;               //User who gets the notification

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String notificationMessage;

    private boolean isRead;

    private LocalDateTime createdAt;

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
        this.notificationMessage = notificationType
                                           .getNotificationTypeEnglish()
                                   + " / " +
                                   notificationType
                                           .getNotificationTypeHungarian();
    }
}
