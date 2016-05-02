Feature: Check if username in local web page is the expected

    Scenario Outline: Check Correct info loop
        
    Given new event <number>
        Given I open user page 
        Then I should get right result

Examples:
|number|
|1|

