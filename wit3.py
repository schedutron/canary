from requests import get
from wit import Wit

    


client = Wit(access_token='TFVKPDYHXP74N5VN4VALBSNY4PYWGX5C')
while True:
    print(client.message(input()))

"""
wit_client = Wit(access_token=WIT_TOKEN)
                resp = wit_client.message(tweet)
                value = []
if resp['entities']:
    for entity in entities:    
        if entity in resp['entities'].keys():
            present_entities.append(entity)
            for present_entity in present_entities:
                value.append(present_entity, resp['entities'][present_entity][0]['value'])
                values.append(value)
        else:
            values.append(None)
"""            

#Values is a list which contains entities
