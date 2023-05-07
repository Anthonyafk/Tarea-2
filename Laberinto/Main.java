import java.util.Scanner;
public class Main{
    public static void main(String[] args) {

        System.out.println("\n"+"Okay ahora probemos si es que funciona mi clase lista");
        Lista miLista = new Lista<>(null, null);
        miLista.agregarAlInicio(67);
        miLista.agregarAlInicio(16);
        System.out.println(miLista);
        System.out.println("Parece que funciona xd"+ "\n");
        
        //Var para la entrada de datos
        int n;
        int m;
        Scanner scan = new Scanner(System.in); //Scanner

        System.out.print("Ingrese el valor de m: ");
        m = scan.nextInt();

        System.out.print("Ingrese el valor de n: ");
        n= scan.nextInt();
        

        Laberinto prueba = new Laberinto(n,m);
        prueba.generarLaberinto();
        System.out.println(prueba);

        System.out.println("La resolucion del laberinto se ve asi:");
        prueba.resolverLaberinto();
    }
}