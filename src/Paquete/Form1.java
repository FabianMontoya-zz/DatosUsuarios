package Paquete;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabian_Montoya
 */
public class Form1 extends JFrame implements ActionListener, KeyListener, MouseListener {

    Connection conexion;
    ResultSet resultado;
    Statement sentencia;

    JTextField Tusuario;
    JLabel LTitulo, LSub, LUsuario, L2, L3, L4, L5;
    JButton BConsultar, BModificar, BBorrar, BActualizar;

    String User;
    String[] data = new String[500];
    int pulso = 0, ID = 0, ID_ADM = 0;

    JMenu Menu1, Menu2, Menu3, Menu4;
    JMenuBar MBarra;
    JMenuItem Item1, Item2, Item3, Item4, Item5, Item6, Item7, Item8, Item9, Item10;

    JTable tabla;
    JScrollPane scroll;
    DefaultTableModel ModeloTabla;
    GridBagLayout GRID = new GridBagLayout();
    String[] columnNames = {"ID Cliente", "Nombres", "Apellidos", "Documento", "Dirección", "Celular", "Teléfono", "Comentarios"};

    private boolean open = true;

    // public static final int DEFAULT_CURSOR;
    public Form1(String USUARIO, int IDADM) {
        super("Datos Clientes - ARTURO 1.0.5");
        try {

            setLayout(GRID);
            Image icon = new ImageIcon(getClass().getResource("/Imagenes/admin.png")).getImage();
            setIconImage(icon);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            User = USUARIO;
            ID_ADM = IDADM;
            menu();
            botones();
            textos();
            Cajas();
            panel();

            Tusuario.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            getContentPane().addKeyListener(this);

            addWindowListener(new WindowAdapter() {
                @Override

                public void windowClosing(WindowEvent e) {
                    control();
                }
            });
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //Evitar que se cierre al dar X o Alt + F4
            setSize(1000, 740);
            setResizable(true);
            //setVisible(true);
            setLocationRelativeTo(null);
            setExtendedState(MAXIMIZED_BOTH);
            Tusuario.requestFocus();
            Item7.setEnabled(false);
            if (ID_ADM == 0) { //Si entra con la Back Door
                Item7.setEnabled(false);
                Item8.setEnabled(false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al abrir la ventana.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Form1.java", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR, la aplicación se cerrará.", "¡ERROR! - Form1.java", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        open = true;
    }

    public void control() {
        int ax = JOptionPane.showConfirmDialog(null, "Se cerrará la sesión.\n¿Desea continuar?", "Cerrar sesión - Confirmar", JOptionPane.YES_NO_OPTION);
        if (ax == JOptionPane.YES_OPTION) {
            dispose();
            INICIO r = new INICIO();
            r.setSize(340, 300);
            r.setResizable(false);
            r.setVisible(true);
            r.setDefaultCloseOperation(EXIT_ON_CLOSE);
            r.setLocationRelativeTo(null);
        } else {
            /* Form1 c = new Form1(User);
             if (open == false) {
             // Form1 c = new Form1(User);
             c.setSize(1000, 740);
             c.setResizable(true);
             c.setVisible(true);
             c.setLocationRelativeTo(null);
             c.setExtendedState(MAXIMIZED_BOTH);
             } else {
             //
             }*/
        }
    }

    public void panel() throws Exception {

        Conexion c = new Conexion();
        GridBagConstraints constraintsTabla = new GridBagConstraints();
        ModeloTabla = new DefaultTableModel(null, columnNames);
        TableRowSorter sorter = new TableRowSorter(ModeloTabla);
        tabla = new JTable(ModeloTabla) {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        }; //return false: Desabilitar edición de celdas.

        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60); //ID
        columnModel.getColumn(1).setPreferredWidth(150); //Nombres
        columnModel.getColumn(2).setPreferredWidth(180);//Apellidos
        columnModel.getColumn(3).setPreferredWidth(90); //Documento
        columnModel.getColumn(4).setPreferredWidth(180); //Dirección
        columnModel.getColumn(5).setPreferredWidth(80); //Celular
        columnModel.getColumn(6).setPreferredWidth(60); //Teléfono
        columnModel.getColumn(7).setPreferredWidth(500); //Comentarios

        // tabla.setPreferredScrollableViewportSize(new Dimension(600, 100)); //Se define el tamaño
        scroll = new JScrollPane(tabla);
        constraintsTabla.ipadx = 200; //Solicita 150 de espacio extra
        constraintsTabla.fill = GridBagConstraints.BOTH;// El area de texto debe estirarse en ambos sentidos. Ponemos el campo fill.
        constraintsTabla.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsTabla.gridx = 0; // El área de texto empieza en la columna cero.
        constraintsTabla.gridy = 2; // El área de texto empieza en la fila uno
        constraintsTabla.gridwidth = 2; // El área de texto ocupa dos columnas.
        constraintsTabla.gridheight = 4; // El área de texto ocupa 3 filas.
        constraintsTabla.weighty = 2; //Se estira en Y       
        constraintsTabla.weightx = 5; //Se estira en X

        this.getContentPane().add(scroll, constraintsTabla);
        try {
            TraerDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al traer los datos de la base de datos.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Form1.java", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR para cerrar esta ventana.", "¡ERROR! - Form1.java", JOptionPane.ERROR_MESSAGE);
        }
        // tabla.setRowSorter(sorter); //Ordenar Alfabeticamente
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //add(scroll, BorderLayout.CENTER); //Solo cuando no tiene Layout definido
        scroll.addKeyListener(this);
        tabla.addKeyListener(this);
        tabla.addMouseListener(this);
    }

    public void menu() {
        MBarra = new JMenuBar();
        setJMenuBar(MBarra);

        Menu1 = new JMenu("Inicio");
        Menu2 = new JMenu("Clientes");
        Menu3 = new JMenu("Cuenta");
        Menu4 = new JMenu("Ayuda");

        MBarra.add(Menu1);
        MBarra.add(Menu2);
        MBarra.add(Menu3);
        MBarra.add(Menu4);

        Item1 = new JMenuItem("Cerrar Sesión"); //Alt+F4
        Item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        // Item1.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        Item2 = new JMenuItem("Cerrar Aplicación"); //Alt+F1
        Item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        //
        Item3 = new JMenuItem("Agregar nuevo cliente"); //Ctrl+N
        Item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        Item4 = new JMenuItem("Modificar cliente"); //Ctrl+M
        Item4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        Item5 = new JMenuItem("Consultar cliente"); //Ctrl+F
        Item5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        Item6 = new JMenuItem("Eliminar cliente"); //Ctrl+D
        Item6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        //
        Item7 = new JMenuItem("Ver mis datos");

        Item8 = new JMenuItem("Cambiar mi contraseña");
        //
        Item9 = new JMenuItem("Cuadro de Ayuda");
        Item9.setAccelerator(KeyStroke.getKeyStroke("F1"));
        Item10 = new JMenuItem("Acerca de la aplicación");
        Item10.setAccelerator(KeyStroke.getKeyStroke("F11"));

        Menu1.add(Item1);
        Menu1.addSeparator();
        Menu1.add(Item2);

        Menu2.add(Item3);
        Menu2.add(Item4);
        Menu2.add(Item5);
        Menu2.addSeparator();
        Menu2.add(Item6);

        Menu3.add(Item7);
        Menu3.add(Item8);

        Menu4.add(Item9);
        Menu4.addSeparator();
        Menu4.add(Item10);

        Item1.addActionListener(this);
        Item2.addActionListener(this);
        Item3.addActionListener(this);
        Item4.addActionListener(this);
        Item5.addActionListener(this);
        Item6.addActionListener(this);
        Item7.addActionListener(this);
        Item8.addActionListener(this);
        Item9.addActionListener(this);
        Item10.addActionListener(this);
    }

    public void botones() {
        GridBagConstraints constraintsActualizar = new GridBagConstraints();
        BActualizar = new JButton("Actualizar Tabla (R)");
        //constraintsActualizar.ipady = 20; //Solicita 20 de espacio extra
        constraintsActualizar.fill = GridBagConstraints.HORIZONTAL;// El area de texto debe estirarse en ambos sentidos. Ponemos el campo fill.

        constraintsActualizar.gridx = 0; // El área de texto empieza en la columna cero.
        constraintsActualizar.gridy = 6; // El área de texto empieza en la fila uno
        constraintsActualizar.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsActualizar.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsActualizar.weighty = 0.0; //Se estira en Y       
        constraintsActualizar.weightx = 0.5; //Se estira en X
        add(BActualizar, constraintsActualizar);
        BActualizar.addKeyListener(this);
        BActualizar.addActionListener(this);

        GridBagConstraints constraintsConsultar = new GridBagConstraints();
        BConsultar = new JButton("Consultar");
        //constraintsActualizar.ipady = 20; //Solicita 20 de espacio extra
        constraintsConsultar.fill = GridBagConstraints.HORIZONTAL;
        constraintsConsultar.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsConsultar.gridx = 2; // El área de texto empieza en la columna cero.
        constraintsConsultar.gridy = 5; // El área de texto empieza en la fila uno
        constraintsConsultar.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsConsultar.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsConsultar.weighty = 0.0; //Se estira en Y       
        constraintsConsultar.weightx = 0.5; //Se estira en X
        add(BConsultar, constraintsConsultar);
        BConsultar.addKeyListener(this);
        BConsultar.addActionListener(this);

        GridBagConstraints constraintsModificar = new GridBagConstraints();
        BModificar = new JButton("Modificar");
        //constraintsActualizar.ipady = 20; //Solicita 20 de espacio extra
        constraintsModificar.fill = GridBagConstraints.HORIZONTAL;
        constraintsModificar.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsModificar.gridx = 3; // El área de texto empieza en la columna cero.
        constraintsModificar.gridy = 5; // El área de texto empieza en la fila uno
        constraintsModificar.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsModificar.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsModificar.weighty = 0.0; //Se estira en Y       
        constraintsModificar.weightx = 0.5; //Se estira en X
        add(BModificar, constraintsModificar);
        BModificar.addKeyListener(this);
        BModificar.addActionListener(this);

        GridBagConstraints constraintsBorrar = new GridBagConstraints();
        BBorrar = new JButton("Eliminar");
        //constraintsActualizar.ipady = 20; //Solicita 20 de espacio extra
        constraintsBorrar.fill = GridBagConstraints.HORIZONTAL;
        constraintsBorrar.anchor = GridBagConstraints.NORTH;
        constraintsBorrar.gridx = 4; // El área de texto empieza en la columna cero.
        constraintsBorrar.gridy = 5; // El área de texto empieza en la fila uno
        constraintsBorrar.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsBorrar.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsBorrar.weighty = 0.0; //Se estira en Y       
        constraintsBorrar.weightx = 0.5; //Se estira en X
        add(BBorrar, constraintsBorrar);
        BBorrar.addKeyListener(this);
        BBorrar.addActionListener(this);

    }

    public void textos() {

        GridBagConstraints constraintsTitulo = new GridBagConstraints();

        LTitulo = new JLabel("SISTEMA DE GESTIÓN DE CLIENTES");
        LTitulo.setFont(LTitulo.getFont().deriveFont(15f));
        constraintsTitulo.fill = GridBagConstraints.FIRST_LINE_START;
        constraintsTitulo.gridx = 0; // El área de texto empieza en la columna.
        constraintsTitulo.gridy = 0; // El área de texto empieza en la fila
        constraintsTitulo.gridwidth = 5; // El área de texto ocupa 3 columnas.
        constraintsTitulo.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsTitulo.weightx = 1; //Fila 0. Necesita estirarse, hay que poner weighty        
        constraintsTitulo.anchor = GridBagConstraints.CENTER;
        add(LTitulo, constraintsTitulo);

        LUsuario = new JLabel("Bienvenido " + User);
        LTitulo.setFont(LTitulo.getFont().deriveFont(15f));
        constraintsTitulo.fill = GridBagConstraints.FIRST_LINE_START;
        constraintsTitulo.gridx = 0; // El área de texto empieza en la columna.
        constraintsTitulo.gridy = 0; // El área de texto empieza en la fila
        constraintsTitulo.gridwidth = 5; // El área de texto ocupa 3 columnas.
        constraintsTitulo.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsTitulo.weightx = 1; //Fila 0. Necesita estirarse, hay que poner weighty        
        constraintsTitulo.anchor = GridBagConstraints.LINE_END;
        add(LUsuario, constraintsTitulo);

        LSub = new JLabel("  ");
        constraintsTitulo.fill = GridBagConstraints.FIRST_LINE_START;
        constraintsTitulo.gridx = 1; // El área de texto empieza en la columna.
        constraintsTitulo.gridy = 1; // El área de texto empieza en la fila
        constraintsTitulo.gridwidth = 3; // El área de texto ocupa 3 columnas.
        constraintsTitulo.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsTitulo.weightx = 0.0; //Fila 0. Necesita estirarse, hay que poner weighty        
        constraintsTitulo.anchor = GridBagConstraints.CENTER;
        add(LSub, constraintsTitulo);

        GridBagConstraints constraintsL2 = new GridBagConstraints();
        L2 = new JLabel("Seleccione o digite el ID de uno de los clientes y luego la opción que desea ejecutar."); // 
        // constraintsL2.fill = GridBagConstraints.PAGE_END;
        constraintsL2.ipady = 40;
        constraintsL2.gridx = 2; // El área de texto empieza en la columna cero.
        constraintsL2.gridy = 3; // El área de texto empieza en la fila uno
        constraintsL2.gridwidth = 3; // El área de texto ocupa dos columnas.
        constraintsL2.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsL2.weightx = 0.0; //Fila 0. Necesita estirarse, hay que poner weighty        
        //  constraintsL2.anchor = GridBagConstraints.PAGE_END;
        add(L2, constraintsL2);

        GridBagConstraints constraintsL3 = new GridBagConstraints();
        L3 = new JLabel("   "); // 
        constraintsL3.fill = GridBagConstraints.HORIZONTAL;// El area de texto debe estirarse en ambos sentidos. Ponemos el campo fill.
        constraintsL3.gridx = 1; // El área de texto empieza en la columna cero.
        constraintsL3.gridy = 6; // El área de texto empieza en la fila uno
        constraintsL3.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsL3.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsL3.weighty = 0.0; //Se estira en Y       
        constraintsL3.weightx = 0.5; //Se estira en X
        add(L3, constraintsL3);

        // GridBagConstraints constraintsL3 = new GridBagConstraints();
        L4 = new JLabel("   "); // 
        constraintsL3.fill = GridBagConstraints.HORIZONTAL;// El area de texto debe estirarse en ambos sentidos. Ponemos el campo fill.
        constraintsL3.ipady = 100;
        constraintsL3.gridx = 2; // El área de texto empieza en la columna cero.
        constraintsL3.gridy = 2; // El área de texto empieza en la fila uno
        constraintsL3.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsL3.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsL3.weighty = 0.0; //Se estira en Y       
        constraintsL3.weightx = 0.5; //Se estira en X
        add(L4, constraintsL3);

        //GridBagConstraints constraintsL3 = new GridBagConstraints();
        L5 = new JLabel("ID Cliente:"); // 
        constraintsL3.fill = GridBagConstraints.HORIZONTAL;// El area de texto debe estirarse en ambos sentidos. Ponemos el campo fill.
        constraintsL3.gridx = 3; // El área de texto empieza en la columna cero.
        constraintsL3.ipady = 25;
        constraintsL3.gridy = 4; // El área de texto empieza en la fila uno
        constraintsL3.gridwidth = 1; // El área de texto ocupa 1 columnas.
        constraintsL3.gridheight = 1; // El área de texto ocupa 1 filas.
        constraintsL3.weighty = 0.0; //Se estira en Y       
        constraintsL3.weightx = 0.5; //Se estira en X
        add(L5, constraintsL3);
    }

    public void Cajas() {
        Tusuario = new JTextField("1");
        GridBagConstraints constraintsUsuario = new GridBagConstraints();
        constraintsUsuario.ipadx = 17; //Solicita 20 de espacio extra
        constraintsUsuario.anchor = GridBagConstraints.CENTER;
        constraintsUsuario.gridx = 2; // El área de texto empieza en la columna cero.
        constraintsUsuario.gridy = 4; // El área de texto empieza en la fila uno
        constraintsUsuario.gridwidth = 3; // El área de texto ocupa 1 columnas.
        constraintsUsuario.gridheight = 1; // El área de texto ocupa 1 filas.
        // constraintsUsuario.weighty = 0.0; //Se estira en Y       
        // constraintsUsuario.weightx = 0.0; //Se estira en X
        add(Tusuario, constraintsUsuario);
        this.Tusuario.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        Tusuario.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Item1) {
            control();
        }
        if (ae.getSource() == Item2) {
            System.exit(0);
        }
        //-----
        if (ae.getSource() == Item3) {
            NewUser AP = new NewUser(this, true);
        }
        if (ae.getSource() == Item4) {
            Modify AP = new Modify(this, true, 0);
        }
        if (ae.getSource() == Item5) {
            Find AP = new Find(this, true, 0);
        }
        if (ae.getSource() == Item6) {
            Delete AP = new Delete(this, true, 0);
        }
        //-----
        if (ae.getSource() == Item7) {
            //NewUser aplicacion = new NewUser(this, true);
        }
        if (ae.getSource() == Item8) {
            ChangePSS AP = new ChangePSS(this, true, ID_ADM);
        }
        //-----
        if (ae.getSource() == Item9) {
            JOptionPane.showMessageDialog(null, "Entró al mensaje de al pulsar F1", "Ayuda Inicio - ARTURO 1.0.5", JOptionPane.INFORMATION_MESSAGE);
        }
        if (ae.getSource() == Item10) {
            JOptionPane.showMessageDialog(null, "ARTURO 1.0.5 es una aplicación de gestión de base de datos de clientes, ARTURO maneja un sistema de encriptamiento\ncompuesto el cual le permite ser el unico capaz de manipular los datos registrados en sus bases de datos.\n\nDesarrollado y distribuido por Fabian Dario Montoya, la distribucion, copia o uso no autorizado de este aplicativo puede \nconllevar procesos legales en contra de quien incumpla.\n\n• Se autoriza el uso personal de la aplicación exclusivamente al señor Miguel Angel Montoya Rojas, para quien ARTURO fue desarrollado.\n\n• Aviso de Copyright:\nCopyright © 2016-2017 Fabian_Montoya Developing Creations, Bogotá D.C, Colombia\nTodos los derechos reservados.\nTodos los textos, imágenes, gráficos, pistas de sonidos, datos de vídeo y animación, así como su composición o diseño están protegidos \npor derechos de autor y otras leyes de protección. Su contenido no puede copiarse para fines comerciales o de otras, ni puede mostrarse, \nincluso en una versión modificada sin la previa autorización del respectivo desarrollador.", "Acerca de - ARTURO 1.0.5", JOptionPane.INFORMATION_MESSAGE);
        }

        if (ae.getSource() == BActualizar) {
            try {
                TraerDatos();
                Tusuario.setEditable(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error al traer los datos de la base de datos.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Form1.java", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR para cerrar esta ventana.", "¡ERROR! - Form1.java", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (ae.getSource() == BModificar) {
            ID = Integer.parseInt(Tusuario.getText());
            Modify AP = new Modify(this, true, ID);
        }

        if (ae.getSource() == BConsultar) {
            ID = Integer.parseInt(Tusuario.getText());
            Find AP = new Find(this, true, ID);
        }

        if (ae.getSource() == BBorrar) {
            ID = Integer.parseInt(Tusuario.getText());
            BorrarCliente(ID);
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();
        int LimiteID = 5;
        if (ke.getSource() == Tusuario) {
            if (Character.isLetter(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite números, no letras.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) >= 33 && ((int) ke.getKeyChar()) <= 47 || ((int) ke.getKeyChar()) >= 58) { // && ((int) ke.getKeyChar()) <= 58 || ((int) ke.getKeyChar()) >= 123 && ((int) ke.getKeyChar()) <= 163 || ((int) ke.getKeyChar()) >= 166 && ((int) ke.getKeyChar()) <= 255) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de carácteres especiales.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            } else if (((int) ke.getKeyChar()) == 32) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "Este campo no admite Espacios.\n\nIntente de nuevo.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }
            if (Tusuario.getText().length() == LimiteID) {
                ke.consume();
            }

            if (Tusuario.getText().length() == 0) {
                BConsultar.setEnabled(false);
                BModificar.setEnabled(false);
                BBorrar.setEnabled(false);
            }

        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_ALT) {
            // JOptionPane.showMessageDialog(null, "Pulsó Alt", "Alt + F1 Press", JOptionPane.INFORMATION_MESSAGE);
            pulso = pulso + 1;
        }

        if (key == KeyEvent.VK_F1) {
            // JOptionPane.showMessageDialog(null, "Pulsó F1", "Alt + F1 Press", JOptionPane.INFORMATION_MESSAGE);
            pulso = pulso + 1;
        }

        if (key == KeyEvent.VK_R) {
            try {
                TraerDatos();
                Tusuario.setEditable(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error al traer los datos de la base de datos.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Form1.java", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR para cerrar esta ventana.", "¡ERROR! - Form1.java", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (pulso == 2) {
            System.exit(0);
        }
        pulso = 0;

        if (Tusuario.getText().length() >= 1) {
            BConsultar.setEnabled(true);
            BModificar.setEnabled(true);
            BBorrar.setEnabled(true);
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP) {
            Tusuario.setEditable(false);
            int Fila = tabla.getSelectedRow();
            Tusuario.setText(String.valueOf(tabla.getValueAt(Fila, 0)));
        }

    }

    public static void main(String args[]) {

    }

    public void TraerDatos() throws Exception {
        String Data[] = new String[500];
        int x;

        String sql = "SELECT * FROM Clientes ORDER BY Nombres_Cli ASC;";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            if (tabla.getRowCount() >= 1) {
                for (int i = 0; i < tabla.getRowCount(); i++) {
                    ModeloTabla.removeRow(i);
                    i -= 1;
                }
            }
            while (resultado.next()) {
                for (x = 2; x < 8; x++) {
                    Data[0] = resultado.getString(1);
                    Data[1] = resultado.getString(2);
                    Data[x] = Utilidades.Desencriptar(resultado.getString(x + 1));
                }
                ModeloTabla.addRow(Data);
            }
            conexion.close();
            Tusuario.setText("1");
            Tusuario.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al consultar la Base de Datos y no se motrarán datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Form1 - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void BorrarCliente(int ID) {
        String Nombre = "", Apellido = "";
        try {
            int Fila = tabla.getSelectedRow();

            Nombre = (String.valueOf(tabla.getValueAt(Fila, 1)));
            Apellido = (String.valueOf(tabla.getValueAt(Fila, 2)));
            int ax = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar al cliente " + Nombre + " " + Apellido + " con ID " + ID + " permanentemente?", "Eliminar Cliente - Confirmar", JOptionPane.YES_NO_OPTION);
            if (ax == JOptionPane.YES_OPTION) {
                Borrar(ID);
            } else {
                //No se hace nada
            }
        } catch (Exception ex) {
            try {

                int val = ID, X = 0;
                int columna = 0;

                for (int fila = 0; fila < tabla.getRowCount(); fila++) {

                    Integer num = Integer.parseInt((String) tabla.getValueAt(fila, columna));

                    if (val == num) {
                        X = num;
                        Nombre = (String.valueOf(tabla.getValueAt(fila, 1)));
                        Apellido = (String.valueOf(tabla.getValueAt(fila, 2)));
                        //tabla.changeSelection(fila, 0, true, true);
                        int ax = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar al cliente " + Nombre + " " + Apellido + " con ID " + ID + " permanentemente?", "Eliminar Cliente - Confirmar", JOptionPane.YES_NO_OPTION);
                        if (ax == JOptionPane.YES_OPTION) {
                            Borrar(ID);
                        } else {
                            //No se hace nada
                        }
                    }

                }

                if (X != ID) {
                    Delete AP = new Delete(this, true, ID);
                }

            } catch (Exception e) {
                //
            }

        }
    }

    private void Borrar(int ID) {
        String sql = "DELETE FROM CLIENTES WHERE ID_CLI = " + ID + ";";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();

            sentencia.execute(sql);
            conexion.close();

            JOptionPane.showMessageDialog(null, "Se ha borrado al cliente correctamente.", "Confirmación - Eliminación correcta", JOptionPane.INFORMATION_MESSAGE);
            try {
                TraerDatos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error al traer los datos de la base de datos.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Form1.java", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR para cerrar esta ventana.", "¡ERROR! - Form1.java", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al Borrar el cliente de la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Form1.java - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int Fila = tabla.getSelectedRow();
        Tusuario.setText(String.valueOf(tabla.getValueAt(Fila, 0)));
        Tusuario.setEditable(false);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
