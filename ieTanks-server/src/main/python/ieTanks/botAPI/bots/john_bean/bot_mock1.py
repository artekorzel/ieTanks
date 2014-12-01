from ieTanks.bot.bot import Bot


class BotMock1(Bot):

    def next_action(self, board):

        raise NotImplementedError
