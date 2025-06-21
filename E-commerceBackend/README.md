# 🛒 Ecommerce Backend API - FastAPI

A fully-featured, role-based ecommerce backend built with **FastAPI** and **SQLAlchemy**. It handles product management, user authentication, cart handling, and order checkout — simulating real-world shopping backend logic.

---

## 🚀 Features

- ✅ JWT Authentication (signup/login)
- ✅ Role-based access: `admin` and `user`
- ✅ Admin-only Product CRUD
- ✅ Public Product Listing with Pagination & Search
- ✅ Cart System (Add, Update, View, Delete Items)
- ✅ Checkout Flow (Order creation + inventory deduction)
- ✅ Order History for Users
- ✅ Database seeding script (`seed_products.py`)
- ✅ Modular, production-ready folder structure

---

## 🧠 Tech Stack

- **Backend**: FastAPI, SQLAlchemy
- **Database**: SQLite (for dev; extendable to PostgreSQL/MySQL)
- **Auth**: JWT via `python-jose`, password hashing via `passlib`
- **Environment Config**: `python-dotenv`
- **Docs**: Auto-generated Swagger UI

---

## 🛠️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone <https://github.com/suryasingh30/NucleusTeqAssignment/tree/master/E-commerceBackend>
```

### 2️⃣ Create Virtual Environment
```bash
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
```

### 3️⃣ Install Dependencies
```bash
pip install -r requirements.txt
```

### 4️⃣ Run the App
```bash
uvicorn app.main:app --reload
```

📖 Open Swagger UI: [http://localhost:8000/docs](http://localhost:8000/docs)

---

## 🧪 Seed Sample Products

Run this script to populate the database with test products:
```bash
python seed_products.py
```

---

## 🧾 Sample Admin Signup (via `/auth/signup`)
```json
{
  "username": "admin1",
  "email": "admin@email.com",
  "password": "securepass",
  "role": "admin"
}
```

---

## 📂 Project Structure

```
ecommerce-backend/
├── app/
│   ├── auth/            # Auth routes, JWT utils, dependencies
│   ├── cart/            # Cart routes + schemas
│   ├── core/            # DB connection, config, logging
│   ├── models/          # All SQLAlchemy models
│   ├── orders/          # Checkout, order history
│   ├── products/        # Admin product CRUD + schemas
│   ├── public/          # Public product listing/search
│   └── main.py          # FastAPI app entry point
├── seed_products.py     # Script to add sample products
├── requirements.txt     # Python dependencies
└── README.md
```

---

## 📦 Core Models

- **User**: `id`, `username`, `email`, `password`, `role`
- **Product**: `id`, `name`, `description`, `price`, `quantity`
- **CartItem**: `id`, `user_id`, `product_id`, `quantity`
- **Order**: `id`, `user_id`, `status`, `created_at`
- **OrderItem**: `id`, `order_id`, `product_id`, `quantity`, `price`

---

## 🔐 Access Levels

- **Admin**:
  - Create/update/delete products (`/admin/products`)
  - View all products
- **User**:
  - View products
  - Manage cart
  - Checkout
  - View order history

---

## 🎯 Use Cases

- Online store backend
- Admin product inventory system
- Full-stack ecommerce project (React + FastAPI)
- Training/portfolio project for real-world backend experience

---

## ✨ Future Extensions

- 🛒 Payment gateway integration (Razorpay / Stripe)
- 🖼️ Product image upload support
- 📩 Email notifications on order
- 🧾 Invoice generation
- 🧑‍💼 Admin dashboard frontend
- 🧠 Redis caching / background workers

---

## 🧠 Project Summary (Presentation)

This project is a modular, scalable **Ecommerce Backend API** developed using **FastAPI** and **SQLAlchemy**. It simulates the core backend logic behind any online store, including:

- **Authentication** with JWT and hashed passwords
- **Role-based access** for `admin` and `user`
- **Product CRUD** for admin
- **Public-facing product search/listing**
- **Cart management** and **order checkout**
- **Inventory management** on checkout
- **Order history** tracking per user

This backend mimics real systems like Amazon/Flipkart and can be extended with payment processing and admin dashboards.

---

## 👨‍💻 Author

**Surya Singh** – Full-stack Developer  
📧 Email: your.email@example.com  
🔗 GitHub: [github.com/yourusername](https://github.com/yourusername)  
🔗 LinkedIn: [linkedin.com/in/yourprofile](https://linkedin.com/in/yourprofile)

---

## 📝 License

This project is licensed under the MIT License.
