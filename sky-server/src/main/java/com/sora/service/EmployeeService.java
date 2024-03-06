package com.sora.service;

import com.sora.dto.EmployeeDTO;
import com.sora.dto.EmployeeLoginDTO;
import com.sora.dto.EmployeePageQueryDTO;
import com.sora.entity.Employee;
import com.sora.result.PageResult;

public interface EmployeeService {

     

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void state(Integer status, long id);

    Employee getById(Long id);

    void updata(EmployeeDTO employeeDTO);
}
