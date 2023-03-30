package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.BorrowedBooksPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class US07_StudentBorrowBookStepDefs extends BasePage {

    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();
    BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();

    List <String> ui_book_is_borrowed;


    @Given("the user {string} on the home page")
    public void the_user_on_the_home_page(String user) {

        loginPage.login(user);
    }

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String page) {

        navigateModule(page);
        BrowserUtil.waitFor(2);

    }

    @Given("the user searches for {string} book")
    public void the_user_searches_for_book(String book) {

        bookPage.search.sendKeys(book);
        BrowserUtil.waitFor(1);

    }

    @When("the user clicks Borrow Book")
    public void the_user_clicks_borrow_book() {

        //table[@id='tbl_books']//tbody/tr[1]/td[1]
        //td[.='Self Confidence']
        //table[@id='tbl_books']//td[.=' Borrow Book']/../td[.='Self Confidence']

        bookPage.borrowBookButton(ConfigurationReader.getProperty("book")).click();

        BrowserUtil.waitFor(1);

        String expectedMessage = "The book has been borrowed...";

        String actualMessage = bookPage.toastMessage.getText();
        Assert.assertEquals(expectedMessage,actualMessage);

    }

    @Then("verify that book is shown in {string} page")
    public void verify_that_book_is_shown_in_page(String moduleName) {

        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(1);

       borrowedBooksPage.borrowedBook(ConfigurationReader.getProperty("book")).isDisplayed();

       String ui_account_HolderName = accountHolderName.getText();

        String ui_BookName = borrowedBooksPage.borrowedBook_IsMatches(ConfigurationReader.getProperty("book")).getText();


        String ui_isBorrowed = borrowedBooksPage.borrowedBook(ConfigurationReader.getProperty("book")).getText();

        String str = "0";

        String ui_isBorrowed_new = ui_isBorrowed.substring(ui_isBorrowed.length()) + str;

        ui_book_is_borrowed = new ArrayList<>();
        ui_book_is_borrowed.add(ui_account_HolderName);
        ui_book_is_borrowed.add(ui_BookName);
        ui_book_is_borrowed.add(ui_isBorrowed_new);


        System.out.println("ui_book_is_borrowed = " + ui_book_is_borrowed);


    }

    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {

        String query="select full_name, name, is_returned\n" +
                "from book_borrow bb join books b on bb.book_id = b.id\n" +
                "                    join users u on bb.user_id = u.id\n" +
                "where email = '"+ ConfigurationReader.getProperty("student_username") +
                "' and name = '" + ConfigurationReader.getProperty("book") + "' and is_returned = 0";
        DB_Util.runQuery(query);

        List<String> db_book_is_borrowed = DB_Util.getRowDataAsList(1);

        System.out.println("db_book_is_borrowed = " + db_book_is_borrowed);

        Assert.assertEquals(db_book_is_borrowed, ui_book_is_borrowed);

        borrowedBooksPage.returnBook(ConfigurationReader.getProperty("book")).click();

    }
}
