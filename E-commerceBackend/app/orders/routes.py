from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.core.database import SessionLocal
from app.models.cart import CartItem
from app.models.order import Order, OrderItem
from app.models.product import Product
from app.auth.dependencies import get_current_user
from typing import cast

router = APIRouter(prefix="/orders", tags=["Orders"])

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/checkout")
def checkout(db: Session = Depends(get_db), user=Depends(get_current_user)):
    cart_items = db.query(CartItem).filter_by(user_id=user.id).all()
    if not cart_items:
        raise HTTPException(status_code=400, detail="Cart is empty")

    order = Order(user_id=user.id)
    db.add(order)
    db.commit()
    db.refresh(order)

    for item in cart_items:
        product = db.query(Product).filter(Product.id == item.product_id).first()

        if product is None:
            raise HTTPException(400, "Product not found")

        if not cast(bool, product.is_active):
            raise HTTPException(400, "Product is inactive")

        product_quantity: int = cast(int, product.quantity)
        if product_quantity < item.quantity:    # type: ignore[operator]
            raise HTTPException(status_code=404, detail="Product out of stock")

        product.quantity = product_quantity - item.quantity # type: ignore[operator]

        order_item = OrderItem(
            order_id=order.id,
            product_id=product.id,
            quantity=item.quantity,
            price=product.price
        )
        db.add(order_item)

    db.query(CartItem).filter_by(user_id=user.id).delete()
    db.commit()

    return {"message": "checkout successful,order created.", "order_id": order.id}
