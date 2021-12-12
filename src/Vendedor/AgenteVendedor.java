package Vendedor;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.HashMap;

import java.util.Hashtable;

public class AgenteVendedor extends Agent {
	// The catalogue of books for sale (maps the title of a book to its price)
	private HashMap<String,Subasta> catalogue;
	// The GUI by means of which the user can add books in the catalogue
	private GUIVendedor myGui;

	// Put agent initializations here
        @Override
	protected void setup() {
		// Create the catalogue
		catalogue = new HashMap();

		// Create and show the GUI 
		myGui = new GUIVendedor(this);
                myGui.showGui();
                
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-auction");
		sd.setName("JADE-book-auction");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Agrega el comportamiento de las consultas de servicio del agente comprador 
		addBehaviour(new OfferRequestsServer());

		// Añadir el comportamiento de servicio de las órdenes de compra de los agentes compradores
		addBehaviour(new BidOrdersServer()); 
	}

	// Put agent clean-up operations here
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

        
        
	public void anadirSubasta(String titulo, Subasta sb) {
		addBehaviour(new OneShotBehaviour() {
                        @Override
			public void action() {
				catalogue.put(titulo, sb);
				myGui.mostrarNotificacion("Nueva Subasta:\nLibro: "+sb.getTituloLibro()+" Precio Inicial  = "+sb.getPrecio()+"\n");
			}
		} );
	}

        // Comportamiento de las consultas de servicio del agente comprador 
	private class OfferRequestsServer extends CyclicBehaviour {
                @Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();
                                
                                // Obtención del precio actual del libro
				Integer price = catalogue.get(title).getPrecio();
                                
				if (price != null) {
					// The requested book is available for sale. Reply with the price
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(String.valueOf(price.intValue()));
				}
				else {
					// The requested book is NOT available for sale.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer

        // TODO: contabilizar el numer ode pujantes
        
        // Comportamiento de puja de uno de los clientes
	private class BidOrdersServer extends CyclicBehaviour {
                @Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// ACCEPT_PROPOSAL Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();

				Integer price = catalogue.remove(title).getPrecio();
				if (price != null) {
					reply.setPerformative(ACLMessage.INFORM);
					myGui.mostrarNotificacion(msg.getSender().getName()+" ha pujado por: "+title);
                                        
				}
				else {
					// The requested book has been sold to another buyer in the meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
                            myGui.mostrarNotificacion("Hay "+" pujantes");
                            block();
			}
		}
	}  // End of inner class OfferRequestsServer
}
