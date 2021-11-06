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
public class Courses implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    private String courseName;

    private Integer teacherId;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Courses{" +
        "courseId=" + courseId +
        ", courseName=" + courseName +
        ", teacherId=" + teacherId +
        "}";
    }
}
