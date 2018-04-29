package gr.mgourlis.draftnationallity.service;

import gr.mgourlis.draftnationallity.dto.ImportQuestionDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IImportExportService {

    public String importQuestions(List<ImportQuestionDTO> importQuestions);
    public String exportQuestions();

}
