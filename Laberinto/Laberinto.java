import java.util.Random;
public class Laberinto extends Pila{
    private int m; // Columnas
    private int n; // Filas
    private boolean[][] paredesHorizontales;
    private boolean[][] paredesVerticales;

    public Laberinto(int n, int m) {
        this.n = n;
        this.m = m;
        paredesHorizontales = new boolean[n][m];
        paredesVerticales = new boolean[n][m];
    }

    public Laberinto() {
        this(10, 10); // constructor por defecto, crea un laberinto de 10x10
     
    }

    public Laberinto(boolean[][] paredesHorizontales, boolean[][] paredesVerticales) {
        this.paredesHorizontales = paredesHorizontales;
        this.paredesVerticales = paredesVerticales;
    }

    public boolean[][] getParedesHorizontales() {
        return paredesHorizontales;
    }

    public boolean[][] getParedesVerticales() {
        return paredesVerticales;
    }
    
    private boolean hayCeldasNoVisitadas(boolean[][] visitadas) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visitadas[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Crear una pila para almacenar las celdas visitadas
    Pila<Integer> pilaFilas = new Pila();
    Pila<Integer> pilaColumnas = new Pila();

    public Laberinto generarLaberinto() {
        // Inicializar todas las paredes como presentes
        for (int i = 0; i < n; i++) {
            //Recorrimiento de las paredes Horizontales 
            for (int j = 0; j < m; j++) {
                paredesHorizontales[i][j] = true;
            }
            //Recorrimiento de las paredes Verticales 
            for (int j = 0; j < m; j++) {
                paredesVerticales[i][j] = true;
            }            
     }
    
        // Hacemos que la Cela inicial sea la esquina superior izquierda
        int filaActual = 0;
        int columnaActual = 0;

        // Marcar la celda inicial como visitada
        boolean[][] visitadas = new boolean[n][m];
        visitadas[filaActual][columnaActual] = true;
    
        // Iterar hasta que se hayan visitado todas las celdas
        while (hayCeldasNoVisitadas(visitadas)) {
            //Creamos nuestro objeto ramdom para utilizarlo en el while que vamos a hacer
            Random random = new Random();
            // Seleccionar una celda vecina no visitada al azar
            Lista<Integer> vecinos = obtenerVecinosNoVisitados(filaActual, columnaActual, visitadas);
            if (!vecinos.esVacia()) {
                int indiceVecino = random.nextInt(vecinos.getTamanio());
                int filaVecino = vecinos.getElemento(indiceVecino) / m;
                int columnaVecino = vecinos.getElemento(indiceVecino) % m;
    
                // Apila la celda actual en la pila
                pilaFilas.push(filaActual);
                pilaColumnas.push(columnaActual);
    
                // Elimina la pared entre la celda actual y la celda vecina
                if (filaActual == filaVecino) {
                    paredesVerticales[filaActual][Math.min(columnaActual, columnaVecino)] = false;
                } else {
                    paredesHorizontales[Math.min(filaActual, filaVecino)][columnaActual] = false;
                }
                // Marca la celda vecina como visitada y hacerla la celda actual
                filaActual = filaVecino;
                columnaActual = columnaVecino;
                visitadas[filaActual][columnaActual] = true;
            } else if (!pilaFilas.esVacia()) {
                // Retroceder a la celda anterior
                filaActual = pilaFilas.pop();
                columnaActual = pilaColumnas.pop();
            }
        }
        return new Laberinto(paredesHorizontales,paredesVerticales);
    }    

    private Lista<Integer> obtenerVecinosNoVisitados(int fila, int columna, boolean[][] visitadas) {
        Lista<Integer> vecinos = new Lista();
        
        if (fila > 0 && !visitadas[fila - 1][columna]) {
            vecinos.agregarAlFinal((fila - 1) * m + columna);
        }
        if (columna > 0 && !visitadas[fila][columna - 1]) {
            vecinos.agregarAlFinal(fila * m + columna - 1);
        }
        if (fila < n - 1 && !visitadas[fila + 1][columna]) {
            vecinos.agregarAlFinal((fila + 1) * m + columna);
        }
        if (columna < m - 1 && !visitadas[fila][columna + 1]) {
            vecinos.agregarAlFinal(fila * m + columna + 1);
        }
        return vecinos;
    }    

    public void resolverLaberinto() {
        boolean[][] visitadas = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                visitadas[i][j] = false;
            }
        }
        int filaActual = 0;
        int columnaActual = 0;
        visitadas[filaActual][columnaActual] = true;
        
        Pila<Integer> pilaFilas = new Pila<>();
        Pila<Integer> pilaColumnas = new Pila<>();
        pilaFilas.push(filaActual);
        pilaColumnas.push(columnaActual);
        
        while (!pilaFilas.esVacia()) {
            filaActual = pilaFilas.pop();
            columnaActual = pilaColumnas.pop();
            
            if (filaActual == n - 1 && columnaActual == m - 1) {
                // Se llegó al final del laberinto
                break;
            }
            
            Lista<Integer> vecinos = obtenerVecinosNoVisitados(filaActual, columnaActual, visitadas);
            for (int i = 0; i < vecinos.getTamanio(); i++) {
                int filaVecino = vecinos.getElemento(i) / m;
                int columnaVecino = vecinos.getElemento(i) % m;
                visitadas[filaVecino][columnaVecino] = true;
                pilaFilas.push(filaVecino);
                pilaColumnas.push(columnaVecino);
            }
        }
        String[][] laberinto = new String[n][m];
        // Marcar la trayectoria en el laberinto
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visitadas[i][j]) {
                    laberinto[i][j] = "x";
                }
            }
        }
        // Imprimir el laberinto con la trayectoria marcada
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(laberinto[i][j]);
            }
            System.out.println();
        }
    }    

        public String toString() {
            StringBuilder xd = new StringBuilder();
        
            // Imprimir la primera fila de barras superiores
            for (int i = 0; i < m; i++) {
                xd.append(" _");
            }
            xd.append("\n");

            // Imprimir las filas de celdas y paredes horizontales
            for (int i = 0; i < n; i++) {
                xd.append("|");
                for (int j = 0; j < m; j++) {
                    xd.append(paredesVerticales[i][j] ? " " : "_");
                    xd.append(paredesHorizontales[i][j] ? " " : "|");
                }
                xd.append("\n");
            }
            return xd.toString();
        }    
}