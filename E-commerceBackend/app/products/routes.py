from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.core.database import SessionLocal
from app.models.product import Product
from app.products.schemas import ProductCreate, ProductUpdate, ProductOut
from app.auth.dependencies import admin_required

router = APIRouter(prefix="/admin/products", tags=["Admin Products"])

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.get("/", response_model=list[ProductOut])
def get_all_products(db: Session = Depends(get_db), user = Depends(admin_required)):
    return db.query(Product).all()

@router.get("/{product_id}", response_model=ProductOut)
def get_product(product_id: int, db: Session = Depends(get_db), user = Depends(admin_required)):
    product = db.query(Product).filter(Product.id == product_id).first()
    if not product:
        raise HTTPException(status_code=404, detail="product not found")
    return product

@router.post("/", response_model=ProductOut)
def create_product(product_data: ProductCreate, db: Session = Depends(get_db), user = Depends(admin_required)):
    product = Product(**product_data.dict())
    db.add(product)
    db.commit()
    db.refresh(product)
    return product

@router.put("/{product_id}", response_model=ProductOut)
def update__product(product_id: int, product_data: ProductUpdate, db: Session=Depends(get_db), user = Depends(admin_required)):
    product = db.query(Product).filter(Product.id == product_id).first()
    if not product:
        raise HTTPException(status_code=404, detail="product not found")
    
    for key, value in product_data.dict().items():
        setattr(product, key, value)

    db.commit()
    db.refresh(product)
    return product

@router.delete("/{product_id}")
def delete_product(product_id: int, db: Session = Depends(get_db), user = Depends(admin_required)):
    product = db.query(Product).filter(Product.id == product_id).first()
    if not product:
        raise HTTPException(status_code=404, detail="product not found")
    
    db.delete(product)
    db.commit()
    return {"message": "product deleted"}