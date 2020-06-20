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
                    } else if (read == -1){
                        channelList.remove(socketChannel);
                    }
                }

                SocketChannel accept = serverSocket.accept();
                if (accept != null) {

                    accept.configureBlocking(false); // 等待数据传输的socket设置为非阻塞
                    channelList.add(accept);
                }

            }
            /*
            *   效率低下:
            *   1. for循环: 不写在java代码中，让OS循环
            *   2. for循环: 大部分循环都是无意义的: e.g. 10000个, 1000个activeconn, 9000个无用的
            *   1> selector交给OS; epoll循环效率实际上并不比selector高，只是循环的次数通过 #events减少了
            *   selector/epoll 就是为了解决 channelList和for循环这个问题
            * */


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
