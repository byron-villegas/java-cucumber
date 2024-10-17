Feature: Buscar Pokedex

  @Microservice
  Scenario: Buscar Pokedex por Id Generacion
    Given Un id de generacion 2
    When Buscamos el pokedex
    And Validamos que el nombre de la region es "kanto"
    And Validamos que la cantidad total de pokemones es 151
    Then Validamos que la respuesta del servicio de busqueda de pokedex por generacion es "200"