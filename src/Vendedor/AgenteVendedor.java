package Vendedor;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Hashtable;

public class AgenteVendedor extends Agent {
	// The catalogue of books for sale (maps the title of a book to its price)
	private HashMap<String,Subasta> catalogue;
	// The GUI by means of which the user can add books in the catalogue
	private GUIVendedor myGui;
        AID[] agentesComprador;
        
	// Put agent initializations here
       @Override
    protected void setup() {
        // Create the catalogue
        catalogue = new HashMap();

        // Create and show the GUI 
        myGui = new GUIVendedor(this);
        myGui.showGui();
        
        // Comportamiento para ir añadiendo nuevos posibles compradores
        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                // Update the list of buyer agents
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("book-auction");
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Found the following seller agents:");
                    agentesComprador = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        agentesComprador[i] = result[i].getName();
                        System.out.println(agentesComprador[i].getName());
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }

            }

        });
                        
                
	}

	// Put agent clean-up operations here
        @Override
	protected void takeDown() {
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
                                myAgent.addBehaviour(new BidOrdersServer(sb)); 
			}
		} );
	}

        
        // Comportamiento asociado a cada subasta
	private class BidOrdersServer extends CyclicBehaviour {
            private int step = 0;
            private MessageTemplate mt; // The template to receive replies
            private Subasta sb;
            private int respuestasCompradores = 0;
            
            public BidOrdersServer(Subasta sb){
                this.sb = sb;
            }
            
            @Override
            public void action() {                               
                boolean primero = true;
                
                
                switch(step){  
                
                // Aviso a los compradores     
                case 0:    
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < agentesComprador.length; ++i) {
                        cfp.addReceiver(agentesComprador[i]);
                    }
                    cfp.setContent("Libro: "+sb.getTituloLibro()+" Precio: "+sb.getPrecio());
                    cfp.setConversationId("subasta-libro");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    // Prepare the template to get proposals
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("subasta-libro"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;

                // Recibir las respuestas de los compradores    
                case 1:
                        // Receive all proposals/refusals from seller agents
                        ACLMessage reply = myAgent.receive(mt);
                        if (reply != null) {
                            // Reply received
                            if (reply.getPerformative() == ACLMessage.PROPOSE) {
                                // This is an auction 
                                if(primero == true){
                                    sb.setGanador(reply.getSender());
                                    primero = false;
                                }
                                
                            }
                            respuestasCompradores++;
                            
                            //TODO: Hay que hacer alguna espera para recibir todas las peticiones
                            
                            // Si hay más de 1 respuesta 
                            if (respuestasCompradores > 1) {
                                step = 1;
                            }
                            
                            // Si en esta subida no puja ningún comprador
                            if (respuestasCompradores == 0) {
                                step = 2;
                            }
                            
                            if (respuestasCompradores == 1){
                                step = 2;
                            }
                            
                        } else {
                            block();
                        }
                    
                    break;
                
                // Avisar al ganador de la puja y al resto de compradores    
                case 2:
                    
                    break;
                }
                
                
            }
                        
    
	}  
}
