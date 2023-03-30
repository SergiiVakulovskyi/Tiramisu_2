package com.library.pages;

import com.library.utility.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BorrowedBooksPage extends BasePage{


    @FindBy(xpath = "//tbody//td[2]")
    public List<WebElement> allBorrowedBooksName;

    public WebElement borrowedBook_IsMatches(String book) {
        String xpath = "//table[@id='borrowed_list']//td[.='NOT RETURNED ']/../td[.='" + book + "']";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement returnBook(String book) {
        String xpath = "//table[@id='borrowed_list']//td[.='NOT RETURNED ']/../td[.='" + book + "']/preceding-sibling::td//a[@role='button']";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement borrowedBook(String book) {
        String xpath = "//table[@id='borrowed_list']//td[.='" + book + "']/../td[.='NOT RETURNED ']";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }
}
