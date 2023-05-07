import java.util.Random;

public class Laberinto extends Pila{
    private int m; // Columnas
    private int n; // Filas
    private boolean[][] paredesHorizontales;
    private boolean[][] paredesVerticales;
    private final int[][] DIRECCIONES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

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
        return new Laberinto (n,m);
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

    public Lista<int[]> buscarCaminoDFS(int[] inicio, int[] fin, boolean[][] visitado) {
        Lista<int[]> camino = new Lista<>();
        if (inicio[0] == fin[0] && inicio[1] == fin[1]) {
            camino.agregarAlFinal(inicio);
            return camino;
        }
        visitado[inicio[0]][inicio[1]] = true;
        for (int[] direccion : DIRECCIONES) {
            int x = inicio[0] + direccion[0];
            int y = inicio[1] + direccion[1];
            if (x < 0 || x >= n || y < 0 || y >= m) {
                continue;
            }
            if (visitado[x][y] || paredesHorizontales[Math.min(x, inicio[0])][Math.min(y, inicio[1])]) {
                continue;
            }
            Lista<int[]> subcamino = buscarCaminoDFS(new int[]{x, y}, fin, visitado);
            if (!subcamino.esVacia()) {
                subcamino.agregarAlInicio(inicio);
                return subcamino;
            }
        }
        return camino;
    }
    
    public void resolverLaberinto() {
        boolean[][] visitado = new boolean[n][m];
        Lista<int[]> camino = buscarCaminoDFS(new int[]{0, 0}, new int[]{n-1, m-1}, visitado);
        if (camino.esVacia()) {
            System.out.println("Se encontró el siguiente camino:");
            for (int[] posicion : camino) {
                System.out.println("(" + posicion[0] + ", " + posicion[0] + ")");
            }
        } else {
            System.out.println("No se encontró un camino.");
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