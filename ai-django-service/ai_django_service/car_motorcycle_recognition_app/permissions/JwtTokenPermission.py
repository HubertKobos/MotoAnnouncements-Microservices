from rest_framework.permissions import BasePermission
from django.conf import settings
import jwt
import base64

class JwtTokenPermission(BasePermission):
    message = 'Token denied'

    def has_permission(self, request, view):
        print(request.META)
        token = request.META.get("HTTP_AUTHORIZATION").split(" ")[1]

        if not token:
            return False
        else:
            try:
                print("passed token -> ", token)
                print("works here")
                secret_key = settings.JWT_SECRET_KEY
                secret_key_bytes = base64.b64decode(secret_key)

                decoded_payload = jwt.decode(
                    token, secret_key_bytes, algorithms=['HS256'])
                print(decoded_payload)
                return True
            except Exception as ex:
                return False

