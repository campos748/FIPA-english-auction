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
        private HashMap<String,Integer> librosInteres;
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
        
        public void anadirInteres(String titulo, Integer precioMaximo) {
		addBehaviour(new OneShotBehaviour() {
                        @Override
			public void action() {
				librosInteres.put(titulo, precioMaximo);
				myGui.mostrarNotificacion("Nuevo Interés:\nLibro: "+ titulo + "Precio Maximo = "+Integer.toString(precioMaximo)+"\n");
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
                
            }

        
	}  // End of inner class RequestPerformer
}
