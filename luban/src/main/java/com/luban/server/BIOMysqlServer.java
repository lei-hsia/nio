package com.luban.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOMysqlServer {

    static byte[]  bytes = new byte[1024]; // static: class-related

    /*
    *   单线程为什么不能处理并发: 因为accept和read两个地方都阻塞;
    *   1st client:
    *         － accept阻塞，server等待连接
    *         － 连上了，read阻塞，server等待输入
    *         － 如果client有输入就OK，没有输入就一直阻塞，直到有输入
    *
    *   如果，让read不阻塞，那么执行完又回到accept，那么server在等待连接的地方阻塞，就可以等待2nd client连接: 单线程达到了并发的目的
    *
    *   实际上NIO就是这个思路，read不阻塞(clientSocket改变模式), 但是:e.g. 过了1分钟client再传数据，也读不到了；
    *   因为之前的clientSocket已经阻塞<=>放弃了CPU; 即使没有放弃CPU之前的client1来的数据也读不到了, 因为socket.accept()
    *   创建了一个全新的socket: client2, 后面read都是client2 read了
    *
    *   2个问题: 1. read阻塞; 2. 再来数据也收不到，因为client丢了
    *   >> 1. 阻塞: clientSocket.xxx(false): 改为非阻塞
    *   >> 2. client丢了: 把前面创建的client放到一个list中;
    * */
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                // accept: 阻塞: 放弃CPU
                Socket clientSocket = serverSocket.accept();

                // clientSocket.xxx(false): 改为非阻塞
                // read也阻塞
                int read = clientSocket.getInputStream().read(bytes); // read=0: 如果没有读到

                if (read > 0) {
                    System.out.printf(new String(bytes));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
