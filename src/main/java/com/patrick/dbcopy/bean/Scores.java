package com.patrick.dbcopy.bean;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
public class Scores implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer studentId;

    private Integer courseId;

    private BigDecimal score;


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Scores{" +
        "studentId=" + studentId +
        ", courseId=" + courseId +
        ", score=" + score +
        "}";
    }
}
