package Vendedor;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AgenteVendedor extends Agent {
    // The catalogue of books for sale (maps the title of a book to its price)

    private HashMap<String, Subasta> catalogue;
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
        super.takeDown();
        // Close the GUI
        myGui.dispose();
        // Printout a dismissal message
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }

    public void anadirSubasta(String titulo, Subasta sb) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                catalogue.put(titulo, sb);
                myAgent.addBehaviour(new BidOrdersServer(sb));
            }
        });
    }

    // Comportamiento asociado a cada subasta
    private class BidOrdersServer extends Behaviour {

        private int step = 0;
        private MessageTemplate mt; // The template to receive replies
        private Subasta sb;
        private int respuestasCompradores = 0;
        private int respuestas = 0;
        boolean primero = true;
        boolean retroceder = false;
        
        public BidOrdersServer(Subasta sb) {
            this.sb = sb;
        }

        @Override
        public void action() {
            
                    
            switch (step) {

                //Busqueda y Aviso a los compradores     
                case 0:
                    // Busqueda
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
                    
                    // Aviso
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
                    break;

                // Recibir las respuestas de los compradores    
                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // This is an auction 
                            if (reply.getContent().equals("interesado")) {
                                if (primero == true) {
                                    sb.setGanador(reply.getSender());
                                    sb.filtrarParticipantes();
                                    myGui.actualizarGanador(sb);
                                    primero = false;
                                } else {
                                    sb.anadirParticipantate(reply.getSender());
                                    sb.filtrarParticipantes();
                                }
                                respuestasCompradores++;
                            }
                            respuestas++;
                            
                            
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        

                        // Compruebo que todos los compradores han respondido
                        if (respuestas == agentesComprador.length) {

                            // Si hay más de 1 respuesta 
                            if (respuestasCompradores > 1) {
                                sb.incrementar();
                                myGui.actualizarPrecio(sb);

                                // Aviso a todos los compradores de su situación
                                
                                // Mensaje de ir ganando
                                ACLMessage acept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                                acept.addReceiver(sb.getGanador());
                                acept.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                                acept.setConversationId("compra-libro");
                                acept.setReplyWith("acept" + System.currentTimeMillis()); // Unique value
                                myAgent.send(acept);

                                // Mensaje de ir perdiendo
                                ACLMessage reject = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                                //Aviso a los que están perdiendo la puja
                                for (int i = 0; i < sb.getParticipantes().size(); i++) {
                                    reject.addReceiver(sb.getParticipantes().get(i));
                                }
                                reject.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                                reject.setConversationId("compra-libro");
                                reject.setReplyWith("reject" + System.currentTimeMillis()); // Unique value
                                myAgent.send(reject);

                                step = 0;
                            }

                            // Ya se ha determinado el ganador
                            if (respuestasCompradores == 1) {
                                step = 2;
                            }
                            if(respuestasCompradores == 0){
                                retroceder = true;
                                step = 2;
                            }
                            
                            
                            primero = true;
                            respuestasCompradores = 0;
                            respuestas = 0;
                        }
                        
                        
                    }
                    
                    break;

                // Avisar al ganador de la puja y al resto de compradores cuando solo hay un comprador   
                case 2:
                    if(retroceder == true){
                        sb.PrecioAnterior();
                        myGui.actualizarPrecio(sb);
                    }
                    
                    sb.filtrarParticipantes();
                    
                    // Marcar Subasta como terminada
                    myGui.terminarSubasta(sb);
                    
                    // Mensaje de aceptación
                    ACLMessage acept = new ACLMessage(ACLMessage.REQUEST);
                    acept.addReceiver(sb.getGanador());
                    acept.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                    acept.setConversationId("compra-libro");
                    acept.setReplyWith("win" + System.currentTimeMillis()); // Unique value
                    myAgent.send(acept);

                    // Mensaje de rechazo
                    ACLMessage reject = new ACLMessage(ACLMessage.INFORM);
                    //Aviso a los que perdiron la puja
                    for (int i = 0; i < sb.getParticipantes().size(); i++) {
                        reject.addReceiver(sb.getParticipantes().get(i));
                    }
                    reject.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                    reject.setConversationId("compra-libro");
                    reject.setReplyWith("lost" + System.currentTimeMillis()); // Unique value
                    myAgent.send(reject);
                    
                    // Elimino el libro del catálogo
                    catalogue.remove(sb.getTituloLibro());
                                
                    step = 4;
                    break;
            }
            
        }

        @Override
        public boolean done() {
            return step == 4;
        }

    }

}
