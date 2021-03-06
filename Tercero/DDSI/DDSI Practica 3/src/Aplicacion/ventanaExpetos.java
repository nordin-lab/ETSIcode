/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import Persistencia.conexionOracle;
import Persistencia.experto;
import Persistencia.manejaExperto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author borja
 */
public class ventanaExpetos extends java.awt.Frame {

    private final conexionOracle conexion;
    private final DefaultTableModel mExpertos;

    /**
     * Creates new form ventanaExpetos
     *
     * @param conexion
     */
    public ventanaExpetos(conexionOracle conexion) {
        initComponents();
        setLocationRelativeTo(getParent());
        setTitle("Tabla de expertos");

        this.conexion = conexion;

        mExpertos = new DefaultTableModel();
        jTableExpertos.setModel(mExpertos);
        String[] nombreColumnas = {"Código", "Nombre", "Pais", "Sexo", "Especialidad"};
        mExpertos.setColumnIdentifiers(nombreColumnas);
        jTableExpertos.getTableHeader().setResizingAllowed(false);
        jTableExpertos.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTableExpertos.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableExpertos.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTableExpertos.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTableExpertos.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableExpertos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldPais = new javax.swing.JTextField();
        jButtonFiltrar = new javax.swing.JButton();
        jButtonTodos = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTableExpertos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableExpertos);

        jLabel1.setText("País:");

        jButtonFiltrar.setText("Filtrar por país");
        jButtonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarActionPerformed(evt);
            }
        });

        jButtonTodos.setText("Listar todos");
        jButtonTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPais, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonTodos)
                        .addGap(0, 149, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFiltrar)
                    .addComponent(jButtonTodos))
                .addGap(18, 18, 18))
        );

        jButtonFiltrar.getAccessibleContext().setAccessibleName("Filtrar por pais");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        dispose();
    }//GEN-LAST:event_exitForm

    private void jButtonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarActionPerformed
        //Filtrar por pais
        limpiarTabla();
        manejaExperto me = new manejaExperto(conexion);
        try {
            ArrayList<experto> expertos = me.listaExpertosPorPais(jTextFieldPais.getText());
            for (experto experto : expertos) {
                mExpertos.addRow(new String[]{experto.getCodExperto(), experto.getNombre(), experto.getPais(), experto.getSexo(), experto.getEspecialidad()});
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventanaExpetos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonFiltrarActionPerformed

    private void jButtonTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTodosActionPerformed
        //Sin filtrado
        limpiarTabla();
        manejaExperto me = new manejaExperto(conexion);
        try {
            ArrayList<experto> expertos = me.listaExpertos();
            for (experto experto : expertos) {
                String[] fila = {experto.getCodExperto(), experto.getNombre(), experto.getPais(), experto.getSexo(), experto.getEspecialidad()};
                mExpertos.addRow(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ventanaExpetos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonTodosActionPerformed

    private void limpiarTabla() {
        while (mExpertos.getRowCount() > 0) {
            mExpertos.removeRow(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFiltrar;
    private javax.swing.JButton jButtonTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableExpertos;
    private javax.swing.JTextField jTextFieldPais;
    // End of variables declaration//GEN-END:variables
}
