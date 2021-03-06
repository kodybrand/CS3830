package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 This is the GUI class for the client. It handles events on the form and also
 sends and receives packets from the tracker.

 @author Kody
 */
public class IMessenger extends javax.swing.JFrame
{

   DatagramSocket socket;
   IMThread im;

   /**
    Creates new form IMessenger
    */
   public IMessenger()
   {
      initComponents();
      createSocket();
      try
      {
         im = new IMThread(listPeers, txtMessageArea);
         im.start();
         Thread.sleep(50);
         sendComingUp();
      }
      catch (SocketException | InterruptedException e)
      {
         System.out.println("IMessenger: " + e);
      }

   }

   /**
    This method is called from within the constructor to initialize the form.
    WARNING: Do NOT modify this code. The content of this method is always
    regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents()
   {

      jScrollPane1 = new javax.swing.JScrollPane();
      listPeers = new javax.swing.JList();
      jScrollPane2 = new javax.swing.JScrollPane();
      txtMessageArea = new javax.swing.JTextArea();
      btnSend = new javax.swing.JButton();
      txtMessage = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter()
      {
         public void windowClosing(java.awt.event.WindowEvent evt)
         {
            closing(evt);
         }
      });

      jScrollPane1.setViewportView(listPeers);

      txtMessageArea.setEditable(false);
      txtMessageArea.setColumns(20);
      txtMessageArea.setRows(5);
      jScrollPane2.setViewportView(txtMessageArea);

      btnSend.setText("Send");
      btnSend.setFocusCycleRoot(true);
      btnSend.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            btnSendActionPerformed(evt);
         }
      });

      txtMessage.setFocusCycleRoot(true);

      jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel1.setText("Peers");

      jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel2.setText("Received Messages");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(btnSend)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(0, 0, Short.MAX_VALUE))
               .addComponent(jScrollPane2)
               .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGap(17, 17, 17)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jScrollPane2)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(btnSend)
                     .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
               .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   /**
    Sends the come down message to the tracker on window closing

    @param evt Window closing event.
    */
   private void closing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_closing
   {//GEN-HEADEREND:event_closing
      sendComingDown();
   }//GEN-LAST:event_closing

   /**
    When the send button is clicked it sends starts the sending process

    @param evt button click
    */
   private void btnSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSendActionPerformed
   {//GEN-HEADEREND:event_btnSendActionPerformed
      if (txtMessage.getText().length() > 0 && !listPeers.isSelectionEmpty())
      {
         im.sendMessage(buildMessagePacket(txtMessage.getText()));
      }
      else
      {
         System.out.println("IMessenger: Must have a message to send"
               + " and selected user!");
      }
   }//GEN-LAST:event_btnSendActionPerformed

   /**
    @param args the command line arguments
    */
   public static void main(String args[])
   {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try
      {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
         {
            if ("Nimbus".equals(info.getName()))
            {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      }
      catch (ClassNotFoundException ex)
      {
         java.util.logging.Logger.getLogger(IMessenger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      catch (InstantiationException ex)
      {
         java.util.logging.Logger.getLogger(IMessenger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      catch (IllegalAccessException ex)
      {
         java.util.logging.Logger.getLogger(IMessenger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      catch (javax.swing.UnsupportedLookAndFeelException ex)
      {
         java.util.logging.Logger.getLogger(IMessenger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable()
      {
         public void run()
         {
            new IMessenger().setVisible(true);
         }
      });
   }

   /**
    Created the udp socket to talk with tracker
    */
   private void createSocket()
   {
      try
      {
         socket = new DatagramSocket(Message.TRACKER_PORT);
      }
      catch (SocketException e)
      {
         System.out.println("IMessenger: " + e);
      }
   }

   /**
    Sends the coming up message to the tracker
    */
   private void sendComingUp()
   {
      try
      {
         System.out.println("IMessenger: Going to try to send I'm up");
         DatagramPacket packet = upMessage();
         socket.send(packet);
         System.out.println("IMessenger: Sent I'm up");
      }
      catch (IOException ex)
      {
         System.out.println("IMessenger Exception: " + ex);
      }
   }

   /**
    Sends the coming down message to the tracker
    */
   private void sendComingDown()
   {
      try
      {
         System.out.println("IMessenger: Going to try to send going down.");
         DatagramPacket packet = downMessage();
         socket.send(packet);
         System.out.println("IMessenger: Sent going down.");
      }
      catch (Exception ex)
      {
         System.out.println("IMessenger Exception: " + ex);
      }
   }

   /**
    This created and returns the coming up message udp packet

    @return udp packet
    */
   private DatagramPacket upMessage()
   {
      try
      {
         byte[] payload = new byte[1 + Message.USER_NAME.length()];
         payload[0] = Message.PEER_UP;
         byte[] user = Message.USER_NAME.getBytes();
         System.arraycopy(user, 0, payload, 1, Message.USER_NAME.length());
         DatagramPacket udp = new DatagramPacket(payload, payload.length,
               InetAddress.getByName(Message.TRACKER), Message.TRACKER_PORT);
         return udp;
      }
      catch (UnknownHostException e)
      {
         System.out.println("IMessenger: " + e);
         return null;
      }

   }

   /**
    This creates and returns the coming down message udp packet

    @return udp packet
    */
   private DatagramPacket downMessage()
   {
      try
      {
         byte[] payload = new byte[1 + Message.USER_NAME.length()];
         payload[0] = Message.PEER_DOWN;
         byte[] user = Message.USER_NAME.getBytes();
         System.arraycopy(user, 0, payload, 1, Message.USER_NAME.length());
         DatagramPacket udp = new DatagramPacket(payload, payload.length,
               InetAddress.getByName(Message.TRACKER), Message.TRACKER_PORT);
         return udp;
      }
      catch (UnknownHostException e)
      {
         System.out.println("IMessenger: " + e);
         return null;
      }

   }

   /**
    Builds and returns the message to peer packet

    @param message message to be sent
    @return udp packet
    */
   private DatagramPacket buildMessagePacket(String message)
   {

      Peer peer = (Peer) listPeers.getSelectedValue();
      byte[] payload = new byte[1 + 1 + Message.USER_NAME.length() + message.length()];
      payload[0] = Message.MSG2PEER;
      String msg = Message.USER_NAME + ":" + message;
      byte[] messageBody = msg.getBytes();
      System.arraycopy(messageBody, 0, payload, 1, msg.length());
      DatagramPacket packet = new DatagramPacket(payload, payload.length,
            peer.getAddr(),
            Message.IMTHREAD_PORT);
      return packet;

   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnSend;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JList listPeers;
   private javax.swing.JTextField txtMessage;
   private javax.swing.JTextArea txtMessageArea;
   // End of variables declaration//GEN-END:variables
}
