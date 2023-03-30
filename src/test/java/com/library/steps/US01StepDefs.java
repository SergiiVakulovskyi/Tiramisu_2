package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class US01StepDefs {
    List<String> actualColumnsList;

    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {

        String query="select * from users";
        DB_Util.runQuery(query);
        actualColumnsList = DB_Util.getAllColumnNamesAsList();
        System.out.println("actualList = " + actualColumnsList);
    }


    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> columnsList) {

        System.out.println("expectedList = " + columnsList);
        // Assertions
        Assert.assertEquals(columnsList, actualColumnsList);
    }


}
