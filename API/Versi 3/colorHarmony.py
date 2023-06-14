from random import randint
from colorsys import hsv_to_rgb


def convertPalette(palette):
    (h, s, v) = (palette[0], palette[1], palette[2])

    # normalize
    (h, s, v) = (h / 360, s / 100, v / 100)

    # convert to RGB
    (r, g, b) = hsv_to_rgb(h, s, v)

    # expand RGB range
    (r, g, b) = (int(r * 255), int(g * 255), int(b * 255))

    return rgb_to_hex(r, g, b)


def rgb_to_hex(r, g, b):
    return '#{:02X}{:02X}{:02X}'.format(r, g, b)


def rgb2hsv(r, g, b):
    # Normalize R, G, B values
    r, g, b = r / 255.0, g / 255.0, b / 255.0

    # h, s, v = hue, saturation, value
    max_rgb = max(r, g, b)
    min_rgb = min(r, g, b)
    difference = max_rgb-min_rgb

    # if max_rgb and max_rgb are equal then h = 0
    if max_rgb == min_rgb:
        h = 0

    # if max_rgb==r then h is computed as follows
    elif max_rgb == r:
        h = (60 * ((g - b) / difference) + 360) % 360

    # if max_rgb==g then compute h as follows
    elif max_rgb == g:
        h = (60 * ((b - r) / difference) + 120) % 360

    # if max_rgb=b then compute h
    elif max_rgb == b:
        h = (60 * ((r - g) / difference) + 240) % 360

    # if max_rgb==zero then s=0
    if max_rgb == 0:
        s = 0
    else:
        s = (difference / max_rgb) * 100

    # compute v
    v = max_rgb * 100
    # return rounded values of H, S and V
    return tuple(map(round, (h, s, v)))


# input r g b
def generateMonochrome(r, g, b):
    (h, s, v) = rgb2hsv(r, g, b)
    if (s + v > 100 and v):  # not grayscale
        # print("Not Grayscale")
        if (s >= 0 and s <= 20):
            temp1 = (h, s, randint(95, 100))

            stemp = randint(max(s+10, 21), 50)
            temp2 = (h, stemp, randint(85, 98))

            stemp = randint(max(stemp+10, 51), 80)
            vtemp = randint(85, 98)
            temp3 = (h, stemp, vtemp)

            stemp = randint(max(stemp+10, 81), 100)
            temp4 = (h, stemp, randint(vtemp-60, vtemp-45))

            # print(temp1, temp2, temp3, temp4)
            return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)

        elif (s > 20 and s <= 50):
            stemp = randint(5, min(s-3, 20))
            temp1 = (h, stemp, randint(95, 100))

            temp2 = (h, s, randint(85, 98))

            stemp = randint(max(stemp+10, 51), 80)
            vtemp = randint(85, 98)
            temp3 = (h, stemp, vtemp)

            stemp = randint(max(stemp+10, 81), 100)
            temp4 = (h, stemp, randint(vtemp-60, vtemp-45))

            # print(temp1, temp2, temp3, temp4)
            return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)

        elif (s > 50 and s <= 80):
            stemp = randint(21, min(s-10, 50))
            temp2 = (h, stemp, randint(95, 100))

            stemp = randint(5, min(stemp-3, 20))
            temp1 = (h, stemp, randint(95, 100))

            vtemp = randint(85, 98)
            temp3 = (h, s, vtemp)

            stemp = randint(max(stemp+10, 81), 100)
            temp4 = (h, stemp,
                     randint(vtemp-60, vtemp-45))

            # print(temp1, temp2, temp3, temp4)
            return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)

        else:
            stemp = randint(51, min(s-10, 80))
            vtemp = randint(85, 98)
            temp3 = (h, stemp, vtemp)

            stemp = randint(21, min(stemp-10, 50))
            temp2 = (h, stemp, randint(95, 100))

            stemp = randint(5, min(stemp-3, 20))
            temp1 = (h, stemp, randint(95, 100))

            temp4 = (h, s, randint(vtemp-60, vtemp-45))

            # print(temp1, temp2, temp3, temp4)
            return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)

    elif (v <= 40):
        # print("Grayscale hitam")
        temp1 = (0, 0, 98)
        temp3 = (h, randint(65, 100), randint(87, 100))
        temp4 = (h, s, v)

        stemp = randint(20, 100)
        vtemp = randint(max(v+25, 41), 85)
        temp2 = (h, stemp, vtemp)

        # print(temp1, temp2, temp3, temp4)
        return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)

    elif (v > 40):
        # print("Grayscale putih")
        temp1 = (0, 0, 98)
        temp3 = (h, randint(65, 100), randint(87, 100))

        temp2 = (h, s, v)
        stemp = randint(0, 100)
        vtemp = randint(0, min(v-25, 40))
        temp4 = (h, stemp, vtemp)

        # print(temp1, temp2, temp3, temp4)
        return convertPalette(temp1), convertPalette(temp2), convertPalette(temp3), convertPalette(temp4)
