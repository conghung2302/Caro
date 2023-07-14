package Interface;

import Core.ActionType;
import java.util.Observable;
import java.util.Observer;
import Core.ClientManager;
import Core.Result;
import Core.ResultCode;

import DropIcon.glasspanepopup.DefaultLayoutCallBack;
import DropIcon.glasspanepopup.DefaultOption;
import DropIcon.glasspanepopup.GlassPanePopup;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.LayoutCallback;
import sample.notification.Notifications;
import sample.notification.NotificationsIcon;
import sweet_alert.Message;
import sweet_alert.MessageNewGame;

public class GameMatch extends javax.swing.JFrame implements Observer, ActionListener {

//    private MicTester micTester;
    public ClientManager mClientManager;
    String mMaPhong = "";
    String mTenPhong = "";
    ListRoom mListRoom;
    int mSoNguoi = 1;
    int N = 100;  // row
    int M = 100;  // column
    int traceX = 0, traceY = 0;
    boolean win;
    boolean startGame;
    String turn = "NO";
    Font f4;
    int cellSize;
    String tick[][] = new String[N + 2][M + 2];
    public final JButton map[][] = new JButton[N + 2][M + 2];
    String mytick = "";
    GameMatch roomchat;
    ImageIcon imgX;
    ImageIcon imgO;

    boolean running = true;
    Color myColor;
    Color yourColor;

    public GameMatch(ListRoom listRoom, ClientManager cm, String maPhong, String tenPhong, int soNguoi, int row, int col) {
        this.N = row;
        this.M = col;
//        cellSize = Math.max(N, M);

        if (N <= 10 && M <= 7) {
            imgX = new ImageIcon(getClass().getResource("/Assets/X/X44.png"));
            imgO = new ImageIcon(getClass().getResource("/Assets/O/O44z.png"));
        } else if (N <= 14 && M <= 11) {
            imgX = new ImageIcon(getClass().getResource("/Assets/X/X33z.png"));
            imgO = new ImageIcon(getClass().getResource("/Assets/O/O33z.png"));
        } else if (N > 14 && N <= 20 && M <= 13) {
            imgX = new ImageIcon(getClass().getResource("/Assets/X/X22.png"));
            imgO = new ImageIcon(getClass().getResource("/Assets/O/O22z.png"));
        } else {
            imgX = new ImageIcon(getClass().getResource("/Assets/X/X22.png"));
            imgO = new ImageIcon(getClass().getResource("/Assets/O/O22z.png"));
        }
//        
//        img = new ImageIcon(getClass().getResource("/Assets/X/X1.png"));
        f4 = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 20);
        initComponents();
        GlassPanePopup.install(this);
        resetComponentRigt();

