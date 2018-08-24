public class FixBookControl {
	
	private FixBookUi userInterface;/*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;
	
	private Library library; // This object class name was "library" before it re-named to "Library".
	private Book currentBook; // This object class name was "book" before it re-named to "Book".


	public FixBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUi(FixBookUi userInterface) { /*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/ 
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUi except in INITIALISED state");
		}	
		this.userInterface = userInterface;
		userInterface.setState(FixBookUi.UI_STATE.READY);/*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/
		state = CONTROL_STATE.READY;		
	}


	public void bookScanned(int bookId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			userInterface.display("Invalid bookId");
			return;
		}
		if (!currentBook.damaged()) { //method name "Damaged" was re-named as "damaged"
			userInterface.display("\"Book has not been damaged");
			return;
		}
		userInterface.display(currentBook.toString());
		userInterface.setState(FixBookUi.UI_STATE.FIXING);/*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/
		state = CONTROL_STATE.FIXING;		
	}


	public void fixBook(boolean fix) {
		if (!state.equals(CONTROL_STATE.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (fix) {
			library.repairBook(currentBook);
		}
		currentBook = null;
		userInterface.setState(FixBookUi.UI_STATE.READY);/*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/
		state = CONTROL_STATE.READY;		
	}

	
	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		userInterface.setState(FixBookUi.UI_STATE.COMPLETED);/*This object class name "FixBookUI" was renamed as "FixBookUi"
        and variable name "ui" was re-named as "userInterface".*/
	}
        
}
