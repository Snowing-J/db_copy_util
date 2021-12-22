package com.patrick.dbcopy.controller;


import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.bean.Teachers;
import com.patrick.dbcopy.service.impl.TeachersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.xml.ws.soap.Addressing;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
@Controller
@RequestMapping("/teachers")
public class TeachersController {

    @Autowired
    TeachersServiceImpl teachersServiceImpl;

    @RequestMapping("/insertManyTeachers")
    public String insertManyStudents(){
        System.out.println("插入100条老师信息");
        String insertRes = "-1";
        for(int i = 1; i <= 100; i++ ) {
            Teachers teacher = new Teachers();
            teacher.setTeacherName("老师" + i);
            teacher.setTeacherSchool("第二中学");
            insertRes = teachersServiceImpl.insert(teacher);
            System.out.println("insertRes: " + insertRes);
        }
        return insertRes;
    }
}

