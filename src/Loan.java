import java.util.Date;

public class Loan {
    private int id;
    private String userName;
    private int bookId;
    private Date loanDate;
    private Date returnDate;

    public Loan(int id, String userName, int bookId, Date loanDate, Date returnDate) {
        this.id = id;
        this.userName = userName;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", bookId=" + bookId +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
