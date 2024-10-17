Feature: Pokemon Vortex
  @ignore
  @Web
  Scenario: Buscar pokemones
    Given Un usuario para iniciar sesion en pokemon
    And Seleccionamos explorar
    And Ejemplo
  @ignore
  @Web
  Scenario Outline: Pelear por tipo pokemon
    Given Un usuario para iniciar sesion en pokemon
    And Seleccionamos batalla
    And Seleccionamos luchar con cualquier miembro
    And Ingresamos el usuario "<USUARIO>" a batallar
    And Presionamos luchar
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para pelear con el siguiente pokemon
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para pelear con el siguiente pokemon
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para pelear con el siguiente pokemon
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para pelear con el siguiente pokemon
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para pelear con el siguiente pokemon
    And Seleccionamos el pokemon "<POKEMON>" a utilizar en batalla
    And Presionamos continuar para iniciar la batalla
    And Seleccionamos el ataque a utilizar "<ATAQUE>"
    And Presionamos atacar
    And Presionamos continuar para finalizar la batalla

    Examples:
    | USUARIO  | POKEMON  | ATAQUE     |
    | TypeRock | Lycanroc | Rock Slide |

  @ignore
  @Web
  Scenario: Pelear por tipo pokemon n veces
    Given Un usuario para iniciar sesion en pokemon
    And Seleccionamos batalla
    And Seleccionamos luchar con cualquier miembro
    And Ingresamos el usuario "TypeGrass" a batallar
    And Presionamos luchar
    And Luchamos con pokemon "Sceptile" con ataque "Leaf Blade" por 5 veces