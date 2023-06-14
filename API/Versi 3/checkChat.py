from model import predict_color
from colorHarmony import generateMonochrome
from random import randint
import re

def extract_hex_codes(string):
    hex_codes = re.findall(r'\b[0-9a-fA-F]{6}\b', string)
    return hex_codes


def checkHeksaRequest(string):
    flag = 0
    for char in string:
        if(flag == 0):
            if(char == "#"): 
                flag = 1
        else:
            if(flag == 7): 
                if(char == ' ' or char == ',' or char == '.'):
                    return flag
                else:
                    flag = -1
                    return flag
            elif(flag == -1): return flag
            elif(flag >= 1 and flag < 7):
                if(char >= 'A' and char <= 'F'):
                    flag = flag+1
                
                elif(char >= 'a' and char <= 'f'):
                    flag = flag+1
                
                elif(char >= '0' and char <= '9'):
                    flag = flag+1
                
                else:
                    flag = -1
    # print("Flag= " + str(flag))
    return flag

def colorBlind(string, blindType):
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
        listOfColour = []
        # Deuteranomaly (~red)
        if(blindType == '1'):
            # Red
            tempHue = randint(20, 60)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(20, 60)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # Green
            tempHue = randint(90, 150)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(90, 150)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # Blue
            tempHue = randint(190, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(190, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

        # Protanomaly   (~green)    -> biru, kuning, ungu
        elif (blindType == '2'):
            # Blue
            tempHue = randint(200, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(200, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # Red
            tempHue = randint(10, 40)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(10, 40)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # Green
            tempHue = randint(70, 120)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(70, 120)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

        # Deuteranopia  (!green)    -> biru, kuning
        elif (blindType == '3'):
            # Red
            tempHue = randint(10, 50)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(10, 50)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # blue
            tempHue = randint(200, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(200, 260)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # yellow
            tempHue = randint(50, 70)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(50, 70)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

        # Tritanomaly (~blue)       -> merah, oren
        else:
            # blue
            tempHue = randint(180 , 240 )
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(180 , 240 )
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))

            # yellow
            tempHue = randint(30, 90)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))
            tempHue = randint(30, 90)
            listOfColour.append((tempHue, randint(0, 100), randint(0, 100)))


        for color in listOfColour:
            print(color)
            myList = []
            myDictionary = {
                "name": "",
                "color1": "",
                "color2": "",
                "color3": "",
                "color4": ""
            }
            index = 1
            for color in listOfColour:
                myDictionary["name"] = f'Contoh {index}'
                index = index + 1

                color = generateMonochrome(color[0], color[1], color[2], 1)

                myDictionary["color1"] = color[0]
                myDictionary["color2"] = color[2]
                myDictionary["color3"] = color[3]
                myDictionary["color4"] = color[1]
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
            return response
    

def checkString(string):
    flag = checkHeksaRequest(string)
    # print(flag)
    if(flag == -1):
        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 1,
                "message": "Maaf, saya tidak dapat memahami permintaan anda. Apakah terdapat salah penulisan pada kode heksa anda?",
                "colorPalette": None
            }
        }
        return response
    elif(flag == 7):
        
        hex_codes = extract_hex_codes(string)
        myList = []
        myDictionary = {
            "name": "",
            "color1": "",
            "color2": "",
            "color3": "",
            "color4": ""
        }
        for hex_code in hex_codes:
            tempNama = hex_code
            myDictionary["name"] = f'Monokrom #{tempNama} 1'
            # hex_code = hex_code.lstrip('#')
            hex_code = tuple(int(hex_code[i:i+2], 16)
                            for i in (0, 2, 4))
            temp = hex_code
            hex_code = generateMonochrome(hex_code[0], hex_code[1], hex_code[2], 0)

            myDictionary["color1"] = hex_code[0]
            myDictionary["color2"] = hex_code[2]
            myDictionary["color3"] = hex_code[3]
            myDictionary["color4"] = hex_code[1]
            myList.append(myDictionary.copy())

            myDictionary["name"] = f'Monokrom #{tempNama} 2'
            hex_code = generateMonochrome(temp[0], temp[1], temp[2], 0)
            myDictionary["color1"] = hex_code[0]
            myDictionary["color2"] = hex_code[2]
            myDictionary["color3"] = hex_code[3]
            myDictionary["color4"] = hex_code[1]
            myList.append(myDictionary.copy())


        response = {
            "error": False,
            "message": "Success",
            "chat": {
                "type": 2,
                "message": "Berikut merupakan desain yang dapat kami berikan. Jika terdapat heksa code yang tidak memiliki palet warna, besar kemungkinan terjadi kesalahan penulisan pada saat input.",
                "colorPalette": myList
            }
        }
        return response


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
            predict = generateMonochrome(predict[0], predict[1], predict[2], 0)

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