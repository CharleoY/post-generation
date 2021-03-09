from PERS.crawl import tweet
from PERS.p_model import PERS
from PERS.preprocess import preprocess
from flask import Flask, jsonify,request
import joblib
import sys
import pandas as pd
app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    print(request.get_json())
    username = request.json['username']

    text = tweet(username)
    text = clean.clean(text, omit=False, lower=True)

    result = model.predict([text])
    return jsonify({'prediction': result})

if __name__ == '__main__':
    tfidf_path = '/home/yang/WinD/PERS/preprocess.pkl'
    # tfidf_path = ''
    model_path = ['/home/yang/WinD/PERS/model/ei_model.pkl',
                  '/home/yang/WinD/PERS/model/sn_model.pkl',
                  '/home/yang/WinD/PERS/model/tf_model.pkl',
                  '/home/yang/WinD/PERS/model/jp_model.pkl']

    # app.run(port=8080)

    try:
        port = int(sys.argv[1])
    except Exception as e:
        port = 80

    try:
        model = PERS(tfidf_path, model_path)
        print('model loaded')
        clean = preprocess()
        print('preprocess loaded')

    except Exception as e:
        print('No model here')
        print('Train first')
        print(str(e))


    app.run(host='0.0.0.0', port=port,debug=True)



