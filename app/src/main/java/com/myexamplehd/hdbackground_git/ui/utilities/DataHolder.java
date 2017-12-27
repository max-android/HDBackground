package com.myexamplehd.hdbackground_git.ui.utilities;

import com.myexamplehd.hdbackground_git.model.entities.net.HDBackgrounds;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 25.12.2017.
 */

public class DataHolder {

    private static DataHolder dataHolder;

        private static List<HDBackgrounds> dataList=new ArrayList<>();;

        public void setDataList(List<HDBackgrounds> data) {
            dataList.addAll(data);
        }

        public List<HDBackgrounds> getDataList() {
            return dataList;
        }

        public  static DataHolder getInstance() {
            if (dataHolder == null) {
                dataHolder = new DataHolder();
            }
            return dataHolder;
    }

public static void destroyHolder(){
    if (dataHolder != null){

        dataHolder=null;
    }}

}
