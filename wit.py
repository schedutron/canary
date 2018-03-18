from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from __future__ import unicode_literals

import sys
from requests import get
from wit import Wit
#TFVKPDYHXP74N5VN4VALBSNY4PYWGX5C
if len(sys.argv) != 2:
    print('usage: python ' + sys.argv[0] + ' <wit-token>')
    exit(1)
access_token = sys.argv[1]

# Celebrities example
# See https://wit.ai/aforaleka/wit-example-celebrities/


def first_entity_value(entities, entity):
    if entity not in entities:
        return None
    val = entities[entity][0]['value']
    if not val:
        return None
    return val


def handle_message(response):
    entities = response['entities']
    greetings = first_entity_value(entities, 'greetings')
    Restaurant = first_entity_value(entities, 'Restaurant')
    sentiment = first_entity_value(entities, 'sentiment')
    if Restaurant:
        # We can call the wikidata API using the wikidata ID for more info
        return 'Seems you are hungry. Checkout our restaurant'
    elif greetings:
        return 'Hi! How are you?'
    elif sentiment:
        if sentiment == 'positive':
            return 'Awesome'
        else:
            return 'That is sad'
    else:
        return "Um. I don't recognize that name. " \
                "Which celebrity do you want to learn about?"

client = Wit(access_token=access_token)
client.interactive(handle_message=handle_message)
