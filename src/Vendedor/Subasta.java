/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendedor;

/**
 *
 * @author martin
 */
public class Subasta {
    
    private String tituloLibro;
    private int precio;
    private int incremento;

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

    
    
    
    public void setPrecioInicial(int precioInicial) {
        this.precio = precioInicial;
    }
    
    
    
    
}
