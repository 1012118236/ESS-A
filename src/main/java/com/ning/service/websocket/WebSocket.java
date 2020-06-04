package com.ning.service.websocket;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/8 15:22
 */
@ServerEndpoint(value = "/websocket/{username}")
@Component
public class WebSocket {

    private static StringRedisTemplate redisTemplate;


    @Autowired
    private void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate){
        WebSocket.redisTemplate = stringRedisTemplate;
    };

    private static Logger log = LoggerFactory.getLogger(WebSocket.class);


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //保存当前登录用户名
    private String username;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String username) {
        try {
            this.session = session;
            this.username = username;
            WebSocket _this = getcurrentWenSocket(this.username);
            if(_this!=null){
                sendMessage("您已有连接信息，不能重复连接");
                return;
            }
            webSocketSet.add(this);
            addOnlineCount();
            log.info("用户："+this.username+"连接成功，当前在线人数为："+this.getOnLineCount());
            sendMessage("连接成功!");
        } catch (Exception e) {
            log.info("websocket连接异常,连接用户："+this.username);
        }
    }



    /**
     * 连接关闭后的回调
     */
    @OnClose
    public void onClose(Session session){
        this.session = session;
        boolean b = webSocketSet.remove(this);
        if(b && this.getOnLineCount() > 0){
            redisTemplate.delete(this.username);
            subOnlineCount();
        }
        log.info("用户："+this.username +"连接关闭!当前在线人数为："+this.getOnLineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message,Session session){
        try {
            WebSocket _this = null;
            for (WebSocket webSocket : webSocketSet) {
                if(webSocket.session.getId() == session.getId()){
                    _this = webSocket;
                }
                if(_this == null){
                    this.sendMessage("未连接不能发送消息");
                    return;
                }
                this.sendMessage("来自服务端的消息："+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("websocket发送消息异常：登陆人ID="+this.username);
        }
    }

    /**
     * 错误回调
     */
    @OnError
    public void onError(Session session,Throwable error){
        log.error("websocket发生错误，当前登陆人： "+this.username);
    }

    /**
     * 在线人数减一
     */
    public void subOnlineCount() {
        int onlineCount = this.getOnLineCount();
        if(onlineCount<=0){
            this.setOnLineCount(0);
        }else{
            this.setOnLineCount(--onlineCount);
        }

    }


    /**
     * 在线人数加一
     */
    private void addOnlineCount() {
        int onlineCount = this.getOnLineCount();
        this.setOnLineCount(++onlineCount);
    }

    /**
     * 给当前用户发送消息
     * @param message
     */
    //public void sendMessage(String message) throws IOException {
     //   this.session.getBasicRemote().sendText(message);
   //}

    /**
     * 给当前用户发送消息
     * @param message
     */
    public void sendMessage(Object message) throws IOException {
        this.session.getBasicRemote().sendText(JSONObject.toJSONString(message));
    }

    /**
     * 给指定用户发送消息
     * @param username
     * @param message
     */
    public static void sendMessage(String username,Object message){
        try {
            if(username == null ||  message==null){
                return;
            }
            WebSocket webSocket = getcurrentWenSocket(username);
            if(webSocket == null){
                return;
            }
            webSocket.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 给指定人群发送消息
     * @param usernameList
     * @param message
     */
    public static void sendMessageList(List<String> usernameList,Object message){
        try {
            if(usernameList == null || usernameList.size() <1 || message==null){
                return;
            }
            for (String username : usernameList) {
                WebSocket webSocket = getcurrentWenSocket(username);
                if(webSocket == null){
                    return;
                }
                webSocket.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 给所有人发送消息
     * @param message
     */
    public static void sendMessageList(Object message){
        try {
            if(webSocketSet == null || webSocketSet.size() <1 || message==null){
                return;
            }
            for (WebSocket webSocket : webSocketSet) {
                if(webSocket == null){
                    return;
                }
                webSocket.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据当前登陆用户ID获取他的websocket对象
     * @param username
     * @return
     */
    public static WebSocket getcurrentWenSocket(String username) {
        if(username == null || webSocketSet == null || webSocketSet.size() < 1){
            return null;
        }
        Iterator<WebSocket> iterator = webSocketSet.iterator();
        while (iterator.hasNext()) {
            WebSocket webSocket = iterator.next();
            String username1 = webSocket.username;
            if(username.equals(username1)){
                return webSocket;
            }
        }
        return  null;
    }

    /**
     * 获取在线人数
     * @return
     */
    public int getOnLineCount(){
        String onlineCountStr = redisTemplate.opsForValue().get("onlineCount");
        int onLineCount = 0;
        if(!org.springframework.util.StringUtils.isEmpty(onlineCountStr)){
            try {
                onLineCount = Integer.parseInt(onlineCountStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                return onLineCount;
            }

        }
        return onLineCount;
    }

    /**
     * 更新在线人数
     * @param value
     */
    private void setOnLineCount(Integer value){
        redisTemplate.opsForValue().set("onlineCount",value==null?"0":value.toString());
        redisTemplate.expire("onlineCount",1800l,TimeUnit.SECONDS);
    }
}
