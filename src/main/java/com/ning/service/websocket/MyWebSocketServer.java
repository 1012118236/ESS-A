package com.ning.service.websocket;

import com.alibaba.fastjson.JSONObject;
import com.ning.service.utils.WebSocketMapUtil;
import org.apache.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/8 14:15
 */
@ServerEndpoint(value = "/websocket")
public class MyWebSocketServer {

    private Logger logger = Logger.getLogger(MyWebSocketServer.class);

    private Session session;

    /**
     * 连接建立后触发的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        logger.info("onOpen"+session.getId());
        WebSocketMapUtil.put(session.getId(),this);
    }

    /**
     * 连接关闭后触发的方法
     */
    @OnClose
    public void onClose(){
        WebSocketMapUtil.remove(session.getId());
        logger.info("======= onClose:"+session.getId()+" ======");
    }

    @OnMessage
    public void onMessage(String params,Session session) throws IOException {
        MyWebSocketServer myWebSocketServer = WebSocketMapUtil.get(session.getId());
        logger.info("收到来自："+session.getId()+"的消息"+params);
        String result = "收到来自"+session.getId()+"的消息"+params;
        myWebSocketServer.sendMessage(1,"成功",result);
    }

    public void sendMessage(int status, String message, Object datas) throws IOException {
        JSONObject result = new JSONObject();
        result.put("status",status);
        result.put("message",message);
        result.put("datas",datas);
        this.session.getBasicRemote().sendText(result.toString());

    }

}
