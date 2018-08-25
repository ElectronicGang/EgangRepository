import java.util.Scanner;


public class FixBookUi {

	public static enum UI_STATE { INITIALISED, READY, FIXING, COMPLETED };

	private FixBookControl control;
	private Scanner inputString;// variable "input" re-named as "inputString"
	private UI_STATE state;

	
	public FixBookUi(FixBookControl control) {
		this.control = control;
		inputString = new Scanner(System.in); // variable "input" re-named as "inputString"
		state = UI_STATE.INITIALISED;
		control.setUi(this);
	}


	public void setState(UI_STATE state) {
		this.state = state;
	}

	
	public void run() {
		outputObject("Fix Book Use Case UI\n"); //method "output" re-named as "outputObject"
		
		while (true) {
			
			switch (state) {
			
			case READY:
				String bookString = input("Scan Book (<enter> completes): ");// String variable name "bookStr" was re-named as "bookString".
				if (bookString.length() == 0) {// String variable name "bookStr" was re-named as "bookString".
                                    control.scanningComplete();//Text alignment one tab moved left.
				}
				else {
                                    try {
                                        int bookId = Integer.valueOf(bookString).intValue();// String variable name "bookStr" was re-named as "bookString".
                                        control.bookScanned(bookId);
                                    }
                                    catch (NumberFormatException e) {
					outputObject("Invalid bookId"); //method "output" re-named as "outputObject"
                                    }
				}
				break;		
				
			case FIXING:
				String answer = input("Fix Book? (Y/N) : "); // This String variable name was "ans" before re-named.
				boolean fix = false;
				if (answer.toUpperCase().equals("Y")) {
					fix = true;
				}
				control.fixBook(fix);
				break;
								
			case COMPLETED:
				outputObject("Fixing process complete"); //method "output" re-named as "outputObject"
				return;
			
			default:
				outputObject("Unhandled state"); //method "output" re-named as "outputObject"
				throw new RuntimeException("FixBookUI : unhandled state :" + state);			
			
			}		
		}
		
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return inputString.nextLine(); // variable "input" re-named as "inputString"
	}	
		
		
	private void outputObject(Object object) { //method "output" re-named as "outputObject"
		System.out.println(object);
	}
	

	public void displayObject(Object object) { //method "display" re-named as "displayObject"
		outputObject(object); //method "output" re-named as "outputObject"
	}
	
	
}
