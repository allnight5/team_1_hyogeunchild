package com.sparta.team_1_hyogeunchild.business.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileService {

//    @Value("${upload.image.location}")
//    private String location;
    private final String location = "C:\\Users\\laanc\\Desktop\\Infrun\\team_1_hyogeunchild\\src\\main\\resources\\static\\image\\";
    public void upload(MultipartFile file, String fineName){
        try{
            file.transferTo(new File(location+fineName + ".jpg"));
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
    public void delete(String filename){
        new File(location + filename).delete();
    }
}
