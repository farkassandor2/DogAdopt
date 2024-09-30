package com.dogadopt.dog_adopt.domain;

import com.dogadopt.dog_adopt.domain.enums.notification.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class NotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isEnabled;

    @ElementCollection(targetClass = NotificationType.class)
    @CollectionTable(name = "user_notification_types", joinColumns = @JoinColumn(name = "notification_preference_id"))
    @Enumerated(EnumType.STRING)
    private Set<NotificationType> notificationType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    protected void onCreate() {
        this.isEnabled = true;
    }
}
