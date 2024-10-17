Feature: Buscar Pagina Anime
  @ignore
  @Web
  Scenario: Buscar pagina de anime AnimeFlv
    Given Una pagina a buscar "AnimeFLV" en google
    And Buscamos en google
    And Seleccionamos la pagina encontrada en google
    And Buscamos un anime en especifico "Elfen Lied"
    And Seleccionamos el anime encontrado
    Then Validamos que sea el anime seleccionado