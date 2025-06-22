package edu.leicester.co2103.repo;

import org.springframework.data.repository.CrudRepository;

import edu.leicester.co2103.domain.Session;

import java.util.List;


public interface SessionRepository extends CrudRepository<Session, Long> {
//
//    List<Session> findByConvenorAndModuleCode(Long convenor, String module);
//
//    List<Session> findByConvenorId(Long convenor);
//
//    List<Session> findByModuleCode(String module);
}
