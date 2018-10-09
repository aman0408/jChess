package jchess.game;

public enum Alliance {

    WHITE() {

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public int getDirection() {
            return UP_DIRECTION;
        }
    },

    BLACK() {

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public int getDirection() {
            return DOWN_DIRECTION;
        }
    };

    public abstract boolean isWhite();
    public abstract int getDirection();
    private static final int UP_DIRECTION = 1;
    private static final int DOWN_DIRECTION = -1;
}
