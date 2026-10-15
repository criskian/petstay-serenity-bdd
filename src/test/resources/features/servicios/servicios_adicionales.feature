# language: es
@HU-04 @servicios
Característica: HU-04 Servicios adicionales y descuento por estadía larga
  Como dueño de una mascota
  Quiero agregar servicios a mi reserva y recibir descuento en estadías largas
  Para pagar un precio justo por lo que realmente uso

  Antecedentes:
    Dado que el dueño inició sesión en PetStay

  @issue:25 @negativo
  Escenario: Una estadía de 6 noches todavía no tiene descuento
    Dado que el dueño tiene registrada la mascota "Firulais" de tipo "Perro" con peso "5" kg
    Cuando entra a la sección Reservar
    Y completa la reserva de "Firulais" con entrada "dentro de 3 días" por 6 noches
    Y calcula el total
    Entonces el resumen muestra "6" noches
    Y el total de la reserva es "$240.000"
    Y el resumen no muestra descuento por estadía larga

  @issue:25 @positivo
  Escenario: Una estadía de 7 noches recibe 15% de descuento sobre el alojamiento
    Dado que el dueño tiene registrada la mascota "Firulais" de tipo "Perro" con peso "5" kg
    Cuando entra a la sección Reservar
    Y completa la reserva de "Firulais" con entrada "dentro de 3 días" por 7 noches
    Y calcula el total
    Entonces el resumen muestra "7" noches
    Y el resumen muestra un descuento por estadía larga de "-$42.000"
    Y el total de la reserva es "$238.000"

  @issue:33 @positivo
  Escenario: Total de una estadía con baño y peluquería
    Dado que el dueño tiene registrada la mascota "Rocky" de tipo "Perro" con peso "15" kg
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 3 días" por 2 noches
    Y agrega el servicio de baño y peluquería
    Y calcula el total
    Entonces el total de la reserva es "$135.000"

  # DEFECTO CONOCIDO (#32): la pantalla anuncia el paseo como "$10.000 por noche" pero la aplicación
  # lo cobra una sola vez ($145.000 en lugar de $155.000). El escenario describe el comportamiento
  # correcto, por eso falla a propósito: es la evidencia de que la automatización detectó el bug.
  @issue:26 @issue:32 @positivo @defecto-conocido
  Escenario: Total de una estadía con baño y paseo diario
    Dado que el dueño tiene registrada la mascota "Rocky" de tipo "Perro" con peso "15" kg
    Cuando entra a la sección Reservar
    Y completa la reserva de "Rocky" con entrada "dentro de 3 días" por 2 noches
    Y agrega el servicio de baño y peluquería
    Y agrega el servicio de paseo diario
    Y calcula el total
    Entonces el total de la reserva es "$155.000"

  @issue:27 @negativo
  Escenario: El paseo diario no está disponible para gatos
    Dado que el dueño tiene registrada la mascota "Misha" de tipo "Gato" con peso "4" kg
    Cuando entra a la sección Reservar
    Y selecciona a la mascota "Misha" en el formulario de reserva
    Entonces el servicio de paseo diario está deshabilitado
    Y se muestra el aviso "El paseo solo está disponible para perros."
