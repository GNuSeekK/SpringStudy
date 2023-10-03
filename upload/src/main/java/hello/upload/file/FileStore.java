package hello.upload.file;

import hello.upload.domain.UploadFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {

    @Value("${file.dir}")
    private  String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile storeFile = storeFile(multipartFile);
                storeFileResult.add(storeFile);
            }
        }
        return storeFileResult;
    }
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        // 서버에 저장하는 파일명
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);



    }

    private String createStoreFileName(String originalFilename) {
        // image.png
        // 서버에 저장하는 파일명 uuid 사용
        // 확장자 가져오기
        return UUID.randomUUID().toString() + "." + extractExt(originalFilename);
    }

    private String extractExt(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
