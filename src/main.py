"""The main source module"""

#import requests

from flask import Flask, request
from flask_restful import Resource, Api
from json import dumps
from flask.ext.jsonpify import jsonify

app = Flask(__name__)
api = Api(app)

class Main(Resource):
    def get(self):
        return {'Hello': 'World'}

api.add_resource(Main, '/')

if __name__ == '__main__':
    app.run(host='0.0.0.0')
