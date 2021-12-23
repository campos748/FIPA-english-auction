package Vendedor;

import jade.content.Concept;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLCodec.CodecException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.*;


public class AgenteVendedor extends Agent {
    // The catalogue of books for sale (maps the title of a book to its price)

    private HashMap<String, Subasta> catalogue;
    // The GUI by means of which the user can add books in the catalogue
    private GUIVendedor myGui;
    AID[] agentesComprador;
    // Ontologia
    private Codec codec;
    private Ontology onto;
    
    
    // Put agent initializations here
    @Override
    protected void setup() {
        // Create the catalogue
        catalogue = new HashMap();

        // Create and show the GUI 
        myGui = new GUIVendedor(this);
        myGui.showGui();
        
        
//----------------------------- Ontologia --------------------------
        codec = new SLCodec();
        onto = SimpleJADEAbstractOntologyOntology.getInstance();
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(onto);
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
        
        private DefaultSubasta dSb;
        private DefaultSubastar dSubastar;
        
        public BidOrdersServer(Subasta sb) {
            this.sb = sb;
            dSb = new DefaultSubasta();
            dSubastar = new DefaultSubastar();            
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
//---------------------------------- Ontología: FillContent ----------------------------------                        
                        cfp.setOntology(onto.getName());
                        cfp.setLanguage(codec.getName());
                        dSb.setTitulo(sb.getTituloLibro());
                        dSb.setPrecio(sb.getPrecio());
                        dSubastar.setSubasta(dSb);
                        
                        try {
                            getContentManager().fillContent(cfp, new Action(getAID(), dSubastar));
                        } catch (OntologyException e) {
                            e.printStackTrace();
                        } catch (Codec.CodecException ex) {
                            Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                        }
//------------------------------------------------------------------------------------------------------
                        
                        //cfp.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                        
                        cfp.setConversationId("subasta-libro");
                        cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                        myAgent.send(cfp);
                        // Prepare the template to get proposals
                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId("subasta-libro"),
                                MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                        step = 1;
                    }
                    else{
                        try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                    break;

                // Recibir las respuestas de los compradores    
                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);
                    
                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            DefaultResponder dfRes = null;
                            try {
                                // This is an auction
//---------------------------------- Ontologia: getContent ----------------------------------
                                Action a = (Action) getContentManager().extractContent(reply);
                                dfRes = (DefaultResponder) a.getAction();
                            } catch (Codec.CodecException ex) {
                                Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (OntologyException ex) {
                                Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            if (dfRes.getRespuesta().getInteresado()) {
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
                                TimeUnit.SECONDS.sleep(2);
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
//---------------------------------- Ontología: FillContent  ----------------------------------                               
                                acept.setOntology(onto.getName());
                                acept.setLanguage(codec.getName());
                                dSb.setTitulo(sb.getTituloLibro());
                                dSb.setPrecio(sb.getPrecio());
                                dSubastar.setSubasta(dSb);

                                try {
                                    getContentManager().fillContent(acept, new Action(getAID(), dSubastar));
                                } catch (OntologyException e) {
                                    e.printStackTrace();
                                } catch (Codec.CodecException ex) {
                                    Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                                }
//------------------------------------------------------------------------------------------------------                            
                                //acept.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                                
                                acept.setConversationId("compra-libro");
                                acept.setReplyWith("acept" + System.currentTimeMillis()); // Unique value
                                myAgent.send(acept);

                                // Mensaje de ir perdiendo
                                ACLMessage reject = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                                //Aviso a los que están perdiendo la puja
                                for (int i = 0; i < sb.getParticipantes().size(); i++) {
                                    reject.addReceiver(sb.getParticipantes().get(i));
                                }
                                
//---------------------------------- Ontología: FillContent --------------------------------------------------------------------
                                reject.setOntology(onto.getName());
                                reject.setLanguage(codec.getName());
                                dSb.setTitulo(sb.getTituloLibro());
                                dSb.setPrecio(sb.getPrecio());
                                dSubastar.setSubasta(dSb);

                                try {
                                    getContentManager().fillContent(reject, new Action(getAID(), dSubastar));
                                } catch (OntologyException e) {
                                    e.printStackTrace();
                                } catch (Codec.CodecException ex) {
                                    Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                                }
//------------------------------------------------------------------------------------------------------
                                //reject.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                                reject.setConversationId("compra-libro");
                                reject.setReplyWith("reject" + System.currentTimeMillis()); // Unique value
                                myAgent.send(reject);
                                
                                try {
                                    TimeUnit.SECONDS.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
//---------------------------------- Ontología: FillContent ----------------------------------
                    acept.setOntology(onto.getName());
                    acept.setLanguage(codec.getName());
                    dSb.setTitulo(sb.getTituloLibro());
                    dSb.setPrecio(sb.getPrecio());
                    dSubastar.setSubasta(dSb);

                    try {
                        getContentManager().fillContent(acept, new Action(getAID(), dSubastar));
                    } catch (OntologyException e) {
                        e.printStackTrace();
                    } catch (Codec.CodecException ex) {
                        Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
//------------------------------------------------------------------------------------------------------                    
                    //acept.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
                    acept.setConversationId("compra-libro");
                    acept.setReplyWith("win" + System.currentTimeMillis()); // Unique value
                    myAgent.send(acept);

                    // Mensaje de rechazo
                    ACLMessage reject = new ACLMessage(ACLMessage.INFORM);
                    //Aviso a los que perdiron la puja
                    for (int i = 0; i < sb.getParticipantes().size(); i++) {
                        reject.addReceiver(sb.getParticipantes().get(i));
                    }
 //---------------------------------- Ontología: FillContent ----------------------------------
                    reject.setOntology(onto.getName());
                    reject.setLanguage(codec.getName());
                    dSb.setTitulo(sb.getTituloLibro());
                    dSb.setPrecio(sb.getPrecio());
                    dSubastar.setSubasta(dSb);

                    try {
                        getContentManager().fillContent(reject, new Action(getAID(), dSubastar));
                    } catch (OntologyException e) {
                        e.printStackTrace();
                    } catch (Codec.CodecException ex) {
                        Logger.getLogger(AgenteVendedor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
//------------------------------------------------------------------------------------------------------                    
                    //reject.setContent("Libro: " + sb.getTituloLibro() + " Precio: " + sb.getPrecio());
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
