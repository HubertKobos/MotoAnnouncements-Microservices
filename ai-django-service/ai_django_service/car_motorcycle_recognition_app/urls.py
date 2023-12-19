from django.urls import path
from .views import CarBikeRecognition


urlpatterns = [
    path('car-bike/', CarBikeRecognition.as_view())
]
