/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendedor;

import jade.core.AID;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author martin
 */
public class Subasta {
    
    private String tituloLibro;
    private Float precio;
    private Float incremento;
    private AID ganador;
    private ArrayList<AID> participantes;

    public Subasta(String tituloLibro, Float precioInicial, Float incremento) {
        this.tituloLibro = tituloLibro;
        this.precio = precioInicial;
        this.incremento = incremento;
        this.participantes = new ArrayList<>();
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
        this.participantes.add(this.ganador);
        this.ganador = ganador;
    }
    
    public void setPrecioInicial(Float precioInicial) {
        this.precio = precioInicial;
    }
    
    // Función para fijar el nuevo precio de la subasta en función del incremento
    public void incrementar(){
        this.precio = this.precio+this.incremento;
    }

    public ArrayList<AID> getParticipantes() {
        return participantes;
    }

    public void anadirParticipantate(AID ag){
        participantes.add(ag);
    }

    void PrecioAnterior() {
        this.precio = this.precio - this.incremento;
    }

    void filtrarParticipantes() {
       // Elimino los repetidos
       Set<AID> hashSet = new HashSet<AID>(this.participantes);
       this.participantes.clear();
       this.participantes.addAll(hashSet);
        
       // Elimino al ganador si se encuentra aqui 
       if(this.participantes.contains(this.ganador))
           this.participantes.remove(this.ganador);
       
    }
    
    
    
    
}
