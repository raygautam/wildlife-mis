package in.gov.forest.wildlifemis.auditTrail;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

class CachingServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream inputStream;

    public CachingServletInputStream(byte[] cachedRequestBody) {
        inputStream = new ByteArrayInputStream(cachedRequestBody);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // Not implemented for simplicity
    }
}
