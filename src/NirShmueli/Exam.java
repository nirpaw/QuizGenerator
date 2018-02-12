package NirShmueli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Exam {
	private static final int maxQuestPerTest = 100;
	private Question[] questArr = new Question[maxQuestPerTest];
	private int indexQuest = 0;

	public void addNewQuestion(String q) {
		questArr[indexQuest] = new Question(q);
		indexQuest++;

	}

	public boolean isRoomForNewQuestion() {
		if (indexQuest < maxQuestPerTest)
			return true;
		return false;
	}

	public void addNewAnsToThisQuest(int i, String a, int v) {
		questArr[i].addAnswer(a, v);
	}

	public boolean isQuestion(int i) {
		if (i >= indexQuest || i < 0)
			return false;
		return true;
	}

	public boolean isAnswerInThisQuest(int i, int j) {
		if (questArr[i].isAnswer(j))
			return true;
		return false;

	}

	public void updateQuestion(String q, int i) {
		questArr[i].setQuestion(q);

	}

	public void updateAnsToThisQuest(String q, int v, int i, int j) {
		questArr[i].updateAnswer(q, v, j);
	}

	public void deleteAnswerInThisQuestion(int i, int j) {
		questArr[i].deleteAnswer(j);
	}

	public boolean isRoomForNewAnswerInThisQuestion(int i) {
		if (questArr[i].isRoomForNewAnswer())
			return true;
		return false;

	}

	public int getIndexQuest() {
		return indexQuest;
	}

	public void setIndexQuest(int indexQuest) {
		this.indexQuest = indexQuest;
	}

	public void deleteQuestion(int i) {
		questArr[i] = questArr[indexQuest - 1];
		questArr[indexQuest - 1] = null;
		indexQuest--;
	}

	public void saveDataToMemoryFile(Exam e, String fileName)
			throws IOException {
		PrintWriter pw = new PrintWriter(fileName);
		pw.println(indexQuest);
		for (int i = 0; i < indexQuest; i++)
			if (questArr[i] != null)
				questArr[i].saveToMemoryFile(pw);
		pw.close();
	}

	public Exam loadFromMemoryFile(String fileName) throws IOException {
		File f = new File(fileName);
		Scanner s = new Scanner(f);
		Exam e = new Exam();
		int questCounter = s.nextInt();
		for (int i = 0; i < questCounter; i++) {
			s.nextLine(); // clean the buffer
			e.addNewQuestion(s.nextLine());
			int ansCounter = s.nextInt();
			for (int j = 0; j < ansCounter; j++) {
				s.nextLine(); // clean the buffer
				e.addNewAnsToThisQuest(i, s.nextLine(), s.nextInt());
			}
		}
		s.close();
		return e;
	}

	public void createManuallyOrAutoExam(int[][] mat, int exe)
			throws FileNotFoundException {
		Date now = new Date();
		String fileName = new SimpleDateFormat("yyyy_mm_yy_hh_mm").format(now);
		for (int i = 0; i < 2; i++) {
			if (i == 0)
				fileName = "exam_" + fileName+".txt";

			if (i == 1)
				fileName = "solution_" + fileName;
			PrintWriter pw = new PrintWriter(fileName);
			for (int q = 0; q < mat.length; q++) {
				pw.print((q + 1) + ". ");
				questArr[(mat[q][0]) - 1]
						.createManuallyOrAutoExam(pw, mat, q, i, exe);
			}
			pw.close();
		}

	}

	public boolean thereIsAtLeastTreeFalseAndfourAnswersInThisQuestion(
			int random) {
		return questArr[random].thereIsAtLeastTreeFalseAndfourAnswers();

	}

	public void addRandomAnswersToMat(int[][] autoMat) {
		for (int i = 0; i < autoMat.length; i++)
			questArr[(autoMat[i][0] - 1)].addRandomAnswersToMat(autoMat, i);
	}

	public String toString() {
		String quest = "";
		for (int i = 0; i < indexQuest; i++) {
			if (questArr[i].getQuestion() != null) {
				quest += (i + 1) + ". " + questArr[i].getQuestion() + "\n"
						+ questArr[i] + "\n";
			}

		}
		return quest;

	}


}
