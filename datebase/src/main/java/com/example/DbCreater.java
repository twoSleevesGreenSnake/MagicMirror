package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DbCreater {
    public static void main(String[] args){
        Schema schama = new Schema(1,"com.qoo.magicmirror.db");
        Entity entity = schama.addEntity("MainPageData");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("path");
        entity.addStringProperty("name");
        entity.addStringProperty("area");
        entity.addStringProperty("price");
        entity.addStringProperty("brand");
        entity.addIntProperty("type");

        try {
            new DaoGenerator().generateAll(schama,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
