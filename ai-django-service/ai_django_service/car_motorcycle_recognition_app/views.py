from rest_framework.views import APIView
from rest_framework.response import Response
import tensorflow as tf
import os
import io
import numpy as np
from PIL import Image
from tensorflow.keras.preprocessing import image
from .permissions.ApiGatewayAdressPermision import ApiGatewayAdressPermision
from .permissions.JwtTokenPermission import JwtTokenPermission

# Create your views here.
class CarBikeRecognition(APIView):
    # permission_classes = [JwtTokenPermission]
    def post(self, request):
        print(request.data)
        model = self.load_model()
        images = request.FILES.getlist("images")
        converted_images = self.convert_data(images)
        predictions = self.make_predictions(converted_images, model)
        print("predictions", predictions)
        return Response(predictions) 
    
    def load_model(self):
        model_dir = os.path.join(os.path.dirname(__file__), 'two_classes_model')
        model = tf.keras.models.load_model(model_dir)
        return model
    
    def convert_data(self, images):
        converted_images = []
        TARGET_SIZE = (224, 224)
        for image_data in images:
            img = Image.open(io.BytesIO(image_data.read()))
            # Convert it to RGB
            img = img.convert("RGB")        
            img = img.resize(TARGET_SIZE)
            img = image.img_to_array(img)
            img = img / 255.0
            img = np.expand_dims(img, axis=0)
            converted_images.append(img)
        return converted_images
    
    def make_predictions(self, converted_images, model):
        all_predictions = []
        class_labels=["motorcycle", "car"]
        for image in converted_images:
            predictions = model.predict(image)
            predicted_class_index = np.argmax(predictions)
            predicted_class = class_labels[predicted_class_index]

            # Multiply each probability by 100 to convert to percentage
            predictions_in_percentage = [probability * 100 for probability in predictions[0]]
            all_predictions.append({"recognition_class": predicted_class, "probability": "{:.2f}".format(max(predictions_in_percentage))})
            
        return all_predictions