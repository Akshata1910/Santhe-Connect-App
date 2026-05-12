PROJECT REQUIREMENTS DOCUMENT  
Android Application with GenAI Integration   
Akshata Ulhas Bhomkar  
1. Introduction  
Santhe-Connect is an Android-based application aimed at helping travelers discover genuine local hospitality 
experiences such as weekly markets (“Santhe”), traditional dining places (“Khana-Vali”), and home-based food 
services. Many of these small vendors operate without any digital presence, making them difficult for tourists 
to find. 
This application incorporates Generative AI (GenAI) to improve user interaction by offering personalized 
suggestions, intelligent search capabilities, and cultural insights about local food, traditions, and markets. The 
platform serves as a connection between tourists and local micro-entrepreneurs within Karnataka’s hospitality 
sector. 
2. Problem Statement  
Visitors to heritage locations often face difficulty in locating authentic local experiences, as small vendors 
and traditional markets are rarely listed on popular platforms like Google Maps or food delivery applications. 
Most of this information is shared informally among locals. 
Due to this gap, tourists miss out on unique cultural experiences, while local vendors lose potential income 
opportunities. Additionally, there is a lack of an intelligent system that can recommend experiences tailored 
to individual user preferences. 
3. Objectives  
•  Develop a unified platform for discovering local hospitality services  
•  Help tourists locate nearby traditional food spots and markets  
•  Provide a schedule-based listing of Santhe markets  
•  Integrate GenAI for personalized suggestions  
•  Enable local vendors to add and manage their listings  
•  Promote regional culture and authentic experiences  
•  Improve digital visibility for small-scale businesses
4. Scope  
In Scope  
• User registration and authentication  
• Location-based discovery of markets and eateries  
• Santhe calendar with day-wise listings  
• AI-powered recommendations for food and places  
• Vendor listing management (add/edit details)  
• Tagging of specialties (e.g., “Best Jolada Rotti”, “Organic Honey”)  
• Review system with images and voice input  
• Map integration with filtering options 
5. Functional Requirements  
1. The system shall allow users to register and log in securely   
2. The system shall display nearby local eateries and markets using GPS   
3. The system shall filter locations based on the current day (for Santhe markets)   
4. The system shall allow users to search by category (Food, Market, Craft)   
5. The system shall provide GenAI-based recommendations based on user preferences   
6. The system shall allow vendors to add new locations with GPS coordinates   
7. The system shall allow users to upload reviews, photos, and voice notes   
8. The system shall display specialty tags for each listing   
9. The system shall provide navigation via Google Maps integration   
6. Non-Functional Requirements  
• Performance: App should load map data and results within 2–3 seconds  
• Reliability: Data must persist even after app closure  
• Security: User and vendor data must be protected  
• Usability: UI should be simple, colorful, and tourism-friendly  
• Scalability: Should support increasing number of listings and users  
• Compatibility: Android version 9.0 and above  
7. User Requirements  
Tourist (User) Requirements:  
• Easy discovery of authentic local experiences  
• Accurate location and timing information  
• Personalized recommendations  
• Ability to leave reviews and media  
Vendor (Local Provider) Requirements:  
• Simple way to add and manage listings  
• Visibility to tourists  
• No complex technical setup required  
8. System Requirements  
Hardware Requirements:  
• Android smartphone with GPS  
Software Requirements:  
• Android OS (version 9 or above)  
• Internet connection  
• Google Maps API  
• Firebase Firestore (database)  
• GenAI API integration  
9. Data Requirements  
Location Table:  
• Location ID (Primary Key)  
• Name  
• Category (Food / Market / Craft)  
• Description  
• Latitude & Longitude  
• Day of Availability (for Santhe)  
• Specialty Tags  
• Images  
User Table:  
• User ID  
• Name  
• Email  
• Preferences  
Review Table:  
• Review ID  
• User ID (Foreign Key)  
• Location ID (Foreign Key)  
• Review Content (Text/Voice/Image)  
• Rating  
10. Constraints  
• Limited development time (6 weeks)  
• No budget for paid APIs (use free tiers)  
• Dependency on user-generated content accuracy  
• Internet connectivity required for real-time features  
11. Expected Outcome  
Santhe-Connect aims to improve tourism by making lesser-known local experiences easily accessible. It will 
support small vendors by increasing their digital presence and connecting them directly with customers. With 
the help of Generative AI, the application will deliver personalized travel experiences, encourage cultural 
exploration, and contribute to the local economy. 
