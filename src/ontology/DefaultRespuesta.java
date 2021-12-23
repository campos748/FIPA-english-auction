package ontology;

/**
* Protege name: Respuesta
* @author OntologyBeanGenerator v4.1
* @version 2021/12/22, 21:35:39
*/
public class DefaultRespuesta implements jade.content.Concept {

  private static final long serialVersionUID = -6333048189015439719L;

  private String _internalInstanceName = null;

  public DefaultRespuesta() {
    this._internalInstanceName = "";
  }

  public DefaultRespuesta(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  @Override
  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: mensaje
   */
   private boolean interesado;
   public void setInteresado(boolean value) { 
    this.interesado=value;
   }
   public boolean getInteresado() {
     return this.interesado;
   }

}
