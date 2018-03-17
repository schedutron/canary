"""High level functions for bot management"""

from twilio.rest import Client
from src.credentials import TWILIO_PHONE_NUMBER, TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN
import src.database as database

client = Client(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN)

def get_mobile(cur, handle):
    cur.execute("SELECT mobile FROM users WHERE handle=%s", handle)
    row = cur.fetchone()
    return str(row[0])


def send_user(mobile_num_str, message):
    if not mobile_num_str.startswith('+'):
        mobile_num_str = "+91" + mobile_num_str  # Defaults to India for now
    message = client.messages.create(
    to=mobile_num_str, 
    from_=TWILIO_PHONE_NUMBER,
    body=message)
