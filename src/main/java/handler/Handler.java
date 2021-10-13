package handler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Handler {
    protected String readInputStream(InputStream is) throws HandlerException, UnsupportedEncodingException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            for (int result = bis.read(); result != -1; result = bis.read()) {
                buf.write((byte) result);
            }
        }
        catch (IOException e) {
            throw new HandlerException("Error reading request input stream");
        }
// StandardCharsets.UTF_8.name() > JDK 7
        return buf.toString(StandardCharsets.UTF_8);
    }
}
