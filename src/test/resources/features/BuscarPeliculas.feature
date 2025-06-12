Feature: Obtener Informacion de Peliculas
  @ignore
  @Web
  Scenario: Obtener los datos de un listado de peliculas
    Given La url de la pagina de peliculas "https://www.megapeliculasrip.net"
    When Buscamos los datos de las peliculas "movies-names.json"
    Then Creamos el archivo "movies.json"