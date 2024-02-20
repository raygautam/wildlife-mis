package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import in.gov.forest.wildlifemis.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Handler;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter{

    @Value("${file.upload.directory}")
    private File fileUploadDirectory;
    @Override
    public ApiResponse<?> save(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(new Error("Please select a file to upload"))
                    .build();
        }
        if (!fileUploadDirectory.exists()) {
            try {
                boolean created = fileUploadDirectory.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                } else {
                    throw new IOException("Failed to create directory.");
                }
            } catch (IOException e) {
                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }
//        String fileExtension = file.getContentType();
        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis();
//        String filePath = fileUploadDirectory + "/" + randomName + "." + fileExtension;
//        File file1=new File(fileUploadDirectory,randomName);
//        Files.write(Path.of(filePath), file.getBytes());
        File destFile = new File(fileUploadDirectory + File.separator + randomName);
        file.transferTo(destFile);
        return ApiResponse.builder()
                .data(
                        destFile
                )
                .build();
    }

    @Override
    public ApiResponse<?> download(String fileName) {
        Path path= Paths.get(String.valueOf(fileUploadDirectory), fileName);
        Resource resource=new FileSystemResource(path);
        return ApiResponse.builder()
                .data(
                        resource
                )
                .build();
    }
}
