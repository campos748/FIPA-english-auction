package ontology;

/**
* Protege name: Subastar
* @author OntologyBeanGenerator v4.1
* @version 2021/12/22, 21:35:39
*/
public class DefaultSubastar implements jade.content.AgentAction {

  private static final long serialVersionUID = -6333048189015439719L;

  private String _internalInstanceName = null;

  public DefaultSubastar() {
    this._internalInstanceName = "";
  }

  public DefaultSubastar(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: subasta
   */
   private DefaultSubasta subasta;
   public void setSubasta(DefaultSubasta value) { 
    this.subasta=value;
   }
   public DefaultSubasta getSubasta() {
     return this.subasta;
   }

}
