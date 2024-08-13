package mister3551.msr.msrserver.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileService {

    private final Path images;

    public enum Types {
        PROFILE,
        MISSION,
        MAP
    }

    public FileService() {
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

    public void storeImage(String imageName, MultipartFile multipartFile, Types types) throws IOException {
        Files.copy(multipartFile.getInputStream(), getPath(types).resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteImage(String imageName, Types types) throws IOException {
        String[] extensions = {".jpg", ".jpeg", ".png", ".gif"};

        Path location = getPath(types);

        for (String extension : extensions) {
            Path fileToDelete = location.resolve(imageName + extension);
            if (Files.deleteIfExists(fileToDelete)) {
                return;
            }
        }
    }

    private Path getPath(Types types) {
        Path location;

        switch (types) {
            case PROFILE -> {
                location = images.resolve("profile/");
            }
            case MISSION -> {
                location = images.resolve("mission/");
            }
            case MAP -> {
                location = images.resolve("map/");
            }
            default -> {
                location = images;
            }
        }
        return location;
    }
}