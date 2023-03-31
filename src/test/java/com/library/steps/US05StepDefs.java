package com.library.steps;


import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class US05StepDefs {

    String actualGenre;
    @Given("Establish the database connection.")
    public void establish_the_database_connection() {

        // Make conn with database
        DB_Util.createConnection();
    }
    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() throws SQLException {
        String query = "SELECT bc.name, COUNT(*) FROM book_borrow bb " +
                "INNER JOIN books b ON bb.book_id = b.id " +
                "INNER JOIN book_categories bc ON b.book_category_id = bc.id " +
                "GROUP BY bc.name " +
                "ORDER BY 2 DESC";

        ResultSet resultSet = DB_Util.runQuery(query);
        if (resultSet.next()) {
            actualGenre = resultSet.getString("bc.name");
        }

        System.out.println(actualGenre);
    }
    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedGenre)  {

        assertEquals(expectedGenre, actualGenre);
        assertNotNull(actualGenre);

        System.out.println(expectedGenre);


    }


}
