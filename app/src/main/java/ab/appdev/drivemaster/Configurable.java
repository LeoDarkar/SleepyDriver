package ab.appdev.drivemaster;

import java.util.ArrayList;

public class Configurable {
    public final static String SENSITIVITY = "sensitivity";
    public final static String DEFAULT_SENSITIVITY = "3";
    public final static String TAG = "eyeSense";
    public final static String SHAREDNAME = "eyesenseData";
    public final static String APPMODE = "APPMODE";
    public final static String RECEIVER = "RECEIVER";
    public final static String SENDER = "SENDER" ;
    public final static String BRODCASTID = "brodcastID";

    public static int getDetectionDelayInMilliseconds(int value) {
        return (value + 2) * 300;
    }



    public static ArrayList<IntroViewItem> getIntroViewList() {

        ArrayList<IntroViewItem> introList = new ArrayList<>();

//        introList.add(
//                new IntroViewItem(
//                        "Create, Collab & Explore",
//                        "personal workspace for teams.",
//                        R.mipmap.ic_launcher)
//        );

        introList.add(
                new IntroViewItem(
                        "Copiloto",
                        "Conecte su dispositvo con el copiloto o un pasajero y reciban alertas si se detecta somnolencia",
                        R.drawable.senderimg)
        );
        introList.add(
                new IntroViewItem(
                        "Conexión por código QR",
                        "Alerta de detección en tiempo en real para chofer y copiloto",
                        R.drawable.recieverimg                )
        );
        introList.add(
                new IntroViewItem(
                        "Ditec",
                        "Nuestra empresa se dedica a la detección de somnolencia en conductores",
                        R.drawable.logditec)
        );


        return introList;
    }


}