import joblib
import pickle


with open("vectorizer.pkl", "rb") as file:
    vectorizer = pickle.load(file)
loaded_classifier = joblib.load('recommendation_color_model.pkl')


def predict_color(teks):
    # teks = input("What do you want to create today?")
    teks_vector = vectorizer.transform([teks])  # vectorizing
    predictions = loaded_classifier.predict(teks_vector)
    return predictions


# Debug here
# strInput = input("Input text: ")
# strInput = "Aku ingin membuat website dengan warna elegant"
# print(predict_color(strInput))
