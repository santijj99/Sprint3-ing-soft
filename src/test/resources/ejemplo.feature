# Todos los esenarios que defina abajo van a ser relacionados a esta funcionalidad
Feature: Esta es una fucnionalidad de prueba

  Scenario: Suma de dos numeros positivos

#dado todo lo que se debe prerarar antes del esenario
    Given dos numeros enteros

#cuando ejecucion del escenario
    When  se suman los numeros

#entonces es lo que se espera que pase luago del cuando
    Then  el resultado es un numero entero positivo


  Scenario: Suma de dos numeros negativos

#dado todo lo que se debe prerarar antes del esenario
    Given dos numeros enteros negativos

#cuando ejecucion del escenario
    When  se suman los numeros

#entonces es lo que se espera que pase luago del cuando
    Then  el resultado es un numero entero negativo


  Scenario: Suma de dos numeros positivo y negativo

#dado todo lo que se debe prerarar antes del esenario
    Given un numero positivo
    And un numero negativo

#cuando ejecucion del escenario
    When  se suman los numeros

#entonces es lo que se espera que pase luago del cuando
    Then  el resultado es una diferencia
    And el signo se mantiene del mayor valor