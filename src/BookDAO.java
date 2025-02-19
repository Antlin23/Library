import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDAO {
    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getBoolean("available")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void addBook(String title, String author){
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";

        try{
            Connection conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);

            System.out.println("Bok tillagd");

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBook(int id){
        String sql = "DELETE FROM books WHERE id= (?)";
        String sqlLoan = "DELETE FROM loans WHERE bookId= (?)";

        try{
            Connection conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            PreparedStatement stmtLoan = conn.prepareStatement(sqlLoan);
            stmtLoan.setInt(1, id);

            stmtLoan.executeUpdate();
            stmt.executeUpdate();

            System.out.println("Bok borttagen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loanBook(int bookId, String userName){
        String sql = "INSERT INTO loans (userName, bookId) VALUES (?, ?);";

        String sqlBook = "UPDATE books SET available = (?) WHERE id= (?);";

        String getBook = "SELECT * FROM books WHERE id=(?);";


        try{
            Connection conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);

            PreparedStatement stmtBook = conn.prepareStatement(sqlBook);
            stmt.setInt(2, bookId);
            stmtBook.setBoolean(1,false);
            stmtBook.setInt(2, bookId);


            PreparedStatement getBooks = conn.prepareStatement(getBook);
            getBooks.setInt(1, bookId);
            ResultSet rs = getBooks.executeQuery();

            while (rs.next()){
                if(rs.getBoolean("available") == true){
                    stmt.executeUpdate();
                    stmtBook.executeUpdate();
                    System.out.println("Bok lånad");
                } else{
                    System.out.println("Den är ej tillgänglig");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId){
        String sqlBook = "UPDATE books SET available = (?) WHERE id= (?);";

        String sqlLoan = "DELETE FROM loans WHERE bookId = (?)";

        try{
            Connection conn = Database.getConnection();

            PreparedStatement stmtBook = conn.prepareStatement(sqlBook);
            stmtBook.setBoolean(1,true);
            stmtBook.setInt(2, bookId);

            PreparedStatement stmtLoan = conn.prepareStatement(sqlLoan);
            stmtLoan.setInt(1,bookId);

            stmtBook.executeUpdate();
            stmtLoan.executeUpdate();
            System.out.println("Bok tillbakalämnad");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getUserBooks(String userName){
        List<Integer> bookIds = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        String loansSql = "SELECT * FROM loans WHERE userName = (?)";

        String booksSql = "SELECT * FROM books WHERE id = (?) AND available = (?);";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmtLoans = conn.prepareStatement(loansSql);
            stmtLoans.setString(1, userName);
            ResultSet rs = stmtLoans.executeQuery();

            while (rs.next()){
                if(Objects.equals(rs.getString("userName"), userName)){
                    bookIds.add(rs.getInt("bookId"));
                }
            }

            for(var bookId : bookIds){
                PreparedStatement stmtBooks = conn.prepareStatement(booksSql);
                stmtBooks.setInt(1, bookId);
                stmtBooks.setBoolean(2, false);
                ResultSet rsBook = stmtBooks.executeQuery();

                while (rsBook.next()){
                    books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getBoolean("available")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
