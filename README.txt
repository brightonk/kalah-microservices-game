Kalah Microservice Game

Overview
    This is a project to illustrate the use of microservice to create a game system.
    The system consists of three components: i. eureka registry (which is also a microservice) ii. bot player microservice iii. web ui microservice. All information is fetched via a RESTful interface. When the game starts, the system switches to websocket to enable realtime update without need for polling.
    Eureka registry is a directory that maintains a list of available services. This enables the system to scale and grow to an ecosystem of microservices that can discover and use each other's services.
    Bot player microservice represents the computer when a human chooses to play against the computer. If the user selects a higher level of difficulty, the system will lookup on the eureka registry for a bot player that has a matching level of difficulty. System maintainers and dev. teams can create bot that use machine learning and AI.
    Web ui microservice represents a frontend that interacts with user. User can make submit his choices at each stage of the game and see the outcome.

Starting the System

    Start up the system in order; starting with Eureka service so that the directory is available, then the bot microservice and the web ui microservice
    
	1. To start Eureka, double click the start-eureka.sh or start-eureka.cmd file. If you change the jar location remember to update the start scripts. Check if it is running by navigating with the browser to Eureka page http://localhost:1111/
	
    2. To start Bot service, click the start-bot.sh or start-bot.cmd file. If you change the jar location remember to update the start scripts. Check if it is running by navigating with the browser to page http://localhost:2222/
    
	3. To start game website, click the start-web.sh or start-web.cmd file. If you change the jar location remember to update the start scripts. Check if it is running by navigating with the browser to page http://localhost:3333/
	
To Play the Game
	
	Navigate to http://localhost:3333/ and play against another player. The other player will have to open his browser to the same url http://localhost:3333/
