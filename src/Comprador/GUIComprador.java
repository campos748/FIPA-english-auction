/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comprador;

/**
 *
 * @author martin
 */
public class GUIComprador extends javax.swing.JFrame {
    AgenteComprador myAgent;
    /**
     * Creates new form GUIComprador
     */
    public GUIComprador(AgenteComprador a) {
        initComponents();
        this.myAgent = a;
        this.Lnombre.setText("Comprador: "+a.getName());
        this.btnEliminarInteres.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Lnombre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tituloField = new javax.swing.JTextField();
        precioMaxField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notificaciones = new javax.swing.JTextArea();
        anadirInteres = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaInteres = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnEliminarInteres = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Lnombre.setFont(new java.awt.Font("Cortoba", 1, 24)); // NOI18N
        Lnombre.setText("Comprador: ");

        jLabel2.setText("Nuevo Inter??s:");

        jLabel3.setText("Libro");

        jLabel4.setText("Precio M??x");

        notificaciones.setEditable(false);
        notificaciones.setColumns(20);
        notificaciones.setRows(5);
        jScrollPane1.setViewportView(notificaciones);

        anadirInteres.setText("A??adir");
        anadirInteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anadirInteresActionPerformed(evt);
            }
        });

        jLabel5.setText("Notificaciones");

        tablaInteres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Libro", "Precio Max", "Precio", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaInteres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaInteresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaInteresFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(tablaInteres);
        if (tablaInteres.getColumnModel().getColumnCount() > 0) {
            tablaInteres.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jLabel6.setText("???");

        jLabel7.setText("Intereses");

        btnEliminarInteres.setText("Eliminar");
        btnEliminarInteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarInteresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarInteres))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(tituloField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(precioMaxField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(101, 101, 101)
                                .addComponent(anadirInteres))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Lnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(Lnombre)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tituloField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(precioMaxField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(anadirInteres))
                .addGap(27, 27, 27)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarInteres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anadirInteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anadirInteresActionPerformed
        
        String titulo = tituloField.getText().trim();
        String precioMax = precioMaxField.getText().trim();
        
        myAgent.anadirInteres(titulo, Float.parseFloat(precioMax));
        nuevoInteresTabla(titulo,Float.parseFloat(precioMax));
        tituloField.setText("");
        precioMaxField.setText("");
    }//GEN-LAST:event_anadirInteresActionPerformed

    // Evento para registrar el cerrado de la ventana
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        myAgent.takeDown();
    }//GEN-LAST:event_formWindowClosing

    private void btnEliminarInteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarInteresActionPerformed
        int row = this.tablaInteres.getSelectedRow();
        myAgent.eliminarInteres((String) tablaInteres.getModel().getValueAt(row, 0));
        tablaInteres.getModel().setValueAt("Cancelado", row, 3);
    }//GEN-LAST:event_btnEliminarInteresActionPerformed

    private void tablaInteresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaInteresFocusGained
        this.btnEliminarInteres.setEnabled(true);
    }//GEN-LAST:event_tablaInteresFocusGained

    private void tablaInteresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaInteresFocusLost
        this.btnEliminarInteres.setEnabled(true);
    }//GEN-LAST:event_tablaInteresFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lnombre;
    private javax.swing.JButton anadirInteres;
    private javax.swing.JButton btnEliminarInteres;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notificaciones;
    private javax.swing.JTextField precioMaxField;
    private javax.swing.JTable tablaInteres;
    private javax.swing.JTextField tituloField;
    // End of variables declaration//GEN-END:variables

    public void showGui(){
        this.setVisible(true);
    }

    public void mostrarNotificacion(String nt){
            notificaciones.append(nt);
    }

    private void nuevoInteresTabla(String titulo, Float precioMaximo) {
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0) == null) {

                tablaInteres.getModel().setValueAt(titulo, i, 0);
                tablaInteres.getModel().setValueAt(precioMaximo, i, 1);
                tablaInteres.getModel().setValueAt(0, i, 2);
                tablaInteres.getModel().setValueAt("Sin Subasta", i, 3);
                return;
            }
        }
    }

    // Funci??n para ir actualizando el precio de la subasta
    void actualizarPrecio(String tituloMen, Float precioMen) {
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0).equals(tituloMen)) {
                
                tablaInteres.getModel().setValueAt(precioMen, i, 2);
                if(tablaInteres.getModel().getValueAt(i, 3).equals("Sin Subasta")){
                    tablaInteres.getModel().setValueAt("Subasta en Curso", i, 3);
                }
                return;
            }
        }   
    }

    // Funci??n para actualizar la tabla al ganar una subasta
    void subastaGanada(String tituloMen, Float precioMen) {
        // Notificacion 
        this.mostrarNotificacion("Se ha ganado la subasta del libro "+tituloMen+" por "+precioMen.toString()+"\n");
        // Tabla
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0).equals(tituloMen)) {
                tablaInteres.getModel().setValueAt(precioMen, i, 2);
                tablaInteres.getModel().setValueAt("Subasta Ganada", i, 3);
                return;
            }
        } 
    }

    
    // Funci??n para actualizar la tabla al ganar una subasta
    void subastaPerdida(String tituloMen, Float precioMen) {
        // Notificacion
        this.mostrarNotificacion("Se ha perdido la subasta del libro "+tituloMen+"\n");
        // Tabla
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0).equals(tituloMen)) {
                
                tablaInteres.getModel().setValueAt(0, i, 2);
                tablaInteres.getModel().setValueAt("Sin Subasta", i, 3);
                return;
            }
        }
    }

    // Actualizo la tabla para indicar que el comprador va perdiendo
    void tablaLoss(String tituloMen, Float precioMen) {
        
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0).equals(tituloMen)) {

                tablaInteres.getModel().setValueAt("Subasta en Curso: Perdiendo", i, 3);
                return;
            }
        }
        
    }

    // Actualizo la tabla para indicar que el comprador va ganando
    void tablaWin(String tituloMen, Float precioMen) {
        for (int i = 0; i < tablaInteres.getRowCount(); i++) {
            if (tablaInteres.getModel().getValueAt(i, 0).equals(tituloMen)) {

                tablaInteres.getModel().setValueAt("Subasta en Curso: Ganando", i, 3);
                return;
            }
        }
    }

}
