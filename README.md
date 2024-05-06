# Bank Management System

This is a simple bank management system that allows users to create accounts, deposit money, withdraw money, and check their balance. The system is implemented in Spring Boot.

## Features

## Installation

## Usage

## Class Diagram

```mermaid
classDiagram
      class User {
          <<abstract>>
          +String name
          +String email
          +String password
      }
      class Customer {
          +String address
          +List~Account~ accounts
      }
      class Admin {
          +void addUser(User user)
          +void deleteUser(User user)
      }
      class Account {
          <<abstract>>
          #String accountNumber
          #Double balance
          +deposit(Double amount)
          +withdraw(Double amount)
      }
      class DepositAccount {
          +Double interestRate
      }
      class SavingsAccount {
          +int withdrawalLimit
      }
      class Transaction {
          -Account fromAccount
          -Account toAccount
          -Double amount
          -Date transactionDate
          +void execute()
      }
      
      User <|-- Customer
      User <|-- Admin
      Account <|-- DepositAccount
      Account <|-- SavingsAccount
      Customer "1" *-- "*" Account : owns
      Transaction "*" -- "1" Account : from
      Transaction "*" -- "1" Account : to
```

## Technologies Used

## Contributing

If you would like to contribute to this project, please open an issue or submit a pull request.
