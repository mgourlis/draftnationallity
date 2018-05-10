package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.dto.CommitteeMemberDTO;
import gr.mgourlis.draftnationallity.dto.EditExamDTO;
import gr.mgourlis.draftnationallity.dto.ExamQuestionDTO;
import gr.mgourlis.draftnationallity.dto.ExamRatingDTO;
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

    @Autowired
    IExamRatingTypeService examRatingTypeService;

    @Autowired
    IExamRatingMarkService examRatingMarkService;

    @Override
    public Exam getOne(long id) {
        return examRepository.findExamByIdAndDeleted(id,false);
    }

    @Override
    public Exam getOneByUser(long id, String email) {
        return examRepository.findExamByIdAndCreatedByAndDeleted(id,email,false);
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
    public List<Exam> findExamsByLocalFileNumberAndUser(String localFileNumber, String email) {
        return examRepository.findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, email,false);
    }

    @Override
    public Page<Exam> findExamsByLocalFileNumberAndUser(String localFileNumber, String email, Pageable pageable) {
        return examRepository.findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, email,false, pageable);
    }

    @Override
    public List<Exam> findExamsByStatusAndUser(String status, String email) {
        try {
            ExamStatus examStatus = ExamStatus.valueOf(status);
            return examRepository.findExamsByStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(examStatus, email,false);
        } catch (IllegalArgumentException e){
            return examRepository.findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(email, false);
        }
    }

    @Override
    public Page<Exam> findExamsByStatusAndUser(String status, String email, Pageable pageable) {
        try {
            ExamStatus examStatus = ExamStatus.valueOf(status);
            return examRepository.findExamsByStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(examStatus, email,false, pageable);
        } catch (IllegalArgumentException e){
            return examRepository.findExamsByCreatedByAndDeletedOrderByCreatedAtDesc(email,false, pageable);
        }
    }

    @Override
    public List<Exam> findExamsByLocalFileNumberAndStatusAndUser(String localFileNumber, String status, String email) {
        try {
            ExamStatus examStatus = ExamStatus.valueOf(status);
            return examRepository.findExamsByLocalFileNumberContainingAndStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, examStatus, email,false);
        } catch (IllegalArgumentException e){
            return examRepository.findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, email,false);
        }
    }

    @Override
    public Page<Exam> findExamsByLocalFileNumberAndStatusAndUser(String localFileNumber, String status, String email, Pageable pageable) {
        try {
            ExamStatus examStatus = ExamStatus.valueOf(status);
            return examRepository.findExamsByLocalFileNumberContainingAndStatusAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, examStatus, email,false,pageable);
        } catch (IllegalArgumentException e){
            return examRepository.findExamsByLocalFileNumberContainingAndCreatedByAndDeletedOrderByCreatedAtDesc(localFileNumber, email,false, pageable);
        }
    }

    @Override
    public List<Exam> findAllByStatus(ExamStatus status) {
        return examRepository.findExamsByStatusAndDeleted(status,false);
    }

    @Override
    public Page<Exam> findAllByStatus(ExamStatus status, Pageable pageable) {
        return examRepository.findExamsByStatusAndDeleted(status,false, pageable);
    }

    @Override
    public List<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(String localFileNumber, String FileNumber, String uID, ExamStatus status) {
        if(status == ExamStatus.FINALIZED)
            return examRepository.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(localFileNumber, FileNumber, uID, status, ExamStatus.VALIDATED,false);
        else
            return examRepository.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(localFileNumber, FileNumber, uID, status, null,false);
    }

    @Override
    public Page<Exam> findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(String localFileNumber, String FileNumber, String uID, ExamStatus status, Pageable pageable) {
        if(status == ExamStatus.FINALIZED)
            return examRepository.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(localFileNumber, FileNumber, uID, status, ExamStatus.VALIDATED,false, pageable);
        else
            return examRepository.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatusAndDeleted(localFileNumber, FileNumber, uID, status, null,false, pageable);
    }

    @Override
    public String createExam(Exam exam, long examSettingId) throws IllegalArgumentException,EntityNotFoundException{
        ExamSetting examSetting = examSettingService.getOne(examSettingId);
        if(examSetting != null) {
            if(!canGenerateQuestions(examSetting)){
                throw new IllegalArgumentException("Can not generate exam with this Exam Setting.");
            }
            if(exam.getForeas().equals("") || exam.getLocalFileNumber().equals("")){
                throw new IllegalArgumentException("Foreas or Local File Number can not be empty.");
            }
            if(exam.isLanguageExemption()){
                if(exam.getLanguageExemptionNotes().equals("")){
                    throw new IllegalArgumentException("Notes on language exception can not be empty.");
                }
            }else{
                exam.setLanguageExemptionNotes("");
            }
            if(exam.isDeaf()){
                if(exam.getLanguageExemptionNotes().equals("")){
                    throw new IllegalArgumentException("Notes on deafness can not be empty.");
                }
            }else{
                exam.setDeafNotes("");
            }
            exam.setStatus(ExamStatus.PENDING);
            exam.setuID(Long.toString(Calendar.getInstance(TimeZone.getTimeZone("Europe/Athens")).getTimeInMillis()));
            exam.setExamSetting(examSetting);
            exam.setExamQuestions(generateQuestions(examSetting));
            examRepository.save(exam);
            return exam.getuID();
        }else {
            throw new EntityNotFoundException("Exam Setting does not exist.");
        }
    }

    @Override
    public void editExam(Exam exam, EditExamDTO editExamDTO) {
        if(exam.getStatus() == ExamStatus.PENDING || exam.getStatus() == ExamStatus.ANSWERED){
            if(editExamDTO.getForeas().equals("") || editExamDTO.getLocalFileNumber().equals("")){
                throw new IllegalArgumentException("Foreas or Local File Number can not be empty.");
            }
            if(editExamDTO.isLanguageExemption()){
                if(editExamDTO.getLanguageExemptionNotes().equals("")){
                    throw new IllegalArgumentException("Notes on language exception can not be empty.");
                }
            }else{
                editExamDTO.setLanguageExemptionNotes("");
            }
            if(editExamDTO.isDeaf()){
                if(editExamDTO.getDeafNotes().equals("")){
                    throw new IllegalArgumentException("Notes on deafness can not be empty.");
                }
            }else{
                editExamDTO.setDeafNotes("");
            }
            exam.setLocalFileNumber(editExamDTO.getLocalFileNumber().trim());
            exam.setForeas(editExamDTO.getForeas().trim());
            exam.setLanguageExemption(editExamDTO.isLanguageExemption());
            exam.setLanguageExemptionNotes(editExamDTO.getLanguageExemptionNotes().trim());
            exam.setDeaf(editExamDTO.isDeaf());
            exam.setDeafNotes(editExamDTO.getDeafNotes().trim());
            examRepository.save(exam);
        }else{
            throw new IllegalArgumentException("Exam can not be edited at this time.");
        }
    }

    @Override
    public void setExamAnswers(Exam exam, List<ExamQuestionDTO> examQuestionsDTO, boolean finalAnswers) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.PENDING)) {
                if(!examQuestionsDTO.isEmpty()) {
                    for (ExamQuestionDTO examQuestionDTO : examQuestionsDTO) {
                        if(examQuestionDTO.getAnswertext().equals("") && finalAnswers){
                            throw new IllegalArgumentException("Answer on Question " + examQuestionDTO.getSortNumber() + " can not be empty");
                        }
                    }
                    exam.setAnsweredDate(new Date());
                    if(finalAnswers) {
                        exam.setStatus(ExamStatus.ANSWERED);
                    }
                    for (ExamQuestionDTO examQuestionDTO :examQuestionsDTO) {
                        if(!examQuestionDTO.getAnswertext().equals("")) {
                            for (ExamQuestion examQuestion : exam.getExamQuestions()) {
                                if (examQuestionDTO.getId() == examQuestion.getId()) {
                                    Answer answer = new Answer();
                                    answer.setAnswertext(examQuestionDTO.getAnswertext());
                                    examQuestion.setAnswer(answer);
                                }
                            }
                        }
                    }
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
    public void rateExam(Exam exam, List<ExamRatingDTO> examRatingsDTO, boolean finalRatings) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.ANSWERED)) {
                if(!examRatingsDTO.isEmpty()) {
                    List<ExamRating> examRatings = new ArrayList<>();
                    for (ExamRatingDTO examRatingDTO : examRatingsDTO) {
                        ExamRating examRating = new ExamRating();
                        ExamRatingType examRatingType = examRatingTypeService.getOne(examRatingDTO.getRatingTypeId());
                        ExamRatingMark examRatingMark = examRatingMarkService.getOne(examRatingDTO.getRatingMarkId());
                        if(examRatingType != null){
                            if(examRatingType.isLanguageType() == true && exam.isLanguageExemption() == true){
                                throw new IllegalArgumentException("You can not give language rating. The exam has language exemption checked");
                            }
                            if(examRatingMark != null){
                                examRating.setExamRatingType(examRatingType);
                                examRating.setExamRatingMark(examRatingMark);
                                examRating.setRatingNotes(examRatingDTO.getRatingNotes());
                                examRatings.add(examRating);
                            }else{
                                throw new EntityNotFoundException("Exam rating mark, not found.");
                            }
                        }else{
                            throw new EntityNotFoundException("Exam rating type, not found.");
                        }
                    }
                    exam.setRatedDate(new Date());
                    exam.getExamRatings().clear();
                    for (ExamRating examRating:examRatings) {
                        exam.getExamRatings().add(examRating);
                    }
                    if(finalRatings)
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
    public void finalizeExam(Exam exam, List<CommitteeMemberDTO> committeeMembersDTO, boolean finalize) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.RATED)) {
                if(!committeeMembersDTO.isEmpty()) {
                    List<CommitteeMember> committeeMembers = new ArrayList<>();
                    int president = 0;
                    int secretary = 0;
                    for(CommitteeMemberDTO committeeMemberDTO : committeeMembersDTO){
                        if(committeeMemberDTO.getName().equals("") || committeeMemberDTO.getLastName().equals("") || committeeMemberDTO.getCommitteeRole() == null){
                            throw new IllegalArgumentException("Committee Members must have a name, a lastname and a role.");
                        }
                        if(committeeMemberDTO.getCommitteeRole() == CommitteeRole.PRESIDENT)
                            president++;
                        if(committeeMemberDTO.getCommitteeRole() == CommitteeRole.SECRETARY)
                            secretary++;
                        CommitteeMember committeeMember = new CommitteeMember();
                        committeeMember.setName(committeeMemberDTO.getName());
                        committeeMember.setLastName(committeeMemberDTO.getLastName());
                        committeeMember.setCommitteeRole(committeeMemberDTO.getCommitteeRole());
                        committeeMembers.add(committeeMember);
                    }
                    if(president != 1 || secretary != 1){
                        throw new IllegalArgumentException("Committee must have one president and one secretary.");
                    }
                    exam.setFinalizedDate(new Date());
                    exam.getCommitteeMembers().clear();
                    for (CommitteeMember committeeMember : committeeMembers) {
                        exam.getCommitteeMembers().add(committeeMember);
                    }
                    if(finalize)
                        exam.setStatus(ExamStatus.FINALIZED);
                    examRepository.save(exam);
                }else {
                    throw new IllegalArgumentException("Committee members can not be empty");
                }
            } else {
                throw new IllegalArgumentException("Can not set status to finalized");
            }
        }else{
            throw new EntityNotFoundException("Exam does not exists");
        }
    }

    @Override
    public void validateExam(Exam exam, String fileNumber, String email, boolean finalValidation) {
        if(getOne(exam.getId()) != null) {
            if (exam.getStatus().equals(ExamStatus.FINALIZED)) {
                exam.setFileNumber(fileNumber);
                if (finalValidation) {
                    exam.setValidationUser(email);
                    exam.setValidatedDate(new Date());
                    exam.setStatus(ExamStatus.VALIDATED);
                }
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

    private boolean canGenerateQuestions(ExamSetting examSetting){
        List<QuestionCategory> questionCategories = examSetting.getQuestionCategories();
        List<DifficultySetting> difficultySettings = examSetting.getDifficultySettings();
        int questionsSize = questionCategories.size();
        if(questionCategories.isEmpty())
            return false;
        if(difficultySettings.isEmpty())
            return false;
        for (QuestionCategory category : questionCategories) {
            int questionCategorySize = ((int) examSetting.getNumOfQuestions() / questionsSize) + 1;
            for (DifficultySetting difficultySetting: difficultySettings) {
                int questionsCategoryDifficultySize = (int)((questionCategorySize * difficultySetting.getPercentage())/100.0);
                int qnum = questionService.countQuestionsByQuestionCategoryNameAndDifficultyLevelNumber(category.getName(),difficultySetting.getDifficulty().getLevelNumber());
                if(questionsCategoryDifficultySize > qnum){
                    return false;
                }
            }
        }
        return true;
    }

    private List<ExamQuestion> generateQuestions(ExamSetting examSetting){
        List<QuestionCategory> questionCategories = examSetting.getQuestionCategories();
        List<DifficultySetting> difficultySettings = examSetting.getDifficultySettings();
        if(!questionCategories.isEmpty()) {
            List<Set<ExamQuestion>> examQuestionsList = new ArrayList<>();
            int questionsSize = questionCategories.size();
            for (QuestionCategory questionCategory : questionCategories) {
                Set<ExamQuestion> categoryQuestions = new LinkedHashSet<>();
                int questionCategorySize = ((int) examSetting.getNumOfQuestions() / questionsSize) + 1;
                for (DifficultySetting difficultySetting : difficultySettings) {
                    int questionsCategoryDifficultySize = (int) ((questionCategorySize * difficultySetting.getPercentage()) / 100.0);
                    Set<Question> randQuestions = questionService.getRandomQuestionsByCategoryAndDifficulty
                            (questionCategory, difficultySetting.getDifficulty(), questionsCategoryDifficultySize);
                    for (Question question : randQuestions) {
                        ExamQuestion examQuestion = new ExamQuestion();
                        examQuestion.setSortNumber(categoryQuestions.size());
                        examQuestion.setQuestion(question);
                        categoryQuestions.add(examQuestion);
                    }
                }
                examQuestionsList.add(categoryQuestions);
            }
            int categoryIndex = questionCategories.size() - 1;
            while ((examSetting.getNumOfQuestions() - countExamQuestions(examQuestionsList)) < 0) {
                if (categoryIndex < 0)
                    categoryIndex = questionCategories.size() - 1;
                examQuestionsList.get(categoryIndex).remove(examQuestionsList.get(categoryIndex).toArray()[examQuestionsList.get(categoryIndex).size() - 1]);
            }
            int count = 1;
            List<ExamQuestion> allExamQuestions = new ArrayList<>();
            for (Set<ExamQuestion> examQuestions : examQuestionsList) {
                for (ExamQuestion examQuestion : examQuestions) {
                    examQuestion.setSortNumber(count);
                    allExamQuestions.add(examQuestion);
                    count++;
                }
            }
            Collections.sort(allExamQuestions);
            return allExamQuestions;
        }else{
            throw new IllegalArgumentException("Failed two generate questions with this Exam Setting");
        }
    }
}
