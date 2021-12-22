/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendedor;

/**
 *
 * @author martin
 */
public class GUIVendedor extends javax.swing.JFrame {

    private AgenteVendedor myAgent;
    
    /**
     * Creates new form GUIVendedor
     * @param a
     */
    public GUIVendedor(AgenteVendedor a) {
        initComponents();
        this.myAgent = a;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        anadirSubasta = new javax.swing.JButton();
        priceField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        increaseField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaSubastas = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        notificaciones = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        titleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Libro");

        anadirSubasta.setText("Añadir");
        anadirSubasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anadirSubastaActionPerformed(evt);
            }
        });

        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Precio Inicial");

        jLabel3.setText("Nueva Subasta: ");

        jLabel4.setText("Incremento");

        jLabel5.setText("Subastas:");

        jLabel6.setFont(new java.awt.Font("Cortoba", 1, 24)); // NOI18N
        jLabel6.setText("Vendedor ");

        tablaSubastas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Libro", "Ganador", "Precio", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
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
        jScrollPane2.setViewportView(tablaSubastas);

        notificaciones.setEditable(false);
        notificaciones.setColumns(20);
        notificaciones.setRows(5);
        jScrollPane1.setViewportView(notificaciones);

        jLabel7.setText("Notificaciones");

        jLabel8.setText("€");

        jLabel9.setText("€");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(688, 716, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(61, 61, 61)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8))
                                    .addComponent(jLabel2))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(increaseField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(anadirSubasta))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(61, 61, 61))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(increaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anadirSubasta)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(42, 42, 42)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void titleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleFieldActionPerformed

    private void anadirSubastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anadirSubastaActionPerformed
        String title = titleField.getText().trim();
        String price = priceField.getText().trim();
        String incremento = increaseField.getText().trim();
        
        Subasta sb = new Subasta(title, Float.parseFloat(price),Float.parseFloat(incremento));
        
	myAgent.anadirSubasta(sb.getTituloLibro(), sb);
	titleField.setText("");
	priceField.setText("");
        increaseField.setText("");
        anadirSubastaTabla(sb);
        
    }//GEN-LAST:event_anadirSubastaActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldActionPerformed

    // Cuando se cierra la ventana se borra el agente
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        myAgent.takeDown();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anadirSubasta;
    private javax.swing.JTextField increaseField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notificaciones;
    private javax.swing.JTextField priceField;
    private javax.swing.JTable tablaSubastas;
    private javax.swing.JTextField titleField;
    // End of variables declaration//GEN-END:variables

    // Muestra la interfaz gráfica
    void showGui() {
        super.setVisible(true);
    }
   
    
    public void mostrarNotificacion(String st){
        notificaciones.append(st);
    }
    
    public void anadirSubastaTabla(Subasta sb) {
        
        this.mostrarNotificacion(">Se ha iniciado la subasta por el libro " + sb.getTituloLibro()+"\n");
        
        for (int i = 0; i < tablaSubastas.getRowCount(); i++) {
            if (tablaSubastas.getModel().getValueAt(i, 0) == null) {

                tablaSubastas.getModel().setValueAt(sb.getTituloLibro(), i, 0);
                tablaSubastas.getModel().setValueAt("Por Determinar", i, 1);
                tablaSubastas.getModel().setValueAt(sb.getPrecio(), i, 2);
                tablaSubastas.getModel().setValueAt("En Curso", i, 3);
                return;
            }
        }
    }

    // Función para actualizar el ganador de una subasta activa
    void actualizarGanador(Subasta sb) {
        for (int i = 0; i < tablaSubastas.getRowCount(); i++) {
            if (tablaSubastas.getModel().getValueAt(i, 0).equals(sb.getTituloLibro())) {
                tablaSubastas.getModel().setValueAt(sb.getGanador().getName(), i, 1);
                return; 
            }
        }
    }

    
    // Función para actualizar el precio de una subasta activa
    void actualizarPrecio(Subasta sb) {
        
        this.mostrarNotificacion(">Se ha subido el precio del libro " + sb.getTituloLibro()+"\n");
        
        for (int i = 0; i < tablaSubastas.getRowCount(); i++) {
            if (tablaSubastas.getModel().getValueAt(i, 0).equals(sb.getTituloLibro())) {
                tablaSubastas.getModel().setValueAt(sb.getPrecio(), i, 2);
                return;
            }
        }
    }

    // Función para indicar que una determinada subasta a finalizado
    void terminarSubasta(Subasta sb) {
        
        this.mostrarNotificacion(">Se ha finalizado la subasta por el libro " + sb.getTituloLibro()+"\n");
        
        for (int i = 0; i < tablaSubastas.getRowCount(); i++) {
            if (tablaSubastas.getModel().getValueAt(i, 0).equals(sb.getTituloLibro())) {
                tablaSubastas.getModel().setValueAt("Finalizada", i, 3);
                return;
            }
        }
    }
}
