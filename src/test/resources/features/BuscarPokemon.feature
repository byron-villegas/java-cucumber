Feature: Buscar Pokemon
  @ignore
  @Microservice
  Scenario: Buscar Pokemon
    Given Un Pokemon "Pikachu"
    When Buscamos el Pokemon
    And Validamos que el id del Pokemon es 25
    Then Validamos que la respuesta del servicio de busqueda Pokemon es "200"