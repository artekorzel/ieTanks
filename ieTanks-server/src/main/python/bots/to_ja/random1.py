from pl.edu.agh.ietanks.gameplay.bot.api import Action
from pl.edu.agh.ietanks.gameplay.bot.api import Board
from pl.edu.agh.ietanks.gameplay.bot.api import Bot
from pl.edu.agh.ietanks.gameplay.bot.api import Missile
from pl.edu.agh.ietanks.gameplay.bot.api import Move
from pl.edu.agh.ietanks.gameplay.bot.api import MoveDirection
from pl.edu.agh.ietanks.gameplay.bot.api import Nothing
from pl.edu.agh.ietanks.gameplay.bot.api import Position
from pl.edu.agh.ietanks.gameplay.bot.api import Shot
from pl.edu.agh.ietanks.gameplay.bot.api import ShotDirection

class SampleBot(Bot):
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