from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from app.auth.schemas import UserCreate, UserLogin, Token
from app.models.user import User
from app.core.database import SessionLocal
from app.auth.utils import hash_password, verify_password, create_access_token

router = APIRouter(prefix="/auth", tags=["Auth"])
ROLES = ["user", "admin"]

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/signup", response_model=Token)
def signup(user_data: UserCreate, db: Session = Depends(get_db)):
    if db.query(User).filter((User.email == user_data.email) | (User.username == user_data.username)).first():
        raise HTTPException(status_code=400, detail="username or username already exists")
    
    if user_data.role not in ROLES:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Invalid role, role must be : {ROLES}"
        )
    
    hashed_pwd = hash_password(user_data.password)
    user = User(username = user_data.username, email = user_data.email, password = hashed_pwd, role = user_data.role)
    db.add(user)
    db.commit()
    db.refresh(user)

    access_token = create_access_token(data={"sub": user.email, "role": user.role})
    return {"access_token": access_token}

@router.post("/login", response_model=Token)
def login(user_data: UserLogin, db: Session = Depends(get_db)):
    user = db.query(User).filter(User.email == user_data.email).first()

    if not user or not verify_password(user_data.password, user.password):
        raise HTTPException(status_code=401, detail="invalid credentials")
    
    access_token = create_access_token(data={"sub": user.email, "role": user.role})
    return {"access_token": access_token}