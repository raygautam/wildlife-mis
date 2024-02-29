package in.gov.forest.wildlifemis.util;

import in.gov.forest.wildlifemis.exception.Error;
import in.gov.forest.wildlifemis.exception.MaxUploadSizeExceededException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.List;

@Component
public class CustomMultipartResolver extends StandardServletMultipartResolver {

//    @Value("${spring.servlet.multipart.max-file-size}")
//    Long maxFileSize;
//    @Value("${pdfUploadSize}")
//    String pdfUploadSize;
//    @Value("${imageUploadSize}")
//    String imageUploadSize;

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return super.isMultipart(request);
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
            MultipartHttpServletRequest parsingResult = super.resolveMultipart(request);
            for (List<MultipartFile> files : parsingResult.getMultiFileMap().values()) {
                for (MultipartFile file : files) {
                    String filename = file.getOriginalFilename();
                    if (filename != null) {
                        String extension = filename.substring(filename.lastIndexOf(".") + 1);
                        long maxSize = getMaxFileSize(extension);
                        if (file.getSize() > maxSize) {
                            throw new MaxUploadSizeExceededException("", new Error("","Maximum upload size of "+maxSize+" MB provided size "+file.getSize()));
                        }
                    }
                }
            }
            return parsingResult;
        } else {
            return super.resolveMultipart(request);
        }
    }


    private long getMaxFileSize(String extension) {
        return switch (extension.toLowerCase()) {
            case "pdf" -> 5*1024*1024;
            case "jpg", "jpeg", "png", "gif" -> 2*1024*1024;
            default -> 5*1024*1024L; // Default size limit if extension is not recognized
        };
    }

    /**
     * This method was supported in old versions ans present inside web.commons
     * org.springframework.web.multipart.commons.CommonsMultipartyResolver
     **/

    //    @Override
//    public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
//        String contentType = request.getContentType();
//        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
//            MultipartParsingResult parsingResult = super.parseRequest(request);
//            for (List<MultipartFile> files : parsingResult.getMultiFileMap().values()) {
//                for (MultipartFile file : files) {
//                    String filename = file.getOriginalFilename();
//                    if (filename != null) {
//                        String extension = filename.substring(filename.lastIndexOf(".") + 1);
//                        long maxSize = getMaxFileSize(extension);
//                        if (file.getSize() > maxSize) {
//                            throw new MaxUploadSizeExceededException(maxSize);
//                        }
//                    }
//                }
//            }
//            return parsingResult;
//        } else {
//            return super.parseRequest(request);
//        }
//    }

}
