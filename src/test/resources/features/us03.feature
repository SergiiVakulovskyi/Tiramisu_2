
Feature: As a data consumer, I want UI and DB book categories are match.

  @monica @ui @db @B28G28-131
  Scenario: Verify Book Categories dropdown menu options match between UI and DB
    Given the "librarian" on the home page
    When the user navigates to "Books" page
    And the user clicks book categories
    Then verify book categories must match book_categories table from db