package com.mukesh.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mukesh.models.*;
import com.mukesh.service.*;

@RestController
@RequestMapping("/api/tasks")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private UserService userService;

    @PostMapping("/{taskId}/solutions")
    public Solution uploadSolution(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long taskId,
            @RequestParam("file") MultipartFile file) throws Exception {

        AppUser user = userService.findUserByJwt(jwt);

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        byte[] data = file.getBytes();

        return solutionService.uploadSolution(taskId, user, fileName, fileType, data);
    }

    @GetMapping("/{taskId}/solutions")
    public List<Solution> getSolutions(@PathVariable Long taskId) {
        List<Solution> solutions = solutionService.getSolutionsByTaskId(taskId);

        // Inicjalizuj tylko potrzebne pola, aby uniknąć problemów z lazy loading
        for (Solution solution : solutions) {
            solution.getId();
            solution.getFileName();
            solution.getFileType();
            solution.getUploader().getFirstName(); // Inicjalizuj uploader
            solution.getUploader().getLastName();
        }

        return solutions;
    }


    @GetMapping("/{taskId}/solutions/{solutionId}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getSolution(
            @PathVariable Long taskId,
            @PathVariable Long solutionId) throws Exception {

        Solution solution = solutionService.getSolutionById(solutionId);

        // Sprawdzenie, czy rozwiązanie należy do zadania o podanym taskId
        if (!solution.getTask().getId().equals(taskId)) {
            throw new Exception("Solution does not belong to the specified task");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + solution.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(solution.getFileType()))
                .body(solution.getData());
    }
}
