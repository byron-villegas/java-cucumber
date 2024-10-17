Feature: Buscar Persona Star Wars

  @Microservice
  Scenario: Buscar Persona por Id Persona
    Given Un Id Persona de Star Wars "1"
    When Buscamos la persona de Star Wars
    And Validamos que el nombre de la persona de Star Wars es "Luke Skywalker"
    And Validamos que el genero de la persona de Star Wars es "male"
    Then Validamos que la respuesta del servicio de busqueda Persona de Star Wars es "200"