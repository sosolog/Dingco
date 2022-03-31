package com.dingco.pedal.controller;

import com.dingco.pedal.dto.DeptDTO;
import com.dingco.pedal.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DeptController {

    @Autowired
    DeptService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String selectAll(Model model, HttpServletRequest request){
        List<DeptDTO> result = service.selectAll();
        model.addAttribute("result", result);
        return "hello";
    }

}
