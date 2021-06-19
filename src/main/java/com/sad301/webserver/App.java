package com.sad301.webserver;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import spark.*;

/**
* Hello world!
*
*/
public class App extends JFrame {

  private JButton btnOpen, btnExec;
  private JFileChooser fc;
  private JTextField tfRoot, tfAddress, tfPort;
  private Service service;

  public static void main( String[] args ) {
    try {
      String laf = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel(laf);
    }
    catch(Exception exc) {
      exc.printStackTrace();
    }
    finally {
      SwingUtilities.invokeLater(() -> {
        App app = new App();
        app.setVisible(true);
      });
    }
  }

  public App() {
    createGUI();
  }

  private void createGUI() {
    fc = new JFileChooser();
    fc.setCurrentDirectory(new File("."));
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    JLabel lRoot = new JLabel("Directory :");
    tfRoot = new JTextField();
    tfRoot.setEditable(false);
    tfRoot.getDocument().addDocumentListener(documentListener);
    btnOpen = new JButton("Open");
    btnOpen.addActionListener(btnOpenListener);

    JLabel lAddress = new JLabel("Address :");
    tfAddress = new JTextField();
    tfAddress.getDocument().addDocumentListener(documentListener);

    JLabel lPort = new JLabel("Port :");
    tfPort = new JTextField();
    tfPort.getDocument().addDocumentListener(documentListener);

    btnExec = new JButton("Start");
    btnExec.setEnabled(false);
    btnExec.addActionListener(btnExecListener);

    JPanel panel = new JPanel();
    panel.setBorder(new TitledBorder("Server Configuration"));
    GroupLayout gl1 = new GroupLayout(panel);
    gl1.setAutoCreateGaps(true);
    gl1.setAutoCreateContainerGaps(true);
    gl1.setHorizontalGroup(gl1.createSequentialGroup()
      .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.TRAILING)
        .addGroup(gl1.createSequentialGroup()
          .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent(lRoot)
            .addComponent(lAddress)
            .addComponent(lPort))
          .addGroup(gl1.createParallelGroup()
            .addGroup(gl1.createSequentialGroup()
              .addComponent(tfRoot)
              .addComponent(btnOpen))
            .addComponent(tfAddress)
            .addComponent(tfPort)))
        .addComponent(btnExec)));
    gl1.setVerticalGroup(gl1.createSequentialGroup()
      .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lRoot)
        .addComponent(tfRoot)
        .addComponent(btnOpen))
      .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lAddress)
        .addComponent(tfAddress))
      .addGroup(gl1.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(lPort)
        .addComponent(tfPort))
      .addComponent(btnExec));
    panel.setLayout(gl1);

    GroupLayout gl = new GroupLayout(getContentPane());
    gl.setAutoCreateGaps(true);
    gl.setAutoCreateContainerGaps(true);
    gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(panel));
    gl.setVerticalGroup(gl.createSequentialGroup().addComponent(panel));
    getContentPane().setLayout(gl);

    pack();
    setSize(350, getHeight());
    setResizable(false);

    setTitle("Simple Web Server");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if(service != null) {
          service.stop();
        }
      }
    });
  }

  private ActionListener btnOpenListener = (e) -> {
    if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      tfRoot.setText(fc.getSelectedFile().getAbsolutePath());
    }
  };

  private ActionListener btnExecListener = (e) -> {
    switch(btnExec.getText()) {
      case "Start" -> {
        btnExec.setText("Stop");
        String root = tfRoot.getText();
        String address = tfAddress.getText();
        int port = Integer.valueOf(tfPort.getText());
        service = Service.ignite()
          .externalStaticFileLocation(root)
          .ipAddress(address)
          .port(port);
        service.init();
      }
      case "Stop" -> {
        btnExec.setText("Start");
        service.stop();
      }
    }
  };

  private DocumentListener documentListener = new DocumentListener() {

    private void detectChanges() {
      String root = tfRoot.getText();
      String address = tfAddress.getText();
      String port = tfPort.getText();
      if(root.equals("") || address.equals("") || port.equals("")) {
        btnExec.setEnabled(false);
      }
      else {
        btnExec.setEnabled(true);
      }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      detectChanges();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
      detectChanges();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      detectChanges();
    }

  };

}
