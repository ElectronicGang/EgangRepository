import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ppt.assignment.pkg2.Book;  // import reference of Book class

@SuppressWarnings("serial")
public class Library implements Serializable {  // change class name from library to Library
	
	private static final String LIBRARY_FILE = "library.obj";
	private static final int LOAN_LIMIT = 2;
	private static final int LOAN_PERIOD = 2;
	private static final double FINE_PER_DAY = 1.0;
	private static final double MAX_FINES_OWED = 5.0;
	private static final double DAMAGE_FEE = 2.0;
	
	private static Library libraryObject; // changed library to Library, changed variable name form self to libraryObject
	private int bookId;  //change variable name from BID to bookId
	private int memberId;  //change variable name from MID to memberId
	private int loanId;  //change variable name from LID to loanId
	private Date loadDate;
	
	private Map<Integer, Book> catalog; // change object type book to Book
	private Map<Integer, member> members;
	private Map<Integer, loan> loans;
	private Map<Integer, loan> currentLoans;
	private Map<Integer, book> damagedBooks; // change object type book to Book
	

	private Library() {  // change constucter name from librabry to Library
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;  //change BID to bookId
		memberId = 1;  //change MID to memberId		
		loanId = 1;  //change LID to loanId		
	}

	
	public static synchronized Library INSTANCE() {	 // chnage method name from library to Library
		if (libraryObject == null) {  // change self to libraryObject
			Path path = Paths.get(LIBRARY_FILE);			
			if (Files.exists(path)) {	
				try (ObjectInputStream libraryObjectFile = new ObjectInputStream(new FileInputStream(LIBRARY_FILE));) {    // change lof object name to libraryObjectFile
			    
					libraryObject = (Library) libraryObjectFile.readObject();  // change self to libraryObject and library to Library and lof to libraryObjectFile
					Calendar.getInstance().setDate(libraryObject.loadDate);  // change self to libraryObject
					libraryObjectFile.close();  // change lof to libraryObjectFile
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else libraryObject = new Library(); // change self to libraryObject and library to Library
		}
		return libraryObject;  // change self to libraryObject
	}

	
	public static synchronized void SAVE() {
		if (libraryObject != null) {  // change self to libraryObject
			self.loadDate = Calendar.getInstance().Date();
			try (ObjectOutputStream libraryObjectFile = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE));) {  // change lof object name to libraryObjectFile
				libraryObjectFile.writeObject(libraryObject);  // change self to libraryObjectFile
				libraryObjectFile.flush();  // change self to libraryObjectFile
				libraryObjectFile.close();  // change self to libraryObjectFile	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getBookId() {  // change method name from BookID to getBookId
		return bookId;  //change BID to bookId
	}
	
	
	public int getMemberId() {  // change method name from MemberID to getMemberId
		return memberId;  //change MID to memberId		
	}
	
	
	private int getNextBookId() {  // change method name from nextBID to getNextBookId
		return bookId++;  //change BID to bookId
	}

	
	private int getNextMemberId() {  // change method name from nextMID to getNextMemberId
		return memberId++;  //change MID to memberId		
	}

	
	private int getNextLoanId() {  // change method name from nextLID to getNextLoanId
		return loanId++;  //change LID to loanId				
	}

	
	public List<member> getMembers() {  // change method name from Members to getMembers	
		return new ArrayList<member>(members.values()); 
	}


	public List<Book> getBooks() { // changed object type from book to Book, change method name from Books to getBooks		
		return new ArrayList<book>(catalog.values()); 
	}


	public List<loan> getCurrentLoans() {  // change method name from CurrentLoans to getCurrentLoans	
		return new ArrayList<loan>(currentLoans.values());
	}


	public member Add_mem(String lastName, String firstName, String email, int phoneNo) {		
		member member = new member(lastName, firstName, email, phoneNo, getNextMemberId());  // changed method from nextMID to getNextMemberId
		members.put(member.getId(), member);		
		return member;
	}

	
	public Book addBook(String author, String title, String callNo) {	// changed return type from book to Book, change method name from Add_book to addBook, changed parameters a, t, c to author, title, callNo	
		// changed object name b to book
		Book book = new Book(author, title, callNo, getNextBookId());   // changed method from nextBID to getNextBookId, changed object type from book to Book, changed arguments from a, t, c to author, title, callNo		
		catalog.put(book.ID(), book);	// changed object b to book	
		return book;	// changed object b to book
	}

	
	public member getMember(int memberId) {
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public Book getBook(int bookId) { // change return type from book to Book, change method name from Book to getBook	
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int getLoanLimit() {  // changed loanLimit to getLoanLimit
		return LOAN_LIMIT;
	}

	
	public boolean memberCanBorrow(member member) {		
		if (member.getNumberOfCurrentLoans() == LOAN_LIMIT ) 
			return false;
				
		if (member.getFinesOwed() >= MAX_FINES_OWED) 
			return false;
				
		for (loan loan : member.getLoans()) 
			if (loan.isOverDue()) 
				return false;
			
		return true;
	}

	
	public int loansRemainingForMember(member member) {		
		return LOAN_LIMIT - member.getNumberOfCurrentLoans();
	}

	
	public loan issueLoan(Book book, member member) { // change parameter object type from book to Book	
		Date dueDate = Calendar.getInstance().getDueDate(LOAN_PERIOD);
		loan loan = new loan(getNextLoanId(), book, member, dueDate);  // change method from nextLID to getNextLoanId
		member.takeOutLoan(loan);
		book.Borrow();
		loans.put(loan.getId(), loan);
		currentLoans.put(book.ID(), loan);
		return loan;
	}
	
	
	public loan getLoanByBookId(int bookId) {
		if (currentLoans.containsKey(bookId)) {
			return currentLoans.get(bookId);
		}
		return null;
	}

	
	public double calculateOverDueFine(loan loan) {
		if (loan.isOverDue()) {
			long daysOverDue = Calendar.getInstance().getDaysDifference(loan.getDueDate());
			double fine = daysOverDue * FINE_PER_DAY;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(loan currentLoan, boolean isDamaged) {
		member member = currentLoan.Member();
		Book book  = currentLoan.Book();   // change object type from book to Book	
		
		double overDueFine = calculateOverDueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);
		book.Return(isDamaged);
		if (isDamaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooks.put(book.ID(), book);
		}
		currentLoan.Loan();
		currentLoans.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (loan loan : currentLoans.values()) {
			loan.checkOverDue();
		}		
	}


	public void repairBook(Book currentBook) { // change object type from book to Book	
		if (damagedBooks.containsKey(currentBook.ID())) {
			currentBook.Repair();
			damagedBooks.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}
