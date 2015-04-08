package rbh9dm.cs2110.virginia.edu.ghost_hunt;

/**
 * Created by Student User on 3/28/2015.
 */
public class HighScore {

    private String score;
    private int id;

    public HighScore() {

    }

    public HighScore(int id, String score) {
        this.id=id;
        this.score = score;
    }

    public HighScore(String score) {
        this.score=score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    public String toString() {
        return "id=" + id + "; score=" + score;
    }

}
