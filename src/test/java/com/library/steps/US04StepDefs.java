package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.util.Map;

public class US04StepDefs {

    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String user) {
            loginPage.login(user);
            BrowserUtil.waitFor(4);
        }

    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
            new DashBoardPage().navigateModule(moduleName);
        }

    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String string) {
        bookPage.searchBook(string);
        BrowserUtil.waitFor(4);
    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        bookPage.editBook("Clean Code").click();
        BrowserUtil.waitFor(4);
    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {

        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthorName = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualDesc = bookPage.description.getAttribute("value");

        System.out.println("actualISBN = " + actualISBN);

        String query = "select * from books\n" +
                "where name = 'Clean Code'\n" +
                "order by isbn desc ";

        DB_Util.runQuery(query);
        Map<String, String> bookInfo = DB_Util.getRowMap(1);

        String expectedBookName = bookInfo.get("name");
        String expectedAuthorName =bookInfo.get("author");
        String expectedISBN = bookInfo.get("isbn");
        String expectedYear = bookInfo.get("year");
        String expectedDesc = bookInfo.get("description");

        System.out.println("expectedAuthorName = " + expectedAuthorName);
        System.out.println("expectedISBN = " + expectedISBN);

        Assert.assertEquals(actualBookName,expectedBookName);
        Assert.assertEquals(actualAuthorName,expectedAuthorName);
        Assert.assertEquals(actualISBN,expectedISBN);
        Assert.assertEquals(actualYear,expectedYear);
        Assert.assertEquals(bookPage.description.getAttribute("value"),bookInfo.get("description"));


    }

}
