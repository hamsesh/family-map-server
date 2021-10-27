package handler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Handler {
    static final String DB_PATH = "sql" + File.separator + "prod-db.db";
    protected String readInputStream(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }

        return buf.toString(String.valueOf(StandardCharsets.UTF_8));
    }

    protected void writeResponseBody(OutputStream os, String data) throws IOException {
        os.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
