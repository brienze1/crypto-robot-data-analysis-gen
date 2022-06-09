Feature: Operation

  Scenario: Return no taxes when operation value is less than 20000
    Given the following operation:
      """
          [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
           {"operation":"sell", "unit-cost":15.00, "quantity": 50},
           {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
      """
    When I type in the command lines
    Then the stdout should return the following values:
      | [{"tax": 0.00},{"tax": 0.00},{"tax": 0.00}] |

