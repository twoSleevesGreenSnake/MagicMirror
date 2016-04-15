package com.qoo.magicmirror.db;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/6.
 */
public class MainPageHelper {
    private DaoMaster master;
    private DaoSession session;
    private boolean notDelete = true;
    private QueryBuilder builder, findBuilder;
    private static MainPageHelper helper;

    /**
     * 单例helper
     * @param context 上下文
     * @return 返回单例的Dbhelper对象
     */
    public static MainPageHelper newHelper(Context context) {
        if (helper == null) {
            synchronized (MainPageHelper.class) {
                if (helper == null) {
                    helper = new MainPageHelper(context);

                }
            }
        }
        return helper;
    }

    public MainPageHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "mainPage.db", null);
        master = new DaoMaster(helper.getReadableDatabase());
        session = master.newSession();
        builder = session.getMainPageDataDao().queryBuilder();
    }


    /**
     * 插入数据
     * @param path 网址
     * @param name 名称
     * @param area 地区
     * @param price 价格
     * @param brand 品牌
     * @param type 型号
     * @return
     */
    public Long addData(String path, String name, String area, String price, String brand, String type) {
        QueryBuilder findBuilder = session.getMainPageDataDao().queryBuilder();
        findBuilder.where(MainPageDataDao.Properties.Path.eq(path));
        if (findBuilder.list().size() <= 0) {
            MainPageData data = new MainPageData();
            data.setArea(area);
            data.setPath(path);
            data.setName(name);
            data.setPrice(price);
            data.setBrand(brand);
            data.setType(type);
            return session.getMainPageDataDao().insert(data);
        } else {
            return 0l;
        }
    }

    /**
     * 删除全部数据
     */
    public void deleteAll() {
        if (notDelete) {
            QueryBuilder builder = session.getMainPageDataDao().queryBuilder();
            DeleteQuery dq = builder.buildDelete();
            dq.executeDeleteWithoutDetachingEntities();
            notDelete = false;
        }
    }

    /**
     * 根据地区删除
     * @param area 地区
     */
    public void deleteSingleDataByArea(String area) {
        if (builder.list().size() > 0) {
            DeleteQuery deleteQuery = builder.where(MainPageDataDao.Properties.Area.eq(area)).buildDelete();
            // 将查询到符合条件的删除
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 根据名字删除
     * @param name 名称
     */
    public void deleteSingleDataByName(String name) {
        if (builder.list().size() > 0) {
            DeleteQuery deleteQuery = builder.where(MainPageDataDao.Properties.Area.eq(name)).buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 根据价钱删除
     * @param price 价格
     */
    public void deleteSingleDataByPrice(String price) {
        if (builder.list().size() > 0) {
            DeleteQuery deleteQuery = builder.where(MainPageDataDao.Properties.Area.eq(price)).buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 根据品牌删除
     * @param brand 品牌
     */
    public void deleteSingleDataByBrand(String brand) {
        if (builder.list().size() > 0) {
            DeleteQuery deleteQuery = builder.where(MainPageDataDao.Properties.Area.eq(brand)).buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 根据地区删除
     * @param type 地区
     */
    public void deleteSingleDataByType(String type) {
        if (builder.list().size() > 0) {
            DeleteQuery deleteQuery = builder.where(MainPageDataDao.Properties.Area.eq(type)).buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
        }
    }


    /**
     * 根据类型查询数据
     * @param type 类型
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByType(String type) {
        QueryBuilder builder = session.getMainPageDataDao().queryBuilder();
        builder.where(MainPageDataDao.Properties.Type.eq(type));
        return builder.list();
    }

    /**
     * 根据网址查询数据
     * @param path 网址
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByPath(String path) {
        builder.where(MainPageDataDao.Properties.Path.eq(path));
        return builder.list();
    }

    /**
     * 根据名称查询数据
     * @param name 名称
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByName(String name) {
        builder.where(MainPageDataDao.Properties.Path.eq(name));
        return builder.list();
    }

    /**
     * 根据地区查询数据
     * @param area 产地
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByArea(String area) {
        builder.where(MainPageDataDao.Properties.Path.eq(area));
        return builder.list();
    }

    /**
     * 根据价格查询数据
     * @param price 价格
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByPrice(String price) {
        builder.where(MainPageDataDao.Properties.Path.eq(price));
        return builder.list();
    }

    /**
     * 根据品牌查询数据
     * @param brand 品牌
     * @return 符合条件的单条数据
     */
    public List<MainPageData> showByBrand(String brand) {
        builder.where(MainPageDataDao.Properties.Path.eq(brand));
        return builder.list();
    }

}
