package Vendedor;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    
                    myAgent.addBehaviour(new BuscarCompradores());
                    
                    if (agentesComprador != null) {
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        for (int i = 0; i < agentesComprador.length; ++i) {
                            cfp.addReceiver(agentesComprador[i]);
                        }
                        cfp.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                        cfp.setConversationId("subasta-libro");
                        cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId("subasta-libro"),
                                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = 1;
                    }
                    else{
                        myGui.mostrarNotificacion("No hay compradores");
                    }
                    break;

                // Recibir las respuestas de los compradores    
                case 1:
                    // Espera para recibir todas las respuestas
//                    try {
//                        TimeUnit.SECONDS.sleep(10);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // This is an auction 
                            if (primero == true) {
                                sb.setGanador(reply.getSender());
                                myGui.actualizarGanador(sb);
                                primero = false;
                            }
                            else{
                                sb.anadirParticipantate(reply.getSender());
                            }
                        }
                        respuestasCompradores++;

                        // Si hay más de 1 respuesta 
                        if (respuestasCompradores > 1) {
                            sb.incrementar();
                            myGui.actualizarPrecio(sb); 
                            step = 1;
                        }

                        // Ya se ha determinado el ganador
                        if (respuestasCompradores <= 1) {
                            step = 2;
                        }


                    } 
                    

                    break;
                
                // Avisar al ganador de la puja y al resto de compradores    
                case 2:
                    // Mensaje de aceptación
                    ACLMessage acept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    acept.addReceiver(sb.getGanador());
                    acept.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                    acept.setConversationId("compra-libro");
                    acept.setReplyWith("acept" + System.currentTimeMillis()); // Unique value
                    myAgent.send(acept);
                    
                    // Mensaje de rechazo
                    ACLMessage reject = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    //Aviso a los que perdiron la puja
                    for (int i = 0; i < sb.getParticipantes().size(); i++) {
                        reject.addReceiver(sb.getParticipantes().get(i));
                    }
                    reject.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                    reject.setConversationId("compra-libro");
                    reject.setReplyWith("reject" + System.currentTimeMillis()); // Unique value
                    myAgent.send(reject);
                    
                    step = 3;
                    break;
                
                // Confirmación de compra
                case 3:
                
                
                }
            }
	}
        
        
    private class BuscarCompradores extends OneShotBehaviour {

        @Override
        public void action() {
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

    }
        
        
        
        
}
