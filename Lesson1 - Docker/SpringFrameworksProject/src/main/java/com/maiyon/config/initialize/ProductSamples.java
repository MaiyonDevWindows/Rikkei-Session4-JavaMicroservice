package com.maiyon.config.initialize;

import com.maiyon.model.entity.Product;
import com.maiyon.repository.CategoryRepository;
import com.maiyon.repository.ProductRepository;
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
public class ProductSamples {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductSamples.class);
    public void initProductSampleData(){
        if(productRepository.count() == 0){
            List<Product> products = readProductCsv();
            if(!products.isEmpty())
                productRepository.saveAll(products);
        }
    }
    public List<Product> readProductCsv() {
        String productFile = String.join(File.separator, DataFilePaths.getCSVDataFilePath(), "product.csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(productFile))) {
            List<String[]> records = csvReader.readAll();
            var products = new ArrayList<Product>();
            records.forEach(record ->{
                Product product = Product.builder()
                    .productId(Long.valueOf(record[0]))
                    .productName(record[1])
                    .unitPrice(Double.valueOf(record[2]))
                    .stockQuantity(Integer.valueOf(record[3]))
                    .image(record[4])
                    .description(record[5])
                    .category(categoryRepository.findCategoryByCategoryId(Long.valueOf(record[6])))
                    .build();
                products.add(product);
            });
            return products;
        } catch (IOException | CsvException e) {
            logger.error("Error when reading .csv file to init category sample data");
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("Undetermined error " + e);
        }
        return new ArrayList<>();
    }
}
