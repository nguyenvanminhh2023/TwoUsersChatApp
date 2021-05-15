package com.muc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BoxChat extends JPanel implements MessageListener, ActionListener{

    private final ChatClient client;
    private final String login;

    //static DataInputStream din;
    //static DataOutputStream dout;

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    JFrame f1 = new JFrame();
    JScrollPane scrollPane;

    static Socket s;

    static Box vertical = Box.createVerticalBox();

    Boolean typing = false;

    public BoxChat (ChatClient client, String login) {
        this.client = client;
        this.login = login;

        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(0, 102, 255));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                try {
                    client.logoff();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        if (login.equalsIgnoreCase("minh")) {
            ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/minh.png"));
            Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            ImageIcon i6 = new ImageIcon(i5);
            JLabel l2 = new JLabel(i6);
            l2.setBounds(40, 5, 60, 60);
            p1.add(l2);
        } else if (login.equalsIgnoreCase("hieu")) {
            ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/hieu.png"));
            Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            ImageIcon i6 = new ImageIcon(i5);
            JLabel l2 = new JLabel(i6);
            l2.setBounds(40, 5, 60, 60);
            p1.add(l2);
        }

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(290, 20, 30, 30);
        p1.add(l5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(350, 20, 35, 30);
        p1.add(l6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("com/muc/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(410, 20, 13, 25);
        p1.add(l7);

        JLabel l3 = new JLabel();
        if (login.equalsIgnoreCase("minh")) {
            l3.setText("Minh");
        } else if (login.equalsIgnoreCase("hieu")) {
            l3.setText("Hieu");
        }
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110, 35, 100, 20);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText(client.getStatus());
                }
            }
        });

        t.setInitialDelay(2000);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //f1.add(a1);
        scrollPane = new JScrollPane(a1);
        scrollPane.setBounds(5, 75, 440, 570);
        f1.add(scrollPane);


        t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("typing...");
                t.stop();
                typing = true;
            }

            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(320, 655, 123, 40);
        b1.setBackground(new Color(0, 102, 255));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(450, 700);
        if (this.login.equalsIgnoreCase("minh")) {
            f1.setLocation(1100, 200);
        } else if (this.login.equalsIgnoreCase("hieu")) {
            f1.setLocation(400, 200);
        }
        f1.setUndecorated(true);
        f1.setVisible(true);

        client.addMessageListener(this);

        //setLayout(new BorderLayout());
        //add(new JScrollPane(messageList), BorderLayout.CENTER);
        //add(inputField, BorderLayout.SOUTH);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String out = t1.getText();
                    client.msg(login, out);
                    JPanel p2 = formatLabel(out);

                    a1.setLayout(new BorderLayout());

                    JPanel right = new JPanel(new BorderLayout());
                    right.add(p2, BorderLayout.LINE_END);
                    vertical.add(right);
                    vertical.add(Box.createVerticalStrut(15));

                    a1.add(vertical, BorderLayout.PAGE_START);

                    //a1.add(p2);
                    //dout.writeUTF(out);
                    t1.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        if (login.equalsIgnoreCase(fromLogin)) {
            a1.setLayout(new BorderLayout());
            JPanel p2 = formatLabel(msgBody);
            JPanel left = new JPanel(new BorderLayout());
            left.add(p2, BorderLayout.LINE_START);

            vertical.add(left);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            f1.validate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try{
            String out = t1.getText();
            client.msg(login, out);

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);
            a1.add(p2);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(0, 204, 255));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }
}