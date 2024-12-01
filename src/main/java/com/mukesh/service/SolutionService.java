package com.mukesh.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mukesh.models.*;
import com.mukesh.repository.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private TaskService taskService;

    public Solution uploadSolution(Long taskId, AppUser uploader, String fileName, String fileType, byte[] data) throws Exception {
        Task task = taskService.getTaskById(taskId);

        Solution solution = new Solution();
        solution.setTask(task);
        solution.setUploader(uploader);
        solution.setFileName(fileName);
        solution.setFileType(fileType);
        solution.setData(data);

        return solutionRepository.save(solution);
    }

    @Transactional(readOnly = true)
    public Solution getSolutionById(Long solutionId) throws Exception {
        return solutionRepository.findById(solutionId)
                .orElseThrow(() -> new Exception("Solution not found"));
    }

    @Transactional(readOnly = true)
    public List<Solution> getSolutionsByTaskId(Long taskId) {
        return solutionRepository.findByTaskId(taskId);
    }

}
