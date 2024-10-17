Feature: Buscar Persona
  @ignore
  Scenario: Buscar Persona en un listado
    Given Un listado de personas
      | rut          | nombres        | primerApellido | segundoApellido | edad | fechaNacimiento |
      | 11.111.111-1 | Pepito Alfonso | Castaño        | Rojas           | 34   | 13/11/1979      |
      | 22.222.222-2 | Abc Def        | Ghi            | Jkl             | 26   | 22/06/1996      |
      | 33.333.333-3 | Jkl Ghi        | Def            | Abc             | 25   | 22/07/1998      |
    When Buscamos por rut "22.222.222-2"
    Then Validamos que sea la persona encontrada