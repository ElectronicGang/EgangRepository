import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {
	
	private static Scanner INPUT; //variable name "IN" re-named as "INPUT"
	private static Library LIBRARY; //variable name "LIB" re-named as "LIBRARY"
	private static String MENU;
	private static Calendar CALENDAR; //variable name "CAL" re-named as "CALENDAR"
	private static SimpleDateFormat DATE_FORMAT; //variable name "SDF" re-named as "DATE_FORMAT"
	
	
	private static String GetMenu() { // Method name "Get_menu()" re-named as "GetMenu()"
		StringBuilder stringBuilder = new StringBuilder(); // Object name "sb" re-named as "stringBuilder"
		
		stringBuilder.append("\nLibrary Main Menu\n\n") // Object name "sb" re-named as "stringBuilder"
		  .append("  M  : add member\n")
		  .append("  LM : list members\n")
		  .append("\n")
		  .append("  B  : add book\n")
		  .append("  LB : list books\n")
		  .append("  FB : fix books\n")
		  .append("\n")
		  .append("  L  : take out a loan\n")
		  .append("  R  : return a loan\n")
		  .append("  LL : list loans\n")
		  .append("\n")
		  .append("  P  : pay fine\n")
		  .append("\n")
		  .append("  T  : increment date\n")
		  .append("  Q  : quit\n")
		  .append("\n")
		  .append("Choice : ");
		  
		return stringBuilder.toString(); // Object name "sb" re-named as "stringBuilder"
	}


	public static void main(String[] args) {		
		try {			
			INPUT = new Scanner(System.in);//variable name "IN" re-named as "INPUT"
			LIBRARY = Library.INSTANCE(); //variable name "LIB" re-named as "LIBRARY"
			CALENDAR = Calendar.getInstance(); //variable name "CAL" re-named as "CALENDAR"
			DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy"); //variable name "SDF" re-named as "DATE_FORMAT"
	
			for (Member member : LIBRARY.getMembers()) { /* Class name "member" re-named as "Member", 
                            obeject name "m" re-named as "member", "variable name "LIB" re-named as "LIBRARY" and
                            method name "Members()" re-named as "getMembers()" */
				output(member);
			}
			output(" ");
			for (Book b : LIBRARY.getBooks()) { //variable name "LIB" re-named as "LIBRARY"
				output(b);
			}
						
			MENU = GetMenu(); // Method name "Get_menu()" re-named as "GetMenu()"
			
			boolean error = false; // variable name "e" re-named as "error" 
			
			while (!error) { // variable name "e" re-named as "error" 
				
				output("\n" + DATE_FORMAT.format(CALENDAR.Date())); /* variable name "CAL" re-named as "CALENDAR" and 
                                variable name "SDF" re-named as "DATE_FORMAT" */
				String choise = input(MENU); // variable name "c" re-named as "choise" 
				
				switch (choise.toUpperCase()) { // variable name "c" re-named as "choise" 
				
				case "M": 
					addMember();
					break;
					
				case "LM": 
					listMembers();
					break;
					
				case "B": 
					addBook();
					break;
					
				case "LB": 
					listBooks();
					break;
					
				case "FB": 
					fixBooks();
					break;
					
				case "L": 
					borrowBook();
					break;
					
				case "R": 
					returnBook();
					break;
					
				case "LL": 
					listCurrentLoans();
					break;
					
				case "P": 
					payFine();
					break;
					
				case "T": 
					incrementDate();
					break;
					
				case "Q": 
					error = true; // variable name "e" re-named as "error" 
					break;
					
				default: 
					output("\nInvalid option\n");
					break;
				}
				
				Library.SAVE();
			}			
		} catch (RuntimeException exception) { // Variable "e" re-named as "exception"
			output(exception);
		}		
		output("\nEnded\n");
	}	

	
	private static void payFine() {
		new PayFineUI(new PayFineControl()).run();		
	}


	private static void listCurrentLoans() {
		output("");
		for (Loan loan : LIBRARY.CurrentLoans()) { /* Class name "Loan" re-named as "Loan"
                    variable name "LIB" re-named as "LIBRARY" */
			output(loan + "\n");
		}		
	}



	private static void listBooks() {
		output("");
		for (Book book : LIBRARY.getBooks()) { /* Method name "getBooks()" re-named as "getBooks()"
                    variable name "LIB" re-named as "LIBRARY" */
			output(book + "\n");
		}		
	}



	private static void listMembers() {
		output("");
		for (Member member : LIBRARY.getMembers()) { /* variable name "LIB" re-named as "LIBRARY" and 
                    method name "Members()" re-named as "getMembers()" */
			output(member + "\n");
		}		
	}



	private static void borrowBook() {
		new BorrowBookUI(new BorrowBookControl()).run();		
	}


	private static void returnBook() {
		new ReturnBookUI(new ReturnBookControl()).run();		
	}


	private static void fixBooks() {
		new FixBookUi(new FixBookControl()).run();		
	}


	private static void incrementDate() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			CALENDAR.incrementDate(days); //variable name "CAL" re-named as "CALENDAR"
			LIBRARY.checkCurrentLoans(); //variable name "LIB" re-named as "LIBRARY"
			output(DATE_FORMAT.format(CALENDAR.Date())); /* variable name "CAL" re-named as "CALENDAR" and
                        variable name "SDF" re-named as "DATE_FORMAT" */
			
		} catch (NumberFormatException exception) { // Variable "e" re-named as "exception"
			 output("\nInvalid number of days\n");
		}
	}


	private static void addBook() {
		
		String author = input("Enter author: ");
		String title  = input("Enter title: ");
		String callNo = input("Enter call number: ");
		Book book = LIBRARY.Add_book(author, title, callNo); //variable name "LIB" re-named as "LIBRARY"
		output("\n" + book + "\n");
		
	}

	
	private static void addMember() {
		try {
			String lastName = input("Enter last name: ");
			String firstName  = input("Enter first name: ");
			String email = input("Enter email: ");
			int phoneNo = Integer.valueOf(input("Enter phone number: ")).intValue();
			Member member = LIBRARY.Add_mem(lastName, firstName, email, phoneNo); //variable name "LIB" re-named as "LIBRARY"
			output("\n" + member + "\n");
			
		} catch (NumberFormatException exception) { // Variable "e" re-named as "exception" 
			 output("\nInvalid phone number\n");
		}
		
	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return INPUT.nextLine(); //variable name "IN" re-named as "INPUT"
	}
	
	
	
	private static void output(Object object) {
		System.out.println(object);
	}

	
}
