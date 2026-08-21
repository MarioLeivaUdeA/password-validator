# Password Validator

Proyecto Java sencillo con Gradle para demostrar validación de contraseñas, pruebas unitarias con patrón AAA, pruebas básicas de rendimiento y análisis estático con Checkstyle.

## Requisitos

- JDK 8 o superior
- Gradle 7 o superior, o un Gradle Wrapper generado para el proyecto

## Comandos

En Windows:

```text
.\gradlew.bat build
.\gradlew.bat test
.\gradlew.bat checkstyleMain checkstyleTest
```

En PowerShell es necesario anteponer `.` y `\` para ejecutar un archivo ubicado
en la carpeta actual. `JAVA_HOME` debe apuntar a la carpeta del JDK, por ejemplo:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Microsoft\jdk-17.0.20.8-hotspot'
.\gradlew.bat build
```

Después de instalar o configurar Java, abre un terminal nuevo para que VS Code
reciba las variables de entorno actualizadas.

Los reportes se generan en `build/reports/tests/test/index.html` y `build/reports/checkstyle/`.

## Estado verificado

El proyecto incluye el Gradle Wrapper (`gradlew.bat`) y fue verificado con:

- JDK Microsoft OpenJDK 17.0.20 LTS
- Gradle Wrapper 8.10.2
- Extensiones de VS Code: Extension Pack for Java y Gradle for Java

Comandos ejecutados correctamente en Windows:

```text
.\gradlew.bat clean build
.\gradlew.bat checkstyleMain checkstyleTest
```

Resultados:

- 5 pruebas unitarias aprobadas.
- 10 repeticiones de la prueba de rendimiento aprobadas.
- Checkstyle aprobado para código principal y pruebas.
- Código de salida: `0`.

## Reglas del validador

Una contraseña válida tiene al menos ocho caracteres, una mayúscula, una minúscula, un dígito y un carácter especial. `getStrengthScore` devuelve de cero a cinco puntos, uno por cada regla satisfecha.