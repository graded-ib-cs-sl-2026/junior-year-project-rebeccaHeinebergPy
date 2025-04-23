import java.time.LocalDate;

public class App {
    private Library myLibrary;
    private Book book; 
    private InputOutput io = new InputOutput();

    public void start() {
        myLibrary = readLibrary("books.txt");
        readStudents("students.txt"); // THIS IS USELESS
        displayMenu();

    }

    public void updateFile() { // I made this a method so I could call it multiple times
        io.openWriteFile("books.txt"); // Clears the file and replaces it with new book array with all the updated information
        Book[] books = myLibrary.getBooks();
        for (int i = 0; i < myLibrary.getNumBooks(); i++) {
            io.writeToFile(books[i]);
        }
        io.closeWriteFile();
    }

    // Calling this displayMenu allows me to call it later on in the code as well
    // which is helpful
    public void displayMenu() {
        updateFile(); // I put this here so that the file is updated every time something is edited

        io.output("Welcome to your library!");
        io.output("[1] Add new book to library");
        io.output("[2] List books");
        io.output("[3] Check out a book");
        io.output("[4] Return a book");
        io.output("[5] Angry email");
        io.output("[0] To leave library");

        int choice = io.inputInt();

        if (choice == 1) {
            io.output("What is the title of the book you would like to add to your library?");
            Book newbook = new Book(io.input());
            // Edits the array of books:
            myLibrary.getBooks()[myLibrary.getNumBooks()] = newbook; // I made the input a book so I could save it to
                                                                    // the file
            myLibrary.setNumBooks(myLibrary.getNumBooks() + 1);
        }

        else if (choice == 2) {
            /**
             * This for loop runs through all the books in the text file and lists them all,
             * it ends when the file ends as is
             * set by: i < myLibrary.getNumBooks
             */

            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                io.output(myLibrary.getBooks()[i].toString());
            }
        }

        else if (choice == 3) {
            io.output("Name the book that is being checked out");
            String title = io.input();
            /**
             * This for loop runs through all the books in the library and checks that the
             * name that
             * the user inputs is an actual book in the library
             */
            boolean found = false;
            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                if (myLibrary.getBooks()[i].getTitle().equals(title)) {
                    found = true;
                    if (myLibrary.getBooks()[i].isCheckedOut()) { // This checks if the said book is already checked out
                                                                  // or not
                        io.output("Book is already checked out by " + myLibrary.getBooks()[i].getStudent()
                                + ". Returning to the menu.");
                    } else {
                        io.output("Who is checking out this book?"); // Originally I had a whole text file for students
                                                                     // but I realized that just complicated things so I
                                                                     // am not using it
                        String name = io.input();
                        io.output("The Book \"" + myLibrary.getBooks()[i].getTitle()
                                + "\" has been successfully checked out by " + name + ".");
                        myLibrary.getBooks()[i].checkOut(name);
                    }
                }
            }
            if (!found) {
                io.output("Book not found. Returning to the menu");
            }

        }

        else if (choice == 4) {
            io.output("Name the book that is being returned");
            String Rtitle = io.input();
            /**
             * This for loop runs through all the books in the library and checks that the
             * name that
             * the user inputs is an actual book in the library
             */
            boolean found = false;
            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                if (myLibrary.getBooks()[i].getTitle().equals(Rtitle)) {
                    found = true;
                    if (myLibrary.getBooks()[i].isCheckedOut()) { // This checks if the book is already checked out or not
                        io.output("Thank you.");
                        myLibrary.getBooks()[i].setCheckedOut(false); // Updates the file 
                        myLibrary.getBooks()[i].setCheckedOutDate(null); 
                    } else {
                        io.output("This book has not been checked out. Returning to menu");
                    }
                }
            }
            if (!found) {
                io.output("Book not found. Returning to the menu");
            }
        }

        else if (choice == 5) {
           boolean anyOverdue = false; 

            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                Book b = myLibrary.getBook(i);
                
                if (b.getDaysGone() > 21) {
                   anyOverdue = true; // Without this my code would print out "None of your students have checked out a book for too long..." for every single book that wasnt checked out for too long when I only intedned it to print out if ALL the books were looking good. 
                    io.output("Dear " + myLibrary.getBook(i).getStudent() + ", you have had the book \""
                            + myLibrary.getBook(i).getTitle() + "\" for " + myLibrary.getBook(i).getDaysGone()
                            + " days. In this classroom we have an agreement, 21 days maximum!!! RETURN THE BOOK TOMORROW.");
                } 
            }
                
            if (!anyOverdue) { // Only runs if all the books are returned in the appropriate time 
                    io.output(
                            "None of your students have checked out a book for too long!! All is good :) Returning to menu");
            }
            
        }

        else if (choice == 0) {
            System.exit(0); // This exits the code so the user has a way out
        }
        displayMenu();
    }

    public Library readLibrary(String filename) {
        Library l = new Library();
        try {
            io.openFile(filename);
        } catch (Exception e) {
            io.output(e.toString());
            return l;

        }

        while (io.fileHasNextLine()) { // I had to edit this as it originally read the entire line as the title
            String next = io.getNextLine(); 
            String[] parts = next.split("//"); // This part of the code separates the tile from the availability from the student. That is why I used //. I was originally going to use commas but those might be present in book titles
            Book b = l.addBook(parts[0].trim());
            if (parts[1].trim().equals("Unavailable")) {
                b.setCheckedOut(true);
                b.setStudent(parts[2].trim()); // "trim" makes it so that it ignores any spaces as theyre probably not intentional
                try {
                    b.setCheckedOutDate(LocalDate.parse(parts[3].trim()));
                } catch (Exception e) {
                    io.output("Book is not checked out silly " + b.getTitle());
                }            
            }
        }

        return l;
    }

    public void readStudents(String filename) {
        try {
            io.openFile(filename);
        } catch (Exception e) {
            io.output(e.toString());
            return;
        }

        while (io.fileHasNextLine()) {
            String next = io.getNextLine();
            myLibrary.addStudent(next);
        }
    }

    public static void main(String[] args) {
        new App().start();
    }
}




