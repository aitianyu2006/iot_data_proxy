package mina;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.api.AbstractIoHandler;
import org.apache.mina.api.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.nio.NioTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Int;

/**
 * A simple TCP server, write back to the client every received messages.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NioEchoServer {

    static final private Logger LOG = LoggerFactory.getLogger(NioEchoServer.class);

    public static void main(final String[] args) {
        System.out.printf("starting echo server");

        final NioTcpServer acceptor = new NioTcpServer();

        // create the filter chain for this service
        acceptor.setFilters(new LoggingFilter("LoggingFilter1"));

        List list = new ArrayList();

        acceptor.setIoHandler(new AbstractIoHandler() {
            @Override
            public void sessionOpened(final IoSession session) {
                System.out.printf("session opened {}" + session);
                list.add(session);

                final String welcomeStr = "headerbody";
                final ByteBuffer bf = ByteBuffer.allocate(welcomeStr.length() + 4);

                bf.putInt(10);
                bf.put(welcomeStr.getBytes());
                bf.flip();
                session.write(bf);
            }

            @Override
            public void messageReceived(IoSession session, Object message) {
                if (message instanceof ByteBuffer) {
//                    System.out.printf("echoing");
//                    int a = 1;
//                    final ByteBuffer bf = ByteBuffer.allocate(4);
//                    bf.put(new Integer(a).byteValue());
//                    bf.flip();
                    //session.write(message);
                }
            }
        });
        //try {
        final SocketAddress address = new InetSocketAddress(9999);
        acceptor.bind(address);

        while (true) {
            if (!list.isEmpty()) {
                System.out.printf("发给客户端包\n");
                int a = 11;
                final ByteBuffer bf = ByteBuffer.allocate("12345678901".getBytes().length + 4);
                bf.putInt(a);
                bf.put("12345678901".getBytes());
                bf.flip();
                ((IoSession) list.get(0)).write(bf);
                System.out.printf("发给客户端包\n");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // LOG.debug("Running the server for 25 sec");
        //Thread.sleep(50000);
        //LOG.debug("Unbinding the TCP port");
        //acceptor.unbind();
//        } catch (final InterruptedException e) {
//            LOG.error("Interrupted exception", e);
//        }
    }
}
