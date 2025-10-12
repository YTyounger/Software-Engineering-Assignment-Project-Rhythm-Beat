package event;

import core.Game;

public class DoubleScoreEvent extends GameEvent implements UpdateScoreInterface {
    private int endFrame;
    private Game game;

    public DoubleScoreEvent() {
        game = Game.getGameInstance();
        endFrame = game.getFrame() + 480;
    }

    
    /** 
     * @return boolean
     */
    public boolean handle() {
        if (game.getFrame() >= endFrame) {
            game.setDoubleScoreStatus(false);
            return true;
        } else {
            game.setDoubleScoreStatus(true);
            return false;
        }
    }
}
