package com.ca.mq.activemq.dt.service;

import com.ca.mq.activemq.dt.dao.PointDao;
import com.ca.mq.activemq.dt.exception.BusinessException;
import com.ca.mq.activemq.dt.model.Point;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    public String newPoint(Point point) {
        if (point != null) {
            return dao.insert(point);
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

}
