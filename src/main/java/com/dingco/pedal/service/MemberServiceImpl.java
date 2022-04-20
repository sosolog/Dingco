package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dto.MemberDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // 민욱 : 회원 조회
    @Override
    public MemberDTO selectByNaverId(String naver_idx) throws Exception {
        return dao.selectByNaverId(naver_idx);
    }

    // 민욱 : 회원 추가
    @Override
    public int memberAdd(MemberDTO memberDTO) throws Exception {
        return dao.memberAdd(memberDTO);
    }

    // 회원가입 아이디 유효성 체크
    @Override
    public int idDuplicateCheck(String userid) throws Exception {
        return dao.idDuplicateCheck(userid);
    }

    // 민욱: 소셜 아이디 중복 체크
    @Override
    public int socialMemberIdCheck(String userid) throws Exception {
        return dao.socialMemberIdCheck(userid);
    }

    // 민욱: 소셜 인덱스 중복 체크
    @Override
    public int socialMemberNaverIdxCheck(String naver_idx) throws Exception {
        return dao.socialMemberNaverIdxCheck(naver_idx);
    }

    // 명지 : 마이페이지 정보 가져오기
    @Override
    public MemberDTO selectMypageInfo(int m_idx) throws Exception {
        return dao.selectMypageInfo(m_idx);
    }

    // 명지 : 마이페이지 정보 수정
    @Override
    public int updateMypage(MemberDTO memberDTO) throws Exception {
        return dao.updateMypage(memberDTO);
    }

    // 명지 : 아이디 찾기
    @Override
    public String findUserId(Map<String, Object> map) throws Exception {
        return dao.findUserId(map);
    }

    // 주황 : 아이디로 로그인 찾기
    @Override
    public MemberDTO selectByLoginId(String userid, String passwd) throws Exception {

        return dao.selectByLoginId(userid)
                .filter(m -> passwordEncoder.matches(passwd, m.getPasswd()))
                .orElse(null);

    }


    @Override
    public int socialMemberAdd(MemberDTO memberDTO) throws Exception {
        return dao.socialMemberAdd(memberDTO);
    }

    // 명지 : 카카오 로그인
    @Override
    public String getKaKaoAccessToken (String code){
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ee5887b0e2e8cce297b9421bb915bc70"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9090/kakaologin"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Override
    public MemberDTO selectByKakaoId(String token){
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        MemberDTO memberDTO = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();

            memberDTO = dao.selectByKakaoId(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberDTO;
    }

    @Override
    public Map<String, Object> createKakaoUser (String token){
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        //access_token을 이용하여 사용자 정보 조회
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("id").getAsString();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            map.put("kakao_idx", id);
            map.put("username", nickname);
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    // 명지 : 카카오 로그인 회원 추가 (최종)
    @Override
    public int memberKakaoAdd(Map<String, Object> memberDTO) throws Exception {
        return dao.memberKakaoAdd(memberDTO);
    }

  
    @Override
    public void memberGoogleAdd(Map<String, Object> map) throws Exception {
        map.get("google_idx");
        dao.memberGoogleAdd(map);
    }

    @Override
    public MemberDTO selectByGoogleIdx(String google_idx) throws Exception {
        return dao.selectByGoogleIdx(google_idx);


    }

}
