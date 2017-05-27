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
public class Modify extends JDialog implements ActionListener, KeyListener {

    Connection conexion;
    ResultSet resultado;
    Statement sentencia;

    JLabel LTitulo, LSub, LSub2, LSub3, LID, LNombres, LApellidos, LDocumento, LDireccion, LCelular, LTelefono, LComentario;
    JTextField TID, TNombres, TApellidos, TDocumento, TDireccion, TCelular, TTelefono, TComentario;
    JButton BConsultar, BGuardar, BReiniciar;

    String Nombre, Apellido, Documento, Telefono, Celular, Direccion, Comentario, Documento1;
    int LimiteID = 5, LimiteNombre = 60, LimiteApellido = 60, LimiteDocumento = 20, LimiteDireccion = 100, LimiteCelular = 11, LimiteTelefono = 8, LimiteComentario = 400;
    int ID = 0;

    public Modify(Frame parent, boolean modal, int id_cli) {
        super(parent, modal);
        try {
            setTitle("Modificar Cliente - ARTURO 1.0.5");
            setLayout(null);
            Image icon = new ImageIcon(getClass().getResource("/Imagenes/Edit.png")).getImage();
            setIconImage(icon);

            ID = id_cli;

            //------
            Textos();
            Cajas();
            Botones();

            if (ID > 0) {
                BConsultar.setEnabled(true);
                Consultar(ID);
            } else if (ID == 0) {
                BConsultar.setEnabled(false);
                TNombres.setEnabled(false);
                TApellidos.setEnabled(false);
                TDocumento.setEnabled(false);
                TDireccion.setEnabled(false);
                TCelular.setEnabled(false);
                TTelefono.setEnabled(false);
                TComentario.setEnabled(false);
                TID.requestFocus();
            }

            setSize(400, 480);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al abrir la ventana.\n\nPor favor consulte con su desarrollador.\n\n Pulse ACEPTAR para ver el error.", "¡ERROR! - Modify.java", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "\n\nPulse ACEPTAR, la acción se cancelará.", "¡ERROR! - Modify.java", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Textos() {
        LTitulo = new JLabel("MODIFICACIÓN DE CLIENTE");
        LTitulo.setFont(LTitulo.getFont().deriveFont(14f));
        LTitulo.setBounds(105, 2, 300, 20);

        LSub = new JLabel("Digite el ID del cliente que desea utilizar, realice las modificaciones");
        LSub.setBounds(8, 25, 440, 20);

        LSub2 = new JLabel("y pulse GUARDAR para efectuar los cambios realizados.");
        LSub2.setBounds(38, 40, 440, 20);

        LSub3 = new JLabel("* Campo obligatorio.");
        LSub3.setBounds(15, 63, 440, 20);
        LSub3.setFont(LTitulo.getFont().deriveFont(8f));

        LID = new JLabel("ID:");
        LID.setBounds(120, 90, 440, 20);

        LNombres = new JLabel("Nombre(s)*:");
        LNombres.setBounds(15, 130, 440, 20);

        LApellidos = new JLabel("Apellidos*:");
        LApellidos.setBounds(15, 160, 440, 20);

        LDocumento = new JLabel("Número Documento*:");
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
        add(LSub3);
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
        TNombres.setBounds(140, 130, 170, 20);
        TNombres.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TNombres.addKeyListener(this);

        TApellidos = new JTextField("");
        TApellidos.setBounds(140, 160, 170, 20);
        TApellidos.addKeyListener(this);

        TDocumento = new JTextField("");
        TDocumento.setBounds(140, 190, 170, 20);
        TDocumento.addKeyListener(this);

        TDireccion = new JTextField("");
        TDireccion.setBounds(140, 220, 170, 20);
        TDireccion.addKeyListener(this);

        TCelular = new JTextField("");
        TCelular.setBounds(140, 250, 170, 20);
        TCelular.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        TCelular.addKeyListener(this);

        TTelefono = new JTextField("");
        TTelefono.setBounds(140, 280, 170, 20);
        TTelefono.addKeyListener(this);

        TComentario = new JTextField("");
        TComentario.setBounds(87, 310, 298, 20);
        TComentario.addKeyListener(this);

        TNombres.setEnabled(false);
        TApellidos.setEnabled(false);
        TDocumento.setEnabled(false);
        TDireccion.setEnabled(false);
        TCelular.setEnabled(false);
        TTelefono.setEnabled(false);
        TComentario.setEnabled(false);

        add(TID);
        add(TNombres);
        add(TApellidos);
        add(TDocumento);
        add(TDireccion);
        add(TCelular);
        add(TTelefono);
        add(TComentario);
    }

    private void Botones() {
        BConsultar = new JButton("Consultar");
        BConsultar.setBounds(200, 90, 90, 20);
        BConsultar.addActionListener(this);
        BConsultar.setEnabled(false);
        add(BConsultar);

        BGuardar = new JButton("Guardar");
        BGuardar.setBounds(82, 370, 100, 20);
        BGuardar.addActionListener(this);
        add(BGuardar);

        BReiniciar = new JButton("Reiniciar");
        BReiniciar.setBounds(200, 370, 100, 20);
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
                TNombres.setEnabled(false);
                TApellidos.setEnabled(false);
                TDocumento.setEnabled(false);
                TDireccion.setEnabled(false);
                TCelular.setEnabled(false);
                TTelefono.setEnabled(false);
                TComentario.setEnabled(false);
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
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al consultar la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Modify - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Modify - Consultar - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Guardar(String nombre, String apellido, String documento, String direccion, String celular, String telefono, String comentario) {

        nombre = Nombre;
        apellido = Utilidades.Encriptar(Apellido);
        documento = Utilidades.Encriptar(Documento);
        direccion = Utilidades.Encriptar(Direccion);
        celular = Utilidades.Encriptar(Celular);
        telefono = Utilidades.Encriptar(Telefono);
        comentario = Utilidades.Encriptar(Comentario);

        String sql = "UPDATE CLIENTES SET NOMBRES_CLI = '" + nombre + "', APELLIDOS_CLI = '" + apellido + "', DOCUMENTO_CLI = '" + documento + "', DIRECCION_CLI = '" + direccion + "', CELULAR_CLI = '" + celular + "', TELEFONO_CLI = '" + telefono + "', COMENTARIOS  = '" + comentario + "' WHERE ID_CLI = " + ID + ";";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();

            sentencia.execute(sql);
            conexion.close();

            JOptionPane.showMessageDialog(null, "Se han guardado los cambios efectuados a " + Nombre + " " + Apellido + " correctamente.\nPara ver los cambios reflejados recuerda actualizar la tabla en la página principal.", "Confirmación - Modificación correcta", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al Modificar la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Modify.java - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int consultardocumento(String Documento) {
        int valido = 1;
        String CadenaDocumento = "";

        Documento = Utilidades.Encriptar(Documento);

        String sql = "SELECT documento_cli FROM CLIENTES WHERE documento_cli = '" + Documento + "'";
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/UsuariosDB", "administrador", "ADMUsuarios");
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            while (resultado.next() != false) {
                CadenaDocumento = resultado.getString("documento_cli");
                valido = 0;
            }
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error al consultar el documento en la Base de Datos.\n\nPor favor consulte con su desarrollador.\nPulse ACEPTAR para ver el error.", "Form1 - ¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Error:  " + ex + "", "Modify.java - ¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

        return valido;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == BConsultar) {
            ID = Integer.parseInt(TID.getText());
            Consultar(ID);
        }

        if (ae.getSource() == BGuardar) {

            Nombre = TNombres.getText().toUpperCase();
            Apellido = TApellidos.getText().toUpperCase();
            Documento = TDocumento.getText().toUpperCase();
            Direccion = TDireccion.getText().toUpperCase();
            Celular = TCelular.getText().toUpperCase();
            Telefono = TTelefono.getText().toUpperCase();
            Comentario = TComentario.getText();

            if (Nombre.length() != 0 && Apellido.length() != 0 && Documento.length() != 0) {
                int ax = JOptionPane.showConfirmDialog(null, "Los datos que usted digito fueron: \n- Nombre: " + Nombre + ".\n- Apellido: " + Apellido + ".\n- Documento: " + Documento + ".\n- Dirección: " + Direccion + ".\n- Número celular: " + Celular + ".\n- Número de teléfono: " + Telefono + ".\n- Comentario: " + Comentario + ".\n\n ¿Son correctos?, confirme para guardar las modificaciones.", "Confirmación - Modificación cliente", JOptionPane.YES_NO_OPTION);
                int documentovalido = 0;
                if (ax == JOptionPane.YES_OPTION) {
                    if ((Documento.compareTo(Documento1)) == 0) {
                        documentovalido = 1;
                    } else {
                        documentovalido = consultardocumento(Documento);
                    }
                    if (documentovalido == 1) {
                        Guardar(Nombre, Apellido, Documento, Direccion, Celular, Telefono, Comentario);
                    } else if (documentovalido == 0) {
                        JOptionPane.showMessageDialog(null, "El documento digitado ya fue asignado a un usuario, rectifique por favor.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        TDocumento.requestFocus();
                    }
                } else if (ax == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Modificación cancelada, corrobore sus datos.");
                }
            } else {
                if (Nombre.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo NOMBRES es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TNombres.requestFocus();
                } else if (Apellido.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo APELLIDOS es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TApellidos.requestFocus();
                } else if (Documento.length() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo DOCUMENTO es obligatorio\nPor favor revice.", "¡ERROR! - Campo incompleto", JOptionPane.ERROR_MESSAGE);
                    TDocumento.requestFocus();
                }

            }

        }

        if (ae.getSource() == BReiniciar) {
            int ax2 = JOptionPane.showConfirmDialog(null, "El formulario se reiniciara\n¿Desea continuar?", "Confirmar - Reiniciar formulario", JOptionPane.YES_NO_OPTION);

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
                    TNombres.setText("");
                    TApellidos.setText("");
                    TDocumento.setText("");
                    TTelefono.setText("");
                    TCelular.setText("");
                    TDireccion.setText("");
                    TComentario.setText("");
                    BConsultar.setEnabled(false);
                    TNombres.setEnabled(false);
                    TApellidos.setEnabled(false);
                    TDocumento.setEnabled(false);
                    TDireccion.setEnabled(false);
                    TCelular.setEnabled(false);
                    TTelefono.setEnabled(false);
                    TComentario.setEnabled(false);
                    TID.requestFocus();
                    TID.selectAll();
                }
            } catch (Exception ex) {
                //
            }
        }

        if (ke.getSource() == TNombres) {
            if (Character.isDigit(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite letras, no números.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            //como vamos a convertir todo a mayúsculas, entonces solo checamos si los caracteres son 
            //minusculas
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TNombres.getText().length() == LimiteNombre - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TApellidos) {
            if (Character.isDigit(c)) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "El campo solo admite letras, no números.", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            //como vamos a convertir todo a mayúsculas, entonces solo checamos si los caracteres son 
            //minusculas
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TApellidos.getText().length() == LimiteApellido - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TDireccion) {
            if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
                //si lo son, entonces lo reemplazamos por su respectivo en mayúscula en el mismo evento
                // (esto se logra por que en java todas las variables son pasadas por referencia)
                ke.setKeyChar((char) (((int) ke.getKeyChar()) - 32));
            }

            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }

            if (TDireccion.getText().length() == LimiteDireccion - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TDocumento) {
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

            if (TDocumento.getText().length() == LimiteDocumento - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TCelular) {
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

            if (TCelular.getText().length() == LimiteCelular - 1) {
                ke.consume();
            }

        }

        if (ke.getSource() == TTelefono) {
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
            if (TTelefono.getText().length() == LimiteTelefono - 1) {
                ke.consume();
            }
        }

        if (ke.getSource() == TComentario) {
            if (((int) ke.getKeyChar()) == 39) {
                ke.consume();
                JOptionPane.showMessageDialog(null, "No se permite el uso de este carácter.\n\nIntente con uno diferente.", "¡ERROR! - Carácter no valido", JOptionPane.ERROR_MESSAGE);
            }
            if (TComentario.getText().length() == LimiteComentario - 1) {
                ke.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_F1) {
            JOptionPane.showMessageDialog(null, "•La ventana Modificar Cliente permite editar cada uno de los campos con los que habíamos registrado \nun cliente anteriormente.\n\n• Para ver los datos de un cliente debes digitar el número de su ID con el cual quedó registrado, luego pulsa el\nbotón Consultar o la tecla Enter.\nSi el ID coincide, en las casillas aparecerán los respectivos datos y se habilitarán para su modificación.\n\n• A la hora de modificar, los campos Nombres, Apellidos y Documento son obligatorios, no los puedes dejar en blanco.\n\n• A excepción de los comentarios, todos los campos se escribirán en mayúsculas automáticamente, sino \nlo hacen al instante, a la hora de confirmar el registro rectifica que todos los datos estén en mayúsculas.\n\n• Para efectuar los cambios dados debes pulsar el botón Guardar, te pedirá que confirmes los datos, al confirmar\nse guardarán los datos según fueron modificados.\n\n• Para visualizar los cambios efectuados debes pulsar 'Actualizar Tabla' en la ventana principal de la aplicación.\n\nAl pulsar ACEPTAR esta ventana se cerrará.", "Ayuda Modificar Cliente - ARTURO 1.0.5", JOptionPane.INFORMATION_MESSAGE);
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
