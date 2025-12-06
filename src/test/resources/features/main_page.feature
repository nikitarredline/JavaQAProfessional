Feature: Main Page Structure

  Scenario: Verify course categories
    Given "Chrome" browser is open
    When I click on random category
    Then The category page checkbox successfully
