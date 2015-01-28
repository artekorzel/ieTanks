package pl.edu.agh.ietanks.gameplay.bot.api;

public class Position {

    private Integer fromLeft;
    private Integer fromTop;

    public Position(Integer fromLeft, Integer fromTop) {
        this.fromLeft = fromLeft;
        this.fromTop = fromTop;
    }

    public Integer fromTop() {
        return fromTop;
    }

    public Integer fromLeft() {
        return fromLeft;
    }

    public Boolean equals(Position position) {
        return fromTop() == position.fromTop() && fromLeft() == position.fromLeft();
    }

    public Position moveInShotDirection(ShotDirection shotDirection, Integer speed) {
        if (shotDirection == ShotDirection.North) {
            return North(speed);
        } else if (shotDirection == ShotDirection.NorthEast) {
            return North(speed).East(speed);
        } else if (shotDirection == ShotDirection.East) {
            return East(speed);
        } else if (shotDirection == ShotDirection.SouthEast) {
            return South(speed).East(speed);
        } else if (shotDirection == ShotDirection.South) {
            return South(speed);
        } else if (shotDirection == ShotDirection.SouthWest) {
            return South(speed).West(speed);
        } else if (shotDirection == ShotDirection.West) {
            return West(speed);
        } else if (shotDirection == ShotDirection.NorthWest) {
            return North(speed).West(speed);
        }
        return this;
    }

    public Position moveInMoveDirection(MoveDirection moveDirection, Integer speed) {
        if (moveDirection == MoveDirection.North) {
            return North(speed);
        } else if (moveDirection == MoveDirection.East) {
            return East(speed);
        } else if (moveDirection == MoveDirection.South) {
            return South(speed);
        } else if (moveDirection == MoveDirection.West) {
            return West(speed);
        }
        return this;
    }

    public Position West(int speed) {
        return new Position(fromLeft() - speed, fromTop());
    }

    public Position North(int speed) {
        return new Position(fromLeft(), fromTop() - speed);
    }

    public Position East(int speed) {
        return new Position(fromLeft() + speed, fromTop());
    }

    public Position South(int speed) {
        return new Position(fromLeft(), fromTop() + speed);
    }
}
