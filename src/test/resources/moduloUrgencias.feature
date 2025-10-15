Feature: Registro de ingresos en el modulo de urgencias

  Como enfermera
  Quiero poder registrar las admisiones de los pacientes a urgencias
  Para determinar qué pacientes tienen mayor prioridad de atención


  Background:
    Given que la siguiente enfermera esta registrada:
      | CUIL          | Nombre | Apellido |
      | 20-32456878-1 | Lucia  | Paz      |
    And que los siguientes pacientes esten registrados:
      | CUIL          | Nombre   | Apellido | Obra social |
      | 20-43336577-2 | Santiago | Martin   | OSECAC      |
      | 20-40902338-0 | Facundo  | Moya     | OSDE        |

  # Escenario 1
  Scenario: Ingreso de paciente existente con datos validos
    When ingresa el siguiente paciente:
      | Cuil          | Informe               | Nivel de emergencia | Temperatura | Frecuencia cardiaca | Frecuencia respiratoria | Tension sistolica | Tension Diastolica | CUIL enfermera |
      | 20-40902338-0 | Dolor abdominal agudo | Emergencia          | 37.5        | 80                  | 20                      | 120               | 80                 | 20-32456878-1  |
    Then la lista de espera esta ordenada segun el nivel de emergencia por cuil de la siguiente manera:
      | CUIL          | Nombre  | Apellido |
      | 20-40902338-0 | Facundo | Moya     |


  # Escenario 2
  Scenario: Ingreso con datos mandatorios faltantes
    When ingresa el siguiente paciente:
      | Cuil          | Informe               | Nivel de emergencia | Temperatura | Frecuencia cardiaca | Frecuencia respiratoria | Tension sistolica | Tension Diastolica | CUIL enfermera |
      | 20-40902338-0 | Dolor abdominal agudo | Emergencia          | 37.5        |                     | 20                      | 120               | 80                 | 20-32456878-1  |
    Then el sistema muestra un error indicando la "frecuencia cardíaca es obligatoria"

  # Escenario 3
  Scenario: Ingreso con frecuencia cardíaca negativa
    When ingresa el siguiente paciente:
      | Cuil          | Informe               | Nivel de emergencia | Temperatura | Frecuencia cardiaca | Frecuencia respiratoria | Tension sistolica | Tension Diastolica | CUIL enfermera |
      | 20-40902338-0 | Dolor abdominal agudo | Emergencia          | 37.5        | -80                 | 20                      | 120               | 80                 | 20-32456878-1  |
    Then el sistema muestra un error indicando la "La frecuencia cardiaca no puede ser negativa"

  # Escenario 4
  Scenario: Ingreso con frecuencia respiratoria negativa
    When ingresa el siguiente paciente:
      | Cuil            | Informe               | Nivel de emergencia | Temperatura | Frecuencia cardiaca | Frecuencia respiratoria | Tension sistolica | Tension Diastolica | CUIL enfermera |
      | 20-40902338-0   | Dolor abdominal agudo | Emergencia          | 37.5        | 80                  | -20                     | 120               | 80                 | 20-32456878-1  |
    Then el sistema muestra un error indicando la "La frecuencia respiratoria no puede ser negativa"

  # Escenario 5
  Scenario: Paciente con mayor prioridad entra después pero debe ser atendido primero
    Given la lista de espera actual es:
      | CUIL          | Nombre  | Apellido |
      | 20-40902338-0 | Facundo | Moya     |
    When ingresa un paciente cuyo CUIL es "20-43336577-2":
      | informe               | nivelEmergencia | temperatura | frecuenciaCardiaca | frecuenciaRespiratoria | tensionSistolica | tensionDiastolica | cuilEnfermera |
      | Dolor abdominal agudo | Critica         | 37.5        | 80                 | 20                     | 120              | 80                | 20-32456878-1 |
    Then el sistema prioriza al paciente por su nivel de emergencia:
      | CUIL          | Nombre   | Apellido |
      | 20-43336577-2 | Santiago | Martin   |
      | 20-40902338-0 | Facundo  | Moya     |

  # Escenario 6
  Scenario: Paciente con menor prioridad entra después y debe ser atendido después
    Given la lista de espera actual es:
      | CUIL          | Nombre  | Apellido |
      | 20-40902338-0 | Facundo | Moya     |
    When ingresa un paciente cuyo CUIL es "20-43336577-2":
      | informe               | nivelEmergencia | temperatura | frecuenciaCardiaca | frecuenciaRespiratoria | tensionSistolica | tensionDiastolica | cuilEnfermera |
      | Dolor abdominal agudo | Urgencia menor  | 37.5        | 80                 | 20                     | 120              | 80                | 20-32456878-1 |
    Then el sistema prioriza al paciente por su nivel de emergencia:
      | CUIL          | Nombre   | Apellido |
      | 20-40902338-0 | Facundo  | Moya     |
      | 20-43336577-2 | Santiago | Martin   |

  # Escenario 7
  Scenario: Dos pacientes con mismo nivel de emergencia, se prioriza por orden de llegada
    Given la lista de espera actual es:
      | CUIL          | Nombre  | Apellido | Horario  | Nivel de emergencia |
      | 20-40902338-0 | Facundo | Moya     | 09:24:15 | Urgencia Menor      |
    When ingresa un paciente cuyo CUIL es "20-43336577-2" y tiene el mismo nivel de emergencia:
      | Informe               | Nivel de emergencia | Temperatura | Frecuencia cardiaca | Frecuencia respiratoria | Tension sistolica | Tension Diastolica | CUIL enfermera |
      | Dolor abdominal agudo | Urgencia menor      | 37.5        | 80                  | 20                      | 120               | 80                 | 20-32456878-1  |
    Then la lista esta ordenada de la siguiente manera:
      | CUIL          | Nombre   | Apellido | Horario  | Nivel de emergencia |
      | 20-40902338-0 | Facundo  | Moya     | 09:24:15 | Urgencia Menor      |
      | 20-43336577-2 | Santiago | Martin   | 10:35:15 | Urgencia Menor      |


    # Given el sistema tiene los estados validos "PENDIENTE", "EN_PROCESO" y "FINALIZADO"
  # And el sistema define niveles de emergencia: "Critica", "Emergencia", "Urgencia", "Urgencia menor", "Sin Urgencia"