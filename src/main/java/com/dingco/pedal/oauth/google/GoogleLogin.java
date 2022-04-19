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

    public static JsonNode getAccessToken(String autorize_code) {

        final String RequestUrl = "https://oauth2.googleapis.com/token";

        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", GOOGLE_SNS_CLIENT_ID));
        postParams.add(new BasicNameValuePair("client_secret", GOOGLE_SNS_CLIENT_SECRET));
        postParams.add(new BasicNameValuePair("redirect_uri", GOOGLE_SNS_CALLBACK_URL)); // 리다이렉트 URI
        postParams.add(new BasicNameValuePair("code", autorize_code)); // 로그인 과정중 얻은 code 값

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);
        JsonNode returnNode = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams) );
            final HttpResponse  response = client.execute(post);


            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());


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

    public static JsonNode getGoogleUserInfo(String autorize_code) {

        final String RequestUrl = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses%2Cnames";

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet get = new HttpGet(RequestUrl);

        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + autorize_code);

        try {
            final HttpResponse response = client.execute(get);
            final int responseCode = response.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());



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
