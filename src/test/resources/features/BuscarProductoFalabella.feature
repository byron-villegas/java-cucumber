Feature: Buscar Producto en Falabella
  @ignore
  @Web
  Scenario: Buscar Producto Playstation 5
    Given Una pagina a buscar "Falabella" en google
    And Buscamos en google
    And Seleccionamos la pagina encontrada en google
    And Cerramos modal abre tu cmr aqui
    And Cerramos modal conocer mejores ofertas
    And Buscamos el producto "Playstation 5"
    And Seleccionamos el producto "Playstation 5"
    When Visualizamos el producto
    And Validamos que el nombre del producto sea el buscado
    And Validamos que el precio del producto sea 599990