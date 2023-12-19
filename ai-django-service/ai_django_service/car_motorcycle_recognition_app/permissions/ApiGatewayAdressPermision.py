from rest_framework import permissions
from django.conf import settings

class ApiGatewayAdressPermision(permissions.BasePermission):
    def has_permission(self, request, view):
        ip_addr = request.META['REMOTE_ADDR']
        if ip_addr in getattr(settings, "ALLOWED_IP", None):
            return True
        else: 
            return False