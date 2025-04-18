import java.time.LocalDate;
import java.time.temporal.ChronoUnit; 
/** The purpose of this was so that I could check for how long a book has been checked out for 
 * I had to google how to do this, I used "Oracle Help Center"
*/

public class Book {
    private String title;
    private boolean checkedOut = false;
    private int daysGone = 0;
    private LocalDate checkedOutDate;
    private String student = "";
    // Default setting for each book 

    public Book(String theTitle) {
        title = theTitle;
    }
    // So that I can compare the books title to the title that the user inputs 

    public void checkOut(String student) {
        this.checkedOut = true;
        this.checkedOutDate = LocalDate.now();
    // Whenever a book is checked out, this code saves the date that the book was checked out so I can find out how long it has been gone for
        this.student = student;
}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
    // Originally I had saved the checked out date here but I realized that I don't always need it so I made it two separate things

    public int getDaysGone() {
        final LocalDate today = LocalDate.now();
        return (int)ChronoUnit.DAYS.between(checkedOutDate, today);
    /**  Here I call the code that I wrote to check the local date and use it to find out how long 
     * a book has been checked out for by checking how many days between when it has been checked out and the current date
     */
    }

    public void setDaysGone(int daysGone) {
        this.daysGone = daysGone;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    @Override
    public String toString() {
        if (checkedOut) { 
            return ("Title: " + title + ", Status: Checked out by " + student + " for " + daysGone + " days.");
        } else {
            return ("Title: " + title + ", Status: Available");
        }
    }
}
