package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 This class handles all of the communication from the peers. It keeps track of
 the peers and populates the GUI.

 @author Kody
 */
public class IMThread extends Thread
{

   private Vector<Peer> pList = new Vector<Peer>();
   private JList peerlist;
   private JTextArea msgArea;
   private DatagramSocket dsocket;

   /**
    Constructor to create a IMThread instance

    @param _peerlist listPeer element on gui
    @param _msgArea message element on the gui
    @throws java.net.SocketException
    */
   public IMThread(JList _peerlist, JTextArea _msgArea) //get the reference of the UI
         throws java.net.SocketException
   {
      peerlist = _peerlist;
      msgArea = _msgArea;
   }

   /**
    method to update the JList when adding or removing a peer.
    */
   private void updatePeerList()
   {
      Runnable updateList = new Runnable()
      {
         public void run()
         {
            peerlist.setListData(pList);
         }
      };
      SwingUtilities.invokeLater(updateList);
   }

   /**
    Opens a udp port so that packets can be received and sent. Also handles
    incoming udp packets.
    */
   @Override
   public void run()
   {
      try
      {
         dsocket = new DatagramSocket(Message.IMTHREAD_PORT);
         byte[] buffer = new byte[2048];

         DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

         while (true)
         {
            dsocket.receive(packet);
            byte[] packetArray = packet.getData();

            if (packetArray[0] == Message.ADD_PEER)
            {
               pAddUser(packet);
            }

            if (packetArray[0] == Message.DEL_PEER)
            {
               pDeleteUser(packet);
            }

            if (packetArray[0] == Message.MSG2PEER)
            {
               pSendPeer(packet);
            }

            if (packetArray[0] == Message.ACK)
            {
               pACK(packet);
            }

            // Reset the length of the packet before reusing it.
            packet.setLength(buffer.length);
            updatePeerList();
         }

      }
      catch (IOException e)
      {
         System.out.println("IMThread Exception: " + e);
      }

   }

   private void pAddUser(DatagramPacket packet)
   {
      try
      {
         Peer p = new Peer(packet.getData(), packet.getLength());
         System.out.println("IMThread Got Peer Up Data: " + p.toString());
         boolean has = false;
         for (int i = 0; i < pList.size(); i++)
         {
            Peer temp = pList.elementAt(i);
            if (p.getAddr().equals(temp.getAddr()))
            {
               has = true;
            }
         }
         if (!has)
         {
            System.out.println("IMThread: Added Peer to list: " + p.toString());
            pList.add(p);
         }
         else
         {
            System.out.println("IMThread: Peer already in list: " + p.toString());
         }
      }
      catch (Exception e)
      {
         System.out.println("IMThread Exception: " + e);
      }

   }

   private void pDeleteUser(DatagramPacket packet)
   {
      try
      {
         Peer p = new Peer(packet.getData(), packet.getLength());
         System.out.println("IMThread Got Peer Down Data: " + p.toString());
         for (int i = 0; i < pList.size(); i++)
         {
            if (pList.elementAt(i).getAddr().equals(p.getAddr()))
            {
               pList.remove(i);
               System.out.println("IMThread: Added Peer to list: " + p.toString());
            }
         }
      }
      catch (Exception e)
      {
         System.out.println("IMThread Exception: " + e);
      }
   }

   private void pSendPeer(DatagramPacket packet)
   {
      byte[] packetArray = packet.getData();
      try
      {
         byte[] message = new byte[packet.getLength() - 1];
         for (int i = 0; i < message.length; i++)
         {
            message[i] = packetArray[i + 1];
         }
         msgArea.append(new String(message) + "\n");
         sendMessage(buildACK(packet.getAddress()));
      }
      catch (Exception e)
      {
         System.out.println("IMThread Exception: " + e);
      }
   }

   private void pACK(DatagramPacket packet)
   {

   }

   /**
   Builds the ACK packet
   @param ip ip to send to 
   @return built ACK udp packet
   */
   private DatagramPacket buildACK(InetAddress ip)
   {
      byte[] message = Message.ACK;
      DatagramPacket packet = new DatagramPacket(message, message.length,
            ip, Message.IMTHREAD_PORT);
      return packet;
   }

   /**
   Sends a packet to the peer
   @param packet built udp packet
   */
   public void sendMessage(DatagramPacket packet)
   {
      try
      {
         System.out.println("IMThread: Sending message.");
         dsocket.send(packet);
      }
      catch (Exception e)
      {
         System.out.println("IMThread: " + e);
      }

   }

}
