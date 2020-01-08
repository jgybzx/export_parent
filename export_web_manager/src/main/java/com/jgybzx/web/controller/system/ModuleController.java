package com.jgybzx.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.jgybzx.domain.system.Module;
import com.jgybzx.service.system.ModuleService;
import com.jgybzx.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: guojy
 * @date: 2020/1/6 14:34
 * @Description: ${TODO}
 * @version:
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    /**
     * 分页查询数据
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "分页查询数据")
    public String list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer size) {
        // 模块管理每个公司看见的都是一样的  不需要根据企业id查询
        PageInfo byPage = moduleService.findByPage(page, size);
        request.setAttribute("page", byPage);
        return "system/module/module-list";
    }

    /**
     * 跳转添加页面
     * 同时要查询出上级模块的数据
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", name = "跳转添加页面")
    public String toAdd() {

        //查询上级模块数据
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus", list);
        return "system/module/module-add";
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping(value = "/toUpdate", name = "跳转修改页面")
    public String toUpdate(String id) {


        //根据 id  查询模块数据
        Module module = moduleService.findById(id);
        request.setAttribute("module", module);

        //取出模块类型
        String ctype = String.valueOf(module.getCtype());
        // 查询该模块的 父模块
        List<Module> parent = moduleService.findParent(ctype);
        request.setAttribute("menus",parent);

        return "system/module/module-update";
    }


    /**
     * 新增或修改
     *
     * @param module
     * @return
     */
    @RequestMapping(value = "/edit", name = "新增或修改")
    public String edit(Module module) {

        if (StringUtils.isEmpty(module.getId())) {
            moduleService.save(module);
        } else {

            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }

    @RequestMapping(value = "/delete",name = "删除模块")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }

    /**
     * 选择模块的上级模块
     * @param ctype
     * @return
     */
    @RequestMapping(value = "parentModule",name = "获取上级模块")
    @ResponseBody
    public List<Module> chooseParentModule(String ctype){
        System.out.println(ctype);
        List<Module> modules = moduleService.findParent(ctype);
        System.out.println(modules);
        return modules;
    }
}
