import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xander on 9/13/16.
 */
public class ResponseTask implements Runnable {

    // Строки для формирования URLзапроса
    String URL = "http://radio.redoc.ru/api/TrackSource/NextTrack";
    String User_IDpart="?userId=";
    String LastTrackIdpart="&lastTrackId=";
    String LastTrackMetodPart="&lastTrackMethod=";
    String ListedToEndPart="&listedTillTheEnd=";
    //--
    //строка для формирования запроса трека
    String TracTrackURLpart ="http://radio.redoc.ru/api/TrackSource/Play?trackId=";


    //систему не хочется делать по генерации и сохранения GUID устройства по этому:
    String testUserGUID="6585290e-9dc3-41b2-926e-106bf621f870";

    //для функции взятой из интернета
    private final String USER_AGENT = "Mozilla/5.0";


    Main main;



  public   ResponseTask(Main main){
      this.main=main;

  }


    @Override
    public void run() {
        String mediaPath=MediaPath(); // MediaURL

        main.SetMedia(mediaPath);
        main.Play();

    }

// тут делает запрос на сервер. получает ответ сервера в string, потом строка ответа в JSONparser, от туда берет Guid трека и добовляет GUID к строке TracTrackURLpart
    // и получается URL трека которое возвращается функцией
    public String MediaPath(){



        String lasttrack=null;
        if(main.CurrentTrackGUID==null){
            lasttrack="-1";
        }else {
            lasttrack=main.CurrentTrackGUID;
        }

        String URLtoSend=CreateURLString(testUserGUID,lasttrack,"",true);
        String ResivedResponce=null;
        try {
            ResivedResponce= sendGet(URLtoSend);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject=null;
        try {
            jsonObject= (JSONObject) jsonParser.parse(ResivedResponce);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String TrackGUIDkey="TrackId";
        String TrackGUID=(String) jsonObject.get(TrackGUIDkey);
        System.out.println(TrackGUID);

        main.CurrentTrackGUID=TrackGUID;

        String MediaPath= TracTrackURLpart +TrackGUID;
        return MediaPath;

    }



    //из инета взял и чуть-чуть изменил ... Получает ответ с задоного URL и возвращает строку ответа с сервера
    // HTTP GET request
    private String sendGet(String URL) throws Exception {



        java.net.URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + URL);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String RespStr=response.toString();
        //print result
        System.out.println(RespStr);
        return RespStr;

    }



    //создание строки URL запроса
    public String CreateURLString(String UserGUID,String lastTrackGUID_or_m1,String lastTrackMethod_name,boolean listedTillTheEnd){
/*
userId - ID пользователя
lastTrackId - ID трэка, который проигрывался
lastTrackMethod - Метод получения трэка
listedTillTheEnd - Был ли трэк доигран до конца
 */

        String listedTillTheEndStr;
        if(listedTillTheEnd){
            listedTillTheEndStr="true";
        }else{
            listedTillTheEndStr="false";
        }


        String ret=URL+User_IDpart+UserGUID+LastTrackIdpart+lastTrackGUID_or_m1+LastTrackMetodPart+lastTrackMethod_name+ListedToEndPart+listedTillTheEndStr;
        return ret;


    }






}
