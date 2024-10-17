Feature: Login Guru
  @ignore
  Scenario: Login Guru
    Given Una pagina de login "https://demo.guru99.com/test/login.html"
    And Validamos que el titulo de la pagina es "Login Page"
    And Ingresamos el email "abc@dfg.hi"
    And Ingresamos el password "abcdfghi"
    When Logeamos
    Then Validamos que el mensaje en pantalla sea "Successfully Logged in..."