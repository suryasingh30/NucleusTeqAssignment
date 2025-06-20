from pydantic import BaseModel

class CartCreate(BaseModel):
    product_id: int
    quantity: int

class CartUpdate(BaseModel):
    quantity: int

class CartItemOut(BaseModel):
    id: int
    product_id: int
    quantity: int

    class Config:
        orm_mode = True