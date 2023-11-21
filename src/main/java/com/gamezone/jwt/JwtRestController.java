package com.gamezone.jwt;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
public class JwtRestController {
    protected Logger logger = LoggerFactory.getLogger(JwtRestController.class);
    @Value("${expire.minutes}")
    private   long expireMinutes;

    @PostMapping("token")
    public String token(@RequestBody String json) throws ParseException {
        System.out.println("expireMinutes:" + expireMinutes );
        String token = JwtUtil.encode(json, expireMinutes);
        return  token;
    }

    @PostMapping("tokenNoExpire")
    public String tokenNoExpire(@RequestBody String json) throws ParseException {
        String token = JwtUtil.encode(json, -1);
        return  token;
    }


    @RequestMapping(value = "/tokenWithExpire/{expireMinutes}" , produces ={"application/json"})
    public String tokenWithExpire(@RequestBody String json ,  @PathVariable(value = "expireMinutes") long expireMinutes ) throws ParseException {
        String token = JwtUtil.encode(json, expireMinutes);
        return  token;
    }
    @RequestMapping(value = "/check_token" , produces ={"application/json"})
    public ResponseEntity<String> check_token(@RequestBody String object){
        System.out.println("check_token: " + object);
        try {
            Map<String ,Object> map = JwtUtil.decode(object);
            JSONObject json = new JSONObject(map);
            return  new ResponseEntity<String>(json.toString(), HttpStatus.OK);
        }catch (Exception e){
            System.out.println("rr: " + e.getMessage());
            return  new ResponseEntity<String>("{\"error\" : \"invalid_token\" , \"error_decription\": \"" + e.getMessage() + "\"}" , HttpStatus.BAD_REQUEST);
        }
    }

}
