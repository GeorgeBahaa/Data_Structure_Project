# Data_Structure_Project
XML (Extensible Markup Language) is one of the most famous formats for storing and sharing
information among different devices. Some text editors such as Sublime Text are able to parse
such files and do some basic operations. In this project, you will work on developing a GUI
(Graphical User Interface) based program to parse and visualize an XML file.

In our system the XML file will represent users in a social network
Each user has id (unique), name, list of posts, list of followers.
Each post has text and list of topics.

GUI Editor: To choose XML File or write XML text.
Then Choose XML Editor Button or Graph Editor button to perform functions

![WhatsApp Image 2023-02-02 at 00 50 18](https://user-images.githubusercontent.com/105325517/216460970-efc5b2cc-5ead-4c8f-8365-7cd470b97a49.jpg)

XML Editor Window appears when XML editor button is selected.
Choose function you want to operate on XML file: Validate, convert Json, Compress, 
Decompress, Prettify.

-validate
to check if xml is valid or has an errors and correct these errors.
-prettify
to print prettified xml file and saved it on a file xml.
-convert json
to convert xml to json objects and save as josn file.
-compress, minify and decompress
to compress xml file to deacrease its size and saved as zip file then decompress this file   

![WhatsApp Image 2023-02-02 at 00 51 01](https://user-images.githubusercontent.com/105325517/216461172-bea5ef6c-116e-4426-8037-6a5bf60adcff.jpg)

representing the users data using the graph data structure: the XML file will represent the
users data in a social network.
The user data is his id (unique across the network), name, list of his posts and followers.

Graph Editor Button Clicked: Graph Editor Window appears with XML Graph Functions.
![WhatsApp Image 2023-02-02 at 01 03 50](https://user-images.githubusercontent.com/105325517/216461388-a25a953a-a86d-4253-9944-f17fc819ac3f.jpg)

-search
 to search by a word or a topic in all posts then return the post has related to with its user 
 -most influencer 
 to return the id of user has the most following users 
 -most active 
 to return the id of user that has more connections
 -mututal friends
 return the mutual friends between two users 
 -suggest
 to suggest users for all users to follow from the followers of thier followers 
 
 
