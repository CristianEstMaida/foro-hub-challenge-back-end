package com.foro.hub.foro_hub_api.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleAndMessage(String title, String message);

    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName")
    Page<Topic> findByCourseName(@Param("courseName") String courseName, Pageable pagination);

    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName AND YEAR(t.creationDate) = :year")
    Page<Topic> findByCourseNameAndYear(@Param("courseName") String courseName, @Param("year") int year, Pageable pageable);


}
