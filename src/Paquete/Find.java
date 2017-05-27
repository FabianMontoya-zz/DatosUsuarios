package Paquete;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fabian_Montoya
 */
public class Find extends JDialog implements ActionListener, KeyListener {

    Connection conexion;
    ResultSet resultado;
    Statement sentencia;

    JLabel LTitulo, LSub, LSub2, LSub3, LID, LNombres, LApellidos, LDocumento, LDireccion, LCelular, LTelefono, LComentario;
    JTextField TID, TNombres, TApellidos, TDocumento, TDireccion, TCelular, TTelefono;
    JTextArea TComentario;
    JScrollPane scroll;
    JButton BConsultar, BEliminar, BReiniciar, BModificar;
    Frame Parent;
    String Nombre, Apellido, Documento, Telefono, Celular, Direccion, Comentario, Documento1;
    int LimiteID = 5, LimiteNombre = 60, LimiteApellido = 60, LimiteDocumento = 20, LimiteDireccion = 100, LimiteCelular = 11, LimiteTelefono = 8, LimiteComentario = 400;
    int ID = 0, Tamaño = 0;

    public Find(Frame parent, boolean modal, int id_cli) {
        super(parent, modal);
        try {
            Parent = parent;
            setTitle("Consultar Cliente - ARTURO 1.0.5");
            setLayout(null);
            Image icon = new ImageIcon(getClass().getResource("/Imagenes/Database.png")).getImage();
            setIconImage(icon);

            ID = id_cli;

            //------
            Textos();
            Cajas();
            Botones();

            if (ID > 0) {
                BConsultar.setEnabled(true);
                BModificar.setEnabled(true);
                BEliminar.setEnabled(false);
                Consultar(ID);
            } else if (ID == 0) {
                BConsultar.setEnabled(false);
                TNombres.setEnabled(false);
                TApellidos.setEnabled(false);
                TDocumento.setEnabled(false);
                TDireccion.setEnabled(false);
                TCelular.setEnabled(false);
                TTelefono.setEnabled(false);
                BModificar.setEnabled(false);
                BEliminar.setEnabled(false);
                // TComentario.setEnabled(false);
                TID.requestFocus();
            }

            setSize(475, 480);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al abrir la ventana.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Find.java", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR, la acción se cancelará.", "¡ERROR! - Find.java", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Textos() {
        LTitulo = new JLabel("CONSULTA DE CLIENTE");
        LTitulo.setFont(LTitulo.getFont().deriveFont(14f));
        LTitulo.setBounds(145, 2, 300, 20);

        LSub = new JLabel("Digite el ID del cliente del cual desea conocer la información registrada,");
        LSub.setBounds(25, 25, 440, 20);

        LSub2 = new JLabel("si desea modificar la información mostrada pulse el botón en la parte inferior.");
        LSub2.setBounds(10, 40, 440, 20);

        /* LSub3 = new JLabel("* Campo obligatorio.");
         LSub3.setBounds(15, 63, 440, 20);
         LSub3.setFont(LTitulo.getFont().deriveFont(8f));*/
        LID = new JLabel("ID:");
        LID.setBounds(120, 90, 440, 20);

        LNombres = new JLabel("Nombre(s):");
        LNombres.setBounds(15, 130, 440, 20);

        LApellidos = new JLabel("Apellidos:");
        LApellidos.setBounds(15, 160, 440, 20);

        LDocumento = new JLabel("Número Documento:");
        LDocumento.setBounds(15, 190, 440, 20);

        LDireccion = new JLabel("Dirección Domicilio:");
        LDireccion.setBounds(15, 220, 440, 20);

        LCelular = new JLabel("Número Celular:");
        LCelular.setBounds(15, 250, 440, 20);

        LTelefono = new JLabel("Número Teléfono:");
        LTelefono.setBounds(15, 280, 440, 20);

        LComentario = new JLabel("Comentario:");
        LComentario.setBounds(15, 310, 440, 20);

        add(LTitulo);
        add(LSub);
        add(LSub2);
        //     add(LSub3);
        add(LID);
        add(LNombres);
        add(LApellidos);
        add(LDocumento);
        add(LDireccion);
        add(LCelular);
        add(LTelefono);
        add(LComentario);
    }

    private void Cajas() {
        TID = new JTextField("" + ID);
        TID.setBounds(140, 90, 45, 20);
        TID.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TID.addKeyListener(this);

        TNombres = new JTextField("");
        TNombres.setBounds(140, 130, 175, 20);
        TNombres.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TNombres.setEditable(false);
        TNombres.addKeyListener(this);

        TApellidos = new JTextField("");
        TApellidos.setBounds(140, 160, 175, 20);
        TApellidos.setEditable(false);
        TApellidos.addKeyListener(this);

        TDocumento = new JTextField("");
        TDocumento.setBounds(140, 190, 175, 20);
        TDocumento.setEditable(false);
        TDocumento.addKeyListener(this);

        TDireccion = new JTextField("");
        TDireccion.setBounds(140, 220, 175, 20);
        TDireccion.setEditable(false);
        TDireccion.addKeyListener(this);

        TCelular = new JTextField("");
        TCelular.setBounds(140, 250, 175, 20);
        TCelular.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TCelular.setEditable(false);
        TCelular.addKeyListener(this);

        TTelefono = new JTextField("");
        TTelefono.setBounds(140, 280, 175, 20);
        TTelefono.setEditable(false);
        TTelefono.addKeyListener(this);

        TComentario = new JTextArea();
        //  TComentario.setBounds(87, 310, 298, 80);
        TComentario.setEditable(false);
        TComentario.setLineWrap(true);
        TComentario.setWrapStyleWord(true);
        TComentario.addKeyListener(this);

        scroll = new JScrollPane(TComentario, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(15, 330, 430, 80);
        /* TNombres.setEnabled(false);
         TApellidos.setEnabled(false);
         TDocumento.setEnabled(false);
         TDireccion.setEnabled(false);
         TCelular.setEnabled(false);
         TTelefono.setEnabled(false);*/
        TComentario.setEnabled(false);
        add(TID);
        add(TNombres);
        add(TApellidos);
        add(TDocumento);
        add(TDireccion);
        add(TCelular);
        add(TTelefono);
        //add(TComentario);
        add(scroll);
    }

    private void Botones() {
        BConsultar = new JButton("Consultar");
        BConsultar.setBounds(200, 90, 90, 20);
        BConsultar.addActionListener(this);
        BConsultar.setEnabled(false);
        add(BConsultar);

        BModificar = new JButton("Modificar");
        BModificar.setBounds(60, 420, 100, 20);
        BModificar.addActionListener(this);
        add(BModificar);

        BEliminar = new JButton("Eliminar");
        BEliminar.setBounds(175, 420, 100, 20);
        BEliminar.addActionListener(this);
        add(BEliminar);

        BReiniciar = new JButton("Reiniciar");
        BReiniciar.setBounds(295, 420, 100, 20);
        BReiniciar.addActionListener(this);
        add(BReiniciar);
    }

    private void Consultar(int ID) {
        boolean existe = false;
        String sql = "SELECT * FROM Clientes WHERE ID_CLI = " + ID + ";";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);

            while (resultado.next() != false) {
                try {
                    TNombres.setText(resultado.getString("Nombres_Cli"));
                    TApellidos.setText(Utilidades.Desencriptar(resultado.getString("Apellidos_Cli")));
                    TDocumento.setText(Utilidades.Desencriptar(resultado.getString("Documento_Cli")));
                    Documento1 = Utilidades.Desencriptar(resultado.getString("Documento_Cli"));
                    TDireccion.setText(Utilidades.Desencriptar(resultado.getString("Direccion_Cli")));
                    TCelular.setText(Utilidades.Desencriptar(resultado.getString("Celular_Cli")));
                    TTelefono.setText(Utilidades.Desencriptar(resultado.getString("Telefono_Cli")));
                    TComentario.setText(Utilidades.Desencriptar(resultado.getString("Comentarios")));
                    existe = true;
                } catch (Exception ex) {

                }
            }

            if (existe == false) {
                JOptionPane.showMessageDialog(null, "No existen datos asociados a este ID (" + ID + ")\nComprueba nuevamente.", "¡ERROR! - ID sin datos", JOptionPane.ERROR_MESSAGE);
                TNombres.setText("");
                TApellidos.setText("");
                TDocumento.setText("");
                TTelefono.setText("");
                TCelular.setText("");
                TDireccion.setText("");
                TComentario.setText("");
                BConsultar.setEnabled(false);
                BEliminar.setEnabled(false);
                BModificar.setEnabled(false);
                TNombres.setEnabled(true);
                TApellidos.setEnabled(true);
                TDocumento.setEnabled(true);
                TDireccion.setEnabled(true);
                TCelular.setEnabled(true);
                TTelefono.setEnabled(true);
                TComentario.setEnabled(true);
                TID.requestFocus();
                TID.selectAll();
            } else if (existe == true) {
                conexion.close();
                TNombres.setEnabled(true);
                TApellidos.setEnabled(true);
                TDocumento.setEnabled(true);
                TDireccion.setEnabled(true);
                TCelular.setEnabled(true);
                TTelefono.setEnabled(true);
                TComentario.setEnabled(true);
                BModificar.setEnabled(true);
                BConsultar.setEnabled(true);
                BEliminar.setEnabled(true);
                Tamaño = TID.getText().length();
                TID.requestFocus();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al consultar la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Modify - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Modify - Consultar - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == BConsultar) {
            ID = Integer.parseInt(TID.getText());
            Consultar(ID);
        }

        if (ae.getSource() == BModificar) {
            ID = Integer.parseInt(TID.getText());
            setVisible(false);
            Modify AP = new Modify(Parent, true, ID);
            dispose();

        }

        if (ae.getSource() == BReiniciar) {
            int ax2 = JOptionPane.showConfirmDialog(null, "Los campos se reiniciara\n¿Desea continuar?", "Confirmar - Reiniciar formulario", JOptionPane.YES_NO_OPTION);

            if (ax2 == JOptionPane.YES_OPTION) {
                TID.setText("0");
                TNombres.setText("");
                TApellidos.setText("");
                TDocumento.setText("");
                TTelefono.setText("");
                TCelular.setText("");
                TDireccion.setText("");
                TComentario.setText("");
                BConsultar.setEnabled(false);
                BEliminar.setEnabled(false);
                BModificar.setEnabled(false);
                TNombres.setEnabled(false);
                TApellidos.setEnabled(false);
                TDocumento.setEnabled(false);
                TDireccion.setEnabled(false);
                TCelular.setEnabled(false);
                TTelefono.setEnabled(false);
                TComentario.setEnabled(false);
                TID.requestFocus();
                TID.selectAll();

            } else if (ax2 == JOptionPane.NO_OPTION) {

            }
        }

        if (ae.getSource() == BEliminar) {
            ID = Integer.parseInt(TID.getText());
            setVisible(false);
            Delete AP = new Delete(Parent, true, ID);
            dispose();
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();

        if (ke.getSource() == TID) {
            try {
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

                if (TID.getText().length() == LimiteID) {
                    ke.consume();
                }

                if (TID.getText().length() == 0 || Integer.parseInt(TID.getText()) == 0) {
                    BConsultar.setEnabled(false);
                    BEliminar.setEnabled(false);
                    TNombres.setText("");
                    TApellidos.setText("");
                    TDocumento.setText("");
                    TTelefono.setText("");
                    TCelular.setText("");
                    TDireccion.setText("");
                    TComentario.setText("");
                    BModificar.setEnabled(false);
                    TID.requestFocus();
                    TID.selectAll();
                }
            } catch (Exception ex) {
                //
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_F1) {
            JOptionPane.showMessageDialog(null, "", "Ayuda Consultar Cliente - ARTURO 1.0.5", JOptionPane.INFORMATION_MESSAGE);
        }
        if (ke.getSource() == TID) {
            if (key == KeyEvent.VK_ENTER) {
                BConsultar.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == TID) {
            try {
                if (Integer.parseInt(TID.getText()) >= 1) {
                    BConsultar.setEnabled(true);
                }
            } catch (Exception ex) {
                //
            }            
                if (Tamaño > TID.getText().length()) {
                    BEliminar.setEnabled(false);
                    BModificar.setEnabled(false);
                }
        }
    }

    public static void main(String args[]) {
        /*JDialog AP = new NewUser();
         AP.setSize(400, 500);
         AP.setResizable(false);
         AP.setLocationRelativeTo(null);
         AP.setVisible(true);*/

    }

}
