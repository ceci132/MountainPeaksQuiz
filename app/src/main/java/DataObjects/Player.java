package DataObjects;

import com.example.mountainpeaksquiz.MainActivity;

public class Player {

    private String userName;
    private int playerScore;

    public Player(String username, int score){
        this.userName = username;
        this.playerScore = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
