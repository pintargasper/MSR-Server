package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/images/{path}/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("path") String path, @PathVariable("name") String name) throws Exception {
        return fileService.getImage(path, name);
    }
}