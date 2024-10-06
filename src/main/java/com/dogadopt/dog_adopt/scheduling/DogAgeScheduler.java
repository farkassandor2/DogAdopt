package com.dogadopt.dog_adopt.scheduling;

import com.dogadopt.dog_adopt.service.dog.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class DogAgeScheduler {

    private final DogService dogService;

    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateAgesOnNewYear() {
        dogService.updateDogAge();
    }
}
