package rbh9dm.cs2110.virginia.edu.ghost_hunt;

/**
 * Created by Bryan on 4/15/15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Barriers  extends Background  {

    private long created;


    public Barriers(Bitmap bitmap, int xCoord, int yCoord,int speed,long created) {

        super(bitmap, xCoord, yCoord, speed);
        this.created = created;

//        Context context = null;
//        rock = new Barriers(BitmapFactory.decodeResource(context.getResources(), R.drawable.rockicon), 40, 80);
//        Canvas canvas = null;
//        rock.draw(canvas);
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}


