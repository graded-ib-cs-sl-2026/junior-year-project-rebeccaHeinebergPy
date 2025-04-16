public class App {
    private Library myLibrary;
    private InputOutput io = new InputOutput();

    public void start() {
        myLibrary = readLibrary("books.txt");
        readStudents("students.txt");
        displayMenu();

    }

    // Calling this displayMenu allows me to call it later on in the code as well which is helpful 
    public void displayMenu() {
        io.output("Welcome to your library!");
        io.output("[1] Add new book to library"); 
        io.output("[2] Add new student to class"); // CHANGE THIS LATER
        io.output("[3] List books"); 
        io.output("[4] Check out a book"); 
        io.output("[5] Return a book"); 
        io.output("[6] Angry email"); 
        io.output("[0] To leave library"); 


        int choice = io.inputInt();
        
        if (choice == 1){ 

            

        }

        else if (choice == 2){ 

        }

        else if (choice == 3){
          /**  This for loop runs through all the books in the text file and lists them all, it ends when the file ends as is 
               set by: i < myLibrary.getNumBooks */

            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                io.output(myLibrary.getBooks()[i].toString());
            }
        }

        else if (choice == 4){
            io.output ("Name the book that is being checked out"); 
            String title = io.input();
            /** This for loop runs through all the books in the library and checks that the name that
             * the user inputs is an actual book in the library
             */
            boolean found = false;
            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                if (myLibrary.getBooks()[i].getTitle().equals(title)) {
                    found = true;
                    if (myLibrary.getBooks()[i].isCheckedOut()) { // This checks if the said book is already checked out or not
                        io.output("Book is already checked out by " + myLibrary.getBooks()[i].getStudent() + ". Returning to the menu.");
                    } else {
                        io.output("Who is checking out this book?"); // Originally I had a whole text file for students but I realized that just complicated things so I am not using it
                        String name = io.input();
                        io.output("The Book \"" + myLibrary.getBooks()[i].getTitle() + "\" has been successfully checked out by " + name + ".");
                        myLibrary.getBooks()[i].checkOut(name);
                        
                    }
                } 
            }
            if (!found) {
                io.output("Book not found. Returning to the menu");
            }
            
        }

        else if (choice == 5){
            io.output ("Name the book that is being returned"); 
            String Rtitle = io.input();
            /** This for loop runs through all the books in the library and checks that the name that
             * the user inputs is an actual book in the library
             */
            boolean found = false;
            for (int i = 0; i < myLibrary.getNumBooks(); i++) {
                if (myLibrary.getBooks()[i].getTitle().equals(Rtitle)) {
                    found = true;
                    if (myLibrary.getBooks()[i].isCheckedOut()) { // This checks if the book is already checked out or not
                       // Add the code that edits the text file
                        io.output("Thank you.");
                    } else {
                        io.output("This book has not been checked out. Returning to menu");
                    }
                } 
            }
            if (!found) {
                io.output("Book not found. Returning to the menu");
            }
        }

        else if (choice == 6){
        

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
            l.addBook(next);
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