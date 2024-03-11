package in.gov.forest.wildlifemis.auditTrail;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    @Override
    public ServletInputStream getInputStream() throws IOException {
        byte[] requestBodyBytes = requestBody.getBytes(getCharacterEncoding());
        return new CachingServletInputStream(requestBodyBytes);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }


    public String getRequestBody() {
        return requestBody;
    }

}
