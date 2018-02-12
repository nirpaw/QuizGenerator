package NirShmueli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class program {

	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(System.in);
		Exam e = new Exam();
		File f = new File("MEMORY");
		if (f.exists() && !f.isDirectory()) {
			e = e.loadFromMemoryFile("MEMORY");
			System.out.println(">>>>>>The data from the file \""+f.getName()+"\" uploaded successfully");
		}
		int menu;
		boolean stay = true;
		do {
			System.out
					.println("----------------------------------\n-->Main menu:"
							+ "\n1) show all questions and answers"
							+ "\n2) add new answer for existing qeustion"
							+ "\n3) add new question"
							+ "\n4) update question"
							+ "\n5) update answer"
							+ "\n6) delete answer"
							+ "\n7) delete question (with all the answers)  "
							+ "\n8) create an exam manually "
							+ "\n9) automatically generate exam "
							+ "\n0) save to file & exit"
							+ "\n-1) start over >> erase file & all data"
							+ "\n----------------------------------");
			menu = s.nextInt();
			switch (menu) {
			case 1:
				System.out.println(e);
				System.out.println("-->Press any key to show menu");
				s.next();
				break;
			case 2:
				System.out
						.println("*2-add answer*\n-->Enter the index of the question:");
				int index = s.nextInt();
				if (!e.isQuestion(index - 1)) {
					System.out.println("!!the question does not exist!!\n");
					break;
				}
				if (!e.isRoomForNewAnswerInThisQuestion(index - 1)) {
					System.out
							.println("!!you can not add more answers to this question!!\n");
					break;
				}
				s.nextLine();// clean the buffer
				System.out.println("-->Enter the answer");
				String answer = s.nextLine();
				System.out
						.println("the value of the answer is: [TRUE = 1 | FALSE = O]:");
				int value = s.nextInt();
				if (value != 0 && value != 1) {
					System.out
							.println("!!wrong input !! (the answer  must be 1 or 0 only)");
					break;
				}
				e.addNewAnsToThisQuest(index - 1, answer, value);
				System.out.println(">>The answer has been added");
				break;
			case 3:
				s.nextLine(); // clean the buffer.
				if (!e.isRoomForNewQuestion()) {
					System.out.println("!!you can not add more question!!");
					break;
				}
				System.out.println("*3- add question*\n-->Enter the question:");
				String question = s.nextLine();
				e.addNewQuestion(question);
				System.out.println(">>The question has been added");
				break;
			case 4:
				System.out
						.println("*4- update question*\n-->Enter the index of the question:");
				int index1 = s.nextInt();
				if (!e.isQuestion(index1 - 1)) {
					System.out.println("!!the question does not exist!!\n");
					break;
				}
				s.nextLine(); // clean the buffer.
				System.out.println("-->Enter the new question");
				String newqeuestion = s.nextLine();
				e.updateQuestion(newqeuestion, index1 - 1);
				System.out.println(">>The question has been updeted");
				break;
			case 5:
				System.out
						.println("*5- update answer*\n-->Enter the index of the question first:");
				int questIndex = s.nextInt();
				if (!e.isQuestion(questIndex - 1)) {
					System.out.println("!!the question does not exist!!\n");
					break;
				}
				System.out.println("-->Enter the index of the answer");
				int ansIndex = s.nextInt();
				if (!e.isAnswerInThisQuest(questIndex - 1, ansIndex - 1)) {
					System.out.println("!!the answer does not exist!!\n");
					break;
				}
				s.nextLine(); // clean the buffer
				System.out.println("-->Enter the new answer");
				String newAnswer = s.nextLine();
				System.out
						.println("-->Enter the new value of the answer is: [TRUE = 1 | FALSE = O]:");
				int newValue = s.nextInt();
				if (newValue != 0 && newValue != 1) {
					System.out
							.println("!!wrong input !! (the answer  must be 1 or 0 only)");
					break;
				}
				e.updateAnsToThisQuest(newAnswer, newValue, questIndex - 1,
						ansIndex - 1);
				System.out.println(">>The answer has been updeted");

				break;
			case 6:
				System.out
						.println("*6- delete answer*\n-->Enter the index of the question first:");
				int questIndex6 = s.nextInt();
				if (!e.isQuestion(questIndex6 - 1)) {
					System.out.println("!!the question does not exist!!\n");
					break;
				}
				System.out.println("-->Enter the index of the answer");
				int ansIndex6 = s.nextInt();
				if (!e.isAnswerInThisQuest(questIndex6 - 1, ansIndex6 - 1)) {
					System.out.println("!!the answer does not exist!!\n");
					break;
				}
				e.deleteAnswerInThisQuestion(questIndex6 - 1, ansIndex6 - 1);
				System.out
						.println(">>The answer has been deleted\nNOTE :  index of another answer may be changed");
				break;
			case 7:
				System.out
						.println("*7- delete question*\n-->Enter the index of the question:");
				int questIndex7 = s.nextInt();
				if (!e.isQuestion(questIndex7 - 1)) {
					System.out.println("!!the question does not exist!!\n");
					break;
				}
				e.deleteQuestion(questIndex7 - 1);
				System.out
						.println(">>The question has been deleted\nNOTE :  index of another question may be changed");

				break;
			case 8:
				System.out
						.println("*8- create an exam manually*\n-->Enter the number of questions");
				int numOfQuestions = s.nextInt();
				if (numOfQuestions > e.getIndexQuest()) {
					System.out.println("!!there are only " + e.getIndexQuest()
							+ " question!!");
					break;
				}
				int numOfAnswers = 10;
				int[][] mat = new int[numOfQuestions][numOfAnswers + 2];
				for (int i = 0; i < numOfQuestions; i++) {
					System.out
							.println("-->Enter the origiinal index of question "
									+ (i + 1));
					int choice = s.nextInt();
					while (!e.isQuestion(choice - 1)) {
						System.out
								.println("!!!!the question does not exist!!\nTry again");
						choice = s.nextInt();
					}
					mat[i][0] = choice;
				}
				for (int i = 0; i < numOfQuestions; i++) {
					System.out
							.println("Now choose the answers of question number "
									+ (i + 1)
									+ " (the original index is "
									+ mat[i][0] + ")");
					for (int j = 1; j < numOfAnswers; j++) {
						System.out
								.println("--> Enter the index of answer number "
										+ j
										+ "        (Enter -1 when you finish)");
						int choice = s.nextInt();
						if (choice == -1)
							break;
						else {
							while (!e.isAnswerInThisQuest(mat[i][0] - 1,
									choice - 1)) {
								System.out
										.println("!!!!the answer does not exist!!\n Try again");
								choice = s.nextInt();
							}
							mat[i][j] = choice;
						}
					}
				}

				System.out
						.println("ֿ\nFor your convenience -  this is the original indexes table:\nquestion/ answers");
				for (int i = 0; i < mat.length; i++) {
					for (int j = 0; j < mat[0].length; j++) {
						System.out.print((mat[i][j] != 0) ? mat[i][j] : "\t");
						System.out.print((j == 0) ? "-->\t" : "");
					}
					System.out.println("");
				}

				e.createManuallyOrAutoExam(mat, 8);
				System.out.println(">>The files created !");

				break;

			case 9:
				System.out
						.println("*9- automatically generate exam\n-->Enter the number of questions ");
				boolean out = false;
				int numOfQuestionsAuto = s.nextInt();
				if (numOfQuestionsAuto > e.getIndexQuest()) {
					System.out.println("!!there are only " + e.getIndexQuest()
							+ " question!!");
					break;
				}
				int numOfAnswersAuto = 4;
				int[][] autoMat = new int[numOfQuestionsAuto][numOfAnswersAuto + 1];

				for (int t = 0, all = 0; t < numOfQuestionsAuto;) {
					if (all > e.getIndexQuest() * 100) {
						System.out
								.println("!!EROR!! \n>> you need at least "
										+ numOfQuestionsAuto
										+ " questions that adjustments to the following conditions :\n- they all have 4 answers or more each \n- at least 3 of the answers must be FALSE ");
						out = true;
						break;
					}
					int random = (int) (Math.random() * e.getIndexQuest() + 1);

					if (e.thereIsAtLeastTreeFalseAndfourAnswersInThisQuestion(random - 1)
							&& !wasAlready(autoMat, random)) {
						autoMat[t][0] = random;
						t++;
					}
					all++;
				}
				if (out)
					break;

				e.addRandomAnswersToMat(autoMat);
				System.out
						.println("ֿ\nFor your convenience -  this is the original indexes table:\nquestion/ answers");
				for (int i = 0; i < autoMat.length; i++) {
					for (int j = 0; j < autoMat[0].length; j++) {
						System.out.print((autoMat[i][j] != 0) ? autoMat[i][j]
								: "\t");
						System.out.print((j == 0) ? "-->\t |" : "|");
					}
					System.out.println("");
				}
				e.createManuallyOrAutoExam(autoMat, 9);
				System.out.println(">>The files created ! ");
				break;
			case -1:
				System.out
						.println("--> You are about to delete the entire contents , Are you sure?\n1- YES. any other number-NO");
				if (s.nextInt() == 1) {
					e = new Exam();
					System.out.println(">>all data hase been deleted<<");
				}
				break;
			case 0:
				e.saveDataToMemoryFile(e, "MEMORY");
				System.out.println("Data saved to file\n--Good Bye--.");
				stay = false;
			}
		} while (stay);

	}// main

	public static boolean wasAlready(int[][] mat, int r) {
		for (int i = 0; i < mat.length; i++)
			if (mat[i][0] != 0) {
				if (mat[i][0] == r)
					return true;
			}
		return false;
	}

	{

	}
}