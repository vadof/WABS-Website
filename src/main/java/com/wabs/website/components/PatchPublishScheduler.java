package com.wabs.website.components;

import com.wabs.website.models.Patch;
import com.wabs.website.repository.PatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PatchPublishScheduler {

    @Autowired
    private PatchRepository patchRepository;

    @Scheduled(fixedDelay = 60000)
    public void publishScheduledPatches() {
        List<Patch> patches = patchRepository.getUnPublishedPatches();
        for (Patch patch : patches) {
            if (LocalDateTime.now().isAfter(patch.getReleaseDate())) {
                patch.setPublished(true);
                patchRepository.save(patch);
            }
        }
    }

}
