from ieTanks.bot.bot import Bot


class BotMock2(Bot):

    def next_action(self, board):

        raise NotImplementedError
