package mina;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;


import org.apache.mina.api.AbstractIoHandler;
import org.apache.mina.api.IoFuture;
import org.apache.mina.api.IoSession;
import org.apache.mina.transport.nio.NioTcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple TCP client, write back to the client every received messages.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NioEchoClient {

    static final private Logger LOG = LoggerFactory.getLogger(NioEchoClient.class);


    public static void main(String[] args) {
        LOG.info("starting echo client");

        final NioTcpClient client = new NioTcpClient();
        client.setFilters();
        client.setIoHandler(new AbstractIoHandler() {
            @Override
            public void sessionOpened(final IoSession session) {
                System.out.println("session opened {}" + session);
            }

            @Override
            public void messageReceived(IoSession session, Object message) {
                System.out.println("message received {}" + message);
                if (message instanceof ByteBuffer) {
                    System.out.println("echoing");
                    ByteBuffer buf = (ByteBuffer) message;
                    int a = buf.getInt();
                    byte[] abytes = new byte[a];
                    buf.get(abytes);
                    String str = new String(abytes);
                    System.out.println("包体长度" + a);
                    System.out.println("包体内容" + str);

                }
            }

            @Override
            public void messageSent(IoSession session, Object message) {
                System.out.println("message sent {}" + message);
            }

            @Override
            public void sessionClosed(IoSession session) {
                System.out.println("session closed {}" + session);
            }
        });

        try {
            IoFuture<IoSession> future = client.connect(new InetSocketAddress("localhost", 9999));

            try {
                IoSession session = future.get();
                System.out.println("cannot connect : " + session);
                LOG.info("session connected : {}", session);
            } catch (ExecutionException e) {

                LOG.error("cannot connect : ", e);
            }

            LOG.debug("Running the client for 25 sec");
            Thread.sleep(25000);
        } catch (InterruptedException e) {
        }
    }
}