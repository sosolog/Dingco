package com.dingco.pedal.controller;

import com.dingco.pedal.annotation.Login;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.service.MemberService;
import com.dingco.pedal.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.UUID;

@Slf4j
@Controller
public class UploadController {

    String fileDir = "/Users/john_park/Desktop/Project/Dingco/src/main/resources/static/upload/";


    // 서버에 저장하는 파일 : 서부 내부에서 관리하는 파일은 유일한 이름을 생성하는 UUID를 사용해서 충돌을 피함(+확장자)
    String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString(); // UUID
        String ext = extractExt(originalFilename); // 확장자
        return uuid + "." + ext; // 서버에 저장하는 파일명 : UUID + 확장자

        // 예시>> 51041c62-8634-4274-801d-61a7d994edb.png
    }

    // 사용자의 이미지 파일의 확장자 추출(.png/.jpg ...)
    String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // 서버의 저장소 위치
    public String getFullPath(String filename) {
        return fileDir + filename;
    }


}
