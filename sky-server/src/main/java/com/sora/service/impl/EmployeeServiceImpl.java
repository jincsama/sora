package com.sora.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sora.constant.MessageConstant;
import com.sora.constant.PasswordConstant;
import com.sora.constant.StatusConstant;
import com.sora.context.BaseContext;
import com.sora.dto.EmployeeDTO;
import com.sora.dto.EmployeeLoginDTO;
import com.sora.dto.EmployeePageQueryDTO;
import com.sora.entity.Employee;
import com.sora.exception.AccountLockedException;
import com.sora.exception.AccountNotFoundException;
import com.sora.exception.PasswordErrorException;
import com.sora.mapper.EmployeeMapper;
import com.sora.result.PageResult;
import com.sora.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工添加
     *
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //拷贝属性
        BeanUtils.copyProperties(employeeDTO, employee);
        //状态
        employee.setStatus(StatusConstant.ENABLE);
        //默认密码
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);
//        //创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //创建人id和修改人id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);

    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //开始分页
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> result = page.getResult();

        return new PageResult(total,result);
    }

    /**
     * 启用/禁用 员工账号
     * @param status
     * @param id
     */
    @Override
    public void state(Integer status, long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id).build();

        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("******");

        return employee;
    }

    /**
     * 更新员工数据
     * @param employeeDTO
     */
    @Override
    public void updata(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

}
