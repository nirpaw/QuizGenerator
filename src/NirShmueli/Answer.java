package NirShmueli;

import java.io.PrintWriter;

public class Answer {
	private String answer;
	private int value;

	public Answer(String a, int v) {
		this.answer = a;
		this.value = v;

	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void saveToMemoryFile(PrintWriter pw) {
		pw.println(answer);
		pw.println(value);
	}

	public void createManuallyOrAutoExam(PrintWriter pw, int k) {
		pw.print(answer);
		if (k == 1) {
			if (this.value == 0)
				pw.println("[FALSE]");
			else
				pw.println("[TRUE]");
		} else
			pw.println("");
	}

}
