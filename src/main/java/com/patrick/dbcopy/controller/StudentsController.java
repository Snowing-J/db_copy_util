package com.patrick.dbcopy.controller;


import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.service.StudentsService;
import com.patrick.dbcopy.service.impl.StudentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {}, allowCredentials = "true")
@RequestMapping("/student")
public class StudentsController {

    @Autowired
    private StudentsServiceImpl studentsServiceImpl;

    @RequestMapping("/insertStudent")
    public void insertStudent(){
        System.out.println("插入学生信息");
        Students student = new Students();
        student.setStudentId(20210101);
        student.setStudentClass(0101);
        System.out.println("student: " + student);
        boolean insertRes = studentsServiceImpl.insert(student);
        System.out.println("insertRes: " + insertRes);
    }

    @RequestMapping("/insertManyStudents")
    public void insertManyStudents(){
        System.out.println("插入10000条学生信息");
        for(int i = 1; i <= 10000; i++ ) {
            Students student = new Students();
            student.setStudentId(20200000 + i);
            int grade = 1001;
            grade = i / 50 > 1 ? grade++ : grade;
            student.setStudentClass(grade);
            student.setStudentName("学" + i);
            LocalDateTime brith = LocalDateTime.now();
            student.setStudentBirthday(brith);
            student.setStudentMajor("语文");
            System.out.println("student: " + student);
            boolean insertRes = studentsServiceImpl.insert(student);
            System.out.println("insertRes: " + insertRes);
        }
    }
}

