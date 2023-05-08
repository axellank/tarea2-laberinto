
public class Main{
    public static void main(String[] args) {        
        int filas = Integer.parseInt(args[0]);
        String parametro2 = args[1];
        int columnas = Integer.parseInt(parametro2);

        Laberinto uno = new Laberinto(filas,columnas);
        uno.crearLaberinto(1, 1, columnas, columnas);
        uno.imprimirLaberinto();        
        uno.resolverLaberintoBFS();          
        uno.imprimirLaberintoResuelto();  
    }
}