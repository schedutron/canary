"""The main source module"""

#import requests

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from flask.ext.jsonpify import jsonify

import src.register as register

app = Flask(__name__)
api = Api(app)

class Main(Resource):
    def get(self):
        return {'Hello': 'World'}

global_mappings = []
global_mappings.append((Main, '/'))
global_mappings.extend(register.mappings)

# Does the global mapping of views
for mapping in global_mappings:
    api.add_resource(*mapping)

if __name__ == '__main__':
    app.run(host='0.0.0.0')
