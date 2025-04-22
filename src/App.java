public class App {
    private Library myLibrary;
    private InputOutput io = new InputOutput();

    public void start() {
        myLibrary = readLibrary("books.txt");
        readStudents("students.txt");
        displayMenu();

    }

    public void updateFile() {
        io.openWriteFile("books.txt");
        Book[] books = myLibrary.getBooks();
        for (int i = 0; i < myLibrary.getNumBooks(); i++) {
            io.writeToFile(books[i]);
        }
        io.closeWriteFile();
    }

    // Calling this displayMenu allows me to call it later on in the code as well
    // which is helpful
    public void displayMenu() {
        updateFile();

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
                    if (myLibrary.getBooks()[i].isCheckedOut()) { // This checks if the book is already checked out or
                                                                  // not
                        io.output("Thank you.");
                        myLibrary.getBooks()[i].setCheckedOut(false);
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
            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                Book b = myLibrary.getBook(i);
                if (b.getDaysGone() > 21) {
                    io.output("Dear " + myLibrary.getBook(i).getStudent() + ", you have had the book \""
                            + myLibrary.getBook(i).getTitle() + "\" for " + myLibrary.getBook(i).getDaysGone()
                            + " days. In this classroom we have an agreement, 21 days maximum!!! RETURN THE BOOK TOMORROW.");
                } else {
                    io.output(
                            "None of your students have checked out a book for too long!! All is good :) Returning to menu");
                }
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

        while (io.fileHasNextLine()) {
            String next = io.getNextLine();
            String[] parts = next.split("//");
            Book b = l.addBook(parts[0].trim());
            if (parts[1].trim().equals("Unavailable")) {
                b.setCheckedOut(true);
                b.setStudent(parts[2].trim());
                b.setDaysGone(Integer.parseInt(parts[3].trim()));
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