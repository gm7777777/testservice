package com.test.service1.service;

import com.test.service1.info.ConvertURLInfo;
import com.test.service1.mapper.URLInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLServ {
    @Autowired
    URLInfoMapper urlInfoMapper;

    public int insert(ConvertURLInfo info){
     return urlInfoMapper.insertInfo(info);
    }

    public int update(ConvertURLInfo info){
        return urlInfoMapper.updateInfo(info);
    }

    public ConvertURLInfo selectById(String Id){
        return urlInfoMapper.selectById(Id).get(0);
    }
}
