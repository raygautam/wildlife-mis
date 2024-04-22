package in.gov.forest.wildlifemis.auditTrail;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//@Getter
//public class RequestBodyCachingWrapper extends HttpServletRequestWrapper {
//
//    private final String requestBody;
//
//    public RequestBodyCachingWrapper(HttpServletRequest request) throws IOException {
//        super(request);
//        requestBody = readRequestBody(request);
//    }
//
//    private String readRequestBody(HttpServletRequest request) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        }
//        return stringBuilder.toString();
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        byte[] requestBodyBytes = requestBody.getBytes(getCharacterEncoding());
//        return new CachingServletInputStream(requestBodyBytes);
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
//    }
//
//
//}


public class RequestBodyCachingWrapper extends HttpServletRequestWrapper {

    private final String requestBody;

    public RequestBodyCachingWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = readRequestBody(request);
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

//private String readRequestBody(HttpServletRequest request) throws IOException {
//    StringBuilder stringBuilder = new StringBuilder();
//    try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//    }
//    return stringBuilder.toString();
//}

//    public String readRequestBody(HttpServletRequest request) throws IOException {
//        StringBuilder requestBody = new StringBuilder();
//        try (ServletInputStream inputStream = request.getInputStream()) {
//            int length;
//            byte[] buffer = new byte[4096]; // Choose an appropriate buffer size
//            while ((length = inputStream.read(buffer)) != -1) {
//                requestBody.append(new String(buffer, 0, length));
//            }
//        }
//        return requestBody.toString();
//    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8); // Or use the character encoding appropriate for your application
        return new CachingServletInputStream(requestBodyBytes);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8)); // Or use the character encoding appropriate for your application
    }

    public String getRequestBody() {
        return requestBody;
    }
}
