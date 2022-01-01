package com.wordkeeper.app.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Entry extends AbstractEntity implements Serializable {
	
	private String word;
	private String tracker;
	private String translation;
	private String sentence;
	private int difficulty;
	private int score;
	private int repetition;
	private LocalDate next_repetition;
	private LocalDate last_repetition;
	private int status;
	private Boolean modified; 
	private int stage;
	// 0, 1, 2 - not learned flashcard, 3 - learned flashcard
	private int flashcard;
	private int flash_stage;
	private Boolean phrase;
	private long section;
	
	@OneToOne
	private Student student;
	
	public Boolean getModified() {
		return modified;
	}
	public void setModified(Boolean modified) {
		this.modified = modified;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTracker() {
		return tracker;
	}
	public void setTracker(String tracker) {
		this.tracker = tracker;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRepetition() {
		return repetition;
	}
	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}
	public LocalDate getNext_repetition() {
		return next_repetition;
	}
	public void setNext_repetition(LocalDate next_repetition) {
		this.next_repetition = next_repetition;
	}
	public LocalDate getLast_repetition() {
		return last_repetition;
	}
	public void setLast_repetition(LocalDate last_repetition) {
		this.last_repetition = last_repetition;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getFlashcard() {
		return flashcard;
	}
	public void setFlashcard(int flashcard) {
		this.flashcard = flashcard;
	}
	public int getFlash_stage() {
		return flash_stage;
	}
	public void setFlash_stage(int flash_stage) {
		this.flash_stage = flash_stage;
	}
	public Boolean getPhrase() {
		return phrase;
	}
	public void setPhrase(Boolean phrase) {
		this.phrase = phrase;
	}
	public long getSection() {
		return section;
	}
	public void setSection(long section) {
		this.section = section;
	}
	@Override
	public String toString() {
		return "Entry [word=" + word + ", tracker=" + tracker + ", translation=" + translation + ", sentence="
				+ sentence + ", difficulty=" + difficulty + ", score=" + score + ", repetition=" + repetition
				+ ", next_repetition=" + next_repetition + ", last_repetition=" + last_repetition + ", status=" + status
				+ ", modified=" + modified + ", stage=" + stage + ", flashcard=" + flashcard + ", student=" + student
				+ ", Id=" + getId() + "]";
	}

}
