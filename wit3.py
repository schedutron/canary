from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from __future__ import unicode_literals

import sys
from requests import get
from wit import Wit

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
    sentiment = first_entity_value(entities, 'Sentiment')
    restaurant = first_entity_value(entities, 'Restaurant')
    return entities
    

client = Wit(access_token=access_token)
client.interactive(handle_message=handle_message)
