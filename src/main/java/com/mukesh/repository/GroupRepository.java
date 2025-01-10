package com.mukesh.repository;


import com.mukesh.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByParentGroupId(Long parentGroupId);
    Group findById(long id);
}
