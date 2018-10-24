package jchess.ai;

import jchess.game.board.Board;
import jchess.game.board.Move;

public interface MoveStrategy {

    Move execute(Board board, int depth);
}
