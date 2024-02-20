package in.gov.forest.wildlifemis.notification;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationServiceInter{
    @Override
    public ApiResponse<?> save() {
//        for (String file : ) {
//            String[] temp = file.split(",");
//            if (temp.length < 2) {
//                throw new IllegalArgumentException("Error: Invalid file string format for file: " + file);
//            }
//            String fileExtension = temp[0].split(";")[0].split("/")[1];
//            String base64Data = temp[1];
//            byte[] fileByte = Base64.getDecoder().decode(base64Data);
//            String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis();
//            if (!fileUploadDirectory.exists()) {
//                try {
//                    boolean created = fileUploadDirectory.mkdirs();
//                    if (created) {
//                        log.info("Directory created successfully.");
//                    } else {
//                        throw new IOException("Failed to create directory.");
//                    }
//                } catch (IOException e) {
//                    System.out.println("Failed to create directory: " + e.getMessage());
//                }
//            }
//            String filePath = fileUploadDirectory + "/" + randomName + "." + fileExtension;
//            IncidentImage incidentImage = new IncidentImage();
//            Files.write(Path.of(filePath), fileByte);
//            incidentImage.setRandomFilename(randomName);
//            incidentImage.setFileType(fileExtension);
//            incidentImage.setIncidentReport(incidentReport);
//            incidentImage.setFilePath(randomName+"."+fileExtension);
//            try {
//                incidentImageRepository.save(incidentImage);
//            } catch (Exception e) {
//                throw new DataInsertionException("Error saving incident image to the database.", e);
//            }
//        }
        return null;
    }
}
