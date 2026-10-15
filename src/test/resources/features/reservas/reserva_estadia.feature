# language: es
@HU-03 @reservas
Característica: HU-03 Reserva de estadía
  Como dueño de una mascota
  Quiero reservar una estadía indicando las fechas de entrada y salida
  Para que cuiden a mi mascota mientras no estoy

  Antecedentes:
    Dado que el dueño inició sesión en PetStay
    Y que el dueño tiene registrada la mascota "Rocky" de tipo "Perro" con peso "15" kg

  @issue:17 @positivo @smoke
  Escenario: Cálculo del total de una estadía sin servicios adicionales
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 5 días" por 3 noches
    Y calcula el total
    Entonces el resumen muestra "3" noches
    Y el total de la reserva es "$165.000"
    Y el resumen no muestra descuento por estadía larga

  @issue:18 @positivo @smoke
  Escenario: Confirmar la reserva genera un código y queda registrada en Mis reservas
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 5 días" por 3 noches
    Y calcula el total
    Y confirma la reserva
    Entonces el mensaje de reserva indica "Reserva confirmada con el código PS-"
    Y el código de la reserva tiene el formato PS-0000
    Cuando entra a Mis reservas
    Entonces la reserva aparece en Mis reservas para "Rocky" con estado "Confirmada" y total "$165.000"

  @issue:19 @negativo
  Escenario: Reserva rechazada con fecha de entrada en el pasado
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "ayer" y salida "dentro de 3 días"
    Y calcula el total
    Entonces el campo "entrada" muestra el error "La fecha de entrada no puede ser anterior a hoy"
    Y el mensaje de reserva indica "Revisa los campos marcados"
    Y no se muestra el resumen de la reserva

  @issue:20 @negativo
  Esquema del escenario: Reserva rechazada con salida <situacion> la entrada
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 5 días" y salida "<salida>"
    Y calcula el total
    Entonces el campo "salida" muestra el error "La fecha de salida debe ser posterior a la de entrada"
    Y no se muestra el resumen de la reserva

    Ejemplos:
      | situacion | salida           |
      | igual a   | dentro de 5 días |
      | anterior a | dentro de 3 días |

  @issue:21 @negativo
  Escenario: Reserva rechazada para una estadía de más de 30 noches
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "mañana" por 31 noches
    Y calcula el total
    Entonces el campo "salida" muestra el error "La estadía máxima es de 30 noches"
    Y no se muestra el resumen de la reserva

  @issue:22 @negativo
  Escenario: Reserva rechazada por cruzarse con otra reserva activa de la misma mascota
    Dado que el dueño tiene una reserva confirmada para "Rocky" con entrada "dentro de 5 días" por 3 noches
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 6 días" por 3 noches
    Y calcula el total
    Entonces el mensaje de reserva indica "Esta mascota ya tiene una reserva activa en esas fechas"
    Y no se muestra el resumen de la reserva

  @issue:23 @negativo
  Escenario: Reserva rechazada con el formulario sin diligenciar
    Cuando entra a la sección Reservar
    Y calcula el total
    Entonces el campo "mascota" muestra el error "Selecciona una mascota"
    Y el campo "entrada" muestra el error "Este campo es obligatorio"
    Y el campo "salida" muestra el error "Este campo es obligatorio"
    Y no se muestra el resumen de la reserva

  @issue:24 @positivo
  Escenario: El enlace Reservar de Mis mascotas preselecciona la mascota
    Dado que el dueño tiene registrada la mascota "Luna" de tipo "Gato" con peso "4" kg
    Cuando pulsa Reservar en la fila de la mascota "Luna"
    Entonces el formulario de reserva tiene seleccionada a la mascota "Luna"
