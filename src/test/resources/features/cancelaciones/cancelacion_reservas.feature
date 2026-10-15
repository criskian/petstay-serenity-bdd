# language: es
@HU-05 @cancelaciones
Característica: HU-05 Cancelación de reservas y reembolsos
  Como dueño de una mascota
  Quiero cancelar una reserva y conocer cuánto dinero me devuelven
  Para decidir con información si cancelo o no

  Antecedentes:
    Dado que el dueño inició sesión en PetStay
    Y que el dueño tiene registrada la mascota "Rocky" de tipo "Perro" con peso "15" kg

  @issue:28 @positivo
  Escenario: Cancelar con 3 días o más de anticipación reembolsa el 100%
    Dado que el dueño tiene una reserva confirmada para "Rocky" con entrada "dentro de 5 días" por 2 noches
    Cuando entra a Mis reservas
    Y cancela la reserva
    Entonces el diálogo informa un reembolso estimado de "$110.000 (100%)"
    Cuando confirma la cancelación
    Entonces la reserva queda con estado "Cancelada" y reembolso "$110.000"
    Y se informa que la reserva fue cancelada con un reembolso de "$110.000"

  @issue:29 @positivo
  Escenario: Cancelar con 1 o 2 días de anticipación reembolsa el 50%
    Dado que el dueño tiene una reserva confirmada para "Rocky" con entrada "dentro de 2 días" por 2 noches
    Cuando entra a Mis reservas
    Y cancela la reserva
    Entonces el diálogo informa un reembolso estimado de "$55.000 (50%)"
    Cuando confirma la cancelación
    Entonces la reserva queda con estado "Cancelada" y reembolso "$55.000"
    Y se informa que la reserva fue cancelada con un reembolso de "$55.000"

  @issue:30 @positivo
  Escenario: Cancelar el mismo día de la entrada no reembolsa nada
    Dado que el dueño tiene una reserva confirmada para "Rocky" con entrada "hoy" por 2 noches
    Cuando entra a Mis reservas
    Y cancela la reserva
    Entonces el diálogo informa un reembolso estimado de "$0 (0%)"
    Cuando confirma la cancelación
    Entonces la reserva queda con estado "Cancelada" y reembolso "$0"
    Y se informa que la reserva fue cancelada con un reembolso de "$0"

  @issue:31 @negativo
  Escenario: Desistir de la cancelación mantiene la reserva confirmada
    Dado que el dueño tiene una reserva confirmada para "Rocky" con entrada "dentro de 5 días" por 2 noches
    Cuando entra a Mis reservas
    Y cancela la reserva
    Y decide no cancelar y vuelve
    Entonces el diálogo de cancelación se cierra
    Y la reserva queda con estado "Confirmada" y reembolso "—"
