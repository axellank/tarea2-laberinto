# tarea2-laberinto

**Axel Daniel Hernandez Sandoval || 320125180**

El código es una implementación de un laberinto generado mediante pilas y colas.
La clase Laberinto tiene atributos como filas, columnas, una matriz laberinto1 de objetos Celda, y cuatro enteros que representan las coordenadas de inicio y fin del laberinto.

La clase Celda representa una celda en el laberinto y tiene atributos como si tiene o no una pared en cada dirección (norte, este, sur, oeste), si ya ha sido visitada o no, y si está en el camino solucionado. Tiene un método para remover una pared en una cierta dirección.

El método crearLaberinto() genera el laberinto de manera aleatoria, se selecciona una celda al azar y se marca como visitada. Luego, se selecciona una de sus celdas vecinas no visitadas al azar y se remueve la pared entre ellas. La celda vecina se marca como visitada y se agrega a una pila de celdas que se han visitado.

El método resolverLaberinto() resuelve el laberinto mediante un algoritmo DFS, se comienza en la celda de inicio y se visita cada celda vecina no visitada, hasta que se llega a la celda de fin, las celdas visitadas se marcan como visitadas y se agregan a una pila que representa el camino solucionado.

Utilice mis propias implementaciones de Listas, Pilas y Colas.

Para ejecutar el programa se tiene que compilar primero usando:
```java
javac Main.java
```
Ya que esta compilado lo debemos correr de la siguiente manera:
```java
java Main <parametro1> <parametro2>
```

De donde **parametro1** son las filas del laberinto y **parametro2** las columnas.
