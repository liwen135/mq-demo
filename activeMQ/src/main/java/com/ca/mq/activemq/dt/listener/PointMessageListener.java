package com.ca.mq.activemq.dt.listener;

import com.ca.mq.activemq.dt.constant.EventProcess;
import com.ca.mq.activemq.dt.constant.EventType;
import com.ca.mq.activemq.dt.model.Event;
import com.ca.mq.activemq.dt.service.PointEventService;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class PointMessageListener implements MessageListener {

    @Resource
    private PointEventService pointEventService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) message;
                String eventContent = txtMsg.getText();
                System.out.println("队列监听器接收到文本消息：" + eventContent);

                if (!StringUtils.isEmpty(eventContent)) {
                    // 新增事件
                    Event event = new Event();
                    event.setType(EventType.NEW_POINT.getValue());
                    event.setProcess(EventProcess.PUBLISHED.getValue());
                    event.setContent(eventContent);
                    pointEventService.newEvent(event);
                }
            } catch (JMSException e) {
                //业务补偿消息
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("只支持 TextMessage 类型消息！");
        }
    }
}
