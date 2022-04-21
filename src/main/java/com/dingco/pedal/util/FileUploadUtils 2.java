package com.dingco.pedal.util;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
public class FileUploadUtils {
    private final String uploadBaseDir; // 서버 내 저장할 base 저장소 path
    private final TableDir tableDir; // 테이블별 저장할 하위 디렉토리 이름

    public void uploadFiles(List<MultipartFile> fileList, List<FileName> fileNames) throws IOException {
        for (MultipartFile file : fileList) {
            // 사용자의 이미지 파일을 들고 옴 => img.png
            String originalFilename = file.getOriginalFilename();

            // 서버에 저장하는 파일명 세팅(같은 이름으로)
            String storeFileName = createStoreFileName(originalFilename);

            // 파일명 리스트 저장...
            fileNames.add(new FileName(TableDir.INQUIRY, originalFilename, storeFileName));

            // 전달받은 데이터(파라미터)를 저장소에 저장해준다.
            file.transferTo(new File(getFullPath(storeFileName)));
        }
    }

    // 서버에 저장하는 파일 : 서부 내부에서 관리하는 파일은 유일한 이름을 생성하는 UUID를 사용해서 충돌을 피함(+확장자)
    public String createStoreFileName (String originalFilename){
        String uuid = UUID.randomUUID().toString(); // UUID
        String ext = extractExt(originalFilename); // 확장자
        return uuid + "." + ext; // 서버에 저장하는 파일명 : UUID + 확장자
        // 예시>> 51041c62-8634-4274-801d-61a7d994edb.png
    }

    // 사용자의 이미지 파일의 확장자 추출(.png/.jpg ...)
    public String extractExt (String originalFilename){
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // 서버의 저장소 위치
    public String getFullPath (String filename){
        return uploadBaseDir + tableDir.getDirectoryName() + "\\" + filename;
    }
}
