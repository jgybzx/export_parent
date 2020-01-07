package com.jgybzx.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Module;
import com.jgybzx.domain.system.Role;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.service.system.RoleService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
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
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate", name = "跳转修改 页面")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "system/role/role-update";

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", name = "删除")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";

    }
//=============================================================================================
    /**
     * 角色 模块页面跳转
     * 1、查询所有的模块信息
     * 2、根据id查询改角色的模块信息
     * 3、由于返回前台是json字符串，所以使用ajax，返回json。想到json中的数据并不是查出来的所有数据
     * 所以需要后台处理，json显示的每一个 模块都是键值对，所以将每一个模块都构建一个map
     * 4、将查出的所有模块信息，循环构建一个map，存入
     * ~ id：该条数据的唯一标识
     * ~ pid，父级的id
     * ~ name：要显示的名字
     * ~ open：默认是否展开
     * ~ checked：是否默认选中
     * 5、比较根据id查询改角色的模块信息，如果比对成功，则checked 默认选中即可
     * 6、ajax请求可以返回list，和String 以及void，此处使用list<Map>
     * @param roleId
     * @return
     */
    @Autowired
    private ModuleService moduleService;
    @RequestMapping(value = "/roleModule",name = "跳转角色_权限页面")
    public String roleModule(String roleId){
        // 根据id查询角色数据
        Role role = roleService.findById(roleId);
        request.setAttribute("role", role);

        return "system/role/role-module";
    }


    @RequestMapping(value = "/getZtreeNodes", name = "获取Ztree的数据")
    @ResponseBody
    public List getZtreeNodes(String roleId) {
        List<Map<String,String>> list=new ArrayList<>();

        // 根据 id查询该角色的所有权限(模块)
        List<Module> roleListById =moduleService.findRoleModuleById(roleId);
        // 查询全部的角色信息
        List<Module> roleListAll = moduleService.findAll();

        for (Module module : roleListAll) {
            Map<String,String> moduleMap = new HashMap();
            moduleMap.put("id",module.getId());
            moduleMap.put("pId",module.getParentId());
            moduleMap.put("name",module.getName());
            moduleMap.put("open","true");
            //判断当前对象是否在 使用id查出来的对象集合中
            if(roleListById.contains(module)){
                //如果存在，说明 checked 默认选中
                moduleMap.put("checked","true");
            }else {
                //如果不存在，不处理
                moduleMap.put("checked","");
            }
            //将每个 map数据存入集合
            list.add(moduleMap);
        }
        return list;
    }

    /**
     * 跟新 角色权限，思路，先全删，再遍历增加
     * @param moduleIds
     * @param roleId
     * @return
     */
    @RequestMapping("updateRoleModule")
    public String updateRoleModule(String roleId,String moduleIds){
        /// System.out.println(moduleIds);
        moduleService.updateRoleModule(roleId,moduleIds);

        //刷新该页面
        return "redirect:/system/role/roleModule.do?roleId="+roleId;
    }

}
