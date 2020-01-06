package com.jgybzx.web.controller.system;

import java.awt.Desktop.Action;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Dept;
import com.jgybzx.domain.system.Role;
import com.jgybzx.domain.system.User;
import com.jgybzx.service.system.DeptService;
import com.jgybzx.service.system.RoleService;
import com.jgybzx.service.system.UserService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/5 16:01
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 根据企业id获取用户分页数据，此时的企业id暂时写死，以后要从session中查
     * 当前登陆用，获取其企业id，从而查询
     * companyId 放在BaseController中
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "根据企业id查询用户")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size) {
        //调用分页查询
        PageInfo pageInfo = userService.findByPage(page, size, companyId);
        request.setAttribute("page", pageInfo);
        return "system/user/user-list";
    }

    /**
     * 跳转添加页面，要注意携带这当前企业的部门数据
     * 因为添加页面需要选择 所属部门
     *
     * @return
     */
    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "toAdd", name = "跳转添加页面")
    public String toAdd() {
        //根据企业id查询部门数据
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/user/user-add";
    }

    /**
     * 跳转修改页面，并且携带着id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "toUpdate", name = "跳转修改页面")
    public String toUpdate(String id) {

        // 根据id查询用户信息
        User user = userService.findById(id);

        //根据企业id查询部门数据
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);

        request.setAttribute("user", user);
        return "system/user/user-update";
    }

    /**
     * 添加或修改用户
     * 由于前台页面没有选择 企业id和企业名字，所以本次需要手动写死
     * 正常是从session中获取出本地登陆用户的信息，然后获取企业id
     *
     * @param user 用户对象
     * @return
     */
    @RequestMapping(value = "edit", name = "添加或修改用户")
    public String edit(User user) {

        // 设置企业id
        user.setCompanyId(companyId);
        // 设置企业名字
        user.setCompanyName(companyName);
        if (StringUtils.isEmpty(user.getId())) {
            userService.save(user);
        } else {
            userService.update(user);
        }

        //执行查询方法，重新查询
        return "redirect:/system/user/list.do";
    }

    /**
     * 删除用户数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", name = "删除用户数据 ")
    public String delete(String id) {
        userService.delete(id);
        //执行查询方法，重新查询
        return "redirect:/system/user/list.do";
    }

    //======================================================================
    // RBAC 权限管理

    @Autowired
    private RoleService roleService;

    /**
     * 用户角色 管理
     * 1、查询所有的角色，用户页面显示
     * <p>
     * 2、根据用户id查询该用户的所有角色，转换成字符串 userRoleStr
     * 2.1、fn:contains(userRoleStr,role.id)，该方法可以判断  userRoleStr 是否有 role.id，如果在 userRoleStr中，就默认选中
     * SELECT * FROM pe_role WHERE role_id IN (
     * SELECT role_id FROM pe_role_user WHERE user_id = 'id')
     *
     * @param id
     * @return
     */
    @RequestMapping("roleList")
    public String roleList(String id) {

        // 根据id查询用户
        User user = userService.findById(id);
        request.setAttribute("user", user);
        // 查询所有的角色
        List<Role> list = roleService.findAll(companyId);
        request.setAttribute("roleList", list);

        //查询该用户所拥有的角色

        List<Role> roleList = roleService.findByUid(id);
        System.out.println(roleList);
        // 使用空字符串拼接，防止返回结果为空，从而造成前台报错
        String userRoleStr = "";
        for (Role role : roleList) {
            // 取出list 中每个角色的id
            userRoleStr += role.getId();
        }
        request.setAttribute("userRoleStr", userRoleStr);

        return "system/user/user-role";
    }

    /**
     * 修改用户角色的功能，将该用户角色全部删除，然后再全部添加
     * 使用的方式：1、将页面选中的角色利用字符串接收
     * 2、删除用户全部角色
     * 2.1，判断用户角色是否为空，如果不为空，继续循环新增；如果为空，不做操作
     *
     * @param roleIds 前台页面 所选角色
     * @param userid 用户id
     * @return
     */
    @RequestMapping("changeRole")
    public String changeRole(@RequestParam(defaultValue = " ") String roleIds,String userid) {
        userService.changeRole(roleIds,userid);
        // 修改完之后 跳转用户列表
        return "redirect:/system/user/list.do";
    }
}
