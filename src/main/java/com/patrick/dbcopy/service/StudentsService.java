package com.patrick.dbcopy.service;

import com.patrick.dbcopy.bean.Students;
import com.baomidou.mybatisplus.extension.service.IService;
import com.patrick.dbcopy.mapper.StudentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
public interface StudentsService extends IService<Students> {
    boolean insert(Students student);
}
