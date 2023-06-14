import time
from model import predict_color
from colorHarmony import generateMonochrome

def checkString(string):
    res = str(len(string.split()))
    if int(res) < 4:
        # print("Ga paham chat user")
        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 1,
                "message": "Maaf, saya tidak dapat memahami permintaan anda. Tolong berikan informasi yang lebih detail.",
                "colorPalette": None
            }
        }
        # print(response)
        return response

    else:
        # print("Paham")
        predicts = predict_color(string)
        myList = []
        myDictionary = {
            # "id": 0,
            "name": "",
            "color1": "",
            "color2": "",
            "color3": "",
            "color4": ""
        }

        indexList = 1
        for predict in predicts[0]:
            # print(predict)
            # if (indexList > 1):
            #     break
            # Menghilangkan karakter "#"
            predict = predict.lstrip('#')

            # HEX to RGB
            predict = tuple(int(predict[i:i+2], 16)
                            for i in (0, 2, 4))

            # RGB to HSV
            predict = generateMonochrome(predict[0], predict[1], predict[2])

            # print(predict)
            # print(indexList)
            myDictionary["name"] = f'Monokrom {indexList}'
            myDictionary["color1"] = predict[0]
            myDictionary["color2"] = predict[2]
            myDictionary["color3"] = predict[3]
            myDictionary["color4"] = predict[1]
            indexList = indexList + 1
            # print
            # print(myList)
            myList.append(myDictionary.copy())
        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 2,
                "message": "Berikut merupakan desain yang dapat kami berikan",
                "colorPalette": myList
            }
        }
        # print(response)
        return response