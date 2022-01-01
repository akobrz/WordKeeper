package com.wordkeeper.app.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.wordkeeper.app.entities.Entry;
import com.wordkeeper.app.entities.Student;

public interface EntriesRepository extends JpaRepository<Entry, Long> {
	
	@Query("from Entry")
	List<Entry> viewWords();
	
	@Query("from Entry where status=:status and student=:student order by repetition, stage, id")
	List<Entry> findByStatusStudent(@Param("status") int status, @Param("student") Student student);
	
	@Query("from Entry where status=:status and student=:student and next_repetition<=:next_repetition order by repetition, stage, id")
	List<Entry> findByStatusStudentNextDate(@Param("status") int status, @Param("student") Student student, @Param("next_repetition") LocalDate next_repetition );
	
	@Query("from Entry where student=:student order by next_repetition")
	List<Entry> findByStudentOrdered(@Param("student") Student student);
	
	@Query("from Entry where student=:student and status = 2 and flashcard != 3 order by flash_stage, flashcard, difficulty desc, last_repetition desc")
	List<Entry> old_findByStudentFlashcard(@Param("student") Student student);
	
	@Query("from Entry where student=:student and status = 2 and flashcard != 3 and section=:section order by flash_stage, flashcard, difficulty desc, last_repetition desc")
	List<Entry> findByStudentFlashcard(@Param("student") Student student, @Param("section") Long section);
	
	List<Entry> findByStudent(Student student);
	List<Entry> findByStatus(int status);
	List<Entry> findByRepetition(int repetition);
	List<Entry> findByModified(Boolean modified);
	List<Entry> findByWord(String word);
}
