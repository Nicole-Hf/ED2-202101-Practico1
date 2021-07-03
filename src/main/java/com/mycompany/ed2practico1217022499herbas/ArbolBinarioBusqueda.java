/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ed2practico1217022499herbas;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import practico1.excepciones.ExcepcionClaveNoExiste;

/**
 *
 * @author Nicole
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> 
        implements IArbolBusqueda<K,V> {
    
    protected NodoBinario<K,V> raiz;

    public ArbolBinarioBusqueda() {
        
    }
    
    public ArbolBinarioBusqueda(K clave, V valor) {
        this.raiz = new NodoBinario<>(clave,valor);
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (valorAInsertar == null) {
            throw new RuntimeException("No se pueden insertar valores nulos");
        }
        
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }
        
        NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K,V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveActual) < 0){
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        
        NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }

    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }
    
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> supuestoNuevoHijoIzq = eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> supuestoNuevoHijoDer = eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoActual;
        }
        //caso 1: es hoja
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2.1. tiene hi
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso 2.2. tiene hd
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoDerecho();
        }
        //caso 3 tiene ambos hijos
        NodoBinario<K,V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(),nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }
    
    protected NodoBinario<K,V> buscarSucesor(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoBinario<K,V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidadDeNodos = 0;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            cantidadDeNodos++;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidadDeNodos;
    }

    public int alturaIt() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int alturaDelArbol = 0;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {
            int nroDeNodosDelNivel = colaDeNodos.size();
            int posicion = 0;
            while (posicion < nroDeNodosDelNivel) {
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }
    
    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    protected int altura(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1 
                : alturaPorDerecha + 1;
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        pilaDeNodos.push(nodoActual);
        
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = nodoActual.getHijoIzquierdo();
            if (!NodoBinario.esNodoVacio(nodoActual)) {
                pilaDeNodos.push(nodoActual);
            }
            while (NodoBinario.esNodoVacio(nodoActual) && !pilaDeNodos.isEmpty()) {
                NodoBinario<K,V> temporal = pilaDeNodos.pop();
                recorrido.add(temporal.getClave());
                nodoActual = temporal.getHijoDerecho();
                if (!NodoBinario.esNodoVacio(nodoActual)) {
                    pilaDeNodos.push(nodoActual);
                }
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        insertarEnPilaParaPostOrden(nodoActual,pilaDeNodos);
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop(); 
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K,V> nodoDelTope = pilaDeNodos.peek();       
                if (!nodoDelTope.esVacioHijoDerecho() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    insertarEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(),pilaDeNodos);
                }
            }
        }
        return recorrido;
    }

    private void insertarEnPilaParaPostOrden(NodoBinario<K, V> nodoActual, Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }           
        }
    }

    @Override
    public String toString() {
        String arbol = " ";
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        System.out.println("    ("+this.raiz.getClave()+")");
        
        while (!colaDeNodos.isEmpty()) {
            int nroDeNodosDelNivel = colaDeNodos.size();
            int posicion = 0;
            while (posicion < nroDeNodosDelNivel) {
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();                
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    System.out.print("  ("+nodoActual.getHijoIzquierdo().getClave()+")_|");
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                    System.out.print("|_("+nodoActual.getHijoDerecho().getClave()+")");
                }
                posicion++;
            }
            System.out.println();
        }
        return arbol;
    }
    
    /** 3. Implemente un método iterativo que retorne la cantidad de nodos 
     *  que tienen ambos hijos distintos de vacío en un árbol binario
     * */
    @Override
    public int cantidadDeNodosConAmbosHijos() {
        int contador = 0;
        if (this.esArbolVacio()) {
            return 0;
        }
        Stack<NodoBinario<K,V>> pilaNodos = new Stack<>();
        pilaNodos.push(this.raiz);
        
        while (!pilaNodos.isEmpty()) {
            NodoBinario<K,V> nodoActual = pilaNodos.pop();
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaNodos.push(nodoActual.getHijoIzquierdo());
            }
            if (nodoActual.cantidadHijos() == 2) {
                contador++;
            }
        }   
        return contador;
    }
    
    /** 4. Implemente un método recursivo que retorne la cantidad de nodos 
     * que tienen ambos hijos distintos de vacío en un árbol binario
     * */
    @Override
    public int cantidadDeNodosConAmbosHijosRec() {
        return cantidadDeNodosConAmbosHijosRec(this.raiz);
    }
    
    protected int cantidadDeNodosConAmbosHijosRec(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 0;
        }
        int cantidadIzquierda = cantidadDeNodosConAmbosHijosRec(nodoActual.getHijoIzquierdo());
        int cantidadDerecha = cantidadDeNodosConAmbosHijosRec(nodoActual.getHijoDerecho());   
        if (nodoActual.cantidadHijos() == 2) {
            return cantidadIzquierda + cantidadDerecha + 1;
        }
        return cantidadIzquierda + cantidadDerecha;
    }
    
    /** 5. Implemente un método iterativo que retorne la cantidad de nodos 
     * que tienen ambos hijos distintos de vacío en un árbol binario, 
     * pero solo en el nivel N
     * */
    @Override
    public int cantidadNodosConAmbosHijosEnN(int nivel) {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        int nivelDelArbol = 0;
        Queue<NodoBinario<K,V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int nroNodosDelNivel = colaNodos.size();
            int posicion = 0;        
            while (posicion < nroNodosDelNivel) {
                NodoBinario<K,V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                } 
                if (nivel == nivelDelArbol) {
                    if (nodoActual.cantidadHijos() == 2) {
                        cantidad++;
                    }
                }
                posicion++;
            }         
            nivelDelArbol++;
        }
        return cantidad;
    }
    
    /** 6. Implemente un método RECURSIVO que retorne la cantidad de nodos 
     * que tienen ambos hijos distintos de vacío en un árbol binario, 
     * pero solo en el nivel N
     * */
    @Override
    public int cantidadNodosConAmbosHijosEnNRec(int nivel) {
        return cantidadNodosConAmbosHijosEnNRec(this.raiz,nivel,0);
    }
    
    protected int cantidadNodosConAmbosHijosEnNRec(NodoBinario<K,V> nodoActual, int nivel, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadIzquierda = cantidadNodosConAmbosHijosEnNRec(nodoActual.getHijoIzquierdo(),nivel,nivelActual + 1);
        int cantidadDerecha = cantidadNodosConAmbosHijosEnNRec(nodoActual.getHijoDerecho(),nivel,nivelActual + 1);
        if (nivelActual == nivel) {
            if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                return 1;
            }
        }
        return cantidadIzquierda + cantidadDerecha;
    }
    
    /** 7. Implemente un método iterativo que retorne la cantidad nodos 
     * que tienen un solo hijo diferente de vacío en un árbol binario, 
     * pero solo antes del nivel N
     * */
    @Override
    public int cantidadNodosConUnHijoEnN(int nivel) {
        if (NodoBinario.esNodoVacio(this.raiz)) {
            return 0;
        }
        int cantidad = 0;
        int nivelArbol = 0;
        Queue<NodoBinario<K,V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while (!colaNodos.isEmpty()) {
            int nroNodosDelNivel = colaNodos.size();
            int posicion = 0;
            while (posicion < nroNodosDelNivel) {
                NodoBinario<K,V> nodoActual = colaNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaNodos.offer(nodoActual.getHijoDerecho());
                }
                if (nivelArbol < nivel) {
                    if (nodoActual.cantidadHijos() == 1) {
                        cantidad++;
                    }
                }
                posicion++;
            }
            nivelArbol++;
        }
        return cantidad;
    }
    
    /**  12. Implemente un método iterativo con la lógica de un recorrido 
     * en inOrden que retorne el número de nodos que tiene un árbol binario.
     * */
    @Override
    public int nroNodosConInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int contador = 0;
        Stack<NodoBinario<K,V>> pilaNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        pilaNodos.push(nodoActual);
        while (!pilaNodos.isEmpty()) {
            nodoActual = nodoActual.getHijoIzquierdo();
            if (!NodoBinario.esNodoVacio(nodoActual)) {
                pilaNodos.push(nodoActual);
            }
            while (NodoBinario.esNodoVacio(nodoActual) && !pilaNodos.isEmpty()) {
                NodoBinario<K,V> nodoTemporal = pilaNodos.pop();
                contador++;
                nodoActual = nodoTemporal.getHijoDerecho();
                if (!NodoBinario.esNodoVacio(nodoActual)) {
                    pilaNodos.push(nodoActual);
                }
            }
        }
        return contador;
    }
    
    /** 13. Implemente un método que reciba en listas de parámetros las 
     * llaves y valores de los recorridos en postorden e inorden 
     * respectivamente y que reconstruya el árbol binario original. 
     * Su método no debe usar el método insertar.
     * */
    
    /** 14. Implemente un método privado que reciba un nodo binario de un 
     * árbol binario y que retorne cual sería su sucesor inorden de la 
     * clave de dicho nodo.
     * */
    @Override
    public K sucesorInOrden(NodoBinario<K,V> nodoActual) {       
        List<K> recorridoInOrden = new LinkedList<>();
        recorridoInOrden = this.recorridoEnInOrden();
        int posicion = 0;
        K claveBuscar = nodoActual.getClave();
        K claveSucesora = null;
        if (claveBuscar.compareTo(recorridoInOrden.get(recorridoInOrden.size()-1)) == 0) {
            return null;
        }
        while (!recorridoInOrden.isEmpty()) {
            K claveInOrden = recorridoInOrden.get(posicion);
            if (claveBuscar.compareTo(claveInOrden) < 0) {
                claveSucesora = claveInOrden;
                return claveSucesora;
            }
            posicion++;
        }
        return claveSucesora;
    }
    
    private NodoBinario<K,V> nodoSucesorInOrden(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoSucesor = nodoActual.getHijoDerecho();
        while (!NodoBinario.esNodoVacio(nodoSucesor.getHijoIzquierdo())) {
            nodoSucesor = nodoSucesor.getHijoIzquierdo();
        }
        return nodoSucesor;
    }
    
    /** 15. Implemente un método privado que reciba un nodo binario de un 
     * árbol binario y que retorne cuál sería su predecesor inorden 
     * de la clave de dicho nodo.
     * */
    @Override
    public K predecedorInOrden(NodoBinario<K,V> nodoActual) {
        List<K> recorridoInOrden = new LinkedList<>();
        recorridoInOrden = this.recorridoEnInOrden();
        K claveABuscar = nodoActual.getClave();
        K clavePredecesora = null;
        int posicion = 0;
        if (claveABuscar.compareTo(recorridoInOrden.get(0)) == 0) {
            return null;
        }
        while (!recorridoInOrden.isEmpty()) {
            K claveInOrden = recorridoInOrden.get(posicion);
            if (posicion + 1 < recorridoInOrden.size()) {
                if (claveABuscar.compareTo(claveInOrden) > 0 
                    && claveABuscar.compareTo(recorridoInOrden.get(posicion+1)) <= 0) {
                    clavePredecesora = claveInOrden;
                    return clavePredecesora;                
                }
            }            
            posicion++;
        }
        return clavePredecesora;
    }
    
    private NodoBinario<K,V> nodoPredecesorInOrden(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoPredecesor = nodoActual.getHijoIzquierdo();
        while (nodoPredecesor.getHijoDerecho() != null) {
            nodoPredecesor = nodoPredecesor.getHijoDerecho();
        }
        return nodoPredecesor;
    }
    
    /** 16. Implemente un método que retorne la menor llave en un 
     * árbol binario de búsqueda.
     * */
    @Override
    public K menorLlave() {
        NodoBinario<K,V> nodoActual = this.raiz;
        K claveMenor = nodoActual.getClave();       
        while (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
            claveMenor = nodoActual.getHijoIzquierdo().getClave();  
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return (K)claveMenor;
    }
    
    /** 21. Implemente un método que retorne verdadero si un árbol 
     * binario esta lleno. Falso en caso contrario.
     * */
    @Override
    public boolean esArboLleno() {
        int altura = this.altura();
        
        return this.nroNodosConInOrden() == Math.pow(2, altura) - 1;
    }    
    
    private boolean esLleno(NodoBinario<K,V> nodoActual) {       
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;
        }
        if (nodoActual.esHoja()) {
            return true;
        }
        boolean lleno = true;
        if (cantidadNodos(nodoActual.getHijoIzquierdo()) != cantidadNodos(nodoActual.getHijoDerecho())) {
            lleno = false;
        }
        if (altura(nodoActual.getHijoIzquierdo()) != altura(nodoActual.getHijoDerecho())) {
            lleno = false;
        }
        boolean esLlenoIzquierdo = esLleno(nodoActual.getHijoIzquierdo());
        boolean esLlenoDerecho = esLleno(nodoActual.getHijoDerecho());
        return lleno && esLlenoIzquierdo && esLlenoDerecho;               
    }  
    
    private int cantidadNodos(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 1;
        }
        int cantidadIzquierda = cantidadNodos(nodoActual.getHijoIzquierdo());
        int cantidadDerecha = cantidadNodos(nodoActual.getHijoDerecho());
        return cantidadIzquierda + cantidadDerecha;
    }
}
