import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable { // class name changed from "book" to "Book"

	private String title;  // variable name cahanged from T to title
	private String author; // variable name cahanged from A to author 
	private String callNo;  // variable name cahanged from C to callNo
	private int id;  // variable name cahanged from ID to id

	private enum STATE { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private STATE state;


	public book(String author, String title, String callNo, int id) {
		this.author = author;  // left side variable A change to author
		this.T = title;  // left side variable T change to title
		this.C = callNo;  // left side variable C change to callNo
		this.ID = id;  // left side variable ID change to id
		this.state = STATE.AVAILABLE;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(T).append("\n")
		  .append("  Author: ").append(A).append("\n")
		  .append("  CallNo: ").append(C).append("\n")
		  .append("  State:  ").append(state);

		return sb.toString();
	}

	public Integer ID() {
		return ID;
	}

	public String Title() {
		return T;
	}



	public boolean Available() {
		return state == STATE.AVAILABLE;
	}


	public boolean On_loan() {
		return state == STATE.ON_LOAN;
	}


	public boolean Damaged() {
		return state == STATE.DAMAGED;
	}


	public void Borrow() {
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}

	}


	public void Return(boolean DAMAGED) {
		if (state.equals(STATE.ON_LOAN)) {
			if (DAMAGED) {
				state = STATE.DAMAGED;
			}
			else {
				state = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}
	}


	public void Repair() {
		if (state.equals(STATE.DAMAGED)) {
			state = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
