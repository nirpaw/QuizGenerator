package NirShmueli;

import java.io.PrintWriter;

public class Question {
	public static final int maxAnsPerQuest = 10;
	private String question;
	private Answer[] ansArr = new Answer[maxAnsPerQuest];
	private int indexAns;

	public Question(String q) {
		this.question = q;
		indexAns = 0;

	}

	public void addAnswer(String a, int v) {
		ansArr[indexAns] = new Answer(a, v);
		indexAns++;

	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String q) {
		this.question = q;
	}

	public boolean isAnswer(int i) {
		if (i >= indexAns || i < 0)
			return false;
		return true;
	}

	public void updateAnswer(String a, int v, int j) {
		ansArr[j].setAnswer(a);
		ansArr[j].setValue(v);
	}

	public void deleteAnswer(int j) {
		ansArr[j].setAnswer(ansArr[indexAns - 1].getAnswer());
		ansArr[j].setValue(ansArr[indexAns - 1].getValue());
		ansArr[indexAns - 1] = new Answer(null, 0);
		indexAns--;

	}

	public boolean isRoomForNewAnswer() {
		if (indexAns < maxAnsPerQuest)
			return true;
		return false;
	}

	public void saveToMemoryFile(PrintWriter pw) {
		pw.println(question);
		pw.println(indexAns);
		for (int i = 0; i < indexAns; i++)
			ansArr[i].saveToMemoryFile(pw);
	}

	public void createManuallyOrAutoExam(PrintWriter pw, int[][] mat, int q,
			int kind, int exe) {
		pw.println(question);
		int counter = 1;
		for (int a = 1; a < mat[0].length; a++) {
			if (mat[q][a] != 0) {
				pw.print("\t" + a + ") ");
				counter++;
				ansArr[(mat[q][a]) - 1].createManuallyOrAutoExam(pw, kind);

			}
		}
		pw.print("\t" + counter + ") None of the above are correct ");
		if (kind == 1) {
			if (trueCounter(mat, q) == 0)
				pw.print("[TRUE]");
			else
				pw.print("[FALSE]");
		}
		pw.println();
		counter++;
		if (exe == 8) {
			pw.print("\t" + counter + ") More than one correct answer ");
			if (kind == 1) {
				if (trueCounter(mat, q) > 1)
					pw.print("[TRUE]");
				else
					pw.print("[FALSE]");
			}
		}
		pw.println();
	}

	public int trueCounter(int[][] mat, int q) {
		int counter = 0;
		for (int a = 1; a < mat[0].length; a++) {
			if ((mat[q][a]) != 0 && (ansArr[(mat[q][a]) - 1].getValue()) == 1)
				counter++;
		}
		return counter;
	}

	public int getIndexAns() {
		return indexAns;
	}

	public void setIndexAns(int indexAns) {
		this.indexAns = indexAns;
	}

	public boolean thereIsAtLeastTreeFalseAndfourAnswers() {
		int falseCounter = 0, totCounter = 0;
		for (int i = 0; i < indexAns; i++) {
			if (ansArr[i].getAnswer() != null) {
				if (ansArr[i].getValue() == 0)
					falseCounter++;
				totCounter++;
			}
		}
		if (falseCounter > 2 && totCounter > 3)
			return true;
		return false;
	}

	public void addRandomAnswersToMat(int[][] autoMat, int i) {
		for (int a = 1; a < autoMat[0].length;) {
			int random = (int) (Math.random() * indexAns + 1);
			if (!wasAlready(autoMat, random, i)) {
				if (ansArr[random - 1].getValue() != 1) {
					autoMat[i][a] = random;
					a++;
				} else {
					autoMat[i][a] = random;
					a++;
					for (; a < autoMat[0].length;) {
						random = (int) (Math.random() * indexAns + 1);
						if (ansArr[random - 1].getValue() != 1
								&& !wasAlready(autoMat, random, i)) {
							autoMat[i][a] = random;
							a++;
						}
					}

				}
			}
		}
	}

	public static boolean wasAlready(int[][] mat, int r, int i) {
		for (int j = 1; j < mat[0].length; j++)
			if (mat[i][j] != 0) {
				if (mat[i][j] == r)
					return true;
			}
		return false;
	}

	public String toString() {
		String ans = "";
		for (int i = 0; i < indexAns; i++) {
			if (ansArr[i].getAnswer() != null) {
				ans += "\t" + (i + 1) + ") " + ansArr[i].getAnswer()+"    " ;
				if (ansArr[i].getValue() == 0)
					ans += "[FALSE]";
				if (ansArr[i].getValue() == 1)
					ans += "[TRUE]";
				ans += "\n";

			}
		}
		return ans;

	}

}
