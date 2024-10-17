Feature: Operaciones con calculadora
  @ignore
  Scenario: Suma de dos numeros
    Given Dos numeros 1 y 2
    When Sumamos
    Then Validamos que el resultado sea 3
  @ignore
  Scenario Outline: Resta de dos numeros
    Given Dos numeros <numero1> y <numero2>
    When Restamos
    Then Validamos que el resultado sea <resultado>

    Examples:
    | numero1 | numero2 | resultado |
    | 1       | 2       | -1        |
    | 5       | 9       | -4        |
    | 3       | 2       | 1         |
    | 4       | 4       | 0         |