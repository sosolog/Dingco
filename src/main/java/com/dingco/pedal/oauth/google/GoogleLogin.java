package com.dingco.pedal.oauth.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleLogin {


    private static String GOOGLE_SNS_CLIENT_ID;
    private static String GOOGLE_SNS_CALLBACK_URL;
    private static String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${sns.google.client.id}")
    public void setGoogleSnsClientId(String value){
        GOOGLE_SNS_CLIENT_ID = value;
    }
    @Value("${sns.google.callback.url}")
    public void setGoogleSnsCallbackUrl(String value){
        GOOGLE_SNS_CALLBACK_URL = value;
    }
    @Value("${sns.google.client.secret}")
    public void setGoogleSnsClientSecret(String value){
        GOOGLE_SNS_CLIENT_SECRET = value;
    }


    //code를 이용하여 post 방식으로 access_token 가져오기
    public static JsonNode getAccessToken(String autorize_code) {

        final String RequestUrl = "https://www.googleapis.com/oauth2/v4/token";

        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        // BasicNameValuePair = POST방식에서 사용되는 데이터 전달 방식, Request 메시지를 바디에 포함하여 전달
        // 관련 사이트 - https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=220341345946

        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", GOOGLE_SNS_CLIENT_ID));
        postParams.add(new BasicNameValuePair("client_secret", GOOGLE_SNS_CLIENT_SECRET));
        postParams.add(new BasicNameValuePair("redirect_uri", GOOGLE_SNS_CALLBACK_URL)); // 리다이렉트 URI
        postParams.add(new BasicNameValuePair("code", autorize_code)); // 로그인 과정중 얻은 code 값

        final HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        final HttpPost post = new HttpPost(RequestUrl); // post방식을 이용하여 RequestUrl 넘기기
        JsonNode returnNode = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams) ); // RequestUrl을 넘기면서 ?와 &로 쿼리스트링을 만들어주는 과정

            final HttpResponse  response = client.execute(post);
            // post.setEntity를 통해 결국 https://www.googleapis.com/oauth2/v4/token?grant_type=authorization_code&~~~ 로 요청

            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent()); // response를 통해 얻은 토큰정보를 Json으로 저장


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }

        return returnNode;

    }

    // access_token을 이용하여 get 방식으로 회원정보 가져오기
    public static JsonNode getGoogleUserInfo(String accessToken) {

        final String RequestUrl = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses%2Cnames";

        final HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        final HttpGet get = new HttpGet(RequestUrl); // get방식을 이용하여 RequestUrl 넘기기

        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + accessToken); //access_token은 header를 통해 정보를 전달

        try {
            final HttpResponse response = client.execute(get);

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());// response를 통해 얻은 회원정보를 Json으로 저장



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }
        return returnNode;

    }

}
