# -Student-Information-System Mobile Application

The SIS Mobile Application is an Android-based student information system designed to provide students with easy access to their profile, course details, enrolled courses, and the ability to request new courses directly from their mobile device.

---

## Features

### Authentication

* Login page for students
* Basic validation and error handling

### Student Dashboard

* Displays welcome message
* Quick access cards for Profile, My Courses, and Request New Course
* UI matching the web portal theme

### Student Profile

* View and edit student details such as name, phone, and address
* Email is shown as non-editable
* UI styled with gradients and material design

### My Courses

* Displays list of enrolled courses using a RecyclerView
* Each course card shows name, description, and start date
* No enrollment button in this section

### Available Courses

* Displays all available courses similar to the portal
* Search bar for filtering by name or description
* Allows sending enrollment request

### Reusable Components

* Gradient styled buttons
* Course card layouts
* Custom adapters for enrolled and available courses

---

## Tech Stack

### Frontend (Android)

* Java
* XML Layouts
* Material Components
* RecyclerView
* CardView

### Architecture

* Activity-based navigation
* Adapters for managing lists
* Simple in-memory data for demonstration

---

## Project Structure

```
app/
├── java/com/sis/mobile/
│   ├── LoginActivity.java
│   ├── DashboardActivity.java
│   ├── StudentProfileActivity.java
│   ├── MyCoursesActivity.java
│   ├── AvailableCoursesActivity.java
│   ├── CourseAdapter.java
│   ├── EnrolledCoursesAdapter.java
│   ├── Course.java
│   └── ...
└── res/
    ├── layout/
    │   ├── activity_login.xml
    │   ├── activity_dashboard.xml
    │   ├── activity_profile.xml
    │   ├── activity_my_courses.xml
    │   ├── activity_available_courses.xml
    │   ├── course_card.xml
    │   └── enrolled_course_card.xml
    ├── drawable/
    │   ├── header_gradient.xml
    │   ├── grad_blue.xml
    │   ├── grad_green.xml
    │   ├── grad_purple.xml
    │   └── btn_blue_gradient.xml
    ├── values/
    │   ├── colors.xml
    │   ├── themes.xml
    │   └── strings.xml
    └── ...
```

---

## How to Build and Run

1. Clone the repository into Android Studio
2. Allow Android Studio to sync Gradle
3. Connect an emulator or physical device
4. Run the application using the Run button

---

## Future Enhancements

* API integration with backend server
* JWT-based authentication
* Real-time enrollment synchronization
* Admin panel features

