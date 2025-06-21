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
git clone <your-repo-url>
cd ecommerce-backend
