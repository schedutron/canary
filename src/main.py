"""The main source module"""
import os
import signal

import requests

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from flask.ext.jsonpify import jsonify

import src.register as register
import subprocess

app = Flask(__name__)
api = Api(app)

global_process_map = {}

class Main(Resource):
    def get(self):
        if request.args['mode'] == 'on':
            #bot.on
            rate = request.args['rate']
            if request.args['fav'].lower() == 'true':
                fav = '--fav'
            else:
                fav = ''
            
            if request.args['retweet'].lower() == 'false':
                retweet = '--retweet'
            else:
                retweet = ''
            cmd = 'python3 -m chirps.main --rate=%s %s %s' % (rate, fav, retweet)
            pro = subprocess.Popen(cmd.split())
            global_process_map[request.args['handle']] = pro.pid

        elif request.args['mode'] == 'off':
            """Switch the bot off"""
            # Kill the subprocess started
            user_pid= global_process_map[request.args['handle']]
            os.kill(os.getpid(user_pid), signal.SIGTERM)

            

global_mappings = []
global_mappings.append((Main, '/switch'))
global_mappings.extend(register.mappings)


# Does the global mapping of views
for mapping in global_mappings:
    api.add_resource(*mapping)

if __name__ == '__main__':
    app.run(host='0.0.0.0')
