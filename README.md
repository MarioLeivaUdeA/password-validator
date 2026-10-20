# Password Validator

Proyecto Java con Gradle para validar contraseñas, ejecutar pruebas unitarias y de rendimiento, revisar estilo con Checkstyle, generar cobertura con JaCoCo y analizar calidad en SonarCloud.

## Requisitos

- JDK 8 o superior
- Gradle Wrapper incluido (Gradle 8.10.2)
- JDK 17 recomendado para desarrollo y CI

## Comandos

En Windows:

```text
.\gradlew.bat clean test
.\gradlew.bat checkstyleMain checkstyleTest
.\gradlew.bat jacocoTestReport
```

En PowerShell es necesario anteponer `.` y `\` para ejecutar un archivo ubicado
en la carpeta actual. `JAVA_HOME` debe apuntar a la carpeta del JDK, por ejemplo:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Microsoft\jdk-17.0.20.8-hotspot'
.\gradlew.bat build
```

Después de instalar o configurar Java, abre un terminal nuevo para que VS Code
reciba las variables de entorno actualizadas.

Los reportes se generan en:

- Pruebas: `build/reports/tests/test/index.html`
- Checkstyle: `build/reports/checkstyle/`
- Cobertura JaCoCo: `build/reports/jacoco/test/html/index.html`
- Cobertura JaCoCo XML (consumido por SonarCloud): `build/reports/jacoco/test/jacocoTestReport.xml`

## Integración con SonarCloud

El repositorio `MarioLeivaUdeA/password-validator` ya está en GitHub. Para conectarlo:

1. En SonarCloud, importa ese repositorio en la organización `1193212909wmle`. La importación crea el proyecto y muestra su clave (`project key`).
2. Genera un token en SonarCloud y guárdalo en GitHub como secreto del repositorio llamado `SONAR_TOKEN`.
3. Ejecuta el workflow `SonarCloud` o, localmente, configura el token y ejecuta el escaneo:

```powershell
$env:SONAR_TOKEN = '<token de SonarCloud>'
.\gradlew.bat clean test checkstyleMain checkstyleTest jacocoTestReport sonar
```

No se debe guardar el token en el repositorio. El análisis espera el resultado del Quality Gate; si no se cumple, el comando de análisis falla. GitHub Actions también publica el resultado del análisis y SonarCloud puede mostrar el estado del Quality Gate en los pull requests.

### Quality Gate, Quality Profile y complejidad

- **Quality Gate**: condiciones de aceptación del análisis, como cobertura, duplicación, bugs o mantenibilidad. El resultado se configura y consulta en SonarCloud. Si se quiere bloquear merges, se debe exigir el check de SonarCloud como regla de protección de la rama.
- **Quality Profile**: conjunto de reglas activas para Java; determina qué issues se reportan. Se selecciona/configura en SonarCloud para el proyecto u organización.
- **Complejidad ciclomática**: SonarCloud la reporta por método y para el código, considerando decisiones y operadores lógicos. Se puede añadir al Quality Gate si se define una condición para esa métrica.
- **Complejidad cognitiva**: SonarCloud la reporta como métrica de mantenibilidad; refleja lo difícil que resulta seguir el flujo de control. Un umbral por método también puede configurarse como regla del Quality Profile.
- **Cobertura**: JaCoCo registra qué líneas e instrucciones ejecutan las pruebas. SonarCloud ingiere el XML generado; no ejecuta JaCoCo por sí mismo.

## Estado de código y análisis local

- La aplicación contiene `PasswordValidator.java`, una clase pequeña con validación de longitud, mayúscula, minúscula, dígito y carácter especial, además de una puntuación de 0 a 5.
- `PasswordValidator.class` es bytecode compilado desde el archivo `.java` por Gradle; no es una segunda implementación. Está en `build/classes/java/main`, que es salida de compilación y se puede regenerar.
- Checkstyle aplica reglas de indentación, imports no usados, Javadoc y longitud de método. El último reporte disponible encontró cero violaciones en código de producción y pruebas.
- JaCoCo genera cobertura local y SonarCloud importará ese reporte para mostrar la cobertura y evaluarla según el Quality Gate elegido. En la última ejecución: 100% de líneas e instrucciones, y 79,2% de ramas (5 ramas no cubiertas en `getStrengthScore`).
- El proyecto existente de SonarCloud tiene la clave `MarioLeivaUdeA_password-validator`; el workflow usa el secreto `SONAR_TOKEN` de GitHub para autenticarse.
- La prueba funcional que fallaba usaba `*`, que sí es un carácter especial aceptado; la prueba ahora usa una contraseña sin especial. Se amplió el límite temporal de las pruebas de rendimiento para reducir falsos fallos por variación del entorno.

## Estado verificado

Entorno comprobado: Gradle Wrapper 8.10.2 y JDK Microsoft OpenJDK 17.0.20 LTS.

El validador considera válida una contraseña con al menos ocho caracteres, una mayúscula, una minúscula, un dígito y un carácter especial. `getStrengthScore` devuelve de cero a cinco puntos, uno por cada regla satisfecha.
