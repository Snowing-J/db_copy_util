package com.patrick.dbcopy.service.impl;

import com.patrick.dbcopy.bean.Students;
import com.patrick.dbcopy.mapper.StudentsMapper;
import com.patrick.dbcopy.service.StudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author patrick
 * @since 2021-11-03
 */
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements StudentsService {

    @Autowired
    private StudentsMapper studentsMapper;

    @Override
    public boolean insert(Students student) {
        return studentsMapper.insert(student) == 1 ? true : false;
    }
}
