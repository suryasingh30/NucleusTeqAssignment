from pydantic import BaseModel

class ProductCreate(BaseModel):
    name: str
    description: str
    price: float
    quantity: int

class ProductUpdate(ProductCreate):
    pass

class ProductOut(ProductCreate):
    id: int

    class Config:
        orm_mode = True