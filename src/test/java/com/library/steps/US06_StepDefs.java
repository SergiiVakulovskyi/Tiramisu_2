package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Map;

public class US06_StepDefs {
    BookPage bookPage = new BookPage();
    String expectedBookName, expectedBookIsbn, expectedBookYear, expectedBookAuthor, expectedBookCategory;
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String book) {
        bookPage.navigateModule("Books");
    }
    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book(){
        bookPage.addBook.click();
    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String bookName) {
        bookPage.bookName.sendKeys(bookName);
        expectedBookName = bookName;
    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String bookIsbn) {
        bookPage.isbn.sendKeys(bookIsbn);
        expectedBookIsbn = bookIsbn;
    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String bookYear) {
        bookPage.year.sendKeys(bookYear);
        expectedBookYear = bookYear;
    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String bookAuthor) {
        bookPage.author.sendKeys(bookAuthor);
        expectedBookAuthor = bookAuthor;
    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String bookCategory) {
        bookPage.categoryDropdown.sendKeys(bookCategory);
        BrowserUtil.waitFor(1);
        expectedBookCategory = bookCategory;
    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChanges.click();
        BrowserUtil.waitFor(1);
    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String actualMessage) {
        String expectedMessage = bookPage.toastMessage.getText();
        System.out.println("expectedMessage = " + expectedMessage);

        Assert.assertEquals(expectedMessage,actualMessage);
        System.out.println("actualMessage = " + actualMessage);
    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String bookName) {
        String query = "select id,name,author from books\n" +
                "where name = '"+expectedBookName+"' and author='"+expectedBookAuthor+"' order by id desc;";

       /*
//ENGIN

        String query = "select b.name,isbn,year,author, bc.name as Book_Category from books b\n" +
                "join book_categories bc on b.book_category_id = bc.id\n" +
                "where b.name='"+expectedBookName+"' and bc.name='"+expectedBookCategory+"' and year='"+expectedBookYear+"'"  +
                "having isbn='"+expectedBookIsbn+"'";

        */
        DB_Util.runQuery(query);
//ENGIN

        Map<String, String> bookInfo = DB_Util.getRowMap(1);
        System.out.println(bookInfo);
        // {name=Head First Java, isbn=10112021, year=2021, author=Kathy Sierra, Book_Category=Action and Adventure}
        // {name=The Scrum Field Guide, isbn=11112021, year=2006, author=Mitch Lacey, Book_Category=Short Story}

        String actualAuthor = bookInfo.get("author");
        String actualBookName = bookInfo.get("name");
        String actualBookCategory = bookInfo.get("Book_Category");
        String actualIsbn = bookInfo.get("isbn");
        String actualYear = bookInfo.get("year");

        Assert.assertEquals(expectedBookAuthor,actualAuthor);
        Assert.assertEquals(expectedBookCategory,actualBookCategory);
        Assert.assertEquals(expectedBookIsbn,actualIsbn);
        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedBookYear,actualYear);

        System.out.println("actualIsbn = " + actualIsbn);
        System.out.println("actualBookCategory = " + actualBookCategory);
        System.out.println("actualYear = " + actualYear);
        System.out.println("actualIsbn = " + actualIsbn);
        System.out.println("actualAuthor = " + actualAuthor);


    }


}
