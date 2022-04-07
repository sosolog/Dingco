package com.dingco.pedal.dao;

import com.dingco.pedal.util.FileName;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileDAO {
    private final SqlSession session;

    public int uploadImages(List<FileName> list){
        return session.insert("com.config.FileMapper.imageUpload", list);
    }
}
