from bot import *
from action import *

class KMRunningBot(Bot):
    def __init__(self, id):
        Bot.__init__(self, id)

    def performAction(self, board):
        tankIds = board.tankIds()
        myPosition = self.getMyPosition(board)
        wantedPosition = Position(myPosition.fromTop(), myPosition.fromLeft() + 1)
        if board.isAccessibleForTank(wantedPosition):
            return Move(MoveDirection.East)
        else:
            return Move(MoveDirection.West)