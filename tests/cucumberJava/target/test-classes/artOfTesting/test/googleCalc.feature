Feature: Check if username in local web page is the expected

    Scenario: check correct info for card inserted

        Given new event 1
        Given Id received is "2"
        Given Id "2" corresponds to name "ANA A"
        Given I open user page
        Then I should get name "ANA A" in header

        


#   Scenario Outline: Check Correct info loop
        
#      Given new event <number>
#      Given I get user info
#      Given I open user page 
#      Then I should get right result

#      Examples:
#         |number|
#         |1|
#         |1|


