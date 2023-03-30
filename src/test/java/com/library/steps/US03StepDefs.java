package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class US03StepDefs extends BasePage {

    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String librarian) {
        //1.login
        loginPage.login(librarian);

        BrowserUtil.waitFor(3);

    }
    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String string) {
        navigateModule("Books");

    }
    List<String> uiBookCategoriesOptions;
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {

        //save all Book Categories dropdown options as a List<String>
        //actual
        uiBookCategoriesOptions = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);

        //remove the "ALL" option in the UI
        uiBookCategoriesOptions.remove("ALL");
        //System.out.println("actualBookCategoriesOptions = " + uiBookCategoriesOptions);

    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        //get book categories from DB
        //expected
        DB_Util.runQuery("select name from book_categories");
        List<String> dbBookCategoriesOptions = DB_Util.getColumnDataAsList(1);

        Assert.assertEquals("BOOK CATEGORIES MISMATCH", dbBookCategoriesOptions, uiBookCategoriesOptions);

    }
}
