Feature: Buscar Disco
  @ignore
  @Web
  Scenario: Buscar Disco Por SKU
    Given Un sku "843563125694"
    And Cerramos modal aceptar cookies
    And Lo buscamos mediante el sku
    When Seleccionamos el primer resultado
    And Obtenemos el nombre del artista
    And Obtenemos el nombre del album
    And Obtenemos el editor
    And Obtenemos el formato
    And Obtenemos el pais
    And Obtenemos el ano de publicacion
    And Obtenemos los generos
    And Obtenemos el listado de canciones
    And Abrimos la imagen
    And Obtenemos las imagenes
    Then Generamos el json de salida

  @ignore
  @Web
  Scenario: Buscar Disco Por Url y SKU
    Given Una Url "https://www.discogs.com/release/28936126-Joe-Hisaishi-Royal-Philharmonic-Orchestra-A-Symphonic-Celebration-Music-From-The-Studio-Ghibli-Films" un sku "602448812292"
    And Cerramos modal aceptar cookies
    And Obtenemos el nombre del artista
    And Obtenemos el nombre del album
    And Obtenemos el editor
    And Obtenemos el formato
    And Obtenemos el pais
    And Obtenemos el ano de publicacion
    And Obtenemos los generos
    And Obtenemos el listado de canciones
    And Abrimos la imagen
    And Obtenemos las imagenes
    Then Generamos el json de salida