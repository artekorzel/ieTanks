from bot import Bot
import action
from random import *


class RandomBot(Bot):
    def next_action(self):
        actions = [action.Nothing()]
        actions += [action.Move(action.Direction.North), action.Move(action.Direction.South), action.Move(action.Direction.East), action.Move(action.Direction.West)]
        actions += [action.Shot(action.Direction.North), action.Shot(action.Direction.South), action.Shot(action.Direction.East), action.Shot(action.Direction.West)]
        actions += [action.Shot(action.Direction.NorthEast), action.Shot(action.Direction.SouthEast), action.Shot(action.Direction.SouthWest), action.Shot(action.Direction.NorthWest)]

        a = choice(actions)
        return a
