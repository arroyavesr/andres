package soft3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;

public class Menuregistro extends JFrame {
    private JDesktopPane desktPane;
    private RegistroPersona F1;
    private Searchupdatedelete F2;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new Menuregistro().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initUI() {
        setTitle("Proyecto#1->softwaresIII");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //  MENÚ COMPLETO CON LOS MENÚS
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Programas
        JMenu mnArchivo = new JMenu("Programas");
        mnArchivo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuBar.add(mnArchivo);

        JMenuItem mnuIMC = new JMenuItem("Abrir Registro");
        mnuIMC.addActionListener(e -> a_imc());
        mnuIMC.setForeground(Color.RED);
        mnArchivo.add(mnuIMC);

        mnArchivo.addSeparator();

        JMenuItem mnuFilms = new JMenuItem("editar/buscar/eliminar");
        mnuFilms.addActionListener(e -> video());
        mnuFilms.setForeground(Color.BLUE);
        mnArchivo.add(mnuFilms);

  
        // SALIR
        JMenu mnSalir = new JMenu("Salir");
        mnSalir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuBar.add(mnSalir);

        JMenuItem mnuQuit = new JMenuItem("Quit");
        mnuQuit.addActionListener(e -> System.exit(0));
        mnuQuit.setForeground(Color.DARK_GRAY);
        mnSalir.add(mnuQuit);

        //  DESKTOP PANE
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        desktPane = new JDesktopPane();
        desktPane.setBackground(SystemColor.activeCaption);

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addComponent(desktPane, GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addComponent(desktPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE));
    }

    public void Menu() {
        initUI();
    }

    void a_imc() {
        if (F1 == null || F1.isFocused()) {
            F1 = new RegistroPersona();
            F1.setSize(700, 550);
            F1.setLocation(50, 30);
            F1.setVisible(true);
            desktPane.add(F1);
            try { F1.setSelected(true); } catch (Exception ex) {}
        }
    }

    void video() {
        if (F2 == null || F2.isFocused()) {
            F2 = new Searchupdatedelete();
            F2.setSize(650, 520);
            F2.setLocation(100, 50);
            F2.setVisible(true);
            desktPane.add(F2);
            try { F2.setSelected(true); } catch (Exception ex) {}
        }
    }

  
}