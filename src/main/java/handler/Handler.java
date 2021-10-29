package handler;

import request.RequestException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

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
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(data);
        sw.flush();
    }

    protected String parseID(String uri) throws RequestException {
        Scanner scnr = new Scanner(uri).useDelimiter("/");
        ArrayList<String> params = new ArrayList<>();
        while (scnr.hasNext()) {
            params.add(scnr.next());
        }
        if (params.size() > 2) {
            throw new RequestException("Error: Too many parameters in URI");
        }
        else if (params.size() < 2) {
            return null;
        }
        return params.get(1);
    }
}
