import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.Random;
//import java.util.Stack;

import listapilacola1.Cola;
import listapilacola1.Lista;
import listapilacola1.Pila;


public class Laberinto {

    private int filas, columnas;
    // El de java
    // private Stack<Celda> removedWalls = new Stack<Celda>();

    // El mio
     private Pila<Celda> removedWalls = new Pila<Celda>();
    private Random random;
    int puntoXpartida;
    int puntoYpartida;
    int puntoXfinal;
    int puntoYfinal;
    private Celda[][] laberinto1;

    // El de java
    // Stack<Celda> laberintoDFS = new Stack<>();
    // Stack<Celda> caminoSolucionado = new Stack<Celda>();

     private Pila<Celda> laberintoDFS = new Pila<>();
     private Pila<Celda> caminoSolucionado = new Pila<>();

    protected class Celda {

        protected boolean paredNorte = true;
        protected boolean paredEste = true;
        protected boolean paredSur = true;
        protected boolean paredOeste = true;
        protected boolean visitada = false;
        protected boolean enCaminoSolucionado = false;

        public int x, y;
        public Laberinto.Celda anterior;

        public Celda(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void removeWall(Integer direccion) {
            if (direccion.equals(1)) {
                paredNorte = false;
            } else if (direccion.equals(2)) {
                paredSur = false;
            } else if (direccion.equals(3)) {
                paredEste = false;
            } else if (direccion.equals(4)) {
                paredOeste = false;
            }
        }

        public boolean getNorte() {
            return paredNorte;
        }

        public boolean getEste() {
            return paredEste;
        }

        public boolean getSur() {
            return paredSur;
        }

        public boolean getOeste() {
            return paredOeste;
        }
    }

    public void removerParedEntreCeldas(int celdaActual, int celdaSeleccionada) {
        int xActual = celdaActual / columnas;
        int yActual = celdaActual % columnas;
        int xSeleccionada = celdaSeleccionada / columnas;
        int ySeleccionada = celdaSeleccionada % columnas;

        Celda celdaActualObj = laberinto1[xActual][yActual];
        Celda celdaSeleccionadaObj = laberinto1[xSeleccionada][ySeleccionada];
    
        if (Math.abs(xActual - xSeleccionada) + Math.abs(yActual - ySeleccionada) != 1) {
            System.out.println("Las celdas no son vecinas");
            throw new IllegalArgumentException("Las celdas no son vecinas.");
        }
        
        if (xActual == xSeleccionada) {
            if (yActual < ySeleccionada) {
                celdaActualObj.removeWall(3);
                celdaSeleccionadaObj.removeWall(4);
            } else {
                celdaActualObj.removeWall(4);
                celdaSeleccionadaObj.removeWall(3);
            }
        } else {
            if (xActual < xSeleccionada) {
                celdaActualObj.removeWall(2);
                celdaSeleccionadaObj.removeWall(1);
            } else {
                celdaActualObj.removeWall(1);
                celdaSeleccionadaObj.removeWall(2);
            }
        }
        
        removedWalls.push(celdaActualObj);
        removedWalls.push(celdaSeleccionadaObj);
    }

    public Laberinto(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.random = new Random();
        this.laberinto1 = new Celda[filas][columnas];

    }

