"""The main source module"""

#import requests

from flask import Flask, render_template #request, Response
#from werkzeug.contrib.fixers import ProxyFix

app = Flask(__name__)
#app.wsgi_app = ProxyFix(app.wsgi_app)

@app.route('/')
def main():
    return render_template('index.html', name='Hello world')

if __name__ == '__main__':
    app.run(host='0.0.0.0')