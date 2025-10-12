package event;

import core.Game;
import enums.NoteStatus;

public interface UpdateScoreInterface {
    default public void updateScore(NoteStatus status) {
        Game game = Game.getGameInstance();
        game.updateScore(status);
    }
}
