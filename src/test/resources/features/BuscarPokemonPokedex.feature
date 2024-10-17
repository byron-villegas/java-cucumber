Feature: Buscar pokemon en pokedex
  @ignore
  @Web
  Scenario: Buscar pokemon en pokedex
    Given Un nombre de pokemon "pikachu"
    When Escribimos el nombre
    And Seleccionamos el pokemon
    Then Validamos que el pokemon encontrado sea el buscado