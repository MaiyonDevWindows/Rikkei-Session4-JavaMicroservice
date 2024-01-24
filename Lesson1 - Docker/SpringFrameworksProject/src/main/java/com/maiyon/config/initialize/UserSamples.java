package com.maiyon.config.initialize;

import com.maiyon.model.entity.Role;
import com.maiyon.model.entity.User;
import com.maiyon.model.entity.enums.RoleName;
import com.maiyon.repository.RoleRepository;
import com.maiyon.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserSamples {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserSamples.class);
    public void initUserSampleData(){
        if(userRepository.count() == 0){
            List<User> users = readUserCsv();
            if(!users.isEmpty())
                userRepository.saveAll(users);
        }
    }
    public List<User> readUserCsv() {
        String categoryFile = String.join(File.separator, DataFilePaths.getCSVDataFilePath(), "user.csv");
        try (CSVReader csvReader = new CSVReader(new FileReader(categoryFile))) {
            List<String[]> records = csvReader.readAll();
            var users = new ArrayList<User>();
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN));
            records.forEach(record -> {
                User user = User.builder()
                        .userId(Long.valueOf(record[0]))
                        .username(record[1])
                        .password(new BCryptPasswordEncoder().encode(record[2]))
                        .fullName(record[3])
                        .email(record[4])
                        .phone(record[5])
                        .address(record[6])
                        .roles(roles)
                        .build();
                users.add(user);
            });
            return users;
        } catch (IOException | CsvException e) {
            logger.error("Error when reading .csv file to init category sample data");
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error("Undetermined error " + e);
        }
        return new ArrayList<>();
    }
}
