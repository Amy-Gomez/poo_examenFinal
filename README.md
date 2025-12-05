

# <p align="center"> Proyecto Buscaminas </p>

---

## <p>📝 Descripción del Proyecto</p>

Este proyecto consiste en una aplicación de consola desarrollada en Java que simula el clásico juego **Buscaminas**. El objetivo principal del proyecto es demostrar la aplicación de los conceptos de Programación Orientada a Objetos (POO), la gestión de la persistencia de partidas mediante **Serialización de Objetos**, y la implementación de un **Manejo de Excepciones** personalizado para la lógica del juego.

**Características principales:**
* **Modelo de Objetos:** Implementación de clases para `Tablero`, `Casilla`, `CasillaMina` y `CasillaVacia`.
* **Persistencia de Partida:** El estado completo del tablero puede **guardarse y cargarse** automáticamente usando Serialización de Java (`.dat`).
* **Arquitectura MVC:** Clara separación entre el Modelo (lógica y datos), la Vista (interfaz de consola) y el Controlador (gestión del flujo).
* **Manejo de Excepciones:** Uso de la excepción personalizada **`JugadaInvalidaException`** para gestionar errores de entrada del usuario y violaciones de las reglas del juego.
  
---
## <p style="color:#a64d79;">📂 Estructura del Código (Arquitectura MVC)</p>
El proyecto sigue el patrón **Modelo-Vista-Controlador** organizado en los siguientes paquetes:

```text
src/
├── controlador/
│   ├── ControladorJuego.java    # Orquesta el flujo de la partida y procesa las jugadas.
│   └── Main.java                # Punto de entrada del programa.
├── modelo/
│   ├── Tablero.java             # Crea, inicializa y gestiona las casillas (Algoritmo central).
│   ├── Casilla.java (Abstracta) # Define el comportamiento base de las celdas.
│   ├── CasillaMina.java
│   └── CasillaVacia.java
├── vista/
│   ├── IVista.java              # Interfaz para desacoplar la vista (DIP).
│   └── VistaConsola.java        # Implementación de la interfaz de usuario por consola.
├── util/
│   ├── GestorPersistencia.java  # Serializa/Deserializa el objeto Tablero (.dat).
│   └── JugadaInvalidaException.java # Excepción personalizada para errores de jugada.
└── test/
    └── CasillaTest.java         # Asegura que la casilla se comporta correctamente.
    └── GestorPersistenciaTest.java  # Asegura que guardar y cargar el juego funciona.
    └── TableroTest.java         # Asegura que el tablero y el algoritmo de contar minas funcionan bien. 
```
---
## <p style="color:#a64d79;"> Implementaciones y Mejoras Técnicas</p>

### 1. Persistencia de Partidas (Serialización)
Se utiliza la serialización de Java para guardar el estado completo del tablero (incluyendo la posición de las minas, casillas descubiertas y marcadas) en un archivo binario (`partida_guardada.dat`).
* La clase **`GestorPersistencia`** es estática y maneja las operaciones de lectura/escritura (`ObjectInputStream`/`ObjectOutputStream`).
* Todas las clases del Modelo (`Tablero`, `Casilla`, `CasillaMina`, `CasillaVacia`) implementan la interfaz **`Serializable`** y tienen definido el **`serialVersionUID`** para garantizar la compatibilidad entre versiones y evitar errores al cargar.

### 2. Lógica del Juego y Recursividad
El **`ControladorJuego`** y el **`Tablero`** implementan la lógica del juego:
* **Algoritmo Flood Fill:** El método `revelarCasilla()` en el Controlador usa **recursividad** para descubrir automáticamente todas las casillas adyacentes vacías (con `0` minas alrededor), replicando el comportamiento estándar del Buscaminas.
* **Validación de Jugada:** La conversión de coordenadas (ej: `A5` a Fila/Columna) y la validación de rango se realizan antes de acceder al modelo, previniendo errores de `ArrayIndexOutOfBoundsException`.

### 3. Manejo de Excepciones Personalizado
Para controlar los fallos de la lógica de negocio y las entradas inválidas, se implementó:
* **`JugadaInvalidaException`**: Una excepción personalizada que se lanza en **`ControladorJuego.procesarJugada()`** cuando el usuario intenta una acción no permitida (ej: descubrir una casilla marcada, usar un formato incorrecto).
* Esto permite al programa **mostrar un mensaje claro** al usuario sin detener la ejecución o recurrir a excepciones genéricas del sistema.
  
---

## <p style="color:#a64d79;"> Clonación y Ejecución</p>

### 1. Clonar el repositorio y Preparar el IDE
1.  En la página del repositorio, haga clic en el botón verde **"Code"**.
2.  Copie el enlace **SSH** proporcionado.
3.  Abra su IDE ( en este caso es Eclipse) y proceda a **clonar** el repositorio desde el menú `File` $\rightarrow$ `Import` $\rightarrow$ `Projects from Git` $\rightarrow$ `Clone URI`.
4.  Asegúrese de que el proyecto se importe como un **Proyecto Java** estándar.

### 2. Ejecutar la Aplicación
1.  En el explorador de proyectos, navegue al paquete `controlador`.
2.  Abra el archivo **`Main.java`**.
3.  Haga clic derecho $\rightarrow$ **Run As** $\rightarrow$ **Java Application**.
4.  El juego se iniciará en la consola.
   
---
## <p style="color:#a64d79;">🕹️ Guía de Uso y Ejemplos de Ejecución</p>

El juego comienza mostrando el **Menú Principal**.

### 1. Menú Principal

Al iniciar, se le preguntará si desea:
* **1:** Iniciar una **Nueva Partida**.
* **2:** **Cargar Partida** (Si existe `partida_guardada.dat`).
* **3:** Salir.

### 2. Formato de Jugadas

Una vez en el juego, las jugadas se ingresan con el siguiente formato: **`[ACCIÓN] [COORDENADA]`**

| Acción | Descripción | Ejemplo | Resultado |
| :--- | :--- | :--- | :--- |
| **D** | **Descubrir** la casilla. | `D A5` | Revela el contenido (número o mina). |
| **M** | **Marcar/Desmarcar** la casilla con una bandera (`F`). | `M B7` | Coloca o quita una bandera. |
| **GUARDAR** | Guarda el estado actual de la partida. | `GUARDAR` | La partida se puede cargar después. |
| **SALIR** | Termina la partida actual y cierra el programa. | `SALIR` | Cierra la aplicación. |

### Ejemplos de Interacción en Consola

```bash
=== BUSCAMINAS 1.0 ===
Menú Principal: 1. Nueva Partida / 2. Cargar Partida / 3. Salir
Ingrese opción, jugada (ej: D A5) o comando (GUARDAR/SALIR): 1
¡Partida Iniciada! Usa D A5 (Descubrir) o M B7 (Marcar). Escribe GUARDAR o SALIR.

  |  0  1  2  3  4  5  6  7  8  9
--|------------------------------
A | #  #  #  #  #  #  #  #  #  # 
B | #  #  #  #  #  #  #  #  #  # 
...
Ingrese jugada (ej: D A5, M B7 o SALIR): M C3
Bandera colocada.

Ingrese jugada (ej: D A5, M B7 o SALIR): D B7
Ingrese jugada (ej: D A5, M B7 o SALIR): D Z9 
ERROR: Coordenada fuera de rango del tablero (A0-J9).

Ingrese jugada (ej: D A5, M B7 o SALIR): GUARDAR
Partida guardada exitosamente.

Ingrese jugada (ej: D A5, M B7 o SALIR): D A0
¡BOOM! Has pisado una mina. Fin del juego :(
