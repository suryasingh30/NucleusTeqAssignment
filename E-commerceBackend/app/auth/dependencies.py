from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from jose import jwt, JWTError
from app.core.config import settings

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/auth/login")
SECRET_KEY = settings.SECRET_KEY
ALGORITHM = "HS256"

def get_current_user(token: str = Depends(oauth2_scheme)):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        email = payload.get("sub")
        role = payload.get("role")

        if not isinstance(email, str) or not isinstance(role, str):
            raise HTTPException(status_code=401, detail="Invalid token payload")

        return {"email": email, "role": role}
        
    except JWTError:
        raise HTTPException(status_code=401,detail="token is invalid or expired")
    
def admin_required(user = Depends(get_current_user)):
    if user["role"] != "admin":
        raise HTTPException(status_code=403, detail="admin privileges required")
    return user