package com.maiyon.config.initialize;

import com.maiyon.model.entity.Role;
import com.maiyon.model.entity.enums.RoleName;
import com.maiyon.repository.RoleRepository;
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
public class RoleSamples {
    @Autowired
    private RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(RoleSamples.class);
    public void initRoleSampleData(){
        if(roleRepository.count() == 0){
            List<Role> roles = readRoleCsv();
            if(!roles.isEmpty())
                roleRepository.saveAll(roles);
        }
    }
    public List<Role> readRoleCsv() {
        String roleFile = String.join(File.separator, DataFilePaths.getCSVDataFilePath(), "role.csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(roleFile))) {
            List<String[]> records = csvReader.readAll();
            var roles = new ArrayList<Role>();
            records.forEach(record ->{
                Role role = Role.builder()
                        .roleId(Long.valueOf(record[0]))
                        .roleName(RoleName.valueOf(record[1]))
                        .build();
                roles.add(role);
            });
            return roles;
        } catch (IOException | CsvException e) {
            logger.error("Error when reading .csv file to init role sample data");
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("Undetermined error + e");
        }
        return new ArrayList<>();
    }
}
