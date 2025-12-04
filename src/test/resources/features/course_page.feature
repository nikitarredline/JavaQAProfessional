Feature: Course Structure

  Scenario: Verify course page opens
    Given Courses catalog page is opened
    When I click on a random course
    Then The course page opens successfully

  Scenario: Verify earliest course page opens
    Given Courses catalog page is opened
    When I click on a earliest course
    Then The course page opens by date successfully

  Scenario: Verify earliest course page opens
    Given Courses catalog page is opened
    When I click on a latest course
    Then The course page opens by date successfully
