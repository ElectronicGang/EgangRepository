import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable {
	// The class name should start with a Uppercase letter. Therefore it must be corrected as Loan
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int loanId;
	//It should start with a lowercase letter.Therefore it must be corrected as loanID.
	private Book book;
	// The variable name should be given meaning name
	private Member member;
	// The variable name should be given meaning name
	private Date dueDate;
	// The variable name should be given meaning name
	private LOAN_STATE state;

	
	public Loan(int loanId, book book, member member, Date dueDate) {
		this.ID = loanId;
		this.B = book;
		this.M = member;
		this.D = dueDate;
		this.state = LOAN_STATE.CURRENT;
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(D)) {
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getId() {
		return ID;
	}


	public Date getDueDate() {
		return D;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(ID).append("\n")
		  .append("  Borrower ").append(M.getId()).append(" : ")
		  .append(M.getLastName()).append(", ").append(M.getFirstName()).append("\n")
		  .append("  Book ").append(B.ID()).append(" : " )
		  .append(B.Title()).append("\n")
		  .append("  DueDate: ").append(sdf.format(D)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public member member() {
		return M;
	}


	public book book() {
		return B;
	}


	public void loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
