package sample.notification;

import Core.ClientManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author RAVEN
 */
public class NotificationsIcon extends javax.swing.JPanel {

    ClientManager client;
    public NotificationsIcon(Icon icon, ClientManager client) {
        initComponents(); 
        setOpaque(false);
        this.client = client;
        panel.setLayout(new MigLayout("inset 0, fillx, wrap", "[fill]"));
        loadIcon(icon);
    }

    private void loadIcon(Icon icon) {
        panel.add(new Item(icon,"a", client));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p2.jpg")), "Sophie", "answered to your comment", "1 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p3.jpg")), "Dara", "answered to your comment", "2 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p1.jpg")), "Steve", "answered to your comment", "a day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p2.jpg")), "Sophie", "answered to your comment", "1 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p3.jpg")), "Dara", "answered to your comment", "2 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p1.jpg")), "Steve", "answered to your comment", "a day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p2.jpg")), "Sophie", "answered to your comment", "1 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p3.jpg")), "Dara", "answered to your comment", "2 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p1.jpg")), "Steve", "answered to your comment", "a day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p2.jpg")), "Sophie", "answered to your comment", "1 day ago"));
//        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/notification/p3.jpg")), "Dara", "answered to your comment", "2 day ago"));

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        int header = 10;
        AffineTransform tran = new AffineTransform();
        tran.translate(getWidth() - 25, 5);
        tran.rotate(Math.toRadians(45));
        Path2D p = new Path2D.Double(new RoundRectangle2D.Double(0, 0, 20, 20, 5, 5), tran);
        Area area = new Area(p);
        area.add(new Area(new RoundRectangle2D.Double(0, header, getWidth(), getHeight() - header, 10, 10)));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 10, 10, 10));

        panel.setOpaque(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 175, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
