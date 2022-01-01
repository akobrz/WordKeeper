package com.wordkeeper.app.repos;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wordkeeper.app.entities.Entry;
import com.wordkeeper.app.entities.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
	
	@Query("from Section order by value")
	List<Section> viewSections();
	
	List<Section> findByValue(String value);
	
}
