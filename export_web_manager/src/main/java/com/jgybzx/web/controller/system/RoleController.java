package com.jgybzx.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Role;
import com.jgybzx.service.system.RoleService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: guojy
 * @date: 2020/1/6 9:18
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "查询所有")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size) {

        PageInfo byPage = roleService.findByPage(page, size, companyId);
        request.setAttribute("page", byPage);
        return "system/role/role-list";

    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", name = "跳转添加页面")
    public String toAdd() {
        return "system/role/role-add";
    }

    /**
     * 根据是是否存在id，进行新增或修改
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit", name = "新增或修改")
    public String edit(Role role) {
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtils.isEmpty(role.getId())) {
            roleService.save(role);

        } else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    /**
     * 跳转修改 页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate", name = "跳转修改 页面")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);
        request.setAttribute("role",role);
        return "system/role/role-update";

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", name = "删除")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";

    }

}
