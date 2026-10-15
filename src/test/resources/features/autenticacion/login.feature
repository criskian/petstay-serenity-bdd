# language: es
@HU-01 @autenticacion
Característica: HU-01 Inicio de sesión en PetStay
  Como dueño de una mascota
  Quiero ingresar a PetStay con mi usuario y contraseña
  Para gestionar mis mascotas y mis reservas de forma privada

  Escenario: Inicio de sesión exitoso con credenciales válidas
    Dado que el dueño abre la aplicación PetStay
    Cuando inicia sesión con el usuario "dueno" y la contraseña "huellitas123"
    Entonces ve la pantalla "Mis mascotas"
    Y el saludo muestra al usuario "dueno"

  Escenario: Inicio de sesión rechazado con contraseña incorrecta
    Dado que el dueño abre la aplicación PetStay
    Cuando inicia sesión con el usuario "dueno" y la contraseña "clave-equivocada"
    Entonces ve el error de inicio de sesión "Usuario o contraseña incorrectos"
    Y permanece en la pantalla de ingreso
