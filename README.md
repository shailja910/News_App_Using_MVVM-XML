In hedgehog , if you want to make xml based project instead of jetpack compose , while creating the new project , you have to choose “empty views activity “ instead of empty activity. 

We will be using view binding instead of findviewbyid to avoid null pointer exception and type cast exception.will do it using viewBinding:
*First add build features{ ViewBinding true} block in gradle app file under android tag {} . It will automatically create the binding classes with respect to each xml layout file.
*Then in the main acitivity : mention the binding code I.e. 
*Create an object of binding class and inflate it with the layout inflator I.e.
bindingObj :ActivityMainBinding = ActivityMainBinding.inflate(layoutInflator)
*Then set the contentView to bindingObj.root

In this app we will make one activity and make many different screens into fragments and with the help of navigation components , we will swap between the different fragments on the same single activity using bottom navigation view .

 Navigation Component: The Fragment Navigation Component is a part of the Android Jetpack Navigation Component, which is a library provided by the Android team to simplify and manage fragment-based navigation within an Android application. Following are the key features of navigation components for fragments:
Navigation graph
Navigation controller
Safe args
Animated transitions
deep linking.  Implement Navigation Component  navigation components on the fragments to work ,we must choose one fragment on the main activity xml as the host for navigation components:
Add dependencies
Create the NavGraph XML file (e.g., mobile_navigation.xml) in the res/navigation directory.
Set up the name of “nav graph” (in Navigation directory) and “NavHostFragment" in the layout xml to host your fragments.
Set up the NavController to handle the fragment navigation, set up the NavController instance in the respective kotlin class.

On the main activity xml, create one frame layout as Android Framelayout .It is a ViewGroup  that is used to specify the position of multiple views placed on top of each other to represent a single view screen. It can have a single child .In this case, we create one fragment on the frame layout and that will act as the host fragment for navigation between different fragments of the app. In the main_activity.xml file:
—>. Set name of the navigation graph .xml file in the tag of host fragment.             app:navigation= “@navigation.nav_graph.xml”. 
—> “Android:name tag is used for the Fragment class to instantiate in the layout.that means this fragment will create the object of type . It will also tells about the host fragment to the nav_graph  android:name=“androidx.navigation.fragment.NavHostFragment”.

Now create different fragments for each screen of app. (4 in our case).

Create Navigation graph.xml : Define the navigation graph to show the source and destination fragments. It will be created in res folder. As type navigation under directory “navigation” .and in that folder create the “nav_graph.xml”. Now go to the design of nav_graph.xml , and choose the destination fragment by clicking on the + symbol. So for this app, “breaking news" is our home fragment in the design preview.
Adding animations to navigation component fragment code under <action/> tag . It is not mandatory to do but to make it look beautiful we can always add anim . It can be done in following ways.
Implement bottom navigation : used to call navigate between different fragments by single click.
Create a menu which has item as name of each fragment . And then from the main activity we will connect the bottom navigation view to our navigation components.

	 2. To set up the bottom navigation view on main host fragment , 		we first have to get the fragment manager support for the 			dedicated fragment with its ID, As view Binding 	doesn’t work 			on fragments , so we will use findFragmentById() .

	3.  Now set up the navController to the current host fragment for the bottom navigation view :													binding.bottomNavigationView.setupWithNavController(nav.navController)

9. Retrofit implementation to make network request :

Retrofit is one of the famous HTTP libraries which is used to parse the data from the internet using APIS network request-resoponse.Whenever we send any request to network API through retrofit we get the response in json objects form. Json object is a way to convert complex object into simple string and from that string , we can extract the complex object data again.Implementation of retrofit in three steps :
		
1. Create Model Classes: To convert JSON to kotlin class , android studio provides us a plugin “Json to kotlin class” which will directly convert the string into kotlin objects. 
To install this plugin,  go to api e.g. “newsapi.org" and copy the link of any example response (because news api example response contains”- “ which is not a part of the response syntax) —> open it in new tab —>create a new package names “Model” —> right click on package —> new — > “json to kotlin class” —> copy the entire content from the link and paste it here—> give name to file -> okay. It will create all the model classes with respect to the response you have pasted.


2. Create the Retrofit interface : It is an interface that defines the HTTP operations needs to be performed . In our case it is named “RetrofitNewsAPI” .That will enable us to make requests from everywhere in our code to the network API.It is used to mention the link part of the endpoints.	to create it: 

Create a package —> right click to new-> kotlin file -> named “ RetrofitAPIInterface” —> choose interface . 
This interface will mention the link part of the end points  for every single request you want to make to the network API . It will declare each of  our request as a function of this interface i.e. “breaking new” , “search news “ .As these fxns will make requests to the networks API so we will implement it asynchronously using coroutines . Coroutines are implemented with the keyword “suspend” in front of fxn.  In our case it will be having two functions .
		
a. Our first fxn in the interface is “breaking news “ and second fxn will be “search news” . whenever we make http request to the data over the network , we have to explicitly mention which type of request is it. “Get” , “put” etc. In our case , we want some data from network , we will use “get “.Implemenattion:  So retrofit allow to declare any fxn as a “get” request fxn by using the annotation “GET(“endpoints of the request”) .We can get  the information of endpoints from the documentation of API . For that, api website—> look at left side index—> choose the particular tab for which you want to make request(breaking news in our case) —> under that tab you can view the end point , query parameters etc. After the “get” annotation , create a request fxn (network call fxn as it will get breaking news from the network) asynchronously (using coroutines) . Coroutine fxn is made using the keyword ”suspend” and with parameters . If the parameters are the part of network request , we will annotate that with “@Query(“name of the parameter will be found in newsapi documentation” )” .

		@GET("v2/top-headlines")
		suspend fun getBreakingNews(
   		 @Query("country") countryCode: String = "ca",
   		 @Query("page") pageNumber: Int=1,
    		@Query("apiKey") apiKey: String=API_KEY
		):Response<NewsResponse>

3.. create one retrofit instance class : 	This instance class is also called Retrofit.Builder class: It will use the retrofit interface defined above and the Builder API to allow defining the URL endpoint for the HTTP operations. It also takes the converters we provide to format the Response. I.e. GSON converter.It will consists of 2 functions/lazy blocks .
		a. Its one fxn/lazy block will return the retrofit objects .Retrofit automatically serialises the JSON response using a POJO(Plain Old Java Object) which must be defined in advanced for the JSON Structure. To serialise JSON we need a converter to convert it into Gson first.

		b. Its second fxn will link the retrofit object with retrofit newsAPI interface .It will basically connect the netwrok requests to the retrofit objects.

10. Lazy Block :whenever we make some object “A” whose initialization is depending on the another object “B” .Then to save the resources , we want that object A should only be initialized if we have first initialised the object B otherwise don’t do it. Whenever we want some object to be initialized in certain scenario instead of every time, we will make that run under lazy block . Syntax:
		val abc by lazy{“inside the lazy block call the constructor” which will decide when to initialize this object “ }
