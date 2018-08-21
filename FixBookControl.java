public class FixBookControl {
	
	private FixBookUi userInterface;/*This object class name was "FixBookUI"
        and variable name was "ui" before they re-named.*/
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;
	
	private Library library; // This object class name was "library" before it re-named.
	private Book currentBook; // This object class name was "book" before it re-named.


	public FixBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUi(FixBookUi ui) { // The method name was "SetUI" before it re-named. 
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUi except in INITIALISED state");
		}	
		this.userInterface = ui;
		ui.setState(FixBookUi.UI_STATE.READY);
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
		if (!currentBook.Damaged()) {
			userInterface.display("\"Book has not been damaged");
			return;
		}
		userInterface.display(currentBook.toString());
		userInterface.setState(FixBookUi.UI_STATE.FIXING);
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
		userInterface.setState(FixBookUi.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

	
	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		userInterface.setState(FixBookUi.UI_STATE.COMPLETED);		
	}
        
}
