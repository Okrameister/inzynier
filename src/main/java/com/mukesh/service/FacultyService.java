package com.mukesh.service;

import com.mukesh.models.Faculty;
import com.mukesh.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        Faculty existingFaculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));
        existingFaculty.setName(updatedFaculty.getName());
        existingFaculty.setShortDescription(updatedFaculty.getShortDescription());
        existingFaculty.setLongDescription(updatedFaculty.getLongDescription());
        existingFaculty.setImageUrl(updatedFaculty.getImageUrl());
        existingFaculty.setCourses(updatedFaculty.getCourses());
        return facultyRepository.save(existingFaculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

}
