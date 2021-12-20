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
    private Float precio;
    private Float incremento;
    private AID ganador;

    public Subasta(String tituloLibro, Float precioInicial, Float incremento) {
        this.tituloLibro = tituloLibro;
        this.precio = precioInicial;
        this.incremento = incremento;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public Float getPrecio() {
        return precio;
    }

    public Float getIncremento() {
        return incremento;
    }

    public AID getGanador() {
        return ganador;
    }

    public void setGanador(AID ganador) {
        this.ganador = ganador;
    }
    
    public void setPrecioInicial(Float precioInicial) {
        this.precio = precioInicial;
    }
    
    
    
    
}
