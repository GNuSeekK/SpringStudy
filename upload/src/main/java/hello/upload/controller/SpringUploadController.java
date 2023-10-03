package hello.upload.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/spring")
@Controller
public class SpringUploadController {

    @Value("${file.dir}") // application.properties의 file.dir
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(
        @RequestParam String itemName,
        @RequestParam MultipartFile file,
        HttpServletRequest request) {

        log.info("request={}", request);
        log.info("itemName={}", itemName);
        log.info("multipartFile={}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullPath);
            try {
                file.transferTo(new File(fullPath)); // 파일 저장
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "upload-form";

    }
}
