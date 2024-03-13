package in.gov.forest.wildlifemis.official;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.domian.Notification;
import in.gov.forest.wildlifemis.domian.Official;
import in.gov.forest.wildlifemis.exception.*;
import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.official.dto.OfficialDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class OfficialServiceImpl implements OfficialServiceInter{
    @Value("${fileUploadDirectoryForOfficial}")
    private String fileUploadDirectoryForOfficial;

    @Autowired
    private  OfficialRepository officialRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<?> save(MultipartFile file, OfficialDTO officialDTO) {
        File uploadFileUrl = new File(fileUploadDirectoryForOfficial);

        if(!Objects.equals(file.getContentType(), "image/png") &&
                !Objects.equals(file.getContentType(), "image/jpg") &&
                !Objects.equals(file.getContentType(), "image/jpeg")){
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(Collections.singletonList(new Error("file","File format not supported "+file.getContentType()+"!!")))
                    .build();
        }

        if (file.isEmpty()) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(Collections.singletonList(new Error("file","Please select a file to upload")))
                    .build();
        }

        if( officialDTO.getName()==null || Objects.equals(officialDTO.getName(), "")){
            throw new BadRequestException("", new Error("name","field is required"));
        }

        if(officialDTO.getDesignation()==null || Objects.equals(officialDTO.getDesignation(), "")){
            throw new BadRequestException("", new Error("designation","field is required"));
        }

        if (!uploadFileUrl.exists()) {
            try {
                boolean created = uploadFileUrl.mkdirs();
                if (created) {
                    log.info("Directory created successfully.");
                }
            } catch (Exception e) {
                throw new IOCustomException("",new Error("","Fail to create directory."+e.getMessage()));
//                System.out.println("Failed to create directory: " + e.getMessage());
            }
        }

        String randomName = RandomStringUtils.randomAlphabetic(6) + System.currentTimeMillis()
                + "."+Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        File destFile = new File(uploadFileUrl + File.separator + randomName);


        try {
            Official official = Official.builder()
                    .name(officialDTO.getName())
                    .designation(officialDTO.getDesignation())
                    .fileName(randomName)
                    .fileUrl(String.valueOf(destFile))
                    .build();

            file.transferTo(destFile);
            // Save notification to the database
            // notificationRepository.save(notification);

            try {
                officialRepository.save(official);
                return ApiResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .data(
                                "File inserted successfully."
                        )
                        .build();

            } catch (DataInsertionException e) {
                throw new DataInsertionException("Fail to add official", new Error("",e.getMessage()));
            }

        } catch (Exception e) {
            // Rollback the file operation if an exception occurs

            //This code is also working
            //Path path=Paths.get(destFile.toURI());
            //Files.deleteIfExists(path);
            boolean deleted = false;
            if (destFile.exists() && destFile.isFile()) {
                deleted = destFile.delete(); // Delete the file
            }
            throw new IOCustomException("",new Error("","Fail to add file to directory."+e.getMessage()));
        }
    }
}
