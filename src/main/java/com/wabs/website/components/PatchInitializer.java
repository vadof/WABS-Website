package com.wabs.website.components;

import com.wabs.website.models.Image;
import com.wabs.website.models.Patch;
import com.wabs.website.models.Topic;
import com.wabs.website.repository.ImageRepository;
import com.wabs.website.repository.PatchRepository;
import com.wabs.website.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Component
public class PatchInitializer implements ApplicationRunner {

    @Autowired
    private PatchRepository patchRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (patchRepository.findAll().size() == 0) {
            Patch patch = new Patch();
            patch.setAll(createTopic(), new BigDecimal("1.0"), "Game release",
                    "Game release", LocalDateTime.now(), true, createImage());

            patchRepository.save(patch);
        }
    }

    private Topic createTopic() {
        Topic topic = new Topic();
        topic.setName("Global");
        topic.setColor("#000000");

        topicRepository.save(topic);
        return topic;
    }

    private Image createImage() throws IOException {
        Path path = Path.of("src/main/resources/static/images/patch_images/patch1.png");
        byte[] imageBytes = Files.readAllBytes(path);

        Image image = new Image();
        image.setName("patch1");
        image.setOriginalFileName("patch1.png");
        image.setSize((long) imageBytes.length);
        image.setContentType("image/png");
        image.setBytes(imageBytes);

        imageRepository.save(image);
        return image;
    }

}
