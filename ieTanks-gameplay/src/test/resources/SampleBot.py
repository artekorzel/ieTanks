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
import java.util.Random
import java.lang.Boolean.FALSE as False
import java.lang.Boolean.TRUE as True

class SampleBot(Bot):
    def __init__(self, id):
        Bot.__init__(self, id)
        self.random = java.util.Random()
        self.bot_speed = 1
        self.shot_speed = 1

    def choice(self, array):
        index = self.random.nextInt(len(array))
        return array[index]

    def performAction(self, board):
        my_position = self.getMyPosition(board)
        attacked_fields = self.calculate_attacked_fields(board)
        possible_move_directions = self.possible_move_directions(my_position, attacked_fields, board)
        can_i_move = len(possible_move_directions) > 0
        possible_shot_directions = self.possible_shot_directions(my_position, board)
        should_i_attack = len(possible_shot_directions) > 0

        if self.should_i_run(my_position, attacked_fields):
            if can_i_move:
                selected_direction = self.choice(possible_move_directions)
                return Move(selected_direction)
        elif should_i_attack:
            if len(possible_shot_directions) > 0:
                selected_direction = self.choice(possible_shot_directions)
                return Shot(selected_direction)
        elif can_i_move:
            selected_direction = self.choice(possible_move_directions)
            return Move(selected_direction)
        return Nothing()

    def calculate_attacked_fields(self, board):
        attacked_fields = []
        missiles = board.findMissiles()
        for missile in missiles:
            missile_future_position = missile.position().moveMissile(missile.direction(), missile.speed())
            if board.isWithin(missile_future_position):
                attacked_fields.append(missile_future_position)
        return attacked_fields

    def should_i_run(self, my_position, attacked_fields):
        for attacked_field in attacked_fields:
            if attacked_field.equals(my_position):
                return True
        return False

    def possible_move_directions(self, my_position, attacked_fields, board):
        possible_actions = []
        directions = [MoveDirection.North, MoveDirection.East, MoveDirection.South, MoveDirection.West]
        for direction in directions:
            try_position = my_position.moveInMoveDirection(direction, self.bot_speed)
            if board.isWithin(try_position):
                will_be_attacked = False
                for attacked_field in attacked_fields:
                    if attacked_field.equals(try_position):
                        will_be_attacked = True
                        break
                if not will_be_attacked:
                    possible_actions.append(Move(direction))
        return possible_actions

    def possible_shot_directions(self, my_position, board):
        possible_directions = []
        directions = [ShotDirection.South, ShotDirection.NorthWest, ShotDirection.North, ShotDirection.SouthWest, ShotDirection.East, ShotDirection.NorthEast, ShotDirection.South, ShotDirection.SouthEast]
        my_id = self.id
        tank_ids = []
        for tank_id in board.tankIds():
            if tank_id != my_id:
                tank_ids.append(tank_id)
        tank_positions = []
        for tank_id in tank_ids:
            tank_position = board.findTank(tank_id)
            tank_positions.append(tank_position)
        for tank_position in tank_positions:
            for direction in directions:
                try_position = my_position.moveInShotDirection(direction, self.shot_speed)
                if board.isWithin(try_position) and try_position.equals(tank_position):
                    possible_directions.append(direction)
        return possible_directions

