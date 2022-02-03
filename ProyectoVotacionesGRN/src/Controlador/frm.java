package Controlador;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;

public class frm extends javax.swing.JFrame {

    public frm() {
        initComponents();
        this.setTitle("GrÃ¡ficos");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        mnuLineas = new javax.swing.JMenuItem();
        mnuBarras = new javax.swing.JMenuItem();
        mnuCircular = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(20403), "Valencia"},
                { new Integer(50304), "Quevedo"},
                { new Integer(1938980), "Guayaquil"},
                { new Integer(230043), "Ibarra"},
                { new Integer(2200450), "Quito"},
                { new Integer(305890), "Esmeraldas"},
                { new Integer(489876), "Portoviejo"}
            },
            new String [] {
                "PoblaciÃ³n", "Ciudad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDatos);
        if (tblDatos.getColumnModel().getColumnCount() > 0) {
            tblDatos.getColumnModel().getColumn(0).setResizable(false);
            tblDatos.getColumnModel().getColumn(1).setResizable(false);
        }

        jMenu1.setText("GrÃ¡fico");

        jMenu2.setText("Tipo");

        mnuLineas.setText("LÃ­neas");
        mnuLineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLineasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuLineas);

        mnuBarras.setText("Barras");
        mnuBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBarrasActionPerformed(evt);
            }
        });
        jMenu2.add(mnuBarras);

        mnuCircular.setText("Circular");
        mnuCircular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCircularActionPerformed(evt);
            }
        });
        jMenu2.add(mnuCircular);

        jMenu1.add(jMenu2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBarrasActionPerformed
        try {
            DefaultCategoryDataset data = new DefaultCategoryDataset();
            for (int i = 0; i < tblDatos.getRowCount(); i++) {
                String ciudad = tblDatos.getValueAt(i, 1).toString();
                data.addValue(Integer.parseInt(String.valueOf(tblDatos.getValueAt(i, 0))), ciudad, ciudad);
            }
            JFreeChart ch = ChartFactory.createBarChart3D("GrÃ¡fico de Barras", "Ciudadaes", "PoblaciÃ³n", data, PlotOrientation.VERTICAL, true, true, true);
            ChartFrame fo = new ChartFrame("ESTADÃ�STICAS", ch);
            fo.setSize(1000, 600);
            fo.setLocationRelativeTo(null);
            fo.setVisible(true);
        } catch (Exception e) {
            this.getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnuBarrasActionPerformed

    private void mnuLineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLineasActionPerformed
        try {
            XYSplineRenderer renderer = new XYSplineRenderer();
            XYSeriesCollection dataset = new XYSeriesCollection();
            ValueAxis x = new NumberAxis();
            ValueAxis y = new NumberAxis();
            XYSeries serie = new XYSeries("Ciudades");
            for (int i = 0; i < tblDatos.getRowCount(); i++) {
                serie.add(i + 1, Double.parseDouble(String.valueOf(tblDatos.getValueAt(i, 0))));
            }
            dataset.addSeries(serie);
            x.setLabel("Ciudades");
            y.setLabel("PoblaciÃ³n");
            XYPlot plot = new XYPlot(dataset, x, y, renderer);
            JFreeChart chart = new JFreeChart(plot);
            chart.setTitle("GrÃ¡fico de LÃ­neas");
            ChartFrame f = new ChartFrame("ESTADÃ�STICAS", chart);
            f.setSize(1000, 600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        } catch (Exception e) {
            this.getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnuLineasActionPerformed

    private void mnuCircularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCircularActionPerformed
        try {
            int suma=0;
            for (int i = 0; i < tblDatos.getRowCount(); i++) {
                suma+=(Integer)tblDatos.getValueAt(i,0);
            }
            DefaultPieDataset dat = new DefaultPieDataset();
            for (int i = 0; i < tblDatos.getRowCount(); i++) {
                int poblacion=Integer.parseInt(tblDatos.getValueAt(i, 0).toString());
                double pc = ((double)poblacion* 100 / suma);
                pc=transformar(redondear(pc));
                String ciudad=tblDatos.getValueAt(i,1).toString();
                dat.setValue(ciudad+" (" + pc + ") %", poblacion);
            }
            JFreeChart ct = ChartFactory.createPieChart3D("GrÃ¡fico Circular", dat, true, true, true);
            ChartFrame fl = new ChartFrame("ESTADÃ�STICAS", ct);
            fl.setSize(1000, 600);
            fl.setLocationRelativeTo(null);
            fl.setVisible(true);
        } catch (Exception e) {
            this.getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnuCircularActionPerformed

    double transformar(String valor){
        char trans[]=new char[valor.length()];
        String nuevo="";
        for(int k=0;k<valor.length();k++){
            trans[k]=valor.charAt(k);
            if(trans[k]==','){
                nuevo+=".";
            }else{
                nuevo+=trans[k];
            }
        }
        return Double.parseDouble(nuevo);
    }
    
    String redondear(double numero){
        String valor=valor=String.format("%.2f",numero);;
        return valor;
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mnuBarras;
    private javax.swing.JMenuItem mnuCircular;
    private javax.swing.JMenuItem mnuLineas;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables
}
