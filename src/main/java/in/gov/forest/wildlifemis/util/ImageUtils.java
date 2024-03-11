package in.gov.forest.wildlifemis.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    //Code for file compressing
    private static final String STORAGE_DIRECTORY = "E:\\WildlifeMIS(13-02-2024)\\forest-wildlife-mis(15-02-2024)\\wildlife-mis\\compress";

    @PostMapping("/uploadAndCompress")
    public String uploadAndCompressImage(@RequestParam("imageFile") MultipartFile imageFile) {
        File storageDir = new File(STORAGE_DIRECTORY);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File compressedFile = new File(storageDir, Objects.requireNonNull(imageFile.getOriginalFilename()));
            BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            Thumbnails.of(imageFile.getInputStream())
                    .size(originalWidth/2,originalHeight/2) // Specify the dimensions you want for the compressed image
                    .outputQuality(0.9) // Specify the desired quality (0.0 to 1.0)
                    .toFile(compressedFile);
            return "Image uploaded, compressed, and stored successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/download1/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) {
        try {
            File compressedFile = new File(STORAGE_DIRECTORY, imageName);
            if (!compressedFile.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Decompress the image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(compressedFile)
                    .scale(1)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);

            byte[] imageData = outputStream.toByteArray();
            ByteArrayResource resource = new ByteArrayResource(imageData);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    //end



    //Another logic to compress the file
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }



    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

}
