package sample.notification;

import Core.ClientManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author RAVEN
 */
public class Notifications extends javax.swing.JPanel {

    ClientManager mClient;
    public Notifications(ClientManager mClient) {
        this.mClient = mClient;
        initComponents();
        setOpaque(false);

        loadNoti();
    }

    private void loadNoti() {
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/tt7.png")), "tt7", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/fuck.png")), "fuck", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/dogyellow.png")), "dogyellow", mClient));
        
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/neutral.png")), "neutral", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/confusion.png")), "confusion", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/muted.png")), "muted", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/tongue.png")), "tongue", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/happy.png")), "happy", mClient));
        
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/like.png")), "like", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/cool.png")), "cool", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/cry.png")), "cry", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/smile.png")), "smile", mClient));
        panel.add(new Item(new ImageIcon(getClass().getResource("/sample/icon/love.png")), "love", mClient));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(242, 242, 242));
        int header = 10;
        AffineTransform tran = new AffineTransform();
        tran.translate(getWidth() - 25, 5);
        tran.rotate(Math.toRadians(45));
        Path2D p = new Path2D.Double(new RoundRectangle2D.Double(0, 0, 20, 20, 5, 5), tran);
        Area area = new Area();
        area.add(new Area(new RoundRectangle2D.Double(0, header, getWidth(), getHeight() + header, 10, 10)));
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
