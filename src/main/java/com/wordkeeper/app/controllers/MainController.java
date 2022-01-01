package com.wordkeeper.app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.wordkeeper.app.entities.Entry;
import com.wordkeeper.app.entities.Section;
import com.wordkeeper.app.entities.Student;
import com.wordkeeper.app.repos.EntriesRepository;
import com.wordkeeper.app.repos.SectionRepository;
import com.wordkeeper.app.repos.StudentsRepository;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private StudentsRepository studentsRepository;
	
	@Autowired
	private EntriesRepository entriesRepository;
	
	@Autowired
	private SectionRepository sectionRepository;
	
	@RequestMapping(value="/showSectionsMenu", method=RequestMethod.POST)
	public String showSectionMenu(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		return "sectionMenu";
	}
	
	@RequestMapping(value="/newSection", method=RequestMethod.POST)
	public String newSection(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		return "newSection";
	}
	
	@RequestMapping(value="/newSectionAdded", method=RequestMethod.POST)
	public String newSectionAdded(@ModelAttribute("section") Section section, ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		sectionRepository.save(section);
		return "mainMenu";
	}	

	@RequestMapping(value="/defaultSection", method=RequestMethod.POST)
	public String defaultSection(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("dsection", sectionRepository.findById(student.getSection()).get().getValue());
		modelMap.addAttribute("sections", sectionRepository.viewSections());	
		return "defaultSection";
	}
	
	@RequestMapping(value="/changeSection", method=RequestMethod.POST)
	public String changeSection(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("sections", sectionRepository.viewSections());	
		return "changeSection";
	}
	
	//**********************
	
	@RequestMapping(value="/sectionChanged")
	public String sectionChanged(@RequestParam("section.id") Long id, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		student.setSection(id);
		studentsRepository.save(student);
		return "flashcardMenu";
	}
	
	@RequestMapping(value="/setDefaultSection")
	public String setDefaultSection(@RequestParam("section.id") Long id, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		student.setSection(id);
		studentsRepository.save(student);
		return "mainMenu";	
	}	
	
	@RequestMapping(value="/setChangeSection")
	public String setChangeSection(@RequestParam("section.id") Long id, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		student.setSection(id);
		studentsRepository.save(student);
		return showFlashcardMenu(modelMap);
	}

	@RequestMapping(value="/deleteSection", method=RequestMethod.POST)
	public String deleteSection(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("sections", sectionRepository.viewSections());
		return "deleteSection";
	}

	@RequestMapping(value="/sectionDeleted")
	public String sectionDeleted(@RequestParam("section.id") Long id, ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		insertStudent(modelMap);	
		sectionRepository.deleteById(id);
		return "mainMenu";
	}	
	
	@RequestMapping(value="/showReport", method=RequestMethod.POST)
	public String showReport(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		
		// get number of all words
		modelMap.addAttribute("number_all_words", entriesRepository.findByStudent(student).size());
		
		// get number of words to learn
		modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());
		
		// get number of words to repetition
		modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
		
		return "report";
	}
	
	@RequestMapping(value="/addNewPhrase", method=RequestMethod.POST)
	public String callNewPhrase(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("section", sectionRepository.findById(student.getSection()).get().getValue());
		return "newPhrase";
	}

	@RequestMapping(value="/newPhraseAdded")
	public String addNewPhrase(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {		
		Student student = insertStudent(modelMap);
		entry.setSection(student.getSection());
		entry.setPhrase(true);
		entry.setWord(entry.getSentence());
		entriesRepository.save(entry);
		return "newPhrase";
	}
	
	@RequestMapping(value="/addNewWord", method=RequestMethod.POST)
	public String callNewWord(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("section", sectionRepository.findById(student.getSection()).get().getValue());
		return "newWord";
	}

	@RequestMapping(value="/newWordAdded")
	public String addNewWord(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {		
		Student student = insertStudent(modelMap);
		entry.setSection(student.getSection());
		entry.setPhrase(false);
		entriesRepository.save(entry);
		return "newWord";
	}
	
	@RequestMapping(value="/removeWord")
	public String removeWord(@RequestParam("word.id") Long id, ModelMap modelMap) {
		insertStudent(modelMap);
		Entry entry = entriesRepository.findById(id).get();
		List<Entry> entries = entriesRepository.findByWord(entry.getWord());
		for ( Entry e : entries ) {
			entriesRepository.deleteById(e.getId());	
		}
		modelMap.addAttribute("words", entriesRepository.findByStudent(null));
		return "wordAllList";	
	}
	
	@RequestMapping(value="/editWord")
	public String editWord(@RequestParam("word.id") Long id, ModelMap modelMap) {
		insertStudent(modelMap);
		Boolean isPhrase = false;
		Entry entry = entriesRepository.findById(id).get();
		List<Entry> entries = entriesRepository.findAll();
		for(Entry e : entries ) {
			if ( e.getWord().equals(entry.getWord()) && e.getSentence().equals(entry.getSentence()) ) {
				e.setModified(true);
				isPhrase = e.getPhrase();
				entriesRepository.save(e);
			}
		}
		modelMap.addAttribute("entry", entry);
		if ( isPhrase ) {
			return "editPhrase";
		} else {
			return "editWord";
		}
	}
	
	@RequestMapping(value="/wordModified")
	public String modifyWord(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {
		insertStudent(modelMap);
		List<Entry> entries = entriesRepository.findByModified(true);
		for(Entry e : entries ) {
			e.setWord(entry.getWord());
			e.setTracker(entry.getTracker());
			e.setSentence(entry.getSentence());
			e.setTranslation(entry.getTranslation());			
			e.setModified(false);
			entriesRepository.save(e);
		}
		modelMap.addAttribute("words", entriesRepository.findByStudent(null));
		return "wordAllList";
	}
	
	@RequestMapping(value="/phraseModified")
	public String modifyPhrase(@ModelAttribute("entry") Entry entry, ModelMap modelMap) {
		insertStudent(modelMap);
		List<Entry> entries = entriesRepository.findByModified(true);
		for(Entry e : entries ) {
			e.setWord(entry.getWord());
			e.setSentence(entry.getSentence());
			e.setTranslation(entry.getTranslation());			
			e.setModified(false);
			entriesRepository.save(e);
		}
		modelMap.addAttribute("words", entriesRepository.findByStudent(null));
		return "wordAllList";
	}	
	
	
//	@RequestMapping("/")
//	public String rootPage(ModelMap modelMap) {
//		return "login";
//	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "loginStudent";
	}
	
	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		return "registerStudent";
	}
	
	@RequestMapping("/mainMenu")
	public String showMainMenuPage(ModelMap modelMap) {
		insertStudent(modelMap);
		return "mainMenu";
	}
	
	@RequestMapping("/toolsMenu")
	public String showToolsMenuPage(ModelMap modelMap) {
		insertStudent(modelMap);
		return "toolsView";
	}
	
	@RequestMapping(value="/studentRegistered", method=RequestMethod.POST)
	public String registerStudent(@ModelAttribute("student") Student student) {
		studentsRepository.save(student);
		return "loginStudent";
	}
	
	@RequestMapping(value="/loginStudent", method=RequestMethod.POST)
	public String studentLogged(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap model) {
		LocalDate today = LocalDate.now().plusDays(2);
		Student student = studentsRepository.findByEmail(email);
		if ( student == null ) {
			model.addAttribute("msg","Invalid student name or wrong Password. Please try again.");
			return "loginStudent";
		}
		if ( student.getPassword().equals(password)) {
			student.setLogged(true);
			student.setLogin_date(today);
			studentsRepository.save(student);
			model.addAttribute("student", student.getFirstName() + " " + student.getLastName() );
			return "mainMenu";
		}
		else {
			model.addAttribute("msg","Invalid student name or wrong Password. Please try again.");
			return "loginStudent";
		}
	}
	
	@RequestMapping(value="/quit", method=RequestMethod.POST)
	public String quitToLogin() {
		Student student = studentsRepository.findByLogged(true);
		if ( student != null ) {
			student.setLogged(false);
			studentsRepository.save(student);
		}
		return "loginStudent";
	}
	
	@RequestMapping(value="/viewWords", method=RequestMethod.POST)
	public String viewWords(ModelMap modelMap) {
		List<Entry> words = entriesRepository.findByStudentOrdered(insertStudent(modelMap));
		modelMap.addAttribute("words",words);
		return "wordStudentList";	
	}
	
	@RequestMapping(value="/showAllWords", method=RequestMethod.POST)
	public String showAllWords(ModelMap modelMap) {
		insertStudent(modelMap);
		modelMap.addAttribute("words", entriesRepository.findByStudent(null));
		return "wordAllList";
	}
	
	@RequestMapping(value="/flashcardMenu", method=RequestMethod.POST)
	public String showFlashcardMenu(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
//		if ( studentEntries.size() == 0 ) {
//			return "noWordsToFlashcard";
//		}
		// get number of words to learn
		modelMap.addAttribute( "number_flashcards_to_learn", studentEntries.size() );
		modelMap.addAttribute("section", sectionRepository.findById(student.getSection()).get().getValue());		
		return "flashcardMenu";	
	}
	
	
	@RequestMapping(value="/continueFlashcards", method=RequestMethod.POST)
	public String continueFlashcards(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		if ( studentEntries.size() == 0 ) {
			return "noWordsToFlashcard";
		} else {
			insertParts(studentEntries.get(0), modelMap);
			modelMap.addAttribute("number_words_to_flash", entriesRepository.findByStudentFlashcard(student, student.getSection()).size());
			if ( studentEntries.get(0).getFlashcard() == 0 ) return "learnFlashcards0";
			else if ( studentEntries.get(0).getFlashcard() == 1 ) return "learnFlashcards1";
			else return "learnFlashcards2";
		}
	}
	
	@RequestMapping(value="/resetFlashcards", method=RequestMethod.POST)
	public String resetFlashcards(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudent(student);
		for ( Entry e : studentEntries ) {
			e.setFlashcard(0);
			e.setFlash_stage(0);
			entriesRepository.save(e);
		}
		studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		if ( studentEntries.size() == 0 ) {
			return "noWordsToFlashcard";
		} else {
			insertParts(studentEntries.get(0), modelMap);
			modelMap.addAttribute("number_words_to_flash", entriesRepository.findByStudentFlashcard(student, student.getSection()).size());
			if ( studentEntries.get(0).getFlashcard() == 0 ) return "learnFlashcards0";
			else if ( studentEntries.get(0).getFlashcard() == 1 ) return "learnFlashcards1";
			else return "learnFlashcards2";
		}
	}
	
	@RequestMapping(value="/iFlash", method=RequestMethod.POST)
	public String iFlash(ModelMap modelMap ) {
		Entry entryToLearn = new Entry();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToFlashcard";
		} else if ( studentEntries.get(0).getFlashcard() == 0 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setFlash_stage(entryToLearn.getFlash_stage() + 1);
//			entryToLearn.setFlashcard(1);
			entryToLearn.setFlashcard(3);
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getFlashcard() == 1 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setFlash_stage(entryToLearn.getFlash_stage() + 1);
			entryToLearn.setFlashcard(2);
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getFlashcard() == 2 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setFlash_stage(0);	
			entryToLearn.setFlashcard(3);
			entriesRepository.save(entryToLearn);		
		}
		studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		if ( studentEntries.size() == 0 ) {
			return "noWordsToFlashcard";	
		}
		else {
			insertParts(studentEntries.get(0), modelMap);
			// get number of words to learn
			modelMap.addAttribute("number_words_to_flash", entriesRepository.findByStudentFlashcard(student, student.getSection()).size());
			if ( studentEntries.get(0).getFlashcard() == 0 ) return "learnFlashcards0";
			else if ( studentEntries.get(0).getFlashcard() == 1 ) return "learnFlashcards1";
			else return "learnFlashcards2";			
		}
	}
	
	@RequestMapping(value="/iDontFlash", method=RequestMethod.POST)
	public String iDontFlash(ModelMap modelMap ) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		Entry entryToLearn = new Entry();
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToFlashcard";
		} else {			
			entryToLearn = studentEntries.get(0);
			entryToLearn.setFlash_stage(entryToLearn.getFlash_stage() + 1);
			entryToLearn.setDifficulty(entryToLearn.getDifficulty() + 1);
			entriesRepository.save(entryToLearn);
			studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
			insertParts(studentEntries.get(0), modelMap);
			// get number of words to learn
			modelMap.addAttribute("number_words_to_flash", entriesRepository.findByStudentFlashcard(student, student.getSection()).size());
			if ( studentEntries.get(0).getFlashcard() == 0 ) return "learnFlashcards0";
			else if ( studentEntries.get(0).getFlashcard() == 1 ) return "learnFlashcards1";
			else return "learnFlashcards2";		
		}
	}
	
	@RequestMapping(value="/showFlashcard", method=RequestMethod.POST)
	public String showFlashcard(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudentFlashcard(student, student.getSection());
		insertParts(studentEntries.get(0), modelMap);
		// get number of words to learn
		modelMap.addAttribute("number_words_to_flash", entriesRepository.findByStudentFlashcard(student, student.getSection()).size());
		if ( studentEntries.get(0).getFlashcard() == 0 ) return "showFlashcard0";
		else if ( studentEntries.get(0).getFlashcard() == 1 ) return "showFlashcard1";
		else return "showFlashcard2";		
	}
	
	@RequestMapping(value="/learnWords", method=RequestMethod.POST)
	public String learnWords(ModelMap modelMap) {
		Boolean isFound = false;
		Entry newEntry;
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStudent(student);
		List<Entry> allEntries = entriesRepository.findByStudent(null);
		
		// add new words not learned yet (status=0) to list new words for student (status=1)
		for(Entry e : allEntries ) {
			isFound = false;
			for ( Entry s : studentEntries ) {
				if ( e.getWord().equals(s.getWord()) ) {
					isFound = true;
					break;
				}
			}
			if ( isFound == false ) {
				newEntry = new Entry();
				newEntry.setWord(e.getWord());
				newEntry.setSentence(e.getSentence());
				newEntry.setTracker(e.getTracker());
				newEntry.setTranslation(e.getTranslation());
				newEntry.setModified(false);
				newEntry.setPhrase(e.getPhrase());
				newEntry.setStudent(student);
				// new word, status = 0
				// first learning, status = 1
				// first learned, status = 2
				// repetitions no longer required = 3
				newEntry.setStatus(1);
				// learn by sentences, stage = 0
				// learn by English word, stage = 1
				// learn by translation, stage = 2
				newEntry.setStage(0);
				entriesRepository.save(newEntry);
			}
		}		
		// get number of words to learn
		modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());
		// get number of words to repetition
		modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
		return "learnWord";
	}
	
	@RequestMapping(value="/learnNewWords", method=RequestMethod.POST)
	public String learnNewWords(ModelMap modelMap ) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudent(1, student);
		if ( studentEntries.size() == 0 ) {
			return "noWordsToLearn";
		} else {
			insertParts(studentEntries.get(0), modelMap);
			// get number of words to learn
			modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "learnNewWords0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "learnNewWords1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "learnNewWords2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase1";			
			return "noWordsToLearn";			
		}
	}
	
	@RequestMapping(value="/wordsRepetition", method=RequestMethod.POST)
	public String wordsRepetition(ModelMap modelMap ) {		
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
		if ( studentEntries.size() == 0 ) {
			return "noWordsToRepetition";
		} else {
			insertParts(studentEntries.get(0), modelMap);
			modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "phraseRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "phraseRepetition1";			
			return "noWordsToRepetition";
		}	
	}	
	
	@RequestMapping(value="/iKnow", method=RequestMethod.POST)
	public String iKnow(ModelMap modelMap ) {
		Entry entryToLearn = new Entry();
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudent(1, student);
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToLearn";
		} else if ( studentEntries.get(0).getStage() == 0 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setStage(1);
			
			if ( entryToLearn.getPhrase() ) {
				entryToLearn.setLast_repetition(today);
				entryToLearn.setStage(0);
				entryToLearn.setStatus(2);	
				if ( entryToLearn.getRepetition() == 0 ) {
					entryToLearn.setNext_repetition(today.plusDays(42));	
				} else if ( entryToLearn.getRepetition() <= 1 ) {
					entryToLearn.setNext_repetition(today.plusDays(32));
				} else if ( entryToLearn.getRepetition() <= 2 ) {
					entryToLearn.setNext_repetition(today.plusDays(17));
				} else if ( entryToLearn.getRepetition() <= 3 ) {
					entryToLearn.setNext_repetition(today.plusDays(12));
				} else if ( entryToLearn.getRepetition() <= 4 ) {
					entryToLearn.setNext_repetition(today.plusDays(7));
				} else if ( entryToLearn.getRepetition() <= 5 ) {
					entryToLearn.setNext_repetition(today.plusDays(6));
				} else {
					entryToLearn.setNext_repetition(today.plusDays(5));
				}
				entryToLearn.setRepetition(0);
			}			
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getStage() == 1 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setStage(2);
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getStage() == 2 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setLast_repetition(today);		
			entryToLearn.setStage(0);
			entryToLearn.setStatus(2);
			
			if ( entryToLearn.getRepetition() == 0 ) {
				entryToLearn.setNext_repetition(today.plusDays(42));	
			} else if ( entryToLearn.getRepetition() <= 1 ) {
				entryToLearn.setNext_repetition(today.plusDays(32));
			} else if ( entryToLearn.getRepetition() <= 2 ) {
				entryToLearn.setNext_repetition(today.plusDays(17));
			} else if ( entryToLearn.getRepetition() <= 3 ) {
				entryToLearn.setNext_repetition(today.plusDays(12));
			} else if ( entryToLearn.getRepetition() <= 4 ) {
				entryToLearn.setNext_repetition(today.plusDays(7));
			} else if ( entryToLearn.getRepetition() <= 5 ) {
				entryToLearn.setNext_repetition(today.plusDays(6));
			} else {
				entryToLearn.setNext_repetition(today.plusDays(5));
			}
			
			entryToLearn.setRepetition(0);			
			entriesRepository.save(entryToLearn);		
		}
		studentEntries = entriesRepository.findByStatusStudent(1, student);
		if ( studentEntries.size() == 0 ) {
			return "noWordsToLearn";	
		}
		else {
			insertParts(studentEntries.get(0), modelMap);
			// get number of words to learn
			modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "learnNewWords0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "learnNewWords1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "learnNewWords2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase1";			
			return "noWordsToLearn";			
		}
	}

	@RequestMapping(value="/iRemember", method=RequestMethod.POST)
	public String iRemember(ModelMap modelMap ) {
		Entry entryToLearn = new Entry();
		LocalDate today = LocalDate.now();
		long diffDate = 0;
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToRepetition";
		} else if ( studentEntries.get(0).getStage() == 0 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setStage(1);
			
			if ( entryToLearn.getPhrase() ) {
				diffDate = entryToLearn.getNext_repetition().compareTo(entryToLearn.getLast_repetition());
				
				entryToLearn.setLast_repetition(today);
				entryToLearn.setStage(0);
				entryToLearn.setStatus(2);	
				
				if ( entryToLearn.getRepetition() == 0 ) {
					entryToLearn.setNext_repetition(today.plusDays(42 + (diffDate>20?20:0) ));	
				} else if ( entryToLearn.getRepetition() <= 1 ) {
					entryToLearn.setNext_repetition(today.plusDays(32 + (diffDate>20?10:0) ));
				} else if ( entryToLearn.getRepetition() <= 2 ) {
					entryToLearn.setNext_repetition(today.plusDays(17 + (diffDate>20?5:0) ));
				} else if ( entryToLearn.getRepetition() <= 3 ) {
					entryToLearn.setNext_repetition(today.plusDays(12 + (diffDate>15?5:0) ));
				} else if ( entryToLearn.getRepetition() <= 4 ) {
					entryToLearn.setNext_repetition(today.plusDays(7 + (diffDate>15?2:0) ));
				} else if ( entryToLearn.getRepetition() <= 5 ) {
					entryToLearn.setNext_repetition(today.plusDays(6));
				} else {
					entryToLearn.setNext_repetition(today.plusDays(5));
				}

				entryToLearn.setRepetition(0);
			}				
			
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getStage() == 1 ) {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setStage(2);		
			entriesRepository.save(entryToLearn);
		} else if ( studentEntries.get(0).getStage() == 2 ) {
			entryToLearn = studentEntries.get(0);
			diffDate = entryToLearn.getNext_repetition().compareTo(entryToLearn.getLast_repetition());
			entryToLearn = studentEntries.get(0);
			entryToLearn.setLast_repetition(today);	
			
			if ( entryToLearn.getRepetition() == 0 ) {
				entryToLearn.setNext_repetition(today.plusDays(42 + (diffDate>20?20:0) ));	
			} else if ( entryToLearn.getRepetition() <= 1 ) {
				entryToLearn.setNext_repetition(today.plusDays(32 + (diffDate>20?10:0) ));
			} else if ( entryToLearn.getRepetition() <= 2 ) {
				entryToLearn.setNext_repetition(today.plusDays(17 + (diffDate>20?5:0) ));
			} else if ( entryToLearn.getRepetition() <= 3 ) {
				entryToLearn.setNext_repetition(today.plusDays(12 + (diffDate>15?5:0) ));
			} else if ( entryToLearn.getRepetition() <= 4 ) {
				entryToLearn.setNext_repetition(today.plusDays(7 + (diffDate>15?2:0) ));
			} else if ( entryToLearn.getRepetition() <= 5 ) {
				entryToLearn.setNext_repetition(today.plusDays(6));
			} else {
				entryToLearn.setNext_repetition(today.plusDays(5));
			}
			
			entryToLearn.setRepetition(0);	
			entryToLearn.setStage(0);
			entriesRepository.save(entryToLearn);
		}		
		studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
		if ( studentEntries.size() == 0 ) {
			return "noWordsToRepetition";	
		}
		else {
			insertParts(studentEntries.get(0), modelMap);
			modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "phraseRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "phraseRepetition1";			
			return "noWordsToRepetition";
		}
	}
	
	@RequestMapping(value="/iDontKnow", method=RequestMethod.POST)
	public String iDontKnow(ModelMap modelMap ) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudent(1, student);
		Entry entryToLearn = new Entry();
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToLearn";
		} else {			
			entryToLearn = studentEntries.get(0);
			entryToLearn.setRepetition(entryToLearn.getRepetition() + 1);
			entryToLearn.setDifficulty(entryToLearn.getDifficulty() + 1);
			entriesRepository.save(entryToLearn);
			studentEntries = entriesRepository.findByStatusStudent(1, student);
			insertParts(studentEntries.get(0), modelMap);
			// get number of words to learn
			modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "learnNewWords0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "learnNewWords1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "learnNewWords2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "learnNewPhrase1";			
			return "noWordsToLearn";		
		}
	}

	@RequestMapping(value="/iDontRemeber", method=RequestMethod.POST)
	public String iDontRemember(ModelMap modelMap ) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
		Entry entryToLearn = new Entry();
		
		if ( studentEntries.size() == 0 ) {
			return "noWordsToRepetition";
		} else {
			entryToLearn = studentEntries.get(0);
			entryToLearn.setRepetition(entryToLearn.getRepetition() + 1);
			entryToLearn.setDifficulty(entryToLearn.getDifficulty() + 1);
			entriesRepository.save(entryToLearn);
			studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
			insertParts(studentEntries.get(0), modelMap);
			modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
			if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition1";
			else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "wordsRepetition2";
			else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "phraseRepetition0";
			else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "phraseRepetition1";			
			return "noWordsToRepetition";
		}
	}
	
	@RequestMapping(value="/showLearnedWord", method=RequestMethod.POST)
	public String showWord(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudent(1, student);
		insertParts(studentEntries.get(0), modelMap);
		// get number of words to learn
		modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());	
		if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord0";
		else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord1";
		else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord2";
		else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "showLearnedPhrase0";
		else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "showLearnedPhrase1";			
		return "noWordsToLearn";	
	}
		
	@RequestMapping(value="/showRepetitedWord", method=RequestMethod.POST)
	public String showRepetitedWord(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudentNextDate(2, student, today);
		insertParts(studentEntries.get(0), modelMap);
		// get number of words to learn
		modelMap.addAttribute("number_words_to_repetition", entriesRepository.findByStatusStudentNextDate(2, student, today).size());
		if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "showRepetitedWord0";
		else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "showRepetitedWord1";
		else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "showRepetitedWord2";
		else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "showRepetitedPhrase0";
		else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "showRepetitedPhrase1";			
		return "noWordsToRepetition";
	}
	
	@RequestMapping(value="/showLearnedPhrase", method=RequestMethod.POST)
	public String showPhrase(ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> studentEntries = entriesRepository.findByStatusStudent(1, student);
		insertParts(studentEntries.get(0), modelMap);
		// get number of words to learn
		modelMap.addAttribute("number_words_to_learn", entriesRepository.findByStatusStudent(1, student).size());				
		if ( studentEntries.get(0).getStage() == 0 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord0";
		else if ( studentEntries.get(0).getStage() == 1 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord1";
		else if ( studentEntries.get(0).getStage() == 2 && !studentEntries.get(0).getPhrase() ) return "showLearnedWord2";
		else if ( studentEntries.get(0).getStage() == 0 && studentEntries.get(0).getPhrase() ) return "showLearnedPhrase0";
		else if ( studentEntries.get(0).getStage() == 1 && studentEntries.get(0).getPhrase() ) return "showLearnedPhrase1";			
		return "noWordsToLearn";	
	}	
	
	@RequestMapping("/exportData")
	public String exportData(ModelMap modelMap) {
		LocalDate today = LocalDate.now();
		Student student = insertStudent(modelMap);
		modelMap.addAttribute("file_name", "export_" + today.toString() + ".dat");
		List<Entry> studentEntries = entriesRepository.findByStudent(student);
		modelMap.addAttribute("number_objects", studentEntries.size() );
		try {	
			ObjectOutputStream outputFile = new ObjectOutputStream( new FileOutputStream( new File ( "export_" + today.toString() + ".dat" ) ) );			
			outputFile.writeObject(studentEntries);
			outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "showExported";
	}
	
	
	@RequestMapping(value="/getFileName", method=RequestMethod.POST)
	public String getFileName(ModelMap modelMap) {
		insertStudent(modelMap);
		return "importFile";
	}
	
	@RequestMapping(value="/importData", method=RequestMethod.POST)
	public String importData(@ModelAttribute("file_name") String fileName, ModelMap modelMap) {
		Student student = insertStudent(modelMap);
		List<Entry> entriesToImport;
		List<Entry> studentEntries;
		List<Entry> entriesNotAssigned;
		Boolean shouldBeAdd = false;
		int counter = 0;
		
		try {	
			ObjectInputStream inputFile = new ObjectInputStream( new FileInputStream( new File ( fileName + ".dat" ) ) );
			entriesToImport = (List<Entry>)inputFile.readObject();
			modelMap.addAttribute("objects_imported", entriesToImport.size() );
			inputFile.close();
			if ( entriesToImport.size() > 0 ) {
				
				for ( Entry i : entriesToImport ) {

					Boolean isFound = false;
					
					studentEntries = entriesRepository.findByStudent(student);
					
					if ( i.getLast_repetition() != null ) {		
						
						for ( Entry b : studentEntries ) {
							
							studentEntries = entriesRepository.findByStudent(student);
							
							if ( b.getLast_repetition() != null ) {
								
//								System.out.println("import date: " + i.getLast_repetition() + " base: " + b.getLast_repetition() + " bool: " + i.getLast_repetition().isAfter(b.getLast_repetition()));
								
								if ( i.getWord().equals(b.getWord()) && i.getSentence().equals(b.getSentence() ) && i.getLast_repetition().isAfter(b.getLast_repetition()) ) {
									entriesRepository.deleteById(b.getId());
									i.setStudent(student);
									entriesRepository.save(i);
									isFound = true;
									break;
								}
							} else {
								if ( i.getWord().equals(b.getWord()) && i.getSentence().equals(b.getSentence() ) ) {		
									isFound = true;
									break;
								}
							}
							
						}
						
					} else {
						
						studentEntries = entriesRepository.findByStudent(student);
						
						for ( Entry b : studentEntries ) {
							if ( i.getWord().equals(b.getWord()) && i.getSentence().equals(b.getSentence() ) ) {
								isFound = true;
								break;
							}
						}
						
					}
					if ( !isFound ) {
						i.setStudent(student);
						entriesRepository.save(i);
					}
				}
					
				entriesNotAssigned = entriesRepository.findByStudent(null);
				for ( Entry e : entriesToImport ) {
					shouldBeAdd = true;
					for ( Entry n : entriesNotAssigned ) {
						if ( e.getWord().equals(n.getWord()) ) {
							shouldBeAdd = false;
						}
					}
					if ( shouldBeAdd ) {
						e.setStudent(null);
						e.setRepetition(0);
						e.setDifficulty(0);
						e.setLast_repetition(null);
						e.setNext_repetition(null);
						e.setStage(0);
						e.setFlashcard(0);
						e.setFlash_stage(0);
						entriesRepository.save(e);
						counter++;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		modelMap.addAttribute("objects_refilled", counter );
		
		return "showImported";
	}

	
	public Student insertStudent(ModelMap modelMap) {
		Student student = studentsRepository.findByLogged(true);
		modelMap.addAttribute("student", student.getFirstName() + " " + student.getLastName() );
		return student;
	}
	
	public void insertParts(Entry entry, ModelMap modelMap) {
		String[] parts = entry.getSentence().split(entry.getTracker());
		String tempString = "";
		
		if ( parts.length == 0 ) {
			modelMap.addAttribute("part1", "");
			modelMap.addAttribute("part2", entry.getTracker());
			modelMap.addAttribute("part3", "");			
		} else if ( parts.length == 1 ) {	
			modelMap.addAttribute("part1", parts[0]);
			modelMap.addAttribute("part2", entry.getTracker());
			modelMap.addAttribute("part3", "");
		} else if ( parts.length == 2 ){
			modelMap.addAttribute("part1", parts[0]);
			modelMap.addAttribute("part2", entry.getTracker());
			modelMap.addAttribute("part3", parts[1]);
		} else if ( parts.length >= 3) {
			tempString = entry.getSentence().substring( entry.getSentence().indexOf(entry.getTracker()) + entry.getTracker().length() );
			modelMap.addAttribute("part1", parts[0]);
			modelMap.addAttribute("part2", entry.getTracker());
			modelMap.addAttribute("part3", tempString);			
		}
		
		modelMap.addAttribute("entryToLearn", entry);
	}

}


