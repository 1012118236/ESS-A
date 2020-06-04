package com.ning.service.utils;

import com.ning.service.websocket.MyWebSocketServer;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/8 14:18
 */
public class WebSocketMapUtil {
    public static ConcurrentMap<String, MyWebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, MyWebSocketServer myWebSocketServer){
        webSocketMap.put(key, myWebSocketServer);
    }

    public static MyWebSocketServer get(String key){
        return webSocketMap.get(key);
    }

    public static void remove(String key){
        webSocketMap.remove(key);
    }

    public static Collection<MyWebSocketServer> getValues(){
        return webSocketMap.values();
    }
}
