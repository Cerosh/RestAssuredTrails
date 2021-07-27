package com.spotify.outh2.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;
    private DataLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }
    public static DataLoader getInstance(){
        if (dataLoader == null){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }
    public String getDataProperty(String dataKey){
        String prop = properties.getProperty(dataKey);
        if(prop != null) return  prop;
        else throw new RuntimeException("property"+dataKey+" is not specified in the config properties file");
    }

    public String getPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if(prop != null) return  prop;
        else throw new RuntimeException("property is not specified in the config properties file");
    }
    public String updatePlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if(prop != null) return  prop;
        else throw new RuntimeException("property is not specified in the config properties file");
    }


}
