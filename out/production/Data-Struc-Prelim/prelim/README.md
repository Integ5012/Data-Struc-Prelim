# Gmail Simulator with Database

## Overview
This is an updated Gmail Simulator that now includes:
- **Direct email input**: Users can type recipient email addresses directly instead of selecting from a numbered list
- **Persistent database**: User accounts and emails are saved to a file (`users.dat`) and persist between program runs
- **Enhanced email validation**: Supports any valid email domain (not just gmail/hotmail)

## Features

### User Management
- **Registration**: Create new accounts with name, email, and password
- **Login**: Secure authentication system
- **Database persistence**: All user data is automatically saved and loaded

### Email System
- **Send emails**: Type recipient email address directly
- **Inbox**: View received emails
- **Sent folder**: View sent emails
- **Attachments**: Support for file attachments (metadata only)

### Database Features
- **Automatic saving**: User data is saved after every operation
- **Separate sent messages file**: All sent emails are stored in `sent_messages.txt`
- **Persistent storage**: Data survives program restarts

## How to Use

### Running the Program
```bash
javac -d out src/prelim/*.java
java -cp out prelim.GmailSimulator
```

### Main Menu Options
1. **Login** - Access existing account
2. **Register** - Create new account
3. **Exit** - Close the program

### Sending Emails
1. Login to your account
2. Choose "Send Email"
3. **Type the recipient's email address directly** (e.g., `user@slu.edu.ph`)
4. Enter subject and message body
5. Email is automatically sent and saved

### Supported Email Domains
The system now accepts any valid email format including:
- `@gmail.com`
- `@hotmail.com`
- `@slu.edu.ph`
- `@company.com`
- `@university.edu`
- And many more!

## Technical Details

### Database Storage
- **File**: `users.txt` (created automatically in program directory)
- **Format**: Human-readable text file with structured data
- **Content**: User accounts, emails, and attachments
- **Advantages**: Easy to read, debug, and manually edit if needed

### Classes
- **GmailSimulator**: Main application controller
- **UserDatabase**: Database management and persistence
- **User**: User account representation
- **Email**: Email message structure
- **Attachment**: File attachment metadata
- **EmailValidator**: Email format validation
- **LinkedList/Node**: Custom data structures
- **MyList**: Generic list interface

### Data Persistence
- User accounts are automatically saved after registration
- Email data is saved after sending/receiving
- Database file is loaded when program starts
- All changes are persisted immediately
- **Text Format**: Data is stored in human-readable format for easy debugging

## Benefits of the New System

1. **More realistic**: Users type email addresses directly like real email systems
2. **Persistent data**: No need to re-register users each time
3. **Flexible domains**: Support for educational, business, and personal email domains
4. **Better UX**: Cleaner interface without numbered lists
5. **Scalable**: Database can handle many users efficiently

## File Structure
```
src/prelim/
├── GmailSimulator.java    # Main application
├── UserDatabase.java      # Database management
├── User.java             # User account class
├── Email.java            # Email message class
├── Attachment.java       # File attachment class
├── EmailValidator.java   # Email validation
├── LinkedList.java       # Custom linked list
├── Node.java            # List node
└── MyList.java          # List interface

users.txt                 # Database file (created automatically)
sent_messages.txt         # Sent messages file (created automatically)
```
