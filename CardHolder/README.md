# Digital Business Card Generator

A Java console application that lets you create, save, and load digital business cards.

## What You'll Learn

This project teaches several important Java concepts:

- **Scanner** for getting user input from the console
- **FileWriter and FileReader** for saving and loading data to files
- **String formatting** for creating nice looking output
- **Menu driven loops** for program flow control
- **Static variables** for shared state across methods
- **Try with resources** for safe file handling

## How to Run

```bash
javac CardHolder.java
java CardHolder
```

## Features

1. **Create a card** with your personal and contact info
2. **Display your card** with a nice ASCII border
3. **Save to file** so you can use it later
4. **Load from file** to restore a saved card
5. **Edit individual fields** without starting over

## Sample Output

```
+--------------------------------------------------+
|                                                  |
|                   JOHN DOE                       |
|              Software Engineer                   |
|                 Tech Corp                        |
|                                                  |
|--------------------------------------------------|
|                                                  |
|  Address: 123 Main Street                        |
|  Phone: 555 1234                                 |
|  Email: john@example.com                         |
|  Web: johndoe.dev                                |
|                                                  |
+--------------------------------------------------+
```

## File Format

Cards are saved as simple text files with one field per line:

```
NAME:John Doe
TITLE:Software Engineer
COMPANY:Tech Corp
ADDRESS:123 Main Street
PHONE:555 1234
EMAIL:john@example.com
WEBSITE:johndoe.dev
LINKEDIN:linkedin.com/in/johndoe
```

## Try These Modifications

1. Add more fields like fax number or social media links
2. Add multiple card storage in memory
3. Create different card templates (minimal, full, professional)
4. Export to HTML format
5. Add validation for email and phone formats
