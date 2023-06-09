import time
from model import predict_color

# First response
response1 = {
    "arrayOf": [
        1,
        "Please provide more information!",
        None
    ]
}

# Second response
response2 = {
    "arrayOf": [
        2,
        "Here are the design that we come up to, enjoy",
        [
            {
                "name": "Example One",
                "color1": "#FFFFFF",
                "color2": "#F1C71D",
                "color3": "#333333",
                "color4": "#B8B2B2"
            },
            {
                "name": "Example Two",
                "color1": "#FFFFFF",
                "color2": "#E1A140",
                "color3": "#532200",
                "color4": "#914110"
            },
            # Add more color palettes here
        ]
    ]
}

# Serialize to JSON
# response1_json = json.dumps(response1)
# response2_json = json.dumps(response2)

def checkString(string):
    if string == "I want an elegant website":
        return response2
    else:
        predicted_color = predict_color(string)
        data_set = {
            'Message': predicted_color,
            'Timestamp': time.time()
        }
        return data_set
    # else:
    #     return response1