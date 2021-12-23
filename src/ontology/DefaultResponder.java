package ontology;

/**
* Protege name: Responder
* @author OntologyBeanGenerator v4.1
* @version 2021/12/22, 21:35:39
*/
public class DefaultResponder implements jade.content.AgentAction{

  private static final long serialVersionUID = -6333048189015439719L;

  private String _internalInstanceName = null;

  public DefaultResponder() {
    this._internalInstanceName = "";
  }

  public DefaultResponder(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  @Override
  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: respuesta
   */
   private DefaultRespuesta respuesta;
   public void setRespuesta(DefaultRespuesta value) { 
    this.respuesta=value;
   }
   public DefaultRespuesta getRespuesta() {
     return this.respuesta;
   }

}
