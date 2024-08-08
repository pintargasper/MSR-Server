package mister3551.msr.msrserver.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path images;

    public FileService() throws IOException {
        this.images = Paths.get("./files/img/").toAbsolutePath().normalize();
    }

    public ResponseEntity<byte[]> getImage(String path, String name) throws IOException {
        try {
            byte[] image = Files.readAllBytes(Paths.get(images + "/" + path + "/" + name));
            return ResponseEntity.ok().headers(getHeader(name)).contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (NoSuchFileException noSuchFileException) {
            return null;
        }
    }

    private HttpHeaders getHeader(String filename) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache"); header.add("Expires", "0");
        return header;
    }
}