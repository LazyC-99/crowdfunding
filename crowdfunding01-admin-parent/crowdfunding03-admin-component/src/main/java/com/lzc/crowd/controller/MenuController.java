package com.lzc.crowd.controller;

import com.lzc.crowd.entity.Menu;
import com.lzc.crowd.service.MenuService;
import com.lzc.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping("/menu/remove.json")
    public ResultEntity removeMenu (@RequestParam("id") Integer id) {

        menuService.removeMenu(id);

        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/edit.json")
    public ResultEntity editMenu (Menu menu) {

        menuService.editMenu(menu);

        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/save.json")
    public ResultEntity saveMenu (Menu menu) {

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }
    /**
     * 查询组装目录树
     * @return
     */
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree(){
        //1.查询全部Menu
        List<Menu> menus = menuService.getAllMenu();

        //2.储存根节点
        Menu root = null;

        //3.创建Map对象来存储id和Menu对象的对应关系 便于查找父节点
        Map<Integer,Menu> menuMap = new HashMap<>();

        //4.遍历menus填充menuMap
        for (Menu menu : menus){
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }
        //5.再次遍历menus查找根节点 组装父子节点
        for (Menu menu:menus) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root=menu;
                continue;
            }
            //6.根据pid 在menuMap中查找父节点并组装
            Menu father = menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    /**
     * 查询组装目录树
     * (嵌套循环 浪费性能)
     * @return
     */
    @Deprecated
    @RequestMapping("/menu/get/whole/old/tree.json")
    public ResultEntity<Menu> getWholeTreeOld(){
        //1.查询全部Menu
        List<Menu> menus = menuService.getAllMenu();

        //2.储存根节点
        Menu root = null;

        //3.遍历menus
        for(Menu menu : menus) {
            //4.获取当前对象的父节点
            Integer pid =menu.getPid();
            //5.检查是否为根节点
            if(pid==null){
                //当前对象赋给根节点
                root = menu;
                continue;
            }
            //6.不是根节点  找父节点
            for (Menu father : menus) {
                //获取当前对象的id
                Integer id =father.getId();
                //判断当前对象是否为上层对象的父节点
                if (Objects.equals(pid,id)){
                    //将当前对象存入上层父节点的children属性
                    father.getChildren().add(menu);
                    break;
                }
            }


        }
        return ResultEntity.successWithData(root);
    }
}
