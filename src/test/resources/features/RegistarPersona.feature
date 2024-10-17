Feature: Registar Persona
  @ignore
  Scenario Outline: Registras Persona
    Given Una informacion de persona con rut "<rut>", nombres "<nombres>", primer apellido "<primerApellido>", segundo apellido "<segundoApellido>", edad "<edad>", fecha de nacimiento "<fechaNacimiento>"
    When Registramos
    Then Validamos que se haya guardado

    Examples:
      | rut          | nombres        | primerApellido | segundoApellido | edad | fechaNacimiento |
      | 11.111.111-1 | Pepito Alfonso | Castaño        | Rojas           | 34   | 13/11/1979      |