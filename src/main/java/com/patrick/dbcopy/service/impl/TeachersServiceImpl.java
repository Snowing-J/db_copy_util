package com.patrick.dbcopy.service.impl;

import com.patrick.dbcopy.bean.Teachers;
import com.patrick.dbcopy.mapper.TeachersMapper;
import com.patrick.dbcopy.service.TeachersService;
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
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers> implements TeachersService {

    @Autowired
    TeachersMapper teachersMapper;

    @Override
    public String insert(Teachers teacher) {
        return teachersMapper.insert(teacher) == 1 ? "insertOk" : "insertErr";
    }
}
