package com.eltyl.util;


import com.eltyl.model.vo.Permission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 权限数据处理
 */
public class TreeUtil
{

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public static List<Permission> getChildPerms(List<Permission> list, int parentId)
    {
        List<Permission> returnList = new ArrayList<Permission>();
        for (Iterator<Permission> iterator = list.iterator(); iterator.hasNext();)
        {
            Permission t = (Permission) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getPid() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private static void recursionFn(List<Permission> list, Permission t)
    {
        // 得到子节点列表
        List<Permission> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Permission tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<Permission> it = childList.iterator();
                while (it.hasNext())
                {
                    Permission n = (Permission) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static List<Permission> getChildList(List<Permission> list, Permission t)
    {

        List<Permission> tlist = new ArrayList<Permission>();
        Iterator<Permission> it = list.iterator();
        while (it.hasNext())
        {
            Permission n = (Permission) it.next();
            if (n.getPid().longValue() == t.getId())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    List<Permission> returnList = new ArrayList<Permission>();

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @param prefix 子节点前缀
     */
    public List<Permission> getChildPerms(List<Permission> list, int typeId, String prefix)
    {
        if (list == null)
        {
            return null;
        }
        for (Iterator<Permission> iterator = list.iterator(); iterator.hasNext();)
        {
            Permission node = (Permission) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getPid() == typeId)
            {
                recursionFn(list, node, prefix);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*
             * if (node.getParentId()==0) { recursionFn(list, node); }
             */
        }
        return returnList;
    }

    private void recursionFn(List<Permission> list, Permission node, String p)
    {
        // 得到子节点列表
        List<Permission> childList = getChildList(list, node);
        if (hasChild(list, node))
        {
            // 判断是否有子节点
            returnList.add(node);
            Iterator<Permission> it = childList.iterator();
            while (it.hasNext())
            {
                Permission n = (Permission) it.next();
                n.setTitle(p + n.getTitle());
                recursionFn(list, n, p + p);
            }
        }
        else
        {
            returnList.add(node);
        }
    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<Permission> list, Permission t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
