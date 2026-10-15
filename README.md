# PetStay · Automatización de pruebas Front-End con Serenity BDD

Pruebas end-to-end de **PetStay** (hotel para mascotas, <https://petstay-aphn.onrender.com>) escritas en
**Gherkin (español)** con **Serenity BDD + Cucumber** y el patrón de diseño **Screenplay**.

[![Pruebas Front-End](https://github.com/criskian/petstay-serenity-bdd/actions/workflows/ci.yml/badge.svg)](https://github.com/criskian/petstay-serenity-bdd/actions/workflows/ci.yml)

| | |
|---|---|
| **Aplicación bajo prueba** | PetStay (Render). Todo el estado vive en `localStorage` del navegador |
| **Escenarios** | 28 escenarios (+ filas de *Esquema del escenario*): positivos y negativos |
| **Trazabilidad** | Requisito (`@HU-0X`) → Issue de GitHub (`@issue:N`) → Escenario → Reporte |
| **Pipeline** | GitHub Actions: ejecuta en Chrome headless y publica el reporte en GitHub Pages |

## Requisitos para ejecutar en local

- JDK 17 o superior
- Google Chrome (Selenium Manager descarga el driver automáticamente)
- No hace falta instalar Gradle: se usa el wrapper (`gradlew`)

## Cómo ejecutar

```bash
# Toda la suite (abre Chrome visible)
./gradlew clean test

# Sin ventana de navegador (como en el pipeline)
./gradlew clean test -Dheadless.mode=true

# Más rápido en desarrollo: capturas solo cuando algo falla
./gradlew test -Dheadless.mode=true -Dserenity.take.screenshots=FOR_FAILURES
```

El reporte HTML con capturas de cada paso queda en `target/site/serenity/index.html`.

### Ejecutar una parte de la suite (por tags)

Los tags permiten ejecutar un caso de prueba concreto "desde su issue", un requisito completo o un tipo de escenario:

```bash
./gradlew test -Dcucumber.filter.tags="@issue:11"          # un caso de prueba (issue #11)
./gradlew test -Dcucumber.filter.tags="@HU-03"             # todos los escenarios de un requisito
./gradlew test -Dcucumber.filter.tags="@smoke"             # pruebas rápidas de humo
./gradlew test -Dcucumber.filter.tags="@negativo"          # solo escenarios negativos
./gradlew test -Dcucumber.filter.tags="@HU-02 and @negativo"
./gradlew test -Dcucumber.filter.tags="not @defecto-conocido"
```

Otros parámetros: `-Dpetstay.base.url=<otra URL>`, `-Dwebdriver.driver=edge|firefox`.

## Arquitectura (patrón Screenplay)

Screenplay describe las pruebas desde el punto de vista de **quién** hace **qué** y **qué observa**,
en lugar de "qué botón se pulsa" (Page Object). Cada concepto tiene su propia clase pequeña y reutilizable:

```
src/test/
├── resources/features/        ← Escenarios en Gherkin (QUÉ se prueba, en lenguaje de negocio)
│   ├── autenticacion/login.feature
│   ├── mascotas/registro_mascotas.feature
│   ├── reservas/reserva_estadia.feature
│   ├── servicios/servicios_adicionales.feature
│   └── cancelaciones/cancelacion_reservas.feature
└── java/
    ├── runners/Runner.java            ← Punto de entrada: une Cucumber con Serenity
    ├── stepdefinitions/               ← Traducen cada frase Gherkin a Tasks y Questions
    ├── tasks/                         ← Lo que el actor HACE (Login, RegisterPet, BookStay, CancelReservation…)
    ├── interactions/                  ← Acciones de bajo nivel propias (WaitForLoading, SelectPet…)
    ├── questions/                     ← Lo que el actor OBSERVA (VisibleText, ElementState, RegisteredPets…)
    ├── userinterfaces/                ← Targets: DÓNDE está cada elemento (localizadores)
    ├── models/                        ← Datos de los formularios (PetData, StayData)
    └── utils/                         ← Config (URL) y Fechas ("dentro de 5 días" → DD/MM/AAAA)
```

Flujo de una frase: `Cuando calcula el total` → método en `BookingSteps` → `BookStay.calculatingTotal()` (Task)
→ `Click.on(BookingPage.CALCULAR)` (Interaction sobre un Target) → el actor `Dueño` usa su habilidad `BrowseTheWeb`.

### Decisiones de diseño relevantes

- **Espera explícita al overlay de carga.** PetStay simula procesos lentos con una capa que tapa la pantalla durante
  0,3–1,5 s (tiempo aleatorio). `WaitForLoading` consulta el DOM con JavaScript hasta que desaparece; no se usan `sleep`
  ni `WaitUntil(isNotVisible)`, que tardaría todo el timeout implícito cuando la capa no existe.
- **Fechas relativas.** Los escenarios dicen `"dentro de 5 días"`, `"ayer"` u `"hoy"`, así nunca caducan.
- **Independencia entre escenarios.** El hook `@After` limpia `localStorage` y cookies; si no, un escenario heredaría
  la sesión y las mascotas del anterior.
- **Lectura sin condiciones de carrera.** `VisibleText` espera a que el mensaje deje de estar oculto (`[hidden]`)
  antes de leer su texto; `ElementState` y `RegisteredPets` consultan sin esperar para las verificaciones negativas.
- **Localizadores variados a propósito**, según lo mejor que ofrezca cada elemento: `data-testid`, `name`, `id`,
  `aria-label` y XPath con `following-sibling` para leer celdas de las tablas por nombre o código.

## Trazabilidad: requisito → issue → escenario

Cada requisito es un issue con la etiqueta `requirement` y cada caso de prueba es un issue con la etiqueta
`test-case` que apunta a su requisito. En el `.feature`:

```gherkin
@HU-02 @mascotas                              ← requisito (a nivel de Característica)
Característica: HU-02 Registro de mascotas

  @issue:11 @positivo @smoke                  ← issue del caso de prueba + tipo
  Escenario: Registro exitoso de una mascota con datos válidos
```

Serenity convierte `@issue:N` en un enlace al issue de GitHub dentro del reporte
(`serenity.issue.tracker.url` en `serenity.conf`) y agrupa los resultados por requisito.

| Requisito | Casos de prueba (issues) |
|---|---|
| [#1 HU-01 Inicio de sesión](https://github.com/criskian/petstay-serenity-bdd/issues/1) | #6 login exitoso · #7 contraseña incorrecta · #8 campos vacíos · #9 pantalla interna sin sesión · #10 cerrar sesión |
| [#2 HU-02 Registro de mascotas](https://github.com/criskian/petstay-serenity-bdd/issues/2) | #11 registro exitoso · #12 clasificación de tamaño (5 ejemplos) · #13 sin vacunas · #14 formulario vacío · #15 nombre duplicado · #16 peso/edad fuera de rango (4 ejemplos) |
| [#3 HU-03 Reserva de estadía](https://github.com/criskian/petstay-serenity-bdd/issues/3) | #17 cálculo del total · #18 confirmar y ver en Mis reservas · #19 entrada en el pasado · #20 salida igual/anterior (2 ejemplos) · #21 más de 30 noches · #22 cruce de reservas · #23 formulario vacío · #24 atajo Reservar |
| [#4 HU-04 Servicios y descuentos](https://github.com/criskian/petstay-serenity-bdd/issues/4) | #25 descuento desde 7 noches (2 escenarios) · #33 total con baño · #26 total con baño y paseo ⚠️ · #27 paseo no disponible para gatos |
| [#5 HU-05 Cancelación](https://github.com/criskian/petstay-serenity-bdd/issues/5) | #28 reembolso 100 % · #29 reembolso 50 % · #30 sin reembolso · #31 desistir de cancelar |

### ⚠️ Defecto encontrado en la aplicación

El escenario **#26** falla a propósito: es la evidencia del bug [#32](https://github.com/criskian/petstay-serenity-bdd/issues/32).
La pantalla anuncia *"Paseo diario — $10.000 por noche"*, pero el total solo suma $10.000 una vez
(2 noches con baño y paseo: se espera $155.000 y la app calcula $145.000). Está marcado con `@defecto-conocido`.

## Pipeline (GitHub Actions)

`.github/workflows/ci.yml` se dispara en cada `push`/`pull_request` a `main` y manualmente
(*Actions → Run workflow*, con un filtro de tags opcional). Pasos: instala Java 17, "despierta" la app (Render duerme el
plan gratuito), ejecuta `./gradlew clean test -Dheadless.mode=true`, resume los resultados, guarda el reporte como artefacto
y lo publica en GitHub Pages.
