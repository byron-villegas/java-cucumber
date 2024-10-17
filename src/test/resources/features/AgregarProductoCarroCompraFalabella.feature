Feature: Agregar Producto a Carro de Compra
  @ignore
  @Web
  Scenario: Agregar Producto Nintendo Switch al Carro de Compra
    Given Una pagina a buscar "Falabella" en google
    And Buscamos en google
    And Seleccionamos la pagina encontrada en google
    And Cerramos modal abre tu cmr aqui
    And Cerramos modal conocer mejores ofertas
    And Buscamos el producto "Nintendo Switch"
    And Seleccionamos el producto "Consola Nintendo Switch"
    When Visualizamos el producto
    And Agregamos el producto al carro de compras
    And Validamos que la cantidad total de productos del carro de compras sea 1
  # And Seleccionamos el tipo de garantia "No, gracias"
    And Vamos al carro de compras