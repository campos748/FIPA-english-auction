/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendedor;

import jade.core.AID;

/**
 *
 * @author martin
 */
public class Subasta {
    
    private String tituloLibro;
    private int precio;
    private int incremento;
    private AID ganador;

    public Subasta(String tituloLibro, int precioInicial, int incremento) {
        this.tituloLibro = tituloLibro;
        this.precio = precioInicial;
        this.incremento = incremento;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public int getPrecio() {
        return precio;
    }

    public int getIncremento() {
        return incremento;
    }

    public AID getGanador() {
        return ganador;
    }

    public void setGanador(AID ganador) {
        this.ganador = ganador;
    }
    
    public void setPrecioInicial(int precioInicial) {
        this.precio = precioInicial;
    }
    
    
    
    
}
