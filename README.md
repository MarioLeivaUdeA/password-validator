# Password Validator

Aplicación Java pequeña para validar reglas básicas de contraseñas. Usa Gradle
para compilar, ejecutar pruebas, verificar estilo y generar cobertura.

## Arquitectura

El proyecto no implementa una arquitectura por capas: su alcance es una sola
clase de dominio, sin API, interfaz gráfica ni persistencia.

```text
src/
  main/java/com/example/PasswordValidator.java
  test/java/com/example/PasswordValidatorTest.java
  test/java/com/example/PasswordValidatorPerformanceTest.java
config/checkstyle/checkstyle.xml
gradle/wrapper/                 Wrapper de Gradle
build.gradle                    Plugins, dependencias y tareas
settings.gradle                 Nombre del proyecto
```

`PasswordValidator.java` es el código fuente. Gradle genera
`PasswordValidator.class` al compilar; ese bytecode es un artefacto derivado,
no una segunda copia editable. Los archivos de `build/` y `.gradle/` se
regeneran localmente y no deben versionarse.

La clase principal expone dos operaciones:

- `isValid`: exige al menos ocho caracteres, mayúscula, minúscula, dígito y
  carácter especial.
- `getStrengthScore`: suma un punto por cada una de esas cinco reglas.

Las pruebas funcionales verifican resultados y puntaje. La prueba de
rendimiento realiza validaciones repetidas; no sustituye una herramienta de
benchmark especializada.

## Calidad y reportes locales

Se requiere JDK 17 para compilar y ejecutar las pruebas con el Gradle Wrapper
incluido (Gradle 8.10.2). En PowerShell:

```powershell
.\gradlew.bat clean test checkstyleMain checkstyleTest jacocoTestReport
```

Los reportes quedan bajo `build/`:

- Pruebas: `build/reports/tests/test/index.html`
- Checkstyle: `build/reports/checkstyle/`
- JaCoCo HTML: `build/reports/jacoco/test/html/index.html`
- JaCoCo XML: `build/reports/jacoco/test/jacocoTestReport.xml`

Checkstyle aplica las reglas declaradas en
`config/checkstyle/checkstyle.xml`. JaCoCo mide cobertura; SonarCloud importa
su XML para mostrarla junto con las métricas estáticas. El último resultado
local verificado tuvo todas las pruebas y Checkstyle aprobados. La cobertura de
ramas se debe revisar en el reporte generado, no inferirse de la cobertura de
líneas.

## Análisis manual en SonarCloud

El proyecto SonarCloud ya existe en la organización `1193212909wmle`. La clave
de proyecto configurada en Gradle es `MarioLeivaUdeA_password-validator`.
El análisis se ejecuta bajo demanda; este repositorio no requiere un workflow
de GitHub Actions para ello.

El escáner de SonarCloud requiere JDK 21 o superior. Con el JDK requerido
seleccionado y un token guardado fuera del repositorio:

```powershell
$env:SONAR_TOKEN = '<token de SonarCloud>'
.\gradlew.bat clean test checkstyleMain checkstyleTest jacocoTestReport sonar
```

No se debe escribir el valor del token en archivos versionados ni en comandos
que queden guardados en el historial. El secreto configurado en GitHub no se
usa automáticamente en una ejecución local.

En SonarCloud:

- El **Quality Profile** define las reglas activas para Java.
- El **Quality Gate** compara las métricas del análisis con sus condiciones y
  publica el resultado (`Passed`/`Failed`).
- La complejidad **ciclomática** cuenta rutas de decisión; la **cognitiva**
  estima la dificultad de seguir el flujo. SonarCloud muestra ambas métricas.
- JaCoCo provee la cobertura de pruebas; no mide complejidad cognitiva.

Una ejecución anterior del análisis en SonarCloud mostró Quality Gate `OK`,
complejidad ciclomática 13 y cognitiva 8. Esos datos pertenecen a ese análisis
y no confirman un análisis nuevo de los cambios posteriores.
