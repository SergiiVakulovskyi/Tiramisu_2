package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.DashBoardPage;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;

public class US02_StepDefs extends BasePage {

    DashBoardPage dashBoardPage= new DashBoardPage();
    String ui_NumberBorrowedBooks;

    @When("the librarian gets borrowed books number")
    public void theLibrarianGetsBorrowedBooksNumber() {
        //Actual
        String ui_NumberBorrowedBooks = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("ui_NumberBorrowedBooks = " + ui_NumberBorrowedBooks);
    }

    @Then("borrowed books number information must match with DB")
    public void borrowedBooksNumberInformationMustMatchWithDB() {
        String query="select count(*) from book_borrow where is_returned=0";
        DB_Util.runQuery(query);

        //Expected
        String db_BorrowedBookNumber = DB_Util.getCellValue(1, 1);
        System.out.println("db_BorrowedBookNumber = " + db_BorrowedBookNumber);


        Assert.assertEquals(db_BorrowedBookNumber,ui_NumberBorrowedBooks);



    }
}
