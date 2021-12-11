package Vendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GUIVendedor extends JFrame {
    private AgenteVendedor myAgent;

    private JTextField titleField, priceField,incrementoField;

    GUIVendedor(AgenteVendedor a) {
        super(a.getLocalName());

        myAgent = a;

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(30, 60));
        p.add(new JLabel("Titulo:"));
        titleField = new JTextField(15);
        p.add(titleField);
        p.add(new JLabel("Precio Inicial:"));
        priceField = new JTextField(5);
        p.add(priceField);
        p.add(new JLabel("Incremento:"));
        incrementoField = new JTextField(5);
        p.add(priceField);

        getContentPane().add(p, BorderLayout.CENTER);

        JButton addButton = new JButton("Subastar");

        addButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = titleField.getText().trim();
                    String price = priceField.getText().trim();
                    myAgent.updateCatalogue(title, Integer.parseInt(price));
                    titleField.setText("");
                    priceField.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(GUIVendedor.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } );
        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(new	WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        } );

        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}