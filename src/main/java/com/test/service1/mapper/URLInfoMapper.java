package com.test.service1.mapper;

import com.test.service1.info.ConvertURLInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

//@MapperScan
@Repository
@Mapper
public interface URLInfoMapper {

//    @Insert({"INSERT INTO t_convert_url(id,url_str,comment,shorten_url) " ,
//            "VALUES (#{id},#{urlStr},#{comment},#{shrotenURL})"})
    int insertInfo(ConvertURLInfo info);

//    @Update("update t_convert_url set url_str=#{urlStr},comment=#{comment},shorten_url=#{shrotenURL} where id=#{id}")
    int updateInfo(ConvertURLInfo info);

    List<ConvertURLInfo> selectById(String id);

}
