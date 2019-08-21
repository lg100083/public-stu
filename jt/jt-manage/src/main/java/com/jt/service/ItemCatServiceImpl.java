package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMaper;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		
		ItemCat itemCat = itemCatMaper.selectById(itemCatId);
		return itemCat.getName();
	}
	
	/**
	 * EasyUITree VO对象
	 *   依赖
	 * ItemCat	  数据库对象
	 * 
	  * 思路:
	 * 	1.先查询数据库List信息
	 *  2.将数据库对象转化为VO对象.
	 */
	public List<ItemCat> findItemCatList(Long parentId){
		QueryWrapper<ItemCat> queryWrapper = 
				new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList = 
				itemCatMaper.selectList(queryWrapper);
		return itemCatList;
	}

	@Override
	public List<EasyUITree> findEasyUITreeList(Long parentId) {
		//1.定义返回数据
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		List<ItemCat> itemCatList = findItemCatList(parentId);
		for (ItemCat itemCat: itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			String state = 
					itemCat.getIsParent()?"closed":"open";
			easyUITree.setId(itemCat.getId())
					  .setText(itemCat.getName())
					  //如果是父级菜单应该关闭,否则应该打开
					  .setState(state);
			treeList.add(easyUITree);
		}
		
		return treeList;
	}
	
	
	
	
	
	
	
}
