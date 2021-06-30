/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ed2practico1217022499herbas;

import java.util.List;
import practico1.excepciones.ExcepcionClaveNoExiste;

/**
 *
 * @author Nicole
 * @param <K>
 * @param <V>
 */
public interface IArbolMVias<K extends Comparable<K>, V> {
    void insertar(K claveAInsertar, V valorAInsertar);
    V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste;
    V buscar(K claveABuscar);
    boolean contiene(K claveABuscar);
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPostOrden();
    K mayorLLave();
    boolean hayHojasEnUltimoNivel();
    boolean tieneHojasOTodosHijos();
    boolean sonIguales(ArbolMViasBusqueda<K,V> arbolCopia);
    
}
