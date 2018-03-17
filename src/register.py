"""Registration APIs"""

import random
import sys

from flask import Flask, request, redirect
from flask_restful import Resource
from twitter import Twitter, OAuth

import src.database as database
import src.functions as functions
from src.credentials import *

CONN = database.db_connect(USER_DATABASE_URL)
DB_ACCESS = {'conn': CONN, 'cur': CONN.cursor(), 'url': USER_DATABASE_URL}
CURSOR = database.get_cursor(DB_ACCESS)  # demo

class AddUser(Resource):
    """Register a new user"""
    def get(self):
        """Fetch user data from client and store in db."""
        # Validity checks are done at client side.

        response = {'status': 'ok'}

        pos_text = request.args['pos_text']
        neg_text = request.args['neg_text']
        location = request.args['location']
        mobile = request.args['mobile']

        """if not self.parse_menu(request.files['menu']):
            response['status'] = 'failed'
            response['reason'] = 'Menu parsing failed'"""
        
        access_token = request.args['access_token']
        access_secret = request.args['access_secret']

        # Ping twitter and extract handle
        oauth = OAuth(access_token, access_secret, CONSUMER_KEY, CONSUMER_SECRET)
        t_client = Twitter(auth=oauth)
        handle = t_client.account.verify_credentials().get('screen_name', None)
        if not handle:
            response['status'] = 'failed'
            response['reason'] = 'Twitter handle verification failed'
        if response['status'] == 'ok':  # Everything fine till now
            Verify.otp = random.randint(1000, 9999)
            Verify.handle = handle
            functions.send_user(mobile, "Your OTP for Canary is %d" % Verify.otp)
            CURSOR.execute(
                "INSERT INTO users VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s')" %
                (handle, pos_text, neg_text, access_token, access_secret, mobile, location))
            CONN.commit()
        return response  # Now send OTP, if status ok
        # Client should now prompt for OTP
        # Client then sends OTP via /register/verify

    def parse_menu(self):
        """Parses menu uploaded as spreadsheet and stores the data in db."""
        # Returns True if passed, False otherwise
        pass


class Verify(Resource):
    """Resource for mobile verification"""
    # Has generated OTP as static variable

    def get(self):
        """Checks OTP"""
        response = {}
        user_otp = int(request.args['otp'])
        if user_otp == Verify.otp:
            response['status'] = 'ok'
        else:
            response['status'] = 'failed'
            response['reason'] = 'Mobile verification failed'
            CURSOR.execute("DELETE FROM users WHERE handle=%s", Verify.handle)
            CONN.commit()
        return response


mappings = [(AddUser, '/register')]
mappings.append((Verify, '/register/verify'))
