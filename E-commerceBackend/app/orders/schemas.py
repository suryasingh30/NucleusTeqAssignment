from pydantic import BaseModel
from datetime import datetime
from typing import List

class OrderItemOut(BaseModel):
    product_id: int
    quantity: int
    price: int

    class Config:
        orm_mode = True

class OrderOut(BaseModel):
    id: int
    status: int
    created_at: datetime
    items: List[OrderItemOut]

    class Config:
        orm_mode = True