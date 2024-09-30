package com.dogadopt.dog_adopt.domain.enums.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    NEW_DOG_ADDED("New Dog Added", "Új kutya hozzáadva"),                   // Notification when a new dog is added to the platform
    DOG_ADOPTED("Dog Adopted", "Kutyát örökbefogadtak"),                    // Notification when a dog has been successfully adopted
    COMMENT_REPLY("Comment Reply", "Hozzászólás válasz"),                    // Notification when someone replies to a user's comment
    DOG_UPDATE("Dog Update", "Kutyainformáció frissítve"),                  // Notification for updates on a dog's profile (e.g., status change)
    NEW_COMMENT("New Comment", "Új hozzászólás"),                            // Notification when a new comment is added to a user's favorite dog
    FRIEND_REQUEST("Friend Request", "Barátkérés"),                         // Notification for a new friend request (if social features are included)
    FRIEND_REQUEST_ACCEPTED("Friend Request Accepted", "Barátkérés elfogadva"), // Notification when a friend request is accepted
    EVENT_REMINDER("Event Reminder", "Esemény emlékeztető"),                // Reminder notifications for adoption events or meet-ups
    MESSAGE_RECEIVED("Message Received", "Üzenet érkezett"),                 // Notification when a user receives a message (if messaging is implemented)
    SYSTEM_ALERT("System Alert", "Rendszerfigyelmeztetés"),                 // General system alerts (e.g., maintenance notices)
    ACCOUNT_VERIFICATION("Account Verification", "Fiók ellenőrzés"),        // Notification for account verification or related actions
    ADOPTION_APPLICATION("Adoption Application", "Örökbefogadási kérelem"),   // Notification for updates on adoption applications
    ADOPTION_TIPS("Adoption Tips", "Örökbefogadási tippek"),                // Sending tips or advice related to dog adoption or care
    PROMOTIONAL_OFFER("Promotional Offer", "Promóciós ajánlat");            // Promotional offers related to adoptions, products, or services

    private final String notificationTypeEnglish;
    private final String notificationTypeHungarian;
}
