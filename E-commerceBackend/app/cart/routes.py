from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.core.database import SessionLocal
from app.models.cart import CartItem
from app.models.product import Product
from app.cart.schemas import CartCreate, CartUpdate, CartItemOut
from app.auth.dependencies import get_current_user

router = APIRouter(prefix="/cart", tags=["Cart"])

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

router.post("/", response_model=CartItemOut)
def add_to_cart(item: CartCreate, db: Session = Depends(get_db), user=Depends(get_current_user)):
    existing = db.query(CartItem).filter_by(user_id=user.id, product_id=item.product_id).first()
    if existing:
        new_quantity = existing.quantity + item.quantity
        setattr(existing, 'quantity', new_quantity)
        db.commit()
        db.refresh(existing)
        return existing
    
    new_item = CartItem(user_id=user.id, product_id=item.product_id, quantity=item.quantity)
    db.add(new_item)
    db.commit()
    db.refresh(new_item)
    return new_item

@router.get("/", response_model=list[CartItemOut])
def get_cart(db: Session = Depends(get_db), user = Depends(get_current_user)):
    return db.query(CartItem).filter_by(user_id = user.id).all()

@router.put("/{item_id}", response_model=CartItemOut)
def update_cart(item_id: int, data: CartUpdate, db: Session = Depends(get_db), user = Depends(get_current_user)):
    cart_item = db.query(CartItem).filter_by(id = item_id, user_id = user.id).first()
    if not cart_item:
        raise HTTPException(status_code=404, detail="item not found in cart")
    
    # cart_item.quantity = data.quantity
    setattr(cart_item, 'quantity', data.quantity)
    db.commit()
    db.refresh(cart_item)
    return cart_item

@router.delete("/{item_id}")
def remove_cart_item(item_id: int, db: Session = Depends(get_db), user = Depends(get_current_user)):
    cart_item = db.query(CartItem).filter_by(id = item_id, user_id = user.id).first()
    if not cart_item:
        raise HTTPException(status_code=404, detail="item not found in cart")
    
    db.delete(cart_item)
    db.commit()
    return {"message": "item deleted"}