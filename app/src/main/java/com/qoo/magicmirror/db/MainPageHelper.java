package com.qoo.magicmirror.db;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/6.
 */
public class MainPageHelper {
    private DaoMaster master;
    private DaoSession session;

    private QueryBuilder builder,findBuilder;
    private static MainPageHelper helper;

    public static MainPageHelper newHelper(Context context){
        if (helper==null) {
            synchronized (MainPageHelper.class) {
                if (helper == null) {
                    helper = new MainPageHelper(context);

                }
            }
        }
        return helper;
    }
    public MainPageHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"mainPage.db",null);
        master = new DaoMaster(helper.getReadableDatabase());
        session = master.newSession();
        builder = session.getMainPageDataDao().queryBuilder();
        findBuilder = session.getMainPageDataDao().queryBuilder();
    }

    public void deletAll(){
        session.getMainPageDataDao().deleteAll();
    }

    public void addData(String path,String name,String area,String price,String brand){
        findBuilder.where(MainPageDataDao.Properties.Path.eq(path));
        if (findBuilder.list().size()<=0) {
            MainPageData data = new MainPageData();
            data.setArea(area);
            data.setPath(path);
            data.setName(name);
            data.setPrice(price);
            data.setBrand(brand);
            session.insert(data);
        }
    }
    public List<MainPageData> show(int type){
        findBuilder.where(MainPageDataDao.Properties.Type.eq(type));
        return builder.list();
    }

}
