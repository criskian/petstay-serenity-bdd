# language: es
@HU-02 @mascotas
Característica: HU-02 Registro de mascotas
  Como dueño de una mascota
  Quiero registrar a mi mascota con sus datos
  Para poder reservarle una estadía en el hotel

  Antecedentes:
    Dado que el dueño inició sesión en PetStay

  @issue:11 @positivo @smoke
  Escenario: Registro exitoso de una mascota con datos válidos
    Cuando registra una mascota con los siguientes datos:
      | nombre | especie | peso | edad | vacunas |
      | Rocky  | Perro   | 15   | 4    | sí      |
    Entonces el mensaje de mascotas es "Rocky fue registrado con tamaño Mediano"
    Y la mascota "Rocky" aparece en la lista con tamaño "Mediano"

  @issue:12 @positivo
  Esquema del escenario: Clasificación automática del tamaño de <especie> de <peso> kg como <tamano>
    Cuando registra una mascota con los siguientes datos:
      | nombre   | especie   | peso   | edad | vacunas |
      | <nombre> | <especie> | <peso> | 2    | sí      |
    Entonces la mascota "<nombre>" aparece en la lista con tamaño "<tamano>"

    Ejemplos: límites entre tamaños
      | nombre   | especie | peso | tamano  |
      | Pelusa   | Gato    | 4.5  | Pequeño |
      | Chiquito | Perro   | 10   | Pequeño |
      | Bruno    | Perro   | 10.5 | Mediano |
      | Canela   | Perro   | 24.9 | Mediano |
      | Thor     | Perro   | 25   | Grande  |

  @issue:13 @negativo
  Escenario: Registro rechazado si la mascota no tiene las vacunas al día
    Cuando registra una mascota con los siguientes datos:
      | nombre | especie | peso | edad | vacunas |
      | Rocky  | Perro   | 15   | 4    | no      |
    Entonces el mensaje de mascotas es "No podemos hospedar mascotas sin vacunas al día"
    Y la lista de mascotas no incluye a "Rocky"

  @issue:14 @negativo
  Escenario: Registro rechazado con el formulario vacío
    Cuando registra una mascota con los siguientes datos:
      | nombre | especie | peso | edad | vacunas |
      |        |         |      |      | no      |
    Entonces el mensaje de mascotas es "Revisa los campos marcados"
    Y el campo "nombre" muestra el error "Este campo es obligatorio"
    Y el campo "especie" muestra el error "Selecciona la especie"
    Y el campo "peso" muestra el error "Este campo es obligatorio"
    Y el campo "edad" muestra el error "Este campo es obligatorio"

  @issue:15 @negativo
  Escenario: Registro rechazado con un nombre ya registrado aunque cambien las mayúsculas
    Dado que el dueño tiene registrada la mascota "Rocky" de tipo "Perro" con peso "15" kg
    Cuando registra una mascota con los siguientes datos:
      | nombre | especie | peso | edad | vacunas |
      | rocky  | Perro   | 8    | 2    | sí      |
    Entonces el mensaje de mascotas es "Revisa los campos marcados"
    Y el campo "nombre" muestra el error "Ya existe una mascota registrada con ese nombre"

  @issue:16 @negativo
  Esquema del escenario: Registro rechazado con peso <peso> kg y edad <edad> años
    Cuando registra una mascota con los siguientes datos:
      | nombre | especie | peso   | edad   | vacunas |
      | Rocky  | Perro   | <peso> | <edad> | sí      |
    Entonces el campo "<campo>" muestra el error "<error>"
    Y la lista de mascotas no incluye a "Rocky"

    Ejemplos: peso fuera de rango o con formato inválido
      | peso | edad | campo | error                                      |
      | 0    | 3    | peso  | El peso debe ser mayor que 0 y máximo 80 kg |
      | 81   | 3    | peso  | El peso debe ser mayor que 0 y máximo 80 kg |
      | abc  | 3    | peso  | El peso debe ser mayor que 0 y máximo 80 kg |

    Ejemplos: edad fuera de rango
      | peso | edad | campo | error                                |
      | 10   | 26   | edad  | La edad debe estar entre 0 y 25 años |
