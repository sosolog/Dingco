package com.dingco.pedal.service;

import com.dingco.pedal.dao.MemberDAO;
import com.dingco.pedal.dao.SendEmailDAO;
import com.dingco.pedal.dto.MailDTO;
import com.dingco.pedal.dto.MemberDTO;
import com.dingco.pedal.session.SessionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    SendEmailDAO dao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    Environment env;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String FROM_ADDRESS = "juhong0196@gmail.com";


    public void fakePasswordCreate(Map<String,String> map) throws Exception {
        String userEmail = map.get("userEmail");
        String userid = map.get("userid");
        String str = getTempPassword();
        String fakePw = passwordEncoder.encode(str);

        MailDTO dto = new MailDTO();

        dto.setAddress(userEmail);
        dto.setTitle(userid + "님의 PEDAL 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. PEDAL 임시비밀번호 안내 관련 이메일 입니다.\n" + "[" + userid + "]" + "님의 임시 비밀번호는 "
                + str + " 입니다.");

        map.put("fakePw", fakePw);

        dao.updateFakePassword(map);
        mailSend(dto);
    }

    @Override
    public void emailValidationCreate(HttpServletRequest request, Map<String, String> map) throws Exception {
        String email1 = map.get("email1");
        String email2 = map.get("email2");
        String userEmail = email1 + "@" + email2;
        String str = getTempPassword();
        String  emailValidation = passwordEncoder.encode(str);

        MailDTO dto = new MailDTO();

        dto.setAddress(userEmail);
        dto.setTitle("PEDAL 이메일 인증 번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. PEDAL 이메일 인증 번호 안내 관련 이메일 입니다.\n" + "이메일 인증 번호는 "
                + str + " 입니다.");

        HttpSession session = request.getSession();
        session.setAttribute("emailValidation", emailValidation);

        mailSend(dto);
    }

    //임시비밀번호 생성장치
    public String getTempPassword() {

        ///////////////////////////////아스키코드 버전(숫자, 대소문자,특수문자포함)///////////////////////////////////
        String  pswd = "";
        StringBuffer sb = new StringBuffer();
        StringBuffer sc = new StringBuffer("!@#$%^&*-=?~");  // 특수문자 모음, {}[] 같은 비호감문자는 뺌

// 대문자 4개를 임의 발생
        sb.append((char)((Math.random() * 26)+65));  // 첫글자는 대문자, 첫글자부터 특수문자 나오면 안 이쁨

        for( int i = 0; i<3; i++) {
            sb.append((char)((Math.random() * 26)+65));  // 아스키번호 65(A) 부터 26글자 중에서 택일
        }

// 소문자 4개를 임의발생
        for( int i = 0; i<4; i++) {
            sb.append((char)((Math.random() * 26)+97)); // 아스키번호 97(a) 부터 26글자 중에서 택일
        }


// 숫자 2개를 임의 발생
        for( int i = 0; i<2; i++) {
            sb.append((char)((Math.random() * 10)+48)); //아스키번호 48(1) 부터 10글자 중에서 택일
        }


// 특수문자를 두개  발생시켜 랜덤하게 중간에 끼워 넣는다
        sb.setCharAt(((int)(Math.random()*3)+1), sc.charAt((int)(Math.random()*sc.length()-1))); //대문자3개중 하나
        sb.setCharAt(((int)(Math.random()*4)+4), sc.charAt((int)(Math.random()*sc.length()-1))); //소문자4개중 하나

        pswd = sb.toString();
        ///////////////////////////////아스키코드 버전(숫자, 대소문자,특수문자포함)///////////////////////////////////



/*        ///////////////////////////////일반 버전(숫자, 대문자포함)///////////////////////////////////
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        ///////////////////////////////일반 버전(숫자, 대문자포함)///////////////////////////////////*/


        return pswd;
    }

    public void mailSend(MailDTO dto){


        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");

                message.setTo(dto.getAddress());
                message.setFrom(FROM_ADDRESS);
                message.setSubject(dto.getTitle());
                message.setText(dto.getMessage());


                //Mail에 img 삽입
//                ClassPathResource resource = new ClassPathResource("img 주소/img 이름.png");
//                message.addInline("img", resource.getFile());

            }
        };

        try{
            mailSender.send(preparator);
        } catch (MailException e){
            e.printStackTrace();
        }

    }


    //이메일과 회원정보가 맞는지 체크하는 서비스
    public boolean userEmailCheck(Map<String,String> map) throws Exception{
        return dao.userEmailCheck(map);
    }
}
