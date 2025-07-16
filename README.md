# 💸 BillingApp – Smart Billing & Order Management System

![Version](https://img.shields.io/badge/BillingApp-v1.0-blue)

**BillingApp** is a complete web-based billing and order management platform designed for small businesses and retailers. The app combines the power of **React** on the frontend and **Spring Boot** on the backend, offering seamless user experience and secure payment processing with **Razorpay**.

---

## 🌟 Key Features

- 🔐 **Role-Based Access**: Admins manage users, categories, and items; users can place and track orders.
- 🗂️ **Item & Category Control**: Admins can add/remove categories and items.
- 🛍️ **Order Management**: Create, track, and view order history.
- 💳 **Payment Gateway**: Integrated Razorpay payment support.
- 📊 **Dashboard**: Real-time stats for users.
- 📱 **Mobile-Responsive**: Built with React and Bootstrap for responsive UI.

---

## 🛠 Tech Stack

- **Frontend**: React ⚛️, Bootstrap 🎨  
- **Backend**: Spring Boot 🌿, Java ☕, Lombok  
- **Database**: MySQL 🗄️  
- **Payments**: Razorpay  
- **Tools**: Maven, Git, Postman

---

## 📋 Data Entities

> _Admin APIs handle categories and items. Users access only relevant data._

- **Category**
  - Fields: `id`, `name`
  - Relation: One-to-Many with `Item`

- **Item**
  - Fields: `id`, `name`, `price`, `categoryId`
  - Relation: Many-to-One with `Category`

- **Order**
  - Fields: `id`, `userId`, `total`, `createdAt`, `status`
  - Relation: One-to-Many with `OrderItem`

- **OrderItem**
  - Fields: `id`, `orderId`, `itemId`, `quantity`
  - Relation: Many-to-One with `Order`

- **User**
  - Fields: `id`, `username`, `email`, `password`, `role` (ADMIN/USER)

---

## 🌐 API Overview

Base URL: `/api/v1.0`

| Method | Endpoint                             | Description                         | Access          |
|--------|--------------------------------------|-------------------------------------|-----------------|
| POST   | `/login`                             | User login                          | Public          |
| GET    | `/admin/categories`                  | List all categories                 | Admin           |
| GET    | `/admin/categories/{id}`             | Get category by ID                  | Admin           |
| DELETE | `/admin/categories/{id}`             | Delete category                     | Admin           |
| GET    | `/admin/items`                       | List all items                      | Admin           |
| DELETE | `/admin/items/{id}`                  | Delete item                         | Admin           |
| POST   | `/orders`                            | Create new order                    | Authenticated   |
| GET    | `/orders/{id}`                       | Get order by ID                     | Authenticated   |
| GET    | `/orders/latest`                     | Fetch latest order                  | Authenticated   |
| POST   | `/payments/create-order`             | Create Razorpay order               | Authenticated   |
| POST   | `/payments/verify`                   | Verify payment                      | Authenticated   |
| POST   | `/admin/register`                    | Register a new user                 | Admin           |
| DELETE | `/admin/users/{id}`                  | Remove a user                       | Admin           |

---

## 👥 User Guide

### 🔸 Admin Capabilities
- Manage items and categories.
- Register new users or delete existing ones.
- Use `/admin/...` endpoints for full control.

### 🔹 User Functionality
- Authenticate using `/login`.
- Explore items (via frontend, not direct API).
- Create and view orders.
- Make payments via Razorpay.
- Monitor activity through the dashboard.

---

## 🚀 Local Setup Instructions

### 📌 Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8+
- Maven

### 📥 Clone the Repo

```bash
git clone https://github.com/GURUPRASATH-OG/BillingApp.git
cd BillingApp
## ⚙️ Backend Setup

```bash
cd BillinApplication
```

- **Create MySQL Database**:

```sql
CREATE DATABASE billingapp;
```

- **Update your `application.properties`** file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/billingapp
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
razorpay.api.key=your_razorpay_key
razorpay.api.secret=your_razorpay_secret
```

- **Run the backend**:

```bash
mvn clean install
mvn spring-boot:run
```
## 🔐 Admin Setup Instructions

Before you can use the application as an admin, follow these steps:

1. **Create an encoded password**  
   Use Postman (or any API testing tool) to call the `/encode` endpoint (provided in your backend) to generate a hashed password from a plain text password.

2. **Insert the admin user into the database**  
   Run the following SQL commands in your MySQL console:

   ```sql
   USE billingapp;

   INSERT INTO users(name, email, password, role, created_at, updated_at, user_id)
   VALUES (
       'mynk',
       'mynk@gmail.com',
       'paste_the_encoded_password_here',
       'ROLE_ADMIN',
       CURRENT_TIMESTAMP(),
       CURRENT_TIMESTAMP(),
       UUID()
   );
3. **Restart the backend application**  
   After inserting the admin user, stop the Spring Boot server if it’s running and start it again to apply the changes.

4. **Log in as admin**  
   Use the admin email and password you created to log in to the application.


---

## 🌐 Frontend Setup

```bash
cd BillingSoftware
npm install
npm start
```

---

## 🔗 Access the App

- **Frontend**: [http://localhost:5173](http://localhost:5173)  
- **Backend API**: [http://localhost:8080/api/v1.0](http://localhost:8080/api/v1.0)

---

## 📁 Project Structure

### 🖥️ Backend (`BillinApplication`)

- `entity/`: JPA Models  
- `dto/`: Request and Response DTOs  
- `mapper/`: Mapping between DTOs and entities  
- `repository/`: JPA Repositories  
- `service/`: Business logic  
- `controller/`: API Controllers  
- `config/`, `filter/`, `util/`: Security, JWT, utility classes

### 💻 Frontend (`BillingSoftware`)

- Modular components (React)
- Uses Axios for HTTP requests
- Bootstrap for styling and responsiveness

---

## 🤝 Contributing

1. **Fork the repository** 🍴  
2. **Create a new feature branch**:

   ```bash
   git checkout -b feature/your-feature
   ```

3. **Commit your changes**:

   ```bash
   git commit -m "Add your feature"
   ```

4. **Push and create a pull request** 🚀

---

## 📧 Contact

For issues, feature requests, or support, please open a GitHub issue or start a discussion.

