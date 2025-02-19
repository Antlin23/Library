import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private BookDAO bookDAO = new BookDAO();

    public void ShowMenu(){
        System.out.println("Logga in som admin? ja/nej");

        var isAdmin = scanner.nextLine();

        System.out.println("Ange ditt val:");
        System.out.println("1. Dina aktuella lån");
        System.out.println("2. Lämna tillbaka en bok");
        System.out.println("3. Låna en bok");
        System.out.println("4. Visa alla böcker");
        if(Objects.equals(isAdmin, "ja")){
            System.out.println();
            System.out.println("ADMIN:");
            System.out.println("5. Lägg till bok");
            System.out.println("6. Ta bort bok");
            System.out.println("7. Se alla böcker och dess lånestatus");
        }
        System.out.println("0. Avsluta");

        var menuChoice = scanner.nextInt();

        switch (menuChoice){
            case 1:
                getUserBooks();
                return;
            case 2:
                returnBook();
                return;
            case 3:
                loanBook();
                return;
            case 4:
                getAllBooks();
                return;
            case 5:
                addBook();
                return;
            case 6:
                deleteBook();
                return;
            case 7:
                getAllBooks();
                return;
            case 0:
                System.out.println("Avslutar");
                return;
            default:
                System.out.println("Fel val");
        }
    }

    private void getUserBooks(){
        scanner.nextLine();

        System.out.println("Ange ditt användarnamn ");
        String userName = scanner.nextLine();

        List<Book> books = bookDAO.getUserBooks(userName);
        System.out.println("Dina lånade böcker: ");
        books.forEach(s -> System.out.println(s));
    }
    public void loanBook(){
        scanner.nextLine();

        System.out.println("Ange bokId ");
        int bookId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Ange ditt användarnamn ");
        String userName = scanner.nextLine();

        bookDAO.loanBook(bookId, userName);
    }

    public void returnBook(){
        scanner.nextLine();

        System.out.println("Ange bokId ");
        int bookId = scanner.nextInt();

        bookDAO.returnBook(bookId);
    }

    private void getAllBooks(){
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("Böcker: ");
        books.forEach(s -> System.out.println(s));
    }

    public void addBook(){
        scanner.nextLine();

        System.out.println("Ange titel ");
        String title = scanner.nextLine();

        System.out.println("Ange författare ");
        String author = scanner.nextLine();

        bookDAO.addBook(title, author);
    }

    public void deleteBook(){
        System.out.println("Ange bok id: ");
        int id = scanner.nextInt();

        bookDAO.deleteBook(id);
    }

}
