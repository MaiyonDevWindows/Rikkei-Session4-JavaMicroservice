package com.maiyon.config.initialize;

import com.maiyon.model.entity.Category;
import com.maiyon.model.entity.enums.ActiveStatus;
import com.maiyon.repository.CategoryRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategorySamples {
    @Autowired
    private CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategorySamples.class);
    public void initCategorySampleData(){
        if(categoryRepository.count() == 0){
            List<Category> categories = readCategoryCsv();
            if(!categories.isEmpty())
                categoryRepository.saveAll(categories);
        }
    }
    public List<Category> readCategoryCsv() {
        String categoryFile = String.join(File.separator, DataFilePaths.getCSVDataFilePath(), "category.csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(categoryFile))) {
            List<String[]> records = csvReader.readAll();
            var categories = new ArrayList<Category>();
            records.forEach(record ->{
                Category category = Category.builder()
                        .categoryId(Long.valueOf(record[0]))
                        .categoryName(record[1])
                        .description(record[2])
                        .categoryStatus(ActiveStatus.valueOf(record[3]))
                        .build();
                categories.add(category);
            });
            return categories;
        } catch (IOException | CsvException e) {
            logger.error("Error when reading .csv file to init category sample data");
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("Undetermined error " + e);
        }
        return new ArrayList<>();
    }
}
