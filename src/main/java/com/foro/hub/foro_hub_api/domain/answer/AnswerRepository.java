package com.foro.hub.foro_hub_api.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByTopicId(Long topicoId);

    List<Answer> findByAuthorId(Long autorId);

    List<Answer> findByTopicIdOrderByCreationDateDesc(Long topicoId);
}

