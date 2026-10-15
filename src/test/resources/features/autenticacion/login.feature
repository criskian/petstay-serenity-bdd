# language: es
@HU-01 @autenticacion
Característica: HU-01 Inicio de sesión en PetStay
  Como dueño de una mascota
  Quiero ingresar a PetStay con mi usuario y contraseña
  Para gestionar mis mascotas y mis reservas de forma privada

  @issue:6 @positivo @smoke
  Escenario: Inicio de sesión exitoso con credenciales válidas
    Dado que el dueño abre la aplicación PetStay
    Cuando inicia sesión con el usuario "dueno" y la contraseña "huellitas123"
    Entonces ve la pantalla "Mis mascotas"
    Y el saludo muestra al usuario "dueno"

  @issue:7 @negativo
  Escenario: Inicio de sesión rechazado con contraseña incorrecta
    Dado que el dueño abre la aplicación PetStay
    Cuando inicia sesión con el usuario "dueno" y la contraseña "clave-equivocada"
    Entonces ve el error de inicio de sesión "Usuario o contraseña incorrectos"
    Y permanece en la pantalla de ingreso

  @issue:8 @negativo
  Escenario: Inicio de sesión rechazado con campos vacíos
    Dado que el dueño abre la aplicación PetStay
    Cuando inicia sesión con el usuario "" y la contraseña ""
    Entonces ve el error de inicio de sesión "Usuario o contraseña incorrectos"
    Y permanece en la pantalla de ingreso

  @issue:9 @negativo
  Escenario: Acceso a una pantalla interna sin sesión redirige al ingreso
    Dado que el dueño intenta abrir la página "mascotas.html" sin haber iniciado sesión
    Entonces es redirigido a la pantalla de ingreso

  @issue:10 @positivo
  Escenario: Cerrar sesión devuelve a la pantalla de ingreso
    Dado que el dueño inició sesión en PetStay
    Cuando cierra la sesión
    Entonces es redirigido a la pantalla de ingreso
