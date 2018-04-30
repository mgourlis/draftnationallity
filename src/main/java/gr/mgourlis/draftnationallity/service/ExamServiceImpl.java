package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.model.*;
import gr.mgourlis.draftnationallity.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service("ExamService")
public class ExamServiceImpl implements IExamService {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    IExamSettingService examSettingService;

    @Autowired
    IQuestionService questionService;

    @Override
    public Exam getOne(long id) {
        return examRepository.findExamByIdAndDeleted(id,false);
    }

    @Override
    public Exam findByUID(String uID) {
        return examRepository.findExamByUIDAndDeleted(uID,false);
    }

    @Override
    public Exam findByLocalFileNumber(String localFileNumber) {
        return examRepository.findExamByLocalFileNumberAndDeleted(localFileNumber,false);
    }

    @Override
    public Exam findByFileNumber(String fileNumber) {
        return examRepository.findExamByFileNumberAndDeleted(fileNumber,false);
    }

    @Override
    public List<Exam> findAll() {
        return examRepository.findExamsByDeleted(false);
    }

    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return examRepository.findExamsByDeleted(false,pageable);
    }

    @Override
    public List<Exam> findExamsByUser(String email) {
        return examRepository.findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(email,false);
    }

    @Override
    public Page<Exam> findExamsByUser(String email, Pageable pageable) {
        return examRepository.findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(email,false,pageable);
    }

    @Override
    public String createExam(Exam exam, long examSettingId) {
        ExamSetting examSetting = examSettingService.getOne(examSettingId);
        if(examSetting != null) {
            if(exam.getForeas().equals("") || exam.getLocalFileNumber().equals("")){
                throw new IllegalArgumentException("Foreas or Local File Number can not be empty");
            }
            if(exam.isLanguageExemption()){
                if(exam.getLanguageExemptionNotes().equals("")){
                    throw new IllegalArgumentException("Notes on language exception can not be empty");
                }
            }
            if(exam.isDeaf()){
                if(exam.getLanguageExemptionNotes().equals("")){
                    throw new IllegalArgumentException("Notes on deafness can not be empty");
                }
            }
            exam.setStatus(ExamStatus.PENDING);
            exam.setuID(Long.toString(Calendar.getInstance(TimeZone.getTimeZone("Europe/Athens")).getTimeInMillis()));
            exam.setExamSetting(examSetting);
            List<QuestionCategory> questionCategories = examSetting.getQuestionCategories();
            List<DifficultySetting> difficultySettings = examSetting.getDifficultySettings();
            if(!questionCategories.isEmpty()){
                List<Set<ExamQuestion>> examQuestionsList = new ArrayList<>();
                int questionsSize = questionCategories.size();
                for (QuestionCategory questionCategory : questionCategories) {
                    Set<ExamQuestion> categoryQuestions = new LinkedHashSet<>();
                    int questionCategorySize = ((int) examSetting.getNumOfQuestions() / questionsSize) + 1;
                    for (DifficultySetting difficultySetting : difficultySettings){
                        int questionsCategoryDifficultySize = (int)((questionCategorySize * difficultySetting.getPercentage())/100.0);
                        Set<Question> randQuestions = questionService.getRandomQuestionsByCategoryAndDifficulty
                                (questionCategory,difficultySetting.getDifficulty(),questionsCategoryDifficultySize);
                        for (Question question : randQuestions) {
                            ExamQuestion examQuestion = new ExamQuestion();
                            examQuestion.setSortNumber(categoryQuestions.size());
                            examQuestion.setQuestion(question);
                            categoryQuestions.add(examQuestion);
                        }
                    }
                    examQuestionsList.add(categoryQuestions);
                }
                int categoryIndex = questionCategories.size() -1;
                while ((examSetting.getNumOfQuestions() - countExamQuestions(examQuestionsList)) < 0){
                    if(categoryIndex < 0 )
                        categoryIndex = questionCategories.size() -1;
                    examQuestionsList.get(categoryIndex).remove(examQuestionsList.get(categoryIndex).toArray()[examQuestionsList.get(categoryIndex).size() -1]);
                }
                int count = 0;
                List<ExamQuestion> allExamQuestions = new ArrayList<>();
                for (Set<ExamQuestion> examQuestions :examQuestionsList) {
                    for (ExamQuestion examQuestion : examQuestions) {
                        examQuestion.setSortNumber(count);
                        allExamQuestions.add(examQuestion);
                    }
                }
                exam.setExamQuestions(allExamQuestions);
                examRepository.save(exam);
                return exam.getuID();
            }else   {
                throw new IllegalArgumentException("Something went wrong with Exam Setting");
            }
        }else {
            throw new EntityNotFoundException("Exam Setting does not exist");
        }
    }

    @Override
    public void setExamAnswers(Exam exam, List<ExamQuestion> examQuestions) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.PENDING)) {
                if(examQuestions.isEmpty()) {
                    for (ExamQuestion examQuestion : examQuestions) {
                        if(examQuestion.getAnswer().equals("")){
                            throw new IllegalArgumentException("Answer on Question " + examQuestion.getSortNumnber() + " can not be empty");
                        }
                    }
                    exam.setAnsweredDate(new Date());
                    exam.setStatus(ExamStatus.ANSWERED);
                    exam.setExamQuestions(examQuestions);
                    examRepository.save(exam);
                }else {
                    throw new IllegalArgumentException("Ratings can not be empty");
                }
            } else {
                throw new IllegalArgumentException("Can not set status to answered");
            }
        }else{
            throw new EntityNotFoundException("Exam does not exists");
        }
    }

    @Override
    public void rateExam(Exam exam, List<ExamRating> examRatings) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.ANSWERED)) {
                if(examRatings.isEmpty()) {
                    exam.setRatedDate(new Date());
                    exam.setExamRatings(examRatings);
                    exam.setStatus(ExamStatus.RATED);
                    examRepository.save(exam);
                }else {
                    throw new IllegalArgumentException("Ratings can not be empty");
                }
            } else {
                throw new IllegalArgumentException("Can not set status to rated");
            }
        }else{
            throw new EntityNotFoundException("Exam does not exists");
        }
    }

    @Override
    public void validateExam(Exam exam) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.RATED)) {
                exam.setValidatedDate(new Date());
                exam.setStatus(ExamStatus.VALIDATED);
                examRepository.save(exam);
            } else {
                throw new IllegalArgumentException("Can not set status to validated");
            }
        }else{
            throw new EntityNotFoundException("Exam does not exists");
        }
    }

    @Override
    public void delete(long id) {
        Exam exam = examRepository.findExamByIdAndDeleted(id,false);
        if(exam != null){
            exam.setDeleted(true);
            for (ExamQuestion examQuestion : exam.getExamQuestions()) {
                examQuestion.setDeleted(true);
            }
            for (CommitteeMember committeeMember : exam.getCommitteeMembers()) {
                committeeMember.setDeleted(true);
            }
            for (ExamRating examRating : exam.getExamRatings()) {
                examRating.setDeleted(true);
            }
            examRepository.save(exam);
        }else{
            throw new EntityNotFoundException("Invalid Exam");
        }
    }

    private int countExamQuestions(List<Set<ExamQuestion>> examQuestionsList){
        int count = 0;
        for (Set<ExamQuestion> examQuestions :examQuestionsList) {
            count += examQuestions.size();
        }
        return count;
    }
}
