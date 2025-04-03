# City Listing Application

This Android application provides a user interface for browsing and searching a list of cities. The application is designed with a clean architecture, utilizing the Model-View-ViewModel (MVVM) pattern to ensure separation of concerns and maintainability.

## Architecture

The application follows the **Clean Architecture** principles, organizing the codebase into distinct layers:

* **Data Layer:** This layer is responsible for handling data sources, including local database access (using Room) and remote API communication. It encapsulates data retrieval and persistence logic.
* **Domain Layer:** This layer contains the core business logic and data models (entities) of the application. It is independent of any specific framework or implementation details.
* **Presentation Layer:** This layer handles the user interface and user interactions. It uses the MVVM pattern, with ViewModels orchestrating data flow and UI updates.

The **MVVM** pattern is employed within the Presentation Layer to further separate UI logic from business logic, improving testability and code organization.

## Efficient Search with Trie Data Structure

To optimize the search functionality, a **Trie** (prefix tree) data structure is implemented.

* The Trie allows for highly efficient prefix-based searching of city names.
* This approach significantly reduces the time complexity of search operations compared to linear searching, especially with a large dataset (approximately 200k entries).
* By pre-processing the city names into a Trie, the application achieves a responsive user experience while filtering cities as the user types.
* Linear Search: In the worst case, a linear search would require you to examine every one of your 200,000 city names. Order: O(n), where 'n' is the number of cities.
* Trie Search: In a Trie, the search time is proportional to the length of the prefix you're searching for, not the total number of cities. Order: O(m), where 'm' is the length of the prefix.


## Local Database with Room

A local database is used to persist city data, providing the following benefits:

* **Reduced Network Calls:** The application avoids redundant and frequent calls to the remote API by storing the city data locally. This improves performance and reduces data consumption.
* **Efficient Data Retrieval:** Room, an ORM library, is used to interact with the SQLite database. Room provides an abstraction layer that simplifies database operations and allows for efficient querying and data management.
* This ensures that the application can quickly access and display city information, even when network connectivity is limited or unavailable.


Error screen

![error_screen](https://github.com/user-attachments/assets/34bc772b-6d84-4287-a773-707f82f35638)

Error with favorites message

![error_favorites_1](https://github.com/user-attachments/assets/29994710-c81e-4595-b563-416f0d0fb068)
![error_favorites_2](https://github.com/user-attachments/assets/dfa180db-beb2-4f21-bab8-86fc1244687d)


App video
https://github.com/user-attachments/assets/5faa298a-a5f3-4cf9-9228-3083e281a79a


