package com.dingco.pedal.dao;

import com.dingco.pedal.util.FileName;
import com.dingco.pedal.util.TableDir;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileDAO {
    private final SqlSession session;

    public int uploadImages(List<FileName> list){
        return session.insert("com.config.FileMapper.imageUpload", list);
    }

    public int uploadMoreImages(List<FileName> list, int i_idx){
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("idx", i_idx);
        return session.insert("com.config.FileMapper.uploadMoreImages", map);
    }

    public List<FileName> showImages(HashMap<String, Object> map){
        return session.selectList("com.config.FileMapper.showImages", map);
    }

    public int deleteImage(int img_idx) {
        return session.delete("com.config.FileMapper.deleteImage", img_idx);
    }
    public int deleteAllImagesInPost(int board_idx, TableDir tableDir) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("board_idx", board_idx);
        map.put("tableDir", tableDir);
        return session.delete("com.config.FileMapper.deleteAllImagesInPost", map);
    }
}
