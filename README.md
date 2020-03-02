# Introduction
This was a semester project in Databases course where we had to build a managment system for a newspaper which 

consists from different  users such as Journalists, Editors, Administrators, and Publishers. For this project we 

used neatbeans IDE and mySQL for the database. Every user logs in with his username and password. If he is registered 

in the database the appropriate GUI with all the available options appears. Below we present all the requirments for 

every type of user as well as the entity-relationship, screenshots from runtime and instructions on how to run the project.


# Runtime Screenshots
## Login
First of all the **Login GUI** appears and the user enters his username and password.


<p align="center">
	<img src="/Screenshots/loginGui.png" width="600" height="350">
</p>

## Journalist
In the **first tab** he can submit new article. Obsiously some fields are not required.If the user
doesn't fill the required ones an error message is displayed.

<p align="center">
	<img src="/Screenshots/Journalist.gif" >
</p>

In the **second tab** the journalist can view all the articles that he has submitted as well as their current state. Also
he can select one article and by pressing "More Info" a pop up window appears with all the article details.

<p align="center">
	<img src="/Screenshots/JournalistTab2.png"  width="600" height="350">
</p>

In the **third tab** he can edit the articles which have been either rejected from the editor in chief or are just applied 
waiting for the editor in chief to review them and either accept or reject them. The accepted articles cannot be eddited.
The journalist selects one article and for each change he wants to do the appropriate pop up appears.

<p align="center">
	<img src="/Screenshots/JournalistTab3.png"  width="600" height="350">
</p>

## Editor in Chief
In the **first tab** he can accept and reject articles with or without comments. In order for an article to be accepted there should
be enough space if not an error message is displayed. When an article is accepted an SQL trigger sets the position of the article. 

<p align="center">
	<img src="/Screenshots/EditorInChiefTab1.png"  width="600" height="350">
</p>

In the **second tab** the editor in chief can change the order of the articles in one specific issue.

<p align="center">
	<img src="/Screenshots/EditorInChiefTab2.png"  width="600" height="350">
</p>

In the **Third and forth tab** he can also submit an new article which will automatically be accepted from an SQL trigger and can add
a new category.

> **Also the project has a publisher and an administrator gui**

