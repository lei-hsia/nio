package com.luban.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class TomcatServer {
    static ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8080);
            serverSocket.bind(socketAddress);

            serverSocket.configureBlocking(false); // 等待连接的socket设置为非阻塞

            while (true) {

                for (SocketChannel socketChannel : channelList) {
                    int read = socketChannel.read(byteBuffer);
                    if (read > 0) {
                        System.out.println("read------"+read);
                        byteBuffer.flip();
                        byte[] bytes = new byte[read];
                        byteBuffer.get(bytes);
                        String contents = new String(bytes);
                        System.out.println(contents);
                        byteBuffer.flip();
                    }
                }

                SocketChannel accept = serverSocket.accept();
                if (accept != null) {

                    accept.configureBlocking(false); // 等待数据传输的socket设置为非阻塞
                    channelList.add(accept);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
