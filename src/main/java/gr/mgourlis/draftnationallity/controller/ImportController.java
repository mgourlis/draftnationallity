package gr.mgourlis.draftnationallity.controller;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import gr.mgourlis.draftnationallity.dto.ImportQuestionDTO;
import gr.mgourlis.draftnationallity.service.IImportExportService;
import gr.mgourlis.draftnationallity.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/import")
public class ImportController {

    @Autowired
    IStorageService storageService;

    @Autowired
    IImportExportService importExportService;

    @RequestMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "admin/upload/uploadForm";
    }

    @PostMapping("/")
    public ModelAndView handleImport(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        storageService.store(file);
        File importFile = new File(storageService.load(file.getOriginalFilename()).toString());
        CsvSchema bootstrapSchema = CsvSchema.builder()
                    .addColumn("shortname", CsvSchema.ColumnType.STRING)
                    .addColumn("questiontext", CsvSchema.ColumnType.STRING)
                    .addColumn("questionCategory", CsvSchema.ColumnType.STRING)
                    .addColumn("questionDifficultyLevel", CsvSchema.ColumnType.STRING)
                    .addColumn("questionDifficultyLevelNumber", CsvSchema.ColumnType.NUMBER)
                    .setUseHeader(true)
                    .build();
        CsvMapper mapper = new CsvMapper();
        try (MappingIterator<ImportQuestionDTO> readValues = mapper.reader(ImportQuestionDTO.class).with(bootstrapSchema).readValues(importFile)){
            List<ImportQuestionDTO> importQuestions = readValues.readAll();
            String errors = importExportService.importQuestions(importQuestions);
            if(!errors.equals("")){
                importFile.delete();
                modelAndView.addObject("errorMessageBox", errors);
                modelAndView.setViewName("admin/upload/uploadForm");
                return modelAndView;
            }
        } catch (Exception e) {
            importFile.delete();
            modelAndView.addObject("errorMessageBox","Something went wrong");
            modelAndView.setViewName("admin/upload/uploadForm");
            return modelAndView;
        }
        importFile.delete();


        redirectAttributes.addFlashAttribute("successMessageBox",
                "You successfully imported " + file.getOriginalFilename() + "!");
        modelAndView.setViewName("redirect:/admin/question/");
        return modelAndView;
    }

}
