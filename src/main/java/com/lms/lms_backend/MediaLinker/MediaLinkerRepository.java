package com.lms.lms_backend.MediaLinker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;

public interface MediaLinkerRepository extends JpaRepository<MediaLinker, Long> {
    boolean existsByCourseIdAndMedia_FileName(Integer courseId, String fileName);
    boolean existsByCourseIdAndCategoryNameAndOrderIndex(Integer courseId, String categoryName, BigDecimal orderIndex);
}
