package controller;

import model.PlayIO;
import org.json.JSONArray;

/**
 * Created by Fish on 2017/4/10.
 */
public class LoadController {
    private PlayIO playIO;

    public LoadController(){
        playIO = new PlayIO();
    }

    public JSONArray getPlayList(){
        return playIO.getPlayList();
    }
}
