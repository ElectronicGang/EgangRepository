
import java.util.Scanner;

public class BorrowBookUi { //class changed to BorrowBookUi from BorrowBookUI

    public static enum UI_STATE {
        INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED
    };

    private BorrowBookControl control;
    private Scanner input;
    private UI_STATE state;

    public BorrowBookUi(BorrowBookControl control) { //changed constructor name to BorrowBookUi
        this.control = control;
        input = new Scanner(System.in);
        state = UI_STATE.INITIALISED;
        control.setUi(this);
    }

    private String input(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    private void output(String message) { //Changed parameter type to String from Object
        System.out.println(message);       // Parameter name is changed to 'message' instead of 'object'
    }

    public void setState(UI_STATE state) {
        this.state = state;
    }

    public void run() {
        output("Borrow Book Use Case UI\n");

        while (true) {

            switch (state) {

                case CANCELLED:
                    output("Borrowing Cancelled");
                    return;

                case READY:
                    String memStr = input("Swipe member card (press <enter> to cancel): ");
                    if (memStr.length() == 0) {
                        control.cancel();
                        break;
                    }
                    try {
                        int memberId = Integer.valueOf(memStr).intValue();
                        control.swipeMemberCard(memberId);          // Change the method name to 'swipeMemberCard' from "Swiped" in BorrowBookControl Class
                    } catch (NumberFormatException e) {
                        output("Invalid Member Id");
                    }
                    break;

                case RESTRICTED:
                    input("Press <any key> to cancel");
                    control.cancel();
                    break;

                case SCANNING:
                    String bookStr = input("Scan Book (<enter> completes): ");
                    if (bookStr.length() == 0) {
                        control.completeBorrowBook(); //Change the method name to 'completeBorrowBook' from "Complete" in BorrowBookControl Class
                        break;
                    }
                    try {
                        int bookId = Integer.valueOf(bookStr).intValue();
                        control.Scanned(bookId);

                    } catch (NumberFormatException e) {
                        output("Invalid Book Id");
                    }
                    break;

                case FINALISING:
                    String answer = input("Commit loans? (Y/N): "); //previous word was 'ans' for current variable answer
                    if (answer.toUpperCase().equals("N")) {
                        control.cancel();

                    } else {
                        control.commitLoans();
                        input("Press <any key> to complete ");
                    }
                    break;

                case COMPLETED:
                    output("Borrowing Completed");
                    return;

                default:
                    output("Unhandled state");
                    throw new RuntimeException("BorrowBookUI : unhandled state :" + state);
            }
        }
    }

    public void display(String disMessage) { // Parameter name is changed to String instead of 'object'
        output(disMessage);
    }

}
