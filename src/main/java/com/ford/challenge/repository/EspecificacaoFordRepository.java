package com.ford.challenge.repository;

import com.ford.challenge.model.EspecificacaoFord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecificacaoFordRepository extends JpaRepository<EspecificacaoFord, Long> {}