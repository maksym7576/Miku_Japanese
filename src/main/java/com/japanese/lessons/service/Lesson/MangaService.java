package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.request.AnswerDataDTO;
import com.japanese.lessons.dtos.request.AnswersDTO;
import com.japanese.lessons.dtos.request.QuestionAnswerDTO;
import com.japanese.lessons.dtos.response.QuizRewardsDTO;
import com.japanese.lessons.models.*;
import com.japanese.lessons.models.User.User;
import com.japanese.lessons.models.User.UserIncorrectAnswers;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.models.lesson.mangaExercise.*;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import com.japanese.lessons.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MangaService {

    @Autowired private IMangaRepository iMangaRepository;
    @Autowired private LessonService lessonService;
    @Autowired private UserIncorrectAnswersService userIncorrectAnswersService;
    @Autowired private RewardsService rewardsService;
    @Autowired private ImagesService imagesService;
    @Autowired private UserService userService;
    @Autowired private QuestionService questionService;
    @Autowired private MangaDialogueService mangaDialogueService;
    @Autowired private AudioService audioService;
    @Autowired private Ordered_objects_service orderedObjectsService;

    public Manga getMangaById(Long id) {
        Manga manga = iMangaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + id));
        manga.validateCompletion();

        return manga;
    }
    public Manga getMangaByLessonID(Long lessonId) {
        Manga manga = iMangaRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + lessonId));
        manga.validateCompletion();
        return manga;
    }

    private void addManga(Manga manga, List<StructuredDataForExercisesDTO> contenetList) {
        MangaDetailsDTO mangaDetailsDTO = new MangaDetailsDTO(manga.getId(), manga.getName(),manga.getStartDialog());
        contenetList.add(new StructuredDataForExercisesDTO("manga", mangaDetailsDTO));
    }

    private void addImage(Ordered_objects index, List<StructuredDataForExercisesDTO> contentList) {
        Image image = imagesService.getImageById(index.getObjectId());
            List<Text> textList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.IMAGE_TABLE,image.getId());
            Audio audio = audioService.getByTypeAndObjectId(ETargetType.IMAGE_TABLE, image.getId());
            ImageMangaDTO imagesWithTextAndAudio = new ImageMangaDTO();
            if (!textList.isEmpty()) {
                Text text = textList.get(0);
                imagesWithTextAndAudio.setImage(image);
                imagesWithTextAndAudio.setMangaPhotoDescription(text);
                imagesWithTextAndAudio.setAudio(audio);
            }
                contentList.add(new StructuredDataForExercisesDTO( "image", imagesWithTextAndAudio));
    }

    private void addImagesTogether(Ordered_objects index, List<StructuredDataForExercisesDTO> contentList) {
        Image image = imagesService.getImageById(index.getObjectId());
        Text text = mangaDialogueService.getTextByTypeAndObjectId(ETargetType.IMAGES_TOGETHER, image.getId());
        Audio audio = audioService.getByTypeAndObjectId(ETargetType.IMAGES_TOGETHER, image.getId());
        boolean added = false;

        // Перевірка наявності зображення в контенті
        for (StructuredDataForExercisesDTO data : contentList) {
            if (data.getType().equals("images_together") && data.getContent() instanceof ImagesHasRightLeftDTO) {
                ImagesHasRightLeftDTO dto = (ImagesHasRightLeftDTO) data.getContent();

                // Спочатку додаємо зображення на праву сторону, якщо праве ще немає
                if (dto.getImageRight() == null) {
                    ImageMangaDTO imageRightHasTextAudio = new ImageMangaDTO(image, text, audio);
                    dto.setImageRight(imageRightHasTextAudio);
                    added = true;
                    break;
                }

                // Якщо праве зображення вже є, додаємо на ліву сторону
                if (dto.getImageLeft() == null) {
                    ImageMangaDTO imageLeftHasTextAudio = new ImageMangaDTO(image, text, audio);
                    dto.setImageLeft(imageLeftHasTextAudio);
                    added = true;
                    break;
                }
            }
        }

        // Якщо зображення ще не додано, додаємо новий об'єкт з правим зображенням
        if (!added) {
            ImageMangaDTO imageRight = new ImageMangaDTO(image, text, audio);
            ImagesHasRightLeftDTO newDto = new ImagesHasRightLeftDTO(imageRight, null); // Початково праве зображення
            contentList.add(new StructuredDataForExercisesDTO("images_together", newDto));
        }
    }

    private void addMangaDialogue(Ordered_objects index, List<StructuredDataForExercisesDTO> contenetList) {
        Text text = mangaDialogueService.getMangaDialogueById(index.getObjectId());
        contenetList.add(new StructuredDataForExercisesDTO("dialogue", text));
    }

    private void addQuestion(Ordered_objects index, List<StructuredDataForExercisesDTO> contenetList) {
        Question question = questionService.getQuestionById(index.getObjectId());
        List<Text> questionAnswers = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, question.getId());
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(question, questionAnswers);
            contenetList.add(new StructuredDataForExercisesDTO("question", questionAnswerDTO));
    }

    public List<StructuredDataForExercisesDTO> sortMangaByQueue(Long lessonId) {
        Manga manga = getMangaByLessonID(lessonId);
        List<Ordered_objects> objectsList = orderedObjectsService.getOrderedObjectsListByOrderedIdAndType(ETargetType.MANGA_TABLE, manga.getId());
        Collections.sort(objectsList, Comparator.comparing(Ordered_objects:: getOrderIndex));
        List<StructuredDataForExercisesDTO> contentList = new ArrayList<>();
        addManga(manga, contentList);
        for (Ordered_objects index : objectsList) {
            switch (index.getTargetType()) {
                case IMAGE_TABLE -> addImage(index, contentList);
                case IMAGES_TOGETHER -> addImagesTogether(index, contentList);
                case MANGA_DIALOGUE -> addMangaDialogue(index, contentList);
                case EXERCISE_QUESTION -> addQuestion(index, contentList);
                default -> {}
            }
        }

        return contentList;
    }



    public Manga saveManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null.");
        } else {
            manga.validateCompletion();
            lessonService.getLessonById(manga.getLesson().getId());
            return iMangaRepository.save(manga);
        }
    }

    @Transactional
    public void updateManga(Long id, Manga updatedManga) {
        Manga existingManga = getMangaById(id);
        existingManga.copyNonNullProperties(updatedManga);
        saveManga(existingManga);
    }

    public void deleteManga(Long id) {
        if (iMangaRepository.existsById(id)) {
            iMangaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Manga doesn't exist.");
        }
    }

    private void saveUniqueIncorrectAnswers(Long userId,List<UserIncorrectAnswers> newAnswers) {
        List<UserIncorrectAnswers> responseWords = userIncorrectAnswersService.getAllUserWordsToLEarn(userId);
        Set<String> existingKeys = responseWords.stream()
                        .map((answer -> answer.getObjectId() + "_" + answer.getType()))
                                .collect(Collectors.toSet());
        List<UserIncorrectAnswers> uniqueExercisesToSave = newAnswers.stream()
                .filter(answer -> !existingKeys.contains(answer.getObjectId() + "_" + answer.getType()))
                        .collect(Collectors.toList());
        userIncorrectAnswersService.saveAllIncorrectAnswers(uniqueExercisesToSave);
    }

    private void addPercentageAndExperience(AnswersDTO answersDTO, List<QuizRewardsDTO> quizRewardsDTOList) {
        int numIncorrectAnswers = answersDTO.getUserIncorrectAnswersList().size();
        int percentage = cuntPercentage(answersDTO.getNumCorrectAnswers(), numIncorrectAnswers);
        int experience = countExperience(answersDTO.getNumCorrectAnswers(), percentage);
        quizRewardsDTOList.add(new QuizRewardsDTO("Percentage", percentage, null));
        quizRewardsDTOList.add(new QuizRewardsDTO("Experience", experience, null));
    }

    private void addItem(AnswersDTO answersDTO, List<QuizRewardsDTO> quizRewardsDTOList) {
        List<Rewards> rewardsMangaList = rewardsService.getRewardsByTarget(ETargetType.MANGA_TABLE, answersDTO.getMangaId());
        for (Rewards rewards : rewardsMangaList) {
            List<Image> imageList = imagesService.getImagesByObjectId(ETargetType.REWARD_TABLE, rewards.getId());
            quizRewardsDTOList.add(new QuizRewardsDTO("Item", rewards, imageList.get(0)));
        }
    }

    public List<QuizRewardsDTO> concludeManga(AnswersDTO answersDTO) {
        List<QuizRewardsDTO> quizRewardsDTOList = new ArrayList<>();
        List<UserIncorrectAnswers> incorrectAnswersList = new ArrayList<>();
        User user = userService.getUserById(answersDTO.getUserId());
        for (AnswerDataDTO answerDataDTO : answersDTO.getUserIncorrectAnswersList()) {
            UserIncorrectAnswers userIncorrectAnswers = new UserIncorrectAnswers();
            userIncorrectAnswers.setUser(user);
            userIncorrectAnswers.setType(answerDataDTO.getType());
            userIncorrectAnswers.setObjectId(answerDataDTO.getObjectId());
            incorrectAnswersList.add(userIncorrectAnswers);
        }
        saveUniqueIncorrectAnswers(answersDTO.getUserId(), incorrectAnswersList);
        addPercentageAndExperience(answersDTO, quizRewardsDTOList);
        addItem(answersDTO, quizRewardsDTOList);
        return quizRewardsDTOList;
    }

    private int cuntPercentage(int correct,int incorrect) {
        int sum = correct + incorrect;
        if(sum == 0) return 0;
        double percentage = (double) correct / sum * 100;
        return (int) percentage;
    }
    private int countExperience(int numCorrectAnswer, int percentage) {
        double sum = numCorrectAnswer * 2;
        double experience = sum * (percentage / 100.0);
        return (int) Math.floor(experience);
    }

}
