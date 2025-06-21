from sqlalchemy.orm import Session
from app.core.database import SessionLocal, engine
from app.models.product import Product
from app.core.database import Base

def seed_products():
    Base.metadata.create_all(bind=engine)
    db: Session = SessionLocal()

    sample_products = [
        {
            "name": "Dell XPS 13",
            "description": "13-inch laptop with InfinityEdge display.",
            "price": 999.99,
            "quantity": 15,
        },
        {
            "name": "Nike Air Max",
            "description": "Comfortable and stylish running shoes.",
            "price": 129.99,
            "quantity": 30,
        },
        {
            "name": "Apple iPhone 14",
            "description": "Latest iPhone with improved camera and battery.",
            "price": 1099.00,
            "quantity": 20,
        },
        {
            "name": "The Pragmatic Programmer",
            "description": "Classic book for software developers.",
            "price": 39.99,
            "quantity": 50,
        },
        {
            "name": "Samsung 55\" Smart TV",
            "description": "4K UHD LED TV with smart features.",
            "price": 599.99,
            "quantity": 10,
        },
    ]

    for p in sample_products:
        existing = db.query(Product).filter(Product.name == p["name"]).first()
        if not existing:
            product = Product(**p)
            db.add(product)

    db.commit()
    db.close()
    print("product seeded")

if __name__ == "__main__":
    seed_products()