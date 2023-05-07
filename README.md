# Tarea-2
Laberinto

Antonio Castillo Hernández 
Num Cuenta: 320017438

## Arquitectura del Programa:
En esta tarea  nosotros tuvimos que modelar un laberinto por medio de una matriz (o matrices en este caso) desde 0, utilizando las estructuras de Datos que modelamos en el Laboratorio entre las cuales estaban:
* Pilas
* Colas
* Listas
* Arreglos
* Matrices

y también uno de los requerimientos del programa fue que utilizara la entrada standar de Linux

## Implementacion:
En primera instancia Decidimos utilizar 2 estructuras de datos principales para nuestro programa, las cuales eran Listas y colas, por lo tanto copiamos las clases que ya teniamos dentro de una carpeta para que pertenecieran al mismo paquete
y ademas en nuestra clase principal, (la de laberinto) extiende de Pila para que esta a su vez tenga todas las propiedades y metodos de las pilas y listas

## Interfaces
Las interfaces que utilizabamos eran:
* Apilable
* Listable
* Coleccionable 

## Clase Abstracta
Teniamos una clase abstracta la cual se llamaba LinealAdapter y extendia de Lista.java, su proposito era de servirnos de comodin para implementar correctamente las clases Pila y Cola en practicas pasadas,
esta misma a su vez tenia 3 metodos los cuales debiamos de implemntar utilizando algunos metodos de Lista, esos metodos eran:
* public void  eliminar(T elemento)
* protected T eliminarInicio() throws NoSuchElementException
* protected Nodo ver() throws NoSuchElementException

## Clase Pila
Los metodos que esta tenia eran los siguientes:

* public void push(T elemento) throws IllegalArgumentException
* public T pop() throws NoSuchElementException
* public T top() 
* toString()

```
Pruebas de ejecion en captura de pantalla subida al repositorio

```
