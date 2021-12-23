package ontology;

/**
* Protege name: Subasta
* @author OntologyBeanGenerator v4.1
* @version 2021/12/22, 21:35:39
*/
public class DefaultSubasta implements jade.content.Concept {

  private static final long serialVersionUID = -6333048189015439719L;

  private String _internalInstanceName = null;

  public DefaultSubasta() {
    this._internalInstanceName = "";
  }

  public DefaultSubasta(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: titulo
   */
   private String titulo;
   public void setTitulo(String value) { 
    this.titulo=value;
   }
   public String getTitulo() {
     return this.titulo;
   }

   /**
   * Protege name: precio
   */
   private float precio;
   public void setPrecio(float value) { 
    this.precio=value;
   }
   public float getPrecio() {
     return this.precio;
   }

}
