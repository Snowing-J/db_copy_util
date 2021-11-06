package com.patrick.dbcopy.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
public class Teachers implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;

    private String teacherName;

    private String teacherSchool;


    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSchool() {
        return teacherSchool;
    }

    public void setTeacherSchool(String teacherSchool) {
        this.teacherSchool = teacherSchool;
    }

    @Override
    public String toString() {
        return "Teachers{" +
        "teacherId=" + teacherId +
        ", teacherName=" + teacherName +
        ", teacherSchool=" + teacherSchool +
        "}";
    }
}
