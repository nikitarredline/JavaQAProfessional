Feature: Course Structure

  Scenario: Verify course by title page opens
    Given "Chrome" browser is open
    Given Courses catalog page is opened
    When I click on a course by title "Дизайн сетей ЦОД"
    Then The course page opens by title successfully

  Scenario: Verify random course page opens
    Given "Chrome" browser is open
    Given Courses catalog page is opened
    When I click on a random course
    Then The course page opens by title successfully

  Scenario: Verify earliest random course page opens
    Given "Chrome" browser is open
    Given Courses catalog page is opened
    When I click on a earliest random course
    Then The course page opens by title successfully
    Then The course page opens by date successfully

  Scenario: Verify latest random course page opens
    Given "Chrome" browser is open
    Given Courses catalog page is opened
    When I click on a latest random course
    Then The course page opens by title successfully
    Then The course page opens by date successfully

  Scenario: Search courses starting on or after the specified date
    Given "Chrome" browser is open
    When Courses catalog page is opened
    Then Print courses starting from earliest date "2025-11-24"

  Scenario: qweqe
    Given "Chrome" browser is open
    Given Open a preparatory category
    Then The course page opens by title successfully
