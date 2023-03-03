package com.self.start.startspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.start.startspring.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author szy123
 * @version 1.0
 * @date 2023-02-21 11:07:12
 * @description
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

    List<Test> getAll();

    Test getById(Long id);
}
