package com.jgybzx.dao.company;

import com.jgybzx.domain.company.Company;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/2 17:43
 * @Description: ${TODO}
 * @version:
 */
public interface  CompanyDao {
    public List<Company> finAll();

    //根据id查询企业信息
    Company findById(String id);

    //新增
    void save(Company company);

    //修改
    void update(Company company);

    //删除
    void delete(String id);

    //查询总数
    @Select("select count(*) from ss_company")
    int count();

    //查询分页数据
    List<Company> findPage(@Param("startIndex") int startIndex,@Param("pageSizeInt") int pageSizeInt);
}
