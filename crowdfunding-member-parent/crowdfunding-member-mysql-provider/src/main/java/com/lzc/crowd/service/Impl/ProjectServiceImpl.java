package com.lzc.crowd.service.Impl;

import com.lzc.crowd.mapper.ProjectPOMapper;
import com.lzc.crowd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectPOMapper projectPOMapper;

}
