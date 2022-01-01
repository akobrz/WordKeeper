package com.wordkeeper.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wordkeeper.app.entities.Entry;
import com.wordkeeper.app.entities.Student;

public interface StudentsRepository extends JpaRepository<Student, Long> {

	Student findByEmail(String email);
	Student findByLogged(Boolean logged);
	
}
