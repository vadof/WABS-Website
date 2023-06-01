package com.wabs.website.services;

import com.wabs.website.models.*;
import com.wabs.website.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PatchRepository patchRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FAQRepository faqRepository;

    public long getPlayerAmount() {
        return playerRepository.count();
    }

    public long getPlayerAmountRegisteredToday() {
        return playerRepository.findAllPlayersRegisteredToday().size();
    }

    public long getTotalGamesPlayed() {
        return matchRepository.getNumberOfMatches();
    }

    public LocalDateTime getLastPatchDate() {
        return patchRepository.getLastPatchDate();
    }

    public Patch getLastPatch() {
        return patchRepository.getLastPatch();
    }

    public Map<Player, BigDecimal> getTop4PlayersWithKd() {
        Iterable<Player> players = playerRepository.findAll();
        Map<Player, BigDecimal> playersAndTheirKd = new HashMap<>();

        for (Player player : players) {
            PlayerStatistics playerStatistics = player.getPlayerStatistics();
            if (playerStatistics.getTotalGames() > 2) {
                playersAndTheirKd.put(player, playerStatistics.getKd());
            }
        }

        List<Map.Entry<Player, BigDecimal>> entryList = new ArrayList<>(playersAndTheirKd.entrySet());
        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<Player, BigDecimal> topPlayers = new HashMap<>();
        for (int i = 0; i < entryList.size() && i < 4; i++) {
            topPlayers.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }

        return topPlayers;
    }

    public List<Patch> getAllPatchesInReversedOrder() {
        List<Patch> patches = patchRepository.findAll();
        Collections.reverse(patches);
        return patches;
    }

    public Optional<Patch> getPatchById(Long id) {
        return patchRepository.findById(id);
    }

    public List<Topic> getAllTopics() {
        return (List<Topic>) topicRepository.findAll();
    }

    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void savePatch(Long topicId, BigDecimal patch, String title,
                          String description, String date, MultipartFile imageFile) {
        Topic topic = topicRepository.findById(topicId).get();

        Image image = fileToImage(imageFile);
        imageRepository.save(image);

        Patch patch1 = new Patch();
        patch1.setAll(topic, patch, title, description,
                parseStringToDate(date), false, image);

        patchRepository.save(patch1);
    }

    private Image fileToImage(MultipartFile multipartFile) {
        Image image = null;
        try {
            image = new Image();
            image.setName(multipartFile.getName());
            image.setOriginalFileName(multipartFile.getOriginalFilename());
            image.setContentType(multipartFile.getContentType());
            image.setSize(multipartFile.getSize());
            image.setBytes(multipartFile.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    private LocalDateTime parseStringToDate(String date) {
        String[] dateTime = date.split(" ");
        String[] localDate = dateTime[0].split("/");
        String[] time = dateTime[1].split(":");

        int day = Integer.parseInt(localDate[0]);
        int month = Integer.parseInt(localDate[1]);
        int year = Integer.parseInt(localDate[2]);

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public void updatePatch(Long patchId, Long topicId, BigDecimal patch, String title,
                            String description, String date, MultipartFile imageFile) {
        Topic topic = topicRepository.findById(topicId).get();
        Patch patch1 = patchRepository.findById(patchId).get();

        Image image = patch1.getImage();
        if (imageFile.getSize() != 0) {
            image = fileToImage(imageFile);
            imageRepository.save(image);
        }

        patch1.setAll(topic, patch, title, description,
                parseStringToDate(date), patch1.isPublished(), image);

        patchRepository.save(patch1);
    }

    public void deletePatch(Long patchId) {
        Optional<Patch> optionalPatch = patchRepository.findById(patchId);
        if (optionalPatch.isPresent()) {
            Patch patch = optionalPatch.get();

            patchRepository.delete(patch);
            imageRepository.delete(patch.getImage());
        }
    }

    public List<Player> getAllPlayers() {
        return (List<Player>) playerRepository.findAll();
    }

    public List<Match> getAllMatches() {
        return (List<Match>) matchRepository.findAll();
    }

    public void updateFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                deleteOldGameFile();
                String fileName = "wabs.jar";
                String filePath = "D:\\JavaPrograms\\website\\src\\main\\resources\\files";

                File dest = new File(filePath + File.separator + fileName);
                file.transferTo(dest);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void deleteOldGameFile() {
        String currentFilePath = "src/main/resources/files/wabs.jar";

        Path currentPath = Paths.get(currentFilePath);
        try {
            Files.delete(currentPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<FAQ> getAllFaqs() {
        return (List<FAQ>) faqRepository.findAll();
    }

    public void saveFAQ(String question, String answer) {
        FAQ faq = new FAQ();
        faq.setQuestion(question);
        faq.setAnswer(answer);

        faqRepository.save(faq);
    }

    public Optional<FAQ> getFAQById(Long id) {
        return faqRepository.findById(id);
    }

    public void editFaq(Long id, String question, String answer) {
        Optional<FAQ> faqOptional = faqRepository.findById(id);
        if (faqOptional.isPresent()) {
            FAQ faq = faqOptional.get();
            faq.setQuestion(question);
            faq.setAnswer(answer);

            faqRepository.save(faq);
        }
    }

    public void deleteFAQ(Long id) {
        Optional<FAQ> faqOptional = faqRepository.findById(id);
        faqOptional.ifPresent(faq -> faqRepository.delete(faq));
    }
}
