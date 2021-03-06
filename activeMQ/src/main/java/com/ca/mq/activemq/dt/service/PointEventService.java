package com.ca.mq.activemq.dt.service;

import com.alibaba.fastjson.JSON;
import com.ca.mq.activemq.dt.constant.EventProcess;
import com.ca.mq.activemq.dt.constant.EventType;
import com.ca.mq.activemq.dt.dao.PointEventDao;
import com.ca.mq.activemq.dt.exception.BusinessException;
import com.ca.mq.activemq.dt.model.Event;
import com.ca.mq.activemq.dt.model.Point;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

@Service
public class PointEventService {

    @Resource
    private PointEventDao pointEventDao;

    @Resource
    private PointService pointService;

    public int newEvent(Event event) {
        if (event != null) {
            return pointEventDao.insert(event);
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

    public List<Event> getPublishedEventList() {
        return pointEventDao.getByProcess(EventProcess.PUBLISHED.getValue());
    }

    public void executeEvent(Event event) {
        if (event != null) {
            String eventProcess = event.getProcess();
            if ((EventProcess.PUBLISHED.getValue().equals(eventProcess))
                    && (EventType.NEW_POINT.getValue().equals(event.getType()))) {
                Point point = JSON.parseObject(event.getContent(), Point.class);
                pointService.newPoint(point);

                event.setProcess(EventProcess.PROCESSED.getValue());
                pointEventDao.updateProcess(event);
            }
        }
    }
}
