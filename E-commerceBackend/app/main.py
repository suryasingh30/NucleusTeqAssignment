from fastapi import FastAPI
from app.core.config import settings
from app.core.logger import  logger
from app.core.database import Base, engine
from app import models
from app.auth.routes import router as auth_router
from app.products.routes import router as product_router
from app.public.routes import router as public_router
from app.cart.routes import router as cart_router
from app.orders.routes import router as order_router

app = FastAPI(title=settings.PROJECT_NAME)
app.include_router(auth_router)
app.include_router(product_router)
app.include_router(public_router)
app.include_router(cart_router)
app.include_router(order_router)

@app.on_event("startup")
def startup():
    logger.info("creating database")
    Base.metadata.create_all(bind=engine)

@app.get("/")
def root():
    logger.info("root endpoint")
    return {"message": "Ecommerce backend api"}

