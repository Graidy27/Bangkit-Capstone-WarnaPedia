import time
from model import predict_color

# First response
gaberhasiltakCukup = {
    "error": False,
    "message": "Success",
    "chat": {
        "type": 1,
        "message": "I'm sorry. I don't understad your request. Please provide more information",
        "colorPalette": None
    }
}

berhasilCukup = {
    "error": False,
    "message": "Success",
    "chat": {
        "type": 2,
        "message": "Here are the design that we come up to, enjoy",
        "colorPalette": [
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
            }
        ]
    }
}


# Serialize to JSON
# response1_json = json.dumps(response1)
# response2_json = json.dumps(response2)


def checkString(string):
    res = str(len(string.split()))
    if int(res) < 4:
        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 1,
                "message": "I'm sorry. I don't understad your request. Please provide more information",
                "colorPalette": None
            }
        }
        print(response)
        return response

    else:
        predict = predict_color(string)
        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 2,
                "message": "Here are the design that we come up to, enjoy",
                "colorPalette": [
                    {
                        "name": "Example One",
                        "color1": predict[0][0],
                        "color2": predict[0][1],
                        "color3": predict[0][2],
                        "color4": predict[0][3]
                    }
                ]
            }
        }
        print(response)
        return response


# checkString("Hello world Ges welcom")