    public void crearLaberinto(int puntoXpartida, int puntoYpartida, int puntoXfinal, int puntoYfinal) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto1[i][j] = new Celda(i, j);
            }
        }

        this.puntoXpartida = puntoXpartida;
        this.puntoXfinal = puntoXfinal;
        this.puntoYpartida = puntoYpartida;
        this.puntoYfinal = puntoYfinal;

        Celda celdaActual = laberinto1[random.nextInt(filas)][random.nextInt(columnas)];
        celdaActual.visitada = true;
        removedWalls.push(celdaActual);

        while (!removedWalls.esVacia()) {
            celdaActual = removedWalls.pop();
            int celdaActualIndex = celdaActual.x * columnas + celdaActual.y;

            ArrayList<Integer> vecinos = new ArrayList<Integer>();
            if (celdaActual.y > 0) {
                if (!laberinto1[celdaActual.x][celdaActual.y - 1].visitada) {
                    vecinos.add(celdaActualIndex - 1);
                }
            }
            if (celdaActual.x < filas - 1) {
                if (!laberinto1[celdaActual.x + 1][celdaActual.y].visitada) {
                    vecinos.add(celdaActualIndex + columnas);
                }
            }
            if (celdaActual.y < columnas - 1) {
                if (!laberinto1[celdaActual.x][celdaActual.y + 1].visitada) {
                    vecinos.add(celdaActualIndex + 1);
                }
            }
            if (celdaActual.x > 0) {
                if (!laberinto1[celdaActual.x - 1][celdaActual.y].visitada) {
                    vecinos.add(celdaActualIndex - columnas);
                }
            }

            if (!vecinos.isEmpty()) {
                int celdaSeleccionadaIndex = vecinos.get(random.nextInt(vecinos.size()));
                Celda celdaSeleccionada = laberinto1[celdaSeleccionadaIndex / columnas][celdaSeleccionadaIndex
                        % columnas];
                removerParedEntreCeldas(celdaActualIndex, celdaSeleccionadaIndex);
                celdaSeleccionada.visitada = true;
                removedWalls.push(celdaSeleccionada);
            }
        }

        laberinto1[puntoXpartida - 1][puntoYpartida - 1].paredOeste = false;
        laberinto1[puntoXfinal - 1][puntoYfinal - 1].paredEste = false;
    }

    public void imprimirLaberinto() {        
        for (int j = 0; j < columnas; j++) {
            System.out.print("+");
            if (laberinto1[0][j].paredNorte) {
                System.out.print("---");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println("+");
        
        for (int i = 0; i < filas; i++) {
            if (laberinto1[i][0].paredOeste) {
                System.out.print("|");
            } else {
                System.out.print(" ");
            }
            for (int j = 0; j < columnas; j++) {
                if (laberinto1[i][j].visitada) {
                    System.out.print("   ");
                } else {
                    System.out.print("XXX");
                }
                if (laberinto1[i][j].paredEste) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            
            System.out.print("+");
            for (int j = 0; j < columnas; j++) {
                if (laberinto1[i][j].paredSur) {
                    System.out.print("---");
                } else {
                    System.out.print("   ");
                }
                System.out.print("+");
            }
            System.out.println();
        }
    }

    public void imprimirLaberintoResuelto() {    
        for (int j = 0; j < columnas; j++) {
            System.out.print("+");
            if (laberinto1[0][j].paredNorte) {
                System.out.print("---");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println("+");
        
        for (int i = 0; i < filas; i++) {
            if (laberinto1[i][0].paredOeste) {
                System.out.print("|");
            } else {
                System.out.print(" ");
            }
            for (int j = 0; j < columnas; j++) {
                if (laberinto1[i][j].visitada) {
                    if (laberinto1[i][j].enCaminoSolucionado == true) {
                        System.out.print(" * ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("   ");
                }
                if (laberinto1[i][j].paredEste) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            
            System.out.print("+");
            for (int j = 0; j < columnas; j++) {
                if (laberinto1[i][j].paredSur) {
                    System.out.print("---");
                } else {
                    System.out.print("   ");
                }
                System.out.print("+");
            }
            System.out.println();
        }
    }

    public void resolverLaberintoBFS() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto1[i][j].visitada = false;
            }
        }
        // Cola de java
        // Queue<Celda> cola = new LinkedList<Celda>();

        //La mia
        Cola<Celda> cola = (Cola<Laberinto.Celda>) new Cola<Celda>();

        // Agregamos la celda de partida a la cola
        Celda celdaActual = laberinto1[puntoXpartida - 1][puntoYpartida - 1];
        cola.agregar(celdaActual);

        while (!cola.esVacia()) {            
            celdaActual = cola.dequeue();
            celdaActual.visitada = true;

            if (celdaActual.x == puntoXfinal - 1 && celdaActual.y == puntoYfinal - 1) {
                break;
            }

            int x = celdaActual.x;
            int y = celdaActual.y;
            if (x > 0 && !celdaActual.paredNorte && !laberinto1[x - 1][y].visitada) {
                cola.agregar(laberinto1[x - 1][y]);
                laberinto1[x - 1][y].anterior = celdaActual;
            }
            if (x < filas - 1 && !celdaActual.paredSur && !laberinto1[x + 1][y].visitada) {
                cola.agregar(laberinto1[x + 1][y]);
                laberinto1[x + 1][y].anterior = celdaActual;
            }
            if (y > 0 && !celdaActual.paredOeste && !laberinto1[x][y - 1].visitada) {
                cola.agregar(laberinto1[x][y - 1]);
                laberinto1[x][y - 1].anterior = celdaActual;
            }
            if (y < columnas - 1 && !celdaActual.paredEste && !laberinto1[x][y + 1].visitada) {
                cola.agregar(laberinto1[x][y + 1]);
                laberinto1[x][y + 1].anterior = celdaActual;
            }
        }
        
        Celda celdaRecorrida = laberinto1[puntoXfinal-1][puntoYfinal-1];
        caminoSolucionado.push(celdaRecorrida); // Add the destination cell to the path
        laberinto1[puntoXfinal-1][puntoYfinal-1].enCaminoSolucionado = true;
        while (celdaRecorrida.anterior != null) {
            celdaRecorrida = celdaRecorrida.anterior;
            caminoSolucionado.push(celdaRecorrida);
            laberinto1[celdaRecorrida.x][celdaRecorrida.y].enCaminoSolucionado = true;
            //System.out.println("Camino Solucionado size: " + caminoSolucionado.size());

        }
        //System.out.println("Camino Solucionado size: " + caminoSolucionado.size());           

    }
}