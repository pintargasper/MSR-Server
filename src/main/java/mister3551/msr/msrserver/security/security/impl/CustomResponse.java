package mister3551.msr.msrserver.security.security.impl;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

public interface CustomResponse {

    default void response(final HttpServletResponse httpServletResponse, String response) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json");

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write((response).getBytes());
        outputStream.flush();
    }
}