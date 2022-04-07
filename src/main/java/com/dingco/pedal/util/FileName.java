package com.dingco.pedal.util;

import lombok.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class FileName {
    private int fileIdx;
    private final TableDir tableDir;      // 서버 저장 하위 디렉토리명
    private final String fileName;       // 사용자 업로드파일명
    private final String serverFileName;    // 서버 저장파일명
}
