Feature: Payment service can receive and perform money transfers between accounts

  Payments service holds information about customer accounts and accepts money transfer requests from other services.
  Payment orders should come from our customer accounts only (debtor).
  Otherwise, service reports that it is not allowed to make money transfer.

  If payment beneficiary (creditor) account belongs to another our customer payment service executes order immediately
  and creates double transaction entries transfer operation respectively related to proper accounts and payment orders.
  Otherwise, service keeps payment order in pending state.

  Payment service implements minimalistic field validation constraints and rules
  (f.e. not valid to have creditor and debtor accounts).

  Scenario Outline: Receive and validate money transfers between different customer accounts
    Given service has active customer account "LV80BANK0000435195001"
    And service has active customer account "LV81BANK0000435195002"
    When transfer <amount> <ccy> from <debtor>/<debtorAccount> to <creditor>/<creditorAccount> with "<details>"
    Then service responds with <expected-result>

    Examples:
      | amount | ccy | debtor  | debtorAccount         | creditor | creditorAccount       | details | expected-result |
      | 10     | EUR | Vladmir | LV80BANK0000435195001 | Jegor    | LV81BANK0000435195002 | detals  | CREATED         |
      | ABC    | EUR | Vladmir | LV80BANK0000435195001 | Jegor    | LV81BANK0000435195002 | detals  | INVALID         |
      | 1      | EUR |         | LV80BANK0000435195001 |          | LV81BANK0000435195002 | detals  | INVALID         |
      | 2      | EUR | Vladmir | LV80BANK0000435195001 | Jegor    | LV81BANK0000435195002 |         | INVALID         |

  Scenario: Do not allow money transfers from not our accounts
    Given service has active customer account "LV81BANK0000435195002"
    When transfer 1 EUR from JonhDoe/DOE-ACCOUNT to Jegor/LV80BANK0000435195002 with "details"
    Then service responds with UNAUTHORIZED

  Scenario: Creditor account belongs to our customer and payment service executes order immediately
    Given service has active customer account "LV80BANK0000435195001"
    And service has active customer account "LV80BANK0000435195002"
    When transfer 1 EUR from Vladimir/LV80BANK0000435195001 to Jegor/LV81BANK0000435195002 with "details"
    Then service responds with CREATED
    And payment order status is EXECUTED

  Scenario: Creditor account does belongs to another bank customer and payment service defers order execution
    Given service has active customer account "LV80BANK0000435195001"
    When transfer 1 EUR from Vladimir/LV80BANK0000435195001 to Anna/EE81BANK0000435195002 with "details"
    Then service responds with CREATED
    And payment order status is PENDING

  Scenario: Do not allow money transfers to the same account
    Given service has active customer account "LV80BANK0000435195001"
    When transfer 1 EUR from Vladimir/LV80BANK0000435195001 to Vladimir/LV80BANK0000435195001 with "details"
    Then service responds with INVALID