package projekt2;

import graphs.Edge;
import graphs.GraphMethods;
import graphs.ListGraph;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Karta extends JFrame {

    JMenuItem nytt, sparaSom;
    boolean kartaLaddad = false;
    Bildpanel bild = null;
    private ListGraph<String> g;
    Nod d1 = null, d2 = null;

    Karta() {

        super("Kartprogram");

        setLayout(new BorderLayout());

        g = new ListGraph<>();

        JButton hittaVägKnapp = new JButton("Hitta väg");
        hittaVägKnapp.addActionListener(new HittaVäg());
        JButton visaFörbindelser = new JButton("Visa förbindelser");
        visaFörbindelser.addActionListener(new VisaFörbindelse());
        JButton nyPlatsKnapp = new JButton("Ny Plats");
        nyPlatsKnapp.addActionListener(new NyPlats());
        JButton nyFörbindelseKnapp = new JButton("Ny förbindelse");
        nyFörbindelseKnapp.addActionListener(new NyFörbindelse());
        JButton ändraFörbindelseKnapp = new JButton("Ändra förbindelse");
        ändraFörbindelseKnapp.addActionListener(new ÄndraFörbindelse());
        

        JPanel knappPanel = new JPanel(new GridLayout());
        knappPanel.add(hittaVägKnapp);
        knappPanel.add(visaFörbindelser);
        knappPanel.add(nyPlatsKnapp);
        knappPanel.add(nyFörbindelseKnapp);
        knappPanel.add(ändraFörbindelseKnapp);
        add(knappPanel, BorderLayout.NORTH);

        JMenuBar menyBar = new JMenuBar();
        JMenu arkiv = new JMenu("Arkiv");
        menyBar.add(arkiv);
        nytt = new JMenuItem("Nytt");
        nytt.addActionListener(new NyKarta());
        arkiv.add(nytt);
        JMenuItem spara = new JMenuItem("Spara");
        arkiv.add(spara);
        sparaSom = new JMenuItem("Spara som");
        arkiv.add(sparaSom);
        JMenuItem avsluta = new JMenuItem("Avsluta");
        arkiv.add(avsluta);
        avsluta.addActionListener(new StängFönster());

        JMenu operationer = new JMenu("Operationer");
        menyBar.add(operationer);
        JMenuItem hittaVäg = new JMenuItem("Hitta väg");
        hittaVäg.addActionListener(new HittaVäg());
        operationer.add(hittaVäg);
        JMenuItem visaFörbindelse = new JMenuItem("Visa förbindelse");
        visaFörbindelse.addActionListener(new VisaFörbindelse());
        operationer.add(visaFörbindelse);
        JMenuItem nyPlats = new JMenuItem("Ny plats");
        nyPlats.addActionListener(new NyPlats());
        operationer.add(nyPlats);
        JMenuItem nyFörbindelse = new JMenuItem("Ny förbindelse");
        nyFörbindelse.addActionListener(new NyFörbindelse());
        operationer.add(nyFörbindelse);
        JMenuItem ändraFörbindelse = new JMenuItem("Ändra förbindelse");
        ändraFörbindelse.addActionListener(new ÄndraFörbindelse());
        operationer.add(ändraFörbindelse);

        setJMenuBar(menyBar);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

    }

    private class NyKarta implements ActionListener {

        public void actionPerformed(ActionEvent ave) {

            JFileChooser filVäljare = new JFileChooser(".");
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Bilder", "PNG", "JPG", "GIF", "BMP");
            filVäljare.setFileFilter(fileFilter);

            int svar = filVäljare.showOpenDialog(Karta.this);
            if (svar != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File f = filVäljare.getSelectedFile();
            String filnamn = f.getName();

            if (bild != null) {
                remove(bild);
            }

            bild = new Bildpanel(filnamn);
            bild.setLayout(null);
            add(bild, BorderLayout.CENTER);
            kartaLaddad = true;

            d1 = null;
            d2 = null;

            g = new ListGraph<>();

            validate();
            repaint();
            pack();

        }
    }

    private class Bildpanel extends JPanel {

        private ImageIcon bildIcon;

        public Bildpanel(String filnamn) {

            bildIcon = new ImageIcon(filnamn);
            setPreferredSize(new Dimension(bildIcon.getIconWidth(), bildIcon.getIconHeight()));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bildIcon.getImage(), 0, 0, this);
        }
    }


    private class HittaVäg implements ActionListener {

        public void actionPerformed(ActionEvent eva) {

            if (d1 == null || d2 == null) {
                JOptionPane.showMessageDialog(null, "Du måste välja två städer först!");
            } else if (GraphMethods.pathExists(d1.getNamn(), d2.getNamn(), g) != true) {
                JOptionPane.showMessageDialog(null, "Det finns ingen koppling mellan de två städerna");
            } else {

                VägFönster vf = new VägFönster();
                JOptionPane.showMessageDialog(null, vf, "Visa väg", JOptionPane.OK_OPTION);

            }
        }
    }

    private class NyPlats implements ActionListener {

        public void actionPerformed(ActionEvent ave) {

            if (kartaLaddad) {
                
                bild.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                bild.addMouseListener(new MusNyPlats());

            }
        }
    }

    private class ÄndraFörbindelse implements ActionListener {

        public void actionPerformed(ActionEvent ave) {

            if (d1 == null || d2 == null) {
                JOptionPane.showMessageDialog(null, "Du måste välja två städer först!");
            } else if (g.getEdgeBetween(d1.getNamn(), d2.getNamn()) == null) {
                JOptionPane.showMessageDialog(null, "Det finns ingen connection mellan " + d1.getNamn() + " och " + d2.getNamn());
            } else {
                Edge<String> förbindelse = g.getEdgeBetween(d1.getNamn(), d2.getNamn());

                FörbindelseFönster förbindFönster = new FörbindelseFönster();
                förbindFönster.text1.setEditable(false);
                förbindFönster.text1.setText(förbindelse.getNamn());
                förbindFönster.text2.setText("" + förbindelse.getVikt());

                int svar = JOptionPane.showOptionDialog(null, förbindFönster, "Visa förbindelse", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                if (svar == JOptionPane.OK_OPTION) {
                    int nyTid = Integer.parseInt(förbindFönster.text2.getText());
                    g.setConnectionWeight(d1.getNamn(), d2.getNamn(), nyTid);
                }
            }
        }
    }

    private class VisaFörbindelse implements ActionListener {

        public void actionPerformed(ActionEvent ave) {

            if (d1 == null || d2 == null) {
                JOptionPane.showMessageDialog(null, "Du måste välja två städer först!");
                return;
            }
            if (g.getEdgeBetween(d1.getNamn(), d2.getNamn()) == null) {
                JOptionPane.showMessageDialog(null, "Det finns ingen connection mellan " + d1.getNamn() + " och " + d2.getNamn());
                return;
            } else {

                Edge<String> förbindelse = g.getEdgeBetween(d1.getNamn(), d2.getNamn());

                FörbindelseFönster förbindFönster = new FörbindelseFönster();
                förbindFönster.text1.setEditable(false);
                förbindFönster.text1.setText(förbindelse.getNamn());
                förbindFönster.text2.setEditable(false);
                förbindFönster.text2.setText("" + förbindelse.getVikt());

                JOptionPane.showOptionDialog(null, förbindFönster, "Visa förbindelse", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            }
        }
    }
    
        public class StängFönster implements ActionListener {
            public void actionPerformed (ActionEvent eva) {
                System.exit(0);
            }
        }


    private class NyFörbindelse implements ActionListener {

        public void actionPerformed(ActionEvent ave) {

            try {
                if (d1 == null || d2 == null) {
                    JOptionPane.showMessageDialog(null, "Du måste välja två städer först!");
                    return;
                } else {

                    FörbindelseFönster f = new FörbindelseFönster();

                    int svar = JOptionPane.showOptionDialog(null, f, "Ny förbindelse", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                    if (svar == JOptionPane.OK_OPTION) {

                        String namn = f.text1.getText();
                        int vikt = Integer.parseInt(f.text2.getText());

                        if (namn.trim().equalsIgnoreCase("") || vikt < 0) {
                            JOptionPane.showMessageDialog(null, "Namnfältet är tomt eller så är vikten mindre än 0");
                            return;
                        }

                        g.connect(d1.getNamn(), d2.getNamn(), namn, vikt);

                        Förbindelse förbind = new Förbindelse(d1, d2);
                        bild.add(förbind);
                        repaint();
                    }
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Felaktik tidsinmatning, kontrollera att fältet innehåller ett positivt värde");
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(null, "Det finns redan en koppling mellan de två platserna");
            } catch (NoSuchElementException ev) {
                JOptionPane.showMessageDialog(null, "En eller båda noder fattas i grafen");
            }
        }
    } 

    private class FörbindelseFönster extends JPanel {

        JTextField text1 = new JTextField(10);
        JTextField text2 = new JTextField(10);

        FörbindelseFönster() {

            JLabel connectText = new JLabel("Förbindelse från " + d1.getNamn() + " till " + d2.getNamn());
            add(connectText);
            JLabel namnLabel = new JLabel("Namn: ");
            add(namnLabel);
            add(text1);
            JLabel tidLabel = new JLabel("Tid: ");
            add(tidLabel);
            add(text2);
        }
    }

    private class StadFönster extends JPanel {

        JTextField text1 = new JTextField(10);

        StadFönster() {

            JLabel namnLabel = new JLabel("Namn: ");
            add(namnLabel);
            add(text1);

        }
    }

    private class VägFönster extends JPanel {
        
        int totalTid;

        VägFönster() {
            
            String vägInfo = "Från " + d1.getNamn() + " till " + d2.getNamn() + "\n";

            JTextArea display = new JTextArea();
            display.setEditable(false);
            add(display);
            JScrollPane scroll = new JScrollPane();
            display.add(scroll);
            
            display.append(vägInfo + "\n");

            
            ArrayList<Edge<String>> väg = GraphMethods.snabbasteVäg(d1.getNamn(), d2.getNamn(), g);

                for (Edge e : väg) {
                   display.append(e.toString() + "\n");
                   totalTid += e.getVikt();
                }
                
                display.append("\n Total tid: " + totalTid);

        }
    }

    public class MusNyPlats extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent mev) {
            try {

                StadFönster f = new StadFönster();
                int svar = JOptionPane.showOptionDialog(null, f, "nyPlats", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                String namn = f.text1.getText();
                if (svar == JOptionPane.CANCEL_OPTION) {
                    bild.removeMouseListener(this);
                    bild.setCursor(null);

                } else if (svar == JOptionPane.OK_OPTION) {

                    if (namn.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Namnet får inte vara tomt!", "Fel", JOptionPane.ERROR_MESSAGE);
                        bild.removeMouseListener(this);
                        bild.setCursor(null);
                        return;
                    }

                    Nod nyNod = new Nod(namn, mev.getX(), mev.getY());

                    g.add(namn); //kolla detta
                    nyNod.addMouseListener(new MusLyss());
                    bild.add(nyNod);
                    repaint();

                    bild.removeMouseListener(this);
                    bild.setCursor(Cursor.getDefaultCursor());
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Stad finns redan");
            }
        }
    }

    public class MusLyss extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent mev) {
            Nod nod = (Nod) mev.getSource();

            if (d1 == null && nod != d2) {
                d1 = nod;
                d1.setVald(true);
            } else if (d2 == null && nod != d1) {
                d2 = nod;
                d2.setVald(true);
            } else if (nod == d1) {
                nod.setVald(false);
                d1 = null;
            } else if (nod == d2) {
                nod.setVald(false);
                d2 = null;
            }
        }
    }

    public static void main(String[] args) {

        new Karta();

    }
}
