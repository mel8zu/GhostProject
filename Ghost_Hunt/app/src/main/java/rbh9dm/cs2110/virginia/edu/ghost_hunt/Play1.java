package rbh9dm.cs2110.virginia.edu.ghost_hunt; /**
 * Created by Student User on 3/31/2015.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Student User on 3/26/2015.
 */
public class Play1 extends ActionBarActivity {

    /*private MainCharacter player;
    private ArrayList<Integer> list;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //getting rid of title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this,1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStop () {
        super.onStop();
    }


}
