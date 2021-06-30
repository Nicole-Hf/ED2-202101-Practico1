/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.mycompany.ed2practico1217022499herbas.AVL;
import com.mycompany.ed2practico1217022499herbas.ArbolBinarioBusqueda;
import com.mycompany.ed2practico1217022499herbas.ArbolMViasBusqueda;
import com.mycompany.ed2practico1217022499herbas.IArbolBusqueda;
import com.mycompany.ed2practico1217022499herbas.IArbolMVias;
import com.mycompany.ed2practico1217022499herbas.NodoBinario;
import practico1.excepciones.ExcepcionClaveNoExiste;
import practico1.excepciones.ExcepcionOrdenInvalido;

/**
 *
 * @author Nicole
 */
public class pruebaArboles {

    public static void main(String argumentos[]) throws ExcepcionClaveNoExiste, ExcepcionOrdenInvalido {
        IArbolBusqueda<Integer, String> arbolBinario;
        IArbolBusqueda<Integer, String> arbolAVL;
        IArbolMVias<Integer, String> arbolMVias;  
        System.out.println("***Arbol Binario Busqueda***");
        arbolBinario = new ArbolBinarioBusqueda<>();          
        arbolBinario.insertar(50, "Franco");
        arbolBinario.insertar(60, "Abner");
        arbolBinario.insertar(70, "Yuliana");
        arbolBinario.insertar(90, "Jorge");
        arbolBinario.insertar(40, "Marco");
        arbolBinario.insertar(20, "Ruth");
        arbolBinario.insertar(65, "Ricky");
        arbolBinario.insertar(68, "Harold");
        arbolBinario.insertar(110, "Andrea");
        System.out.println(arbolBinario);
        System.out.println("--------------PRACTICO 1--------------");                     
        arbolAVL = new AVL<>();
        System.out.println("1.Para el árbol AVL implemente el método insertar: ");
        arbolAVL.insertar(50, "Franco");
        arbolAVL.insertar(60, "Abner");
        arbolAVL.insertar(70, "Yuliana");
        arbolAVL.insertar(90, "Jorge");
        arbolAVL.insertar(40, "Marco");
        arbolAVL.insertar(20, "Ruth");
        arbolAVL.insertar(65, "Ricky");
        arbolAVL.insertar(68, "Harold");
        arbolAVL.insertar(110, "Andrea");
        System.out.println(arbolAVL);
        System.out.println("2.Para el árbol AVL implemente el método eliminar: ");
        arbolAVL.eliminar(70);
        System.out.println(arbolAVL);
        System.out.println("3.Implemente un método iterativo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario: "
                + arbolBinario.cantidadDeNodosConAmbosHijos());
        System.out.println("4.Implemente un método recursivo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario: "
                + arbolBinario.cantidadDeNodosConAmbosHijosRec());       
        System.out.println("5.Implemente un método iterativo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario, \n "
                + "pero solo en el nivel N: "
                + arbolBinario.cantidadNodosConAmbosHijosEnN(2));        
        System.out.println("6.Implemente un método recursivo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario, \n "
                + "pero solo en el nivel N: "
                + arbolBinario.cantidadNodosConAmbosHijosEnNRec(2));       
        System.out.println("7.Implemente un método iterativo que retorne la cantidad nodos \n "
                + "que tienen un solo hijo diferente de vacío en un árbol binario, \n "
                + "pero solo antes del nivel N: "
                + arbolBinario.cantidadNodosConUnHijoEnN(3));
        arbolMVias = new ArbolMViasBusqueda<>(4);
        System.out.println("8.Implemente el método insertar de un árbol m-vias de búsqueda: ");
        arbolMVias.insertar(50, "Franco");
        arbolMVias.insertar(60, "Abner");
        arbolMVias.insertar(70, "Yuliana");
        arbolMVias.insertar(90, "Jorge");
        arbolMVias.insertar(40, "Marco");
        arbolMVias.insertar(20, "Ruth");
        arbolMVias.insertar(65, "Ricky");
        arbolMVias.insertar(68, "Harold");
        arbolMVias.insertar(110, "Andrea");
        System.out.println(arbolMVias);
        System.out.println("9.Implemente el método eliminar de un árbol m-vias de búsqueda: ");
        arbolMVias.eliminar(40);
        System.out.println(arbolMVias);
        System.out.println("12.Implemente un método iterativo con la lógica de un recorrido \n "
                + "en inOrden que retorne el número de nodos que tiene un árbol binario: "
                + arbolBinario.nroNodosConInOrden());
        NodoBinario<Integer,String> nodoABuscar = new NodoBinario<>(110,"Ruth");
        System.out.println("14.Implemente un método privado que reciba un nodo binario de un árbol binario y que \n"
                + "retorne cual sería su sucesor inorden de la clave de dicho nodo: "
                + arbolBinario.sucesorInOrden(nodoABuscar));
        System.out.println("15.Implemente un método privado que reciba un nodo binario de un árbol binario y que \n"
                + "retorne cuál sería su predecesor inorden de la clave de dicho nodo: "
                + arbolBinario.predecedorInOrden(nodoABuscar));
        System.out.println("16.Implemente un método que retorne la menor llave en un \n "
                + "árbol binario de búsqueda: "
                + arbolBinario.menorLlave());
        System.out.println("17.Implemente un método que retorne la mayor llave en un \n "
                + "árbol m vias de búsqueda: "
                + arbolMVias.mayorLLave());        
        System.out.println("18.Implemente un método que retorne verdadero si solo hay\n"
                + "hojas en el último nivel de un árbol m-vias de búsqueda. \n"
                + "Falso en caso contrario: "
                + arbolMVias.hayHojasEnUltimoNivel());        
        System.out.println("19.Implemente un método que retorne verdadero si un \n "
                + "árbol m vias tiene solo hojas o nodos con todos sus hijos \n "
                + "distinto de vacío. Falso en caso contrario: "
                + arbolMVias.tieneHojasOTodosHijos());   
        ArbolMViasBusqueda<Integer,String> arbolCopia = new ArbolMViasBusqueda<>(4);
        arbolCopia.insertar(50, "Franco");
        arbolCopia.insertar(60, "Abner");        
        arbolCopia.insertar(70, "Yuliana");
        arbolCopia.insertar(90, "Jorge");
        arbolCopia.insertar(40, "Marco");
        arbolCopia.insertar(45, "jUAN");
        arbolCopia.insertar(20, "Ruth");
        arbolCopia.insertar(65, "Ricky");  
        System.out.println("20. Para un árbol m vías implementar un método que reciba otro \n"
                + "árbol de parámetro y que retorne verdadero si los arboles son \n"
                + "similares. Falso en caso contrario: "
                + arbolMVias.sonIguales(arbolCopia));                
        System.out.println(arbolCopia);
        System.out.println("21. Implemente un método que retorne verdadero si un árbol \n "
                + "binario esta lleno. Falso en caso contrario: "
                + arbolBinario.esArboLleno());
    }
    
}