        System.out.println("ROW" + N);
        System.out.println("COL" + M);
//        micTester = new GameMatch.MicTester();
//        micTester.start();
        lbTurn.setText("Chờ đối thủ !!");
        startGame = true;
        client1.setText(cm.mNickname);
        btnNewGame.setVisible(false);
        init();
        mListRoom = listRoom;
        mClientManager = cm;
        mMaPhong = maPhong;
        mTenPhong = tenPhong;
        mSoNguoi = soNguoi;
        mClientManager.addObserver(this);
        roomchat = this;
        txtMessToSend.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    KeyPressGame();
                }
            }
        });
        myColor = Color.BLACK;
        yourColor = Color.BLACK;
    }

    void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundFile)));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int cntTick = 0;

    public void init() {

        boardGame.removeAll();
        boardGame.setLayout(new GridLayout(N, M));
        for (int i = 0; i <= N + 1; i++) {
            for (int j = 0; j <= M + 1; j++) {
                tick[i][j] = "";
                map[i][j] = new JButton("");
                map[i][j].setIcon(null);
                map[i][j].setActionCommand(i + "-" + j);
                map[i][j].setFont(f4);
                map[i][j].setBackground(Color.white);
                map[i][j].addActionListener(this);

//                map[i][j].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/X/X22.png"))); // NOI18N
//                map[i][j].setIcon(imgX); 
//                map[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
//                tick[i][j] = false;
            }

        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {

                boardGame.add(map[i][j]);
                tick[i][j] = "";
            }
        }

//        try {
//            clientvoice = new Client(ip.getText().trim(), 1111);
//            clientvoice.start(); //connect to specified server at specified port
//        } catch (Exception ex) { //connection failed
//            JOptionPane.showMessageDialog(rootPane, ex, "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        boardGame = new javax.swing.JPanel();
        infor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        client1 = new javax.swing.JLabel();
        client2 = new javax.swing.JLabel();
        lbTick1 = new javax.swing.JLabel();
        lbTick2 = new javax.swing.JLabel();
        lbvs = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnNewGame = new javax.swing.JButton();
        lbTurn = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMessToSend = new javax.swing.JTextArea();
        ScrollChat = new javax.swing.JScrollPane();
        txtNoiDungChat = new javax.swing.JTextPane();
        cmdIcon = new Swing.ButtonBorder();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        boardGame.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout boardGameLayout = new javax.swing.GroupLayout(boardGame);
        boardGame.setLayout(boardGameLayout);
        boardGameLayout.setHorizontalGroup(
            boardGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 939, Short.MAX_VALUE)
        );
        boardGameLayout.setVerticalGroup(
            boardGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        client1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        client1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        client1.setText("Client 1");

        client2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        client2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        client2.setText("Client 2");

        lbTick1.setFont(new java.awt.Font("SansSerif", 1, 100)); // NOI18N
        lbTick1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTick1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/X/lbX2.png"))); // NOI18N

        lbTick2.setFont(new java.awt.Font("SansSerif", 1, 100)); // NOI18N
        lbTick2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTick2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/O/lbO2.png"))); // NOI18N

        lbvs.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbvs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/images/vs5.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbTick1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(client1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbvs)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbTick2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(client2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(client1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTick1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(client2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbvs, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTick2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );

        jPanel4.setBackground(new java.awt.Color(102, 255, 102));

        btnNewGame.setBackground(new java.awt.Color(255, 102, 102));
        btnNewGame.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnNewGame.setText("new Game");
        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

        lbTurn.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lbTurn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTurn.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(255, 255, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Thoát");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(lbTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnNewGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNewGame)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        txtMessToSend.setColumns(20);
        txtMessToSend.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMessToSend.setRows(5);
        jScrollPane2.setViewportView(txtMessToSend);

        txtNoiDungChat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ScrollChat.setViewportView(txtNoiDungChat);

        cmdIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sample/icon/iconBTN.png"))); // NOI18N
        cmdIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdIconActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inforLayout = new javax.swing.GroupLayout(infor);
        infor.setLayout(inforLayout);
        inforLayout.setHorizontalGroup(
            inforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inforLayout.createSequentialGroup()
                        .addGroup(inforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScrollChat)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inforLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        inforLayout.setVerticalGroup(
            inforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inforLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollChat, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(inforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(boardGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setTitle("Nickname: " + mClientManager.mNickname + "      Tên phòng: " + mTenPhong + "     Mã Phòng: " + mMaPhong + "     Số người: " + mSoNguoi);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        CloseWindow();
    }//GEN-LAST:event_formWindowClosing

    public void CloseWindow() {
        try {
            playSound("/Assets/sounds/out.wav");
        } catch (Exception e) {
        }
        mClientManager.LeaveRoom();
        mClientManager.deleteObserver(this);
        mClientManager.addObserver(mListRoom);
        mListRoom.setVisible(true);
    }

    public void KeyPressGame() {
        if (txtMessToSend.getText().trim().length() == 0) {
            return;
        }
        mClientManager.SendMess(txtMessToSend.getText().trim());
        txtMessToSend.setText("");
    }
    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed

        try {
            playSound("/Assets/sounds/click.wav");
        } catch (Exception e) {
        }
        mClientManager.NewGame();
        btnNewGame.setVisible(false);
    }//GEN-LAST:event_btnNewGameActionPerformed

    private void cmdIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdIconActionPerformed
        GlassPanePopup.showPopup(new Notifications(mClientManager), new DefaultOption() {
            @Override
            public float opacity() {
                return 0;
            }

            @Override
            public LayoutCallback getLayoutCallBack(Component parent) {
                return new DefaultLayoutCallBack(parent) {
                    @Override
                    public void correctBounds(ComponentWrapper cw) {
                        if (parent.isVisible()) {
                            Point pl = parent.getLocationOnScreen();
                            Point bl = cmdIcon.getLocationOnScreen();
                            int x = bl.x - pl.x;
                            int y = bl.y - pl.y - 300;
                            y += (1f + getAnimate()) * 10f;
                            cw.setBounds(x - cw.getWidth() + cmdIcon.getWidth(), y + cmdIcon.getHeight(), cw.getWidth(), cw.getHeight());
                        } else {
                            super.correctBounds(cw);
                        }
                    }
                };
            }

            @Override
            public String getLayout(Component parent, float animate) {
                return null;
            }
        });
    }//GEN-LAST:event_cmdIconActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            playSound("/Assets/sounds/out.wav");
        } catch (Exception e) {
        }
        CloseWindow();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    boolean voiceSelect = true;

    Thread thIcon;

    public void RunIcon(Icon icon, String nameSender) {
        thIcon = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Aaaaaa");
                GlassPanePopup.showPopup(new NotificationsIcon(icon, mClientManager), new DefaultOption() {
                    @Override
                    public float opacity() {
                        return 0;
                    }

                    @Override
                    public LayoutCallback getLayoutCallBack(Component parent) {
                        return new DefaultLayoutCallBack(parent) {
                            @Override
                            public void correctBounds(ComponentWrapper cw) {
                                if (nameSender.equals(mClientManager.mNickname)) {
                                    if (parent.isVisible()) {
                                        Point pl = parent.getLocationOnScreen();
                                        Point bl = client1.getLocationOnScreen();
                                        int x = bl.x - pl.x;
                                        int y = bl.y - pl.y;
                                        y += (1f - getAnimate()) * 10f;
                                        cw.setBounds(x - cw.getWidth() + client1.getWidth(), y + client1.getHeight(), cw.getWidth(), cw.getHeight());

                                    } else {
                                        super.correctBounds(cw);
                                    }
                                } else {
                                    if (parent.isVisible()) {
                                        Point pl = parent.getLocationOnScreen();
                                        Point bl = client2.getLocationOnScreen();
                                        int x = bl.x - pl.x;
                                        int y = bl.y - pl.y;
                                        y += (1f - getAnimate()) * 10f;
                                        cw.setBounds(x - cw.getWidth() + client2.getWidth(), y + client2.getHeight(), cw.getWidth(), cw.getHeight());

                                    } else {
                                        super.correctBounds(cw);
                                    }
                                }

                            }

                        };
                    }

                    @Override
                    public String getLayout(Component parent, float animate) {
                        return null;
                    }
                });

                try {
                    Thread.sleep(2000);
                    GlassPanePopup.closePopupLast();
                } catch (InterruptedException ex) {

                }
            }

        });
        thIcon.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        Result result = (Result) arg;

        if (result.mResultCode.equals(ResultCode.ERROR)) {
            JOptionPane.showMessageDialog(null, result.mContent, "Thất bại", JOptionPane.ERROR_MESSAGE);
            return;
        }
        switch (result.mActionType) {
            case ActionType.SEND_MESSAGE: {
                String[] lines = result.mContent.split(";", -1);
                String sender = lines[0];
                String messContent = lines[1];
                messContent = messContent.replaceAll("<br>", "\n");
                if (sender.equals(mClientManager.mNickname)) {
                    appendToPane(txtNoiDungChat, "You: ", myColor);
                    appendToPane(txtNoiDungChat, messContent + "\n", Color.BLACK);

//                    txtNoiDungChat.append("You: " + messContent + "\n");
                } else {
//                    txtNoiDungChat.append(sender + ": " + messContent + "\n");
                    appendToPane(txtNoiDungChat, sender + ": ", yourColor);
                    appendToPane(txtNoiDungChat, messContent + "\n", Color.BLACK);
                }
                break;
            }

            case ActionType.UPDATE_NUMBER_USER: {
                String soNguoi = result.mContent;
                mSoNguoi = Integer.parseInt(soNguoi);
                this.setTitle("Nickname: " + mClientManager.mNickname + "      Tên phòng: " + mTenPhong + "     Mã Phòng: " + mMaPhong + "     Số người: " + mSoNguoi);
                break;
            }
            case ActionType.NOTIFY_JUST_JOIN_ROOM: {
                System.out.println("JOIN ROOM");
                String userJoin = result.mContent;
                client2.setText(userJoin);
                appendToPane(txtNoiDungChat, userJoin + ": vừa tham gia phòng\n", Color.BLACK);
                resetGame();
                break;
            }
            case ActionType.NOTIFY_JUST_LEAVE_ROOM: {
                String userJoin = result.mContent;
                try {
                    playSound("/Assets/sounds/winning.wav");
                } catch (Exception e) {
                }
                lbTurn.setText("GAME OVER");
                if (startGame) {
                    Message mess = new Message(this, true, "You Win", "Đối thủ đã thoát trận");
                    mess.showAlert();
                }

                appendToPane(txtNoiDungChat, "(" + userJoin + ") vừa rời phòng\n", yourColor);
                startGame = false;
                break;
            }
            case ActionType.SEND_ICON: {
                String[] lines = result.mContent.split(";", -1);
                System.out.println("Name" + lines[0]);

                switch (lines[1]) {
                    case "tt7": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/tt7.png")), lines[0]);
                        break;
                    }
                    case "cool": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/cool.png")), lines[0]);
                        break;
                    }
                    case "dogyellow": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/dogyellow.png")), lines[0]);
                        break;
                    }
                    case "fuck": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/fuck.png")), lines[0]);
                        break;
                    }
                    case "like": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/like.png")), lines[0]);
                        break;
                    }

                    case "neutral": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/neutral.png")), lines[0]);
                        break;
                    }
                    case "confusion": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/confusion.png")), lines[0]);
                        break;
                    }
                    case "muted": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/muted.png")), lines[0]);
                        break;
                    }
                    case "tongue": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/tongue.png")), lines[0]);
                        break;
                    }
                    case "happy": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/happy.png")), lines[0]);
                        break;
                    }

                    case "cry": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/cry.png")), lines[0]);
                        break;
                    }
                    case "smile": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/smile.png")), lines[0]);
                        break;
                    }
                    case "love": {
                        RunIcon(new ImageIcon(getClass().getResource("/sample/icon/love.png")), lines[0]);
                        break;
                    }

                    default:
                        throw new AssertionError();
                }
                System.out.println("Icon: " + lines[1]);
                break;
            }
            case ActionType.SEND_TICK: {
                cntTick++;
                String[] mess = result.mContent.split(";");
                String[] locations = mess[1].split("-");

                int i = Integer.valueOf(locations[0]);
                int j = Integer.valueOf(locations[1]);

//                if (mess[0].equals("O")) {
//                    map[i][j].setForeground(Color.BLUE);
//                } else {
//                    map[i][j].setForeground(Color.RED);
//                }
                tick[i][j] = mess[0];
//                map[i][j].setText(mess[0]);
                map[i][j].setIcon(imgO);

                if (mess[0].equals("X")) {
                    map[i][j].setIcon(imgX);
                } else {
                    map[i][j].setIcon(imgO);
                }

                turn = mess[2];
                setTurn();
                if (!mytick.equals(mess[0])) {
                    map[i][j].setBackground(Color.YELLOW);

                    map[traceX][traceY].setBackground(new Button().getBackground());

                    traceX = i;
                    traceY = j;

                    try {
                        playSound("/Assets/sounds/point.wav");
                        map[i][j].setBackground(Color.yellow);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                System.out.println("ChECK WIN: " + checkWin(i, j));

                if (checkWinNew(i, j)) {
                    lbTurn.setText("GAME OVER");
                    startGame = false;
                    btnNewGame.setVisible(true);
                    if (mess[0].equals(mytick)) {
                        Message me = new Message(this, true, "You Win!", "Congratulations...");
                        try {
                            playSound("/Assets/sounds/winning.wav");
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        me.showAlert();

                    } else {
                        Message me = new Message(this, true, "You Lose", "");
                        try {
                            playSound("/Assets/sounds/lose.wav");
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        me.showAlert();
                    }
                }
                if (cntTick == M * N) {
                    startGame = false;
                    btnNewGame.setVisible(true);
                    Message me = new Message(this, true, "HÒA CỜ", "");
                    try {
                        playSound("/Assets/sounds/lose.wav");
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    me.showAlert();
                }

                break;
            }
            case ActionType.GET_OPPONENT: {
                String name = result.mContent;
                client2.setText(name);
                break;
            }

            case ActionType.TYPE_TICK: {
                String[] line = result.mContent.split(";");
                mytick = line[0];
                lbvs.setIcon(new ImageIcon(getClass().getResource("/Assets/images/vs5.png")));
                if (mytick.equals("X")) {
                    lbTick1.setIcon(new ImageIcon(getClass().getResource("/Assets/X/lbX2.png")));
//                    lbTick1.setForeground(Color.BLUE);
                    myColor = Color.RED;
                    yourColor = Color.BLUE;
                    lbTick2.setIcon(new ImageIcon(getClass().getResource("/Assets/O/lbO2.png")));
//                    lbTick2.setForeground(Color.RED);
                } else {
                    lbTick1.setIcon(new ImageIcon(getClass().getResource("/Assets/O/lbO2.png")));
                    lbTick1.setForeground(Color.RED);
                    myColor = Color.BLUE;
                    yourColor = Color.RED;
                    lbTick2.setIcon(new ImageIcon(getClass().getResource("/Assets/X/lbX2.png")));
//                    lbTick2.setForeground(Color.BLUE);
                }
                turn = line[1];
                setTurn();
                break;
            }
            case ActionType.NEW_GAME: {
                String[] line = result.mContent.split(";");
                MessageNewGame mess = new MessageNewGame(this, true, "Yêu Cầu Chơi Lại", "Bấm OK để tiếp tục", this, mMaPhong);
                mess.showAlert();
                break;
            }
            case ActionType.ACCEP_NEWGAME: {
                System.out.println("Accept New Game");
                resetGame();
                break;
            }
            case ActionType.REFUSE_NEWGAME: {
                btnNewGame.setVisible(true);
                Message mess = new Message(this, true, "Đối Thủ Từ Chối", "Bấm OK để tiếp tục");
                mess.showAlert();
                break;
            }
        }
    }
    Random generate = new Random();

    public void resetGame() {
        btnNewGame.setVisible(false);
        startGame = true;
        mytick = "";
        init();
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void setTurn() {
        if (turn.equals("YES")) {
            lbTurn.setText("Đến Lượt bạn");
        } else {
            lbTurn.setText("Lượt Đối Thủ");
        }
    }

    public boolean checkWin(int i, int j) {
        int d = 0, k = i, h;
        // kiểm tra hàng
        while (map[k][j].getText().equals(map[i][j].getText())) {
            d++;
            k++;
        }
        k = i - 1;
        while (map[k][j].getText().equals(map[i][j].getText())) {
            d++;
            k--;
        }
        if (d > 4) {
            return true;
        }
        d = 0;
        h = j;
        // kiểm tra cột
        while (map[i][h].getText().equals(map[i][j].getText())) {
            d++;
            h++;
        }
        h = j - 1;
        while (map[i][h].getText().equals(map[i][j].getText())) {
            d++;
            h--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;
        while (map[i][j].getText().equals(map[h][k].getText())) {
            d++;
            h++;
            k++;
        }
        h = i - 1;
        k = j - 1;
        while (map[i][j].getText().equals(map[h][k].getText())) {
            d++;
            h--;
            k--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;
        while (map[i][j].getText().equals(map[h][k].getText())) {
            d++;
            h++;
            k--;
        }
        h = i - 1;
        k = j + 1;
        while (map[i][j].getText().equals(map[h][k].getText())) {
            d++;
            h--;
            k++;
        }
        if (d > 4) {
            return true;
        }
        System.out.println("D: " + d);
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;
    }

    public boolean checkWinNew(int i, int j) {
        int d = 0, k = i, h;
        // kiểm tra hàng
        while (tick[k][j].equals(tick[i][j])) {
            d++;
            k++;
        }
        k = i - 1;
        while (tick[k][j].equals(tick[i][j])) {
            d++;
            k--;
        }
        if (d > 4) {
            return true;
        }
        d = 0;
        h = j;
        // kiểm tra cột
        while (tick[i][h].equals(tick[i][j])) {
            d++;
            h++;
        }
        h = j - 1;
        while (tick[i][h].equals(tick[i][j])) {
            d++;
            h--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;
        while (tick[i][j].equals(tick[h][k])) {
            d++;
            h++;
            k++;
        }
        h = i - 1;
        k = j - 1;
        while (tick[i][j].equals(tick[h][k])) {
            d++;
            h--;
            k--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;
        while (tick[i][j].equals(tick[h][k])) {
            d++;
            h++;
            k--;
        }
        h = i - 1;
        k = j + 1;
        while (tick[i][j].equals(tick[h][k])) {
            d++;
            h--;
            k++;
        }
        if (d > 4) {
            return true;
        }
        System.out.println("D: " + d);
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollChat;
    private javax.swing.JPanel boardGame;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JLabel client1;
    private javax.swing.JLabel client2;
    private Swing.ButtonBorder cmdIcon;
    private javax.swing.JPanel infor;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTick1;
    private javax.swing.JLabel lbTick2;
    private javax.swing.JLabel lbTurn;
    private javax.swing.JLabel lbvs;
    private javax.swing.JTextArea txtMessToSend;
    private javax.swing.JTextPane txtNoiDungChat;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("TIck");
        String[] location = e.getActionCommand().split("-");
        int x = Integer.parseInt(location[0]);
        int y = Integer.parseInt(location[1]);
        System.out.println(x + "  " + y);
        System.out.println("Turn: " + turn);
        System.out.println("StartGame: " + startGame);
        if (turn != null && startGame && tick[x][y].equals("")) {
            if (turn.equals("YES")) {
//
//                if (mytick.equals("O")) {
//                    map[x][y].setForeground(Color.RED);
//                } else {
//                    map[x][y].setForeground(Color.BLUE);
//                }

//                                map[x][y].setText(mytick);
                try {
                    playSound("/Assets/sounds/wing.wav");
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameMatch.class.getName()).log(Level.SEVERE, null, ex);
                }

                mClientManager.SendTick(mytick, e.getActionCommand());
            }
        }
    }

    private void resetComponentRigt() {
        lbTick1.setIcon(null);
        lbTick2.setIcon(null);
        lbvs.setIcon(null);
    }

}
