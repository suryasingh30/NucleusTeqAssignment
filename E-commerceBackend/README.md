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


2️⃣ Create Virtual Environment
bash
Copy code
python -m venv venv
source venv/bin/activate     # For Linux/macOS
venv\Scripts\activate        # For Windows
3️⃣ Install Dependencies
bash
Copy code
pip install -r requirements.txt
4️⃣ Run the Application
bash
Copy code
uvicorn app.main:app --reload
📖 Swagger UI: http://localhost:8000/docs

🧪 Seed Sample Products
Populate the database with demo products:

bash
Copy code
python seed_products.py
🧾 Sample Admin Signup (via /auth/signup)
json
Copy code
{
  "username": "admin1",
  "email": "admin@email.com",
  "password": "securepass",
  "role": "admin"
}
📂 Project Structure
csharp
Copy code
app/
├── auth/            # JWT auth, signup/login, dependencies
├── cart/            # Cart endpoints + schemas
├── core/            # Database, config, logging
├── models/          # SQLAlchemy models
├── orders/          # Checkout logic, order history
├── products/        # Admin product CRUD + schemas
├── public/          # Public product browsing
├── main.py          # FastAPI entrypoint
seed_products.py     # Product seeding script
📦 Core Models
User: id, username, email, password, role

Product: id, name, description, price, quantity

CartItem: id, user_id, product_id, quantity

Order: id, user_id, status, created_at

OrderItem: id, order_id, product_id, quantity, price

🔐 Access Levels
👨‍💼 Admin
Can Create/Update/Delete Products: /admin/products

Can View All Products

👤 User
Browse Products: /products

Manage Cart: /cart

Checkout: /orders/checkout

View Order History: /orders

✨ Future Extensions (To-Do)
🛒 Payment Gateway Integration (Razorpay/Stripe)

🖼️ Product Image Upload

📩 Email Notifications on Order

🧾 Invoice Generation (PDF)

🧑‍💼 Admin Dashboard Frontend (React/Next.js)

🧑‍🏫 How to Explain This Project (Presentation Summary)
This ecommerce backend API simulates the core of a real online store using FastAPI.
Key modules include:

Authentication using JWTs

Role-based authorization (admin for management, user for shopping)

Product management (CRUD by admin)

Cart system with quantity support

Checkout logic that converts cart → order and updates inventory

User order history tracking

Seed script to populate test data

This project is cleanly structured and scalable, ideal for real-world integration with a frontend like React or Next.js.

👨‍💻 Author
Surya Singh – Full-stack Developer
📧 Email: your-email@example.com
🔗 GitHub: github.com/<your-username>
🔗 LinkedIn: linkedin.com/in/<your-handle>

🧰 Bonus Tools Available (on request)
✅ requirements.txt auto-generator

✅ PDF version of this README.md

✅ HTML template for portfolio websites

✅ AI-generated slide deck for presentation

diff
Copy code

Let me know if you want this exported as:
- 📄 PDF
- 🎨 Canva-style slide outline
- 📦 Ready-to-deploy GitHub repo template

I can also help with:
- README badge styling
- Project cover image
- One-liner for LinkedIn/GitHub bio

Just say the word!