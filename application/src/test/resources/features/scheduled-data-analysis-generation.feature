Feature: Scheduled Data Analysis Generation

  Scenario: Generate and send analysis data via sns topic
    Given the binance-api returns the data contained in "api-responses/one-minute.json" file from it's api:
    And the scheduled function was called
    When the data is sent via sns topic
    Then the data sent should contain the following values

