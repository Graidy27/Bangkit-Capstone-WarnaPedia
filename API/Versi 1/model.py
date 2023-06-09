import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences

# Import dataset
dataset = pd.read_csv("./color_dataset.csv")

# Bagi dataset menjadi data fitur (X) dan label (y)
X = dataset["text"]
y = dataset["warna"]

# Encoding label dengan one-hot encoding
labels = pd.get_dummies(y)

# Bagi dataset menjadi data pelatihan dan data pengujian
X_train, X_test, y_train, y_test = train_test_split(
    X, labels, test_size=0.2, random_state=123)

# Membuat tokenisasi
filt = '!"#$%&()*+.,-/:;=?@[\]^_`{|}~ '  # Untuk menghilangkan symbols
tokenizer = Tokenizer(num_words=2000, oov_token="<OOV>", filters=filt)
tokenizer.fit_on_texts(X_train)

# Color Dict
color_dict = {
    0: 'Biru Muda',
    1: 'Hijau Pucat',
    2: 'Kuning Terang',
    3: 'Ungu Tua',
    4: 'Hijau Daun',
    5: 'Mint',
    6: 'Biru Laut',
    7: 'Hijau Muda',
    8: 'Emas',
    9: 'Merah Jambu',
    10: 'Abu-abu',
    11: 'Merah Cerah',
    12: 'Oranye',
    13: 'Hitam',
    14: 'Ungu Muda',
    15: 'Kuning Cerah',
    16: 'Merah Maroon',
    17: 'Putih',
    18: 'Cokelat',
    19: 'Kuning Ceria',
    20: 'Merah Muda',
    21: 'Oranye Terang',
    22: 'Abu-abu Metalik',
    23: 'Hijau Terang',
    24: 'Biru Royal',
    25: 'Hijau Daun Muda',
    26: 'Kuning Kunyit dan Emas',
    27: 'Merah Anggur',
    28: 'Emerald, Biru Laut',
    29: 'Biru Navy',
    30: 'Biru Pastel',
    31: 'Hijau Zamrud',
    32: 'Kuning Lemon',
    33: 'Merah Darah',
    34: 'Lavender',
    35: 'Ungu Lavender',
    36: 'Kuning Matahari',
    37: 'Merah Menyala',
    38: 'Jingga',
    39: 'Biru Langit',
    40: 'Mawar',
    41: 'Silver'
}

# Load the model
model = load_model('color_sentiment.h5')


# Contoh prediksi warna berdasarkan masukan pengguna
def predict_color(input_text):
    input_text = tokenizer.texts_to_sequences([input_text])
    input_text = pad_sequences(input_text, maxlen=20, padding="post")
    predicted_probabilities = model.predict(input_text)[0]
    predicted_label = np.argmax(predicted_probabilities)
    predicted_color = color_dict[predicted_label]
    return predicted_color

# Preprocess your input data and make predictions
# input_text = "kasih aku warna yang bikin aku jadi keliatan kalem"
# predicted_color = predict_color(input_text)
# print("Prediksi warna:", predicted_color)
