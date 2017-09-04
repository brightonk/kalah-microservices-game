# kalah-microservices-game
This is a project to illustrate the use of microservice to create a game system.

### Overview

*   This is a project to illustrate the use of microservice to create a game system.
*   The system consists of three components: i. eureka registry (which is also a microservice) ii. bot player microservice iii. web ui microservice. All information is fetched via a RESTful interface. When the game starts, the system switches to websocket to enable realtime updates.
*   Eureka registry is a directory that maintains a list of available services. This enables the system to scale and grow to an ecosystem of microservices that can discover and use each other's services.
*   Bot player microservice represents the computer when a human chooses to play against the computer. If the user selects a higher level of difficulty, the system will lookup on the eureka registry for a bot player that has a matching level of difficulty. System maintainers and dev. teams can create bot that use machine learning and AI.
*   Web ui microservice represents a frontend that interacts with user. User can make submit his choices at each stage of the game and see the outcome.

### Starting the System

*   Start up the system in order; starting with Eureka service so that the directory is available, then the bot microservice and the web ui microservice
*   To start Eureka, open a command terminal and change directory to the eureka folder. Open and update the file start-eureka.sh. set the correct path for the jar file according to where you saved the folder. Use the command **"start-eureka.sh"**. Check if it is running by navigating with the browser to [Eureka page](http://localhost:1111/)
*   To start Bot service, open a command terminal and change directory to the bot folder. Open and update the file start-bot.sh. set the correct path for the jar file according to where you saved the folder. Use the command **"start-bot.sh"**. Check if it is running by navigating with the browser to [Bot page](http://localhost:2222/)
*   To start Eureka, open a command terminal and change directory to the eureka folder. Open and update the file start-eureka.sh. set the correct path for the jar file according to where you saved the folder. Use the command **"start-web.sh"**. Check if it is running by navigating with the browser to [Web page](http://localhost:3333/)
*   Web ui is the page used by the users to play the game

### Game Links - The Demo

*   Join game: [/game](/game)
*   Eureka Dashboard: [http://localhost:1111](http://localhost:1111)
*   Check applications registered: [http://localhost:1111/eureka/apps/](http://localhost:1111/eureka/apps/)

### DevOps Links

The following links will be used by the operations and DevOps teams to monitor the health of the system and detect any system issues. The information is provided as RESTful URLs for interoperating with existing monitoring dashboards and alert systems (they return JSON format data):

*   [The beans](/beans)
*   [The environment](/env)
*   [Thread dump](/dump)
*   [Application health](/health)
*   [Application information](/info)
*   [Application metrics](/metrics)
*   [Request call trace](/trace)

<footer>Brighton - 2018</footer>
