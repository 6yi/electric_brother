package com.lzheng.elbweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lzheng.elbweb.entities.Msg;
import com.lzheng.elbweb.service.UserService;
import com.lzheng.elbweb.service.workService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ClassName workCon
 * @Author lzheng
 * @Date 2019/6/28 13:06
 * @Version 1.0
 * @Description:
 */

@Controller
public class workCon {
    @Autowired
    private workService service;
    @Autowired
    private UserService uservice;

    @GetMapping("/work/query")
    public void cx(@RequestParam("loudong")String loudong,@RequestParam("sushe")String sushe ,HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("ajax有反应了！！"+loudong+"xx"+sushe);
        String tokne=(String)request.getSession().getAttribute("token");
        String userid=(String)request.getSession().getAttribute("userid");
        if (tokne==null){
            List<String> list=uservice.query("lzheng","13650010553");
            tokne=list.get(0);
            userid=list.get(1);
        }
        String bulid=service.query(loudong,sushe,tokne,userid);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Msg msg=objectMapper.readValue(bulid, Msg.class);
//        System.out.println(msg.getRemainelectric()+"////////////////////////////");
        request.getSession().setAttribute("msg",bulid);
        PrintWriter out = response.getWriter();
        out.print(bulid);
//        return bulid;
    }


    @GetMapping("/work/queryResult")
    public String queryResult(HttpServletRequest request,HttpServletResponse response){

       String result[]= service.queryResult((String) request.getSession().getAttribute("token"),
                (String) request.getSession().getAttribute("userid"));
       result[0]="亲爱的"+request.getSession().getAttribute("username")+"以下是你本学期的成绩噢！";
       request.getSession().setAttribute("result",result);

       return "result";

    }

}