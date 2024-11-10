package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.request.AnswerDataDTO;
import com.japanese.lessons.dtos.request.AnswersDTO;
import com.japanese.lessons.dtos.response.QuizRewardsDTO;
import com.japanese.lessons.models.Image;
import com.japanese.lessons.models.Manga;
import com.japanese.lessons.models.Rewards;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.User.User;
import com.japanese.lessons.models.User.UserIncorrectAnswers;
import com.japanese.lessons.models.lesson.exercise.Answer;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.models.lesson.mangaExercise.*;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import com.japanese.lessons.service.ImagesService;
import com.japanese.lessons.service.RewardsService;
import com.japanese.lessons.service.UserIncorrectAnswersService;
import com.japanese.lessons.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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

    private void addManga(Manga manga, List<MangaContentDTO> contenetList) {
        MangaDetailsDTO mangaDetailsDTO = new MangaDetailsDTO(manga.getId(), manga.getName(),manga.getStartDialog());
        contenetList.add(new MangaContentDTO(0, "manga", mangaDetailsDTO));
    }

    private void addImage(Manga manga, List<MangaContentDTO> contentList) {
        List<Image> responseImageList = imagesService.getImagesByObjectId(ETargetType.MANGA, manga.getId());
        List<ImageMangaDTO> imagesWithTextList = new ArrayList<>();

        for (Image image : responseImageList) {
            List<Text> textList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.IMAGE ,image.getId());

            if (!textList.isEmpty()) {
                Text text = textList.get(0);
                imagesWithTextList.add(new ImageMangaDTO(image, text));
            }
        }
        List<ImageMangaDTO> centerImages = new ArrayList<>();
        List<ImagesHasRightLeftDTO> rightLeftImages = new ArrayList<>();

        for (ImageMangaDTO imageManga : imagesWithTextList) {
            String layoutPosition = imageManga.getImage().getLayoutPosition();

            switch (layoutPosition) {
                case "center":
                    centerImages.add(imageManga);
                    break;

                case "right":
                    rightLeftImages.add(new ImagesHasRightLeftDTO(imageManga, null));
                    break;

                case "left":
                    if (!rightLeftImages.isEmpty() && rightLeftImages.get(rightLeftImages.size() - 1).getImageLeft() == null) {
                        rightLeftImages.get(rightLeftImages.size() - 1).setImageLeft(imageManga);
                    } else {
                        throw new IllegalArgumentException("Error: unmatched 'left' image without corresponding 'right' image.");
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unknown position type: " + layoutPosition);
            }
        }
        centerImages.forEach(imageMangaDTO ->
                contentList.add(new MangaContentDTO(imageMangaDTO.getImage().getQueue(), "image", imageMangaDTO))
        );
        rightLeftImages.forEach(imagesPair ->
                contentList.add(new MangaContentDTO(imagesPair.getImageRight().getImage().getQueue(), "images_together", imagesPair))
        );
    }

    private void addMangaDialogue(Manga manga, List<MangaContentDTO> contenetList) {
        List<Text> textList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.MANGA, manga.getId());
        textList.forEach(dialogue -> contenetList.add(
                new MangaContentDTO(dialogue.getQueue(), "dialogue", dialogue)
        ));
    }

    private void addQuestion(Manga manga, List<MangaContentDTO> contenetList) {
        List<Question> questionList = questionService.getAllQuestionsByTypeAndObjectId(ETargetType.MANGA, manga.getId());
        questionList.forEach(question -> {
            MangaContentDTO questionContent = new MangaContentDTO(question.getQueue(), "question", question);
            contenetList.add(questionContent);
            question.getAnswerMangas().forEach(answer -> {
                new AnswerMangaDTO(
                        answer.getId(),
                        answer.getAnswer_hiragana_katakana_kanji(),
                        answer.getAnswer_hiragana_katakana(),
                        answer.getAnswer_romanji()
                );
            });
        });
    }

    public List<MangaContentDTO> sortMangaByQueue(Long lessonId) {
        Manga manga = getMangaByLessonID(lessonId);
        List<MangaContentDTO> contentList = new ArrayList<>();
        addManga(manga, contentList);
        addImage(manga, contentList);
        addMangaDialogue(manga, contentList);
        addQuestion(manga, contentList);

        return contentList.stream()
                .sorted(Comparator.comparingInt(MangaContentDTO::getQueue))
                .collect(Collectors.toList());
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
        List<Rewards> rewardsMangaList = rewardsService.getRewardsByTarget(ETargetType.MANGA, answersDTO.getMangaId());
        for (Rewards rewards : rewardsMangaList) {
            List<Image> imageList = imagesService.getImagesByObjectId(ETargetType.REWARD, rewards.getId());
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
