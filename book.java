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
		StringBuilder bookDetails = new StringBuilder(); // variable name sb changed to bookDetails
		bookDetails.append("Book: ").append(ID).append("\n")
		  .append("  Title:  ").append(T).append("\n")
		  .append("  Author: ").append(A).append("\n")
		  .append("  CallNo: ").append(C).append("\n")
		  .append("  State:  ").append(state);  
		// variable sb changed to bookDetails
		// variables ID,T, A, C changed changed to sequently id, title, auther, callNo

		return bookDetails.toString(); // variable sb changed to bookDetails
	}

	public Integer getId() { // method name ID changed to getId and return type Integer changed to int
		return this.id; // variable ID changed to this.id
	}

	public String getTitle() {  // method name Title changed to getTitle
		return this.title;  // variable T changed to this.title
	}



	public boolean setAvailable() {  // method name Available chaged to setAvailable
		return state == STATE.AVAILABLE;
	}


	public boolean setOnLoan() {  // method name On_loan chaged to setOnLoan
		return state == STATE.ON_LOAN;
	}


	public boolean setDamaged() {  // method name Damaged chaged to setDamaged
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
