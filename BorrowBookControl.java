
import java.util.ArrayList;
import java.util.List;
//Dilan Kalpa's Class

public class BorrowBookControl {

    private BorrowBookUi ui; //Changed name to BorrowBookUi because we changed the class name "BorrowBookUI" to BorrowBookUi

    private library library; //previous instance name was "L"
    private member member;  //Changed the value 'M' to member so it makes more sense now

    private enum CONTROL_STATE {
        INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED
    };
    private CONTROL_STATE state;

    private List<book> PENDING;
    private List<loan> COMPLETED;
    private book book; //Previous name was "B" so I changed that to a more meaningful word 

    public BorrowBookControl() {
        this.library = library.INSTANCE(); //instead of word library, the letter 'L' was used before
        state = CONTROL_STATE.INITIALISED;
    }

    public void setUi(BorrowBookUi ui) { //previous name was BorrowBookUI
        if (!state.equals(CONTROL_STATE.INITIALISED)) {
            throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
        }

        this.ui = ui;
        ui.setState(BorrowBookUi.UI_STATE.READY);  //previous name was BorrowBookUI
        state = CONTROL_STATE.READY;
    }

    public void swipeMemberCard(int memberId) {  //  changed the word "Swiped" to "swipeMemberCard"
        if (!state.equals(CONTROL_STATE.READY)) {
            throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
        }

        member = library.getMember(memberId); //instead of word member, the letter 'M' was used before
        if (member == null) {                //instead of word member, the letter 'M' was used before
            ui.display("Invalid memberId");
            return;
        }
        if (library.memberCanBorrow(member)) {
            PENDING = new ArrayList<>();
            ui.setState(BorrowBookUi.UI_STATE.SCANNING);  //previous name was BorrowBookUI
            state = CONTROL_STATE.SCANNING;
        } else {
            ui.display("Member cannot borrow at this time");
            ui.setState(BorrowBookUi.UI_STATE.RESTRICTED);  //previous name was BorrowBookUI
        }
    }

    public void scannedBook(int bookId) { // //  changed the word "Scanned" to "scannedBook"
        book = null;                //instead of word book, the letter 'B' was used before
        if (!state.equals(CONTROL_STATE.SCANNING)) {
            throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
        }
        book = library.Book(bookId);
        if (book == null) {
            ui.display("Invalid bookId");
            return;
        }
        if (!book.Available()) {
            ui.display("Book cannot be borrowed");
            return;
        }
        PENDING.add(book);
        for (book B : PENDING) {
            ui.display(B.toString());
        }
        if (library.loansRemainingForMember(member) - PENDING.size() == 0) {
            ui.display("Loan limit reached");
            completeBorrowBook(); // change 'Complete' to completeBorrowBook
        }
    }

    public void completeBorrowBook() { //changed the word "Complete" to "completeBorrowBook"
        if (PENDING.size() == 0) {
            cancel();
        } else {
            ui.display("\nFinal Borrowing List");
            for (book b : PENDING) {
                ui.display(b.toString());
            }
            COMPLETED = new ArrayList<loan>();
            ui.setState(BorrowBookUi.UI_STATE.FINALISING);  //previous name was BorrowBookUI
            state = CONTROL_STATE.FINALISING;
        }
    }

    public void commitLoans() {
        if (!state.equals(CONTROL_STATE.FINALISING)) {
            throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
        }
        for (book book : PENDING) { //the object of class book is named as 'b' before modifying it to 'book'
            loan loan = library.issueLoan(book, member); //the object name was changed to book from 'b'
            COMPLETED.add(loan);
        }
        ui.display("Completed Loan Slip");
        for (loan loan : COMPLETED) {
            ui.display(loan.toString());
        }
        ui.setState(BorrowBookUi.UI_STATE.COMPLETED);
        state = CONTROL_STATE.COMPLETED;
    }

    public void cancel() {
        ui.setState(BorrowBookUi.UI_STATE.CANCELLED);  //previous name was BorrowBookUI
        state = CONTROL_STATE.CANCELLED;
    }

}
