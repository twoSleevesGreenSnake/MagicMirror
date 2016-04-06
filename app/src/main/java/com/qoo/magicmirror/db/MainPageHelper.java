package com.qoo.magicmirror.db;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/6.
 */
public class MainPageHelper {
    private DaoMaster master;
    private DaoSession session;
    private boolean notDelete = true;
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


    }

    public void deleteAll(){
        if (notDelete) {
            QueryBuilder builder = session.getMainPageDataDao().queryBuilder();
            DeleteQuery dq = builder.buildDelete();
            dq.executeDeleteWithoutDetachingEntities();
            notDelete  = false;
        }
    }

//    public Long addData(){
//       QueryBuilder findBuilder = session.getMainPageDataDao().queryBuilder();
//        Log.i("findbuilder",findBuilder.list().size()+"");
//
//        findBuilder.where(MainPageDataDao.Properties.Path.eq(path));
//        if (findBuilder.list().size()<=0) {
//
//            return session.getMainPageDataDao().insert(data);
//        }
//        else return 0l;
//    }
    public Long addData(String path,String name,String area,String price,String brand,String type){
        QueryBuilder findBuilder = session.getMainPageDataDao().queryBuilder();
        findBuilder.where(MainPageDataDao.Properties.Path.eq(path));
        Log.i("findbuilder99999999", findBuilder.list().size()+"");
        if (findBuilder.list().size()<=0) {
            Log.i("findbuilder99999999", path);
            MainPageData data = new MainPageData();
            data.setArea(area);
            data.setPath(path);
            data.setName(name);
            data.setPrice(price);
            data.setBrand(brand);
            data.setType(type);

            return session.getMainPageDataDao().insert(data);
        }

        else {
            return 0l;
        }
    }
    public List<MainPageData> show(String type){
        QueryBuilder builder = session.getMainPageDataDao().queryBuilder();
        builder.where(MainPageDataDao.Properties.Type.eq(type));
        return builder.list();
    }

}
