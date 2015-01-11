from ieTanks.bot.bot import Bot


class BotMock3(Bot):

    def next_action(self, board):

        raise NotImplementedError
