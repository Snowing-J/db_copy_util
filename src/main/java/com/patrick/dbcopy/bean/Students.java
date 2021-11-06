package com.patrick.dbcopy.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
public class Students implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    private String studentName;

    private Integer studentClass;

    private String studentGender;

    private String studentMajor;

    private LocalDateTime studentBirthday;


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public LocalDateTime getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(LocalDateTime studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    @Override
    public String toString() {
        return "Students{" +
        "studentId=" + studentId +
        ", studentName=" + studentName +
        ", studentClass=" + studentClass +
        ", studentGender=" + studentGender +
        ", studentMajor=" + studentMajor +
        ", studentBirthday=" + studentBirthday +
        "}";
    }
}
