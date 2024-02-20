//package in.gov.forest.wildlifemis.notification;
//
//import jakarta.servlet.ServletContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.ServletContextAware;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.IOException;
//
//@Service
//public class FileStorageService implements ServletContextAware {
//
//    private ServletContext servletContext;
//
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        this.servletContext = servletContext;
//    }
//
//    public void saveFile(MultipartFile file, String fileName) throws IOException {
//        String deploymentDirectory = servletContext.getRealPath("/");
//        File uploadDir = new File(deploymentDirectory, "uploads");
////        if (!uploadDir.exists()) {
////            uploadDir.mkdirs(); // Create directory if it doesn't exist
////        }
////        File destFile = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
////        file.transferTo(destFile);
//
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs(); // Create directory if it doesn't exist
//        }
//    }
//
//    public void saveFileDetailsToDatabase(String fileName) {
//        // Save file details to database here
//    }
//}
//
