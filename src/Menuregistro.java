
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menuregistro extends JFrame {
    private JDesktopPane desktPane;
    private RegistroPersona F1;
    private Searchupdatedelete F2;
    private JPanel contentPane;
    private JPanel sidebar;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new Menuregistro().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Menuregistro() {
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 700));

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // --- SIDEBAR (IZQUIERDA) ---
        sidebar = new JPanel();
        sidebar.setBackground(new Color(230, 235, 240));
        sidebar.setPreferredSize(new Dimension(150, 0));
        sidebar.setLayout(new GridLayout(3, 1, 0, 5));
        contentPane.add(sidebar, BorderLayout.WEST);

        JButton btnInicio = crearBotonSidebar("Inicio");
        JButton btnRegistro = crearBotonSidebar("Registro");
        JButton btnBuscar = crearBotonSidebar("Buscar");

        sidebar.add(btnInicio);
        sidebar.add(btnRegistro);
        sidebar.add(btnBuscar);

        // --- DESKTOP PANE (CENTRO) ---
        desktPane = new JDesktopPane();
        desktPane.setBackground(new Color(44, 53, 57)); // Gris oscuro de la imagen
        contentPane.add(desktPane, BorderLayout.CENTER);

        // Etiqueta de bienvenida central
        JLabel lblTitulo = new JLabel("Sistema de Registro");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 48));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Listener para centrar el título dinámicamente
        desktPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                lblTitulo.setBounds(0, desktPane.getHeight()/2 - 50, desktPane.getWidth(), 100);
            }
        });
        desktPane.add(lblTitulo);

        // EVENTOS DE BOTONES
        btnInicio.addActionListener(e -> {
            if (F1 != null) F1.dispose();
            if (F2 != null) F2.dispose();
            lblTitulo.setVisible(true);
        });

        btnRegistro.addActionListener(e -> {
            lblTitulo.setVisible(false);
            a_imc();
        });

        btnBuscar.addActionListener(e -> {
            lblTitulo.setVisible(false);
            video();
        });
    }

    private JButton crearBotonSidebar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Tahoma", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        return btn;
    }

    void a_imc() {
        if (F1 == null || !F1.isVisible()) {
            F1 = new RegistroPersona();
            F1.setVisible(true);
            desktPane.add(F1);
            try { 
                F1.setMaximum(true); 
                F1.setSelected(true); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    void video() {
        if (F2 == null || !F2.isVisible()) {
            F2 = new Searchupdatedelete();
            F2.setVisible(true);
            desktPane.add(F2);
            try { 
                F2.setMaximum(true); 
                F2.setSelected(true); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
