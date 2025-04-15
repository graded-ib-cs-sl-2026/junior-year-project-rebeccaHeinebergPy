public class Library {
    private Book[] books = new Book[500];
    private String[] studentNames = new String[50]; 
    private int numBooks = 0;

    public Book findBookByTitle(String title) {
        Book book = new Book("default");
        for (int i = 0; i < numBooks; i++) {
            if (books[i].getTitle() == title) {
                book = books[i];
            }
        }
        return book;
    }

    
    private int numStudents = 0;

    public void addStudent(String name) {
        studentNames[numStudents] = name;
        numStudents++;
    // I wrote this to save the book text file into an array of books
    }

    public void addBook(String title) {
        books[numBooks] = new Book(title);
        numBooks++;
    }

    public int getNumBooks() {
        return numBooks;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public Book[] getBooks() {
        return books;
    }
}