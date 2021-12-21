package Comprador; 

import Vendedor.Subasta;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.ArrayList;
import java.util.HashMap;

public class AgenteComprador extends Agent{ 
        // HasMap con cada uno de los libros que quiere el agente y su precio maximo
        private HashMap<String,Float> librosInteres;
        //Interfaz gráfica
        private GUIComprador myGui;

	// Put agent initializations here
	protected void setup() {
            // Printout a welcome message
            System.out.println("Agente comprador "+getAID().getName()+" esta listo.");
            this.librosInteres = new HashMap<>();
            // Create and show the GUI 
            myGui = new GUIComprador(this);
            myGui.showGui();
            
            // Register the book-buller service in the yellow pages
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setType("book-auction");
            sd.setName("JADE-book-auction");
            dfd.addServices(sd);
            try {
                DFService.register(this, dfd);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
            
            addBehaviour(new RequestPerformer());
            
	}

	// Put agent clean-up operations here
        @Override
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
                    DFService.deregister(this);
		}
		catch (FIPAException fe) {
                    fe.printStackTrace();
		}
		// Close the GUI
		myGui.dispose();
		// Printout a dismissal message
		System.out.println("Seller-agent "+getAID().getName()+" terminating.");
	}
        
        public void anadirInteres(String titulo, Float precioMaximo) {
		addBehaviour(new OneShotBehaviour() {
                        @Override
			public void action() {
				librosInteres.put(titulo, precioMaximo);
				myGui.mostrarNotificacion("Nuevo Interés:\nLibro: "+ titulo + "Precio Maximo = "+Float.toString(precioMaximo)+"\n");
			}
		} );
	}
     

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request auctions 
	   agents the target book.
	 */
	private class RequestPerformer extends CyclicBehaviour {

            private MessageTemplate mt; // The template to receive replies
          

            @Override
            public void action() {
                
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage msg = myAgent.receive(mt);
                
                MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
                ACLMessage win = myAgent.receive(mt1);
                
                MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
                ACLMessage loss = myAgent.receive(mt2);
                
                if (msg != null) {
                    // ACCEPT_PROPOSAL Message received. Process it
                    String mensaje = msg.getContent();
                    ACLMessage reply = msg.createReply();

                    // Separo el mensaje para conocer el libro y el valor
                    String[] arrOfStr = mensaje.split(" ", 4);
                    String tituloMen = arrOfStr[1];
                    Float precioMen = Float.parseFloat(arrOfStr[3]);

                    if (precioMen != null) {
                        if (librosInteres.containsKey(tituloMen)) {

                            //Si estoy dispuesto a pagar más del precio del mensaje o el mismo, respondo
                            if (librosInteres.get(tituloMen) >= precioMen) {

                                reply.setPerformative(ACLMessage.PROPOSE);
                                myGui.mostrarNotificacion("Se ha pujado por " + tituloMen + " al agente " + msg.getSender().getName()+" por "+precioMen.toString()+" €\n");
                                myGui.actualizarPrecio(tituloMen,precioMen);
                                
                            } else {
                                reply.setPerformative(ACLMessage.FAILURE);
                                reply.setContent("demasidado-caro");
                            }
                        }
                        else{
                            reply.setPerformative(ACLMessage.FAILURE);
                            reply.setContent("no-interesado");
                        }
                    } else {
                        // The requested book has been sold to another buyer in the meanwhile .
                        reply.setPerformative(ACLMessage.FAILURE);
                        reply.setContent("not-available");
                    }
                    myAgent.send(reply);
                }
                // Notificación de ganar una subasta
                if(win != null){
                    String mensaje = win.getContent();
                    // Separo el mensaje para conocer el libro y el valor
                    String[] arrOfStr = mensaje.split(" ", 4);
                    String tituloMen = arrOfStr[1];
                    Float precioMen = Float.parseFloat(arrOfStr[3]);
                    
                    // Quito el libro del catalogo de intereses
                    librosInteres.remove(tituloMen);
                    //Actualizo la tabla de la GUI
                    myGui.subastaGanada(tituloMen,precioMen);
                    
                }
                // Notificación de perder una subasta
                if(loss != null){
                    String mensaje = win.getContent();
                    // Separo el mensaje para conocer el libro y el valor
                    String[] arrOfStr = mensaje.split(" ", 4);
                    String tituloMen = arrOfStr[1];
                    Float precioMen = Float.parseFloat(arrOfStr[3]);
                    
                    //Actualizo la tabla de la GUI
                    myGui.subastaPerdida(tituloMen,precioMen);
                }
                else {
                    block();    // Bloqueo en caso de no recibir ningún mensaje
                }
            }

        
	}  // End of inner class RequestPerformer
}
