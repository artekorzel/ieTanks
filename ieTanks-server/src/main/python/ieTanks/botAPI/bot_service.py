import json
import os

__author__ = 'adrian'

from os import listdir, path
from tornado.ioloop import IOLoop
from tornado.web import RequestHandler, Application, url


class BotsListHandler(RequestHandler):
    def get(self):
        bots = []
        for dirpath, _, filenames in os.walk('bots'):
            for filename in filenames:
                bots.append({'bot_id': path.splitext(filename)[0],
                             'user_id': path.split(dirpath)[1]})
            #bots = [{'id': path.splitext(f)[0]} for f in listdir('bots')]
        self.write(json.dumps(bots))

    def post(self):
        bot_data = json.loads(self.request.body)
        directory = 'bots/%s' % bot_data['user_id']
        if not path.exists(directory):
            os.makedirs(directory)
        f = open('%s/%s.py' % (directory, bot_data['bot_id']), 'w')
        f.write(bot_data['code'])
        print self.request.body


class BotsHandler(RequestHandler):
    def get(self, bot_id, user_id):
        try:
            f = open('bots/%s/%s.py' % (user_id, bot_id))
            self.write(f.read())
        except IOError as e:
            self.write(json.dumps({'error': str(e)}))


application = Application([
    url(r"/rest/bots", BotsListHandler),
    url(r"/rest/bots/(\w+)/users/(\w+)", BotsHandler),
])

if __name__ == "__main__":
    application.listen(8889)
    IOLoop.instance().start()
