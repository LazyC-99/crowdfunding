package com.lzc.crowd.service;

import com.lzc.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 获取所有Menu
     * @return
     */
    List<Menu> getAllMenu();

    void saveMenu(Menu menu);

    void editMenu(Menu menu);

    void removeMenu(Integer id);
}
