from flask import *
import os
from checkChat import checkString
import time
import json

app = Flask(__name__)


@app.route('/', methods=['GET'])
def home_page():
    data_set = {
        'Page': 'Home',
        'Message': 'Successfully loaded the Home page',
        'Timestamp': time.time()
    }
    json_dump = json.dumps(data_set)

    return json_dump


@app.route('/chat/', methods=['POST'])
def request_page():
    data = request.get_json()
    string = data.get('string')  # /chat/?string=""
    colorblind = data.get('colorblind')
    print(colorblind)
    data_set = checkString(string)

    json_dump = json.dumps(data_set)

    return json_dump


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=7777)
    # app.run(debug=True, host="0.0.0.0", port=int(os.environ.get("PORT, 8080")))
