package com.mallplus.user.service.impl;

import com.mallplus.user.mapper.SysDeptMapper;
import com.mallplus.user.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
}
