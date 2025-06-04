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

11. Steps to save news Articles in the local room database
Add room dependencies
Changes to do in Model class :i.
Annotate Entity tag at the tope of this model class to create a table in the room database : @entity(tableName=“article”)
Define the ID parameter to each of the article by declaring one int variable in it. Val id:Int?=null.  There may be chance for some articles there is not any id available.
Define the unique primary ket too with annotation:
		@primaryKey(autogenerated:true). 
	It will itself provide unique id to each of the news article.
c. Implement DAO for Articles : It will be defined as an interface where fxns of the interface will tell how to make our API request .These response objects can only be used for local database. So we will declare the fxns which will deal with our local database, such as save, read ,delete news etc onto the local database. To implement:
	i.  Create a package “db” .create an interface “ArticleDao” and annotate it with “@Dao” to tell that this interface will be used by the local room database.
ii. There are three basic operations we want to do on our local database i.e. insert, read and delete. So Room libraries provide the annotations for each of the operation.
		insert: to insert news article in room database ,annotate the fxn with @insert(conflict= OnConflict.REPLACE)   , It means if the particular article is already existing , it will replace that with the new one. As it will do change on the database so we will make it on coroutines by declaring it “suspend: fxn. It will return “Long” which is the unique Id of each article .

		ReadAll : to read all the articles ,annotation is @Query(“SQL command”) . As it will not do any changes in the database , so we wont make it suspend fxn .It will return the live data object .Live data says , if any change happens in the database, it will inform to all the observers .

		Delete : to delete the article from database . Annotation is @delete . It will make changes to database so make it suspend fxn .

D. Create a database class.
So make an abstract class as the database class and annotate it with @Database(entities = array of classes of tables (in our case we have only one entity table i.e. article) , version=1) . Mentioning versions as 1 mean that Lateran if you do any changes in the database , change the version to some other integer value and it will make the room to understand that there are some changes in the database. This class will inherit the RoomDatabase .It will have a fxn which will return the ArticleDao instance.
Terms to understand further: Any() : can be used to check if the array/list has at least one element, or if the list has at least one element that matches the given predicate. It returns boolean.
		synchronized {}:  This block says that once it starts ececuting, no other thread can interrupt till it is completely executed.
		also :also is useful for performing some actions that take the context object as an argument. Use also for actions that need a reference to the object rather than its properties and functions, or when you don't want to shadow the this reference from an outer scope.
		operator fxn:A function operator is a function that takes one (or more) functions as input and returns a function as output. nvoke operator allows you to call a function or an object that has a function-like behavior directly using parentheses.
We have to make a companion object in which we will create an instance of  ArticleDatabase type annotate it as @Volatile (means if any change will happen , inform the database, which can be nullable . Call the any() in the companion object block to check , if it has any initialized instance or not and store its returned value in variable named “LOCK”. Now define the operator fxn invoke and check the nullability of ArticleDatabase instance .if it is null means no database is yet created ,then create one synchronized block and for that null instance create the new database by calling the “createDatabase(context)” fxn and also initialize the instance with that same object value using “also”.
Define createDatabase(context) fxn by calling the Room.DatabaseBuilder().build()
E. Create a converter class. Database does not accept the complex data types such as objects . If we see in our Article model class , there is one object of type “Source” . So to save it In the room database we have to convert it into simpler datatypes and that is done through type-converters. Create a class Converter and define the converting functions .Where one fxn will take string and return source type object and other fxn will take source type object and return string. Annotate the fans with @Typeconverters. 
Annotate the articleDatabase class with @TypeConverters(“name of the converter class::class”)

12: RecyclerView Adapter for fragments using viewBinding
Create the adapter class NewsAdapter which will inherit the Recyclerview.adapter<NewsAdapter.ArticleViewHolder> . Where ArticleViewHolder is the inner class which inherits the RecyclerView.ViewHolder(itemView) .
Always implement the recycler view adapter using “DiffUtil” instead of notifyDataSetChanged() method as it will waste time in loading the already present data. Whereas DiffUtil will only load the changed items. DiffUtil works in the background thread. So we create a callback which is an anonymous class of DiffUtil.ItemCallback .   
DiffUtil.itemCallBack=object: DiffUtil.ItemCallback<>(){
“Implement its two method that is whether the two lists or content of list in same or not.” }
Now get the difference by calling AsyncListDiffer(this, differ)
c. Create the ArticleViewHolder as the inner class and to implement viewBinding and pass the viewBinding class of preview.xml to its constructor. It inherits the properties of RecyclerView.Viewholder(binding.root)
D. Implement the method of adapter :
		a. OnCreateViewHolder(—-) : it returns the ArticleViewHolder object by passing inflator to the binder in its constructor.
		b. getCount(_): differ.currentlist.size.
		c. OnBindViewHolder (___) : call the fan on inner class ,where the actually binding of data to the preview.xml items will be done.

13. Implement MVVM
According to the image above , to implement MVVM we need the following classes .
viewmodel type class : This class also inherits the features of ViewModel class.this class will interact with repository class.So to the constructor of Viewmodel class ,we will pass repository class instance as the parameter. We cannot make the parameterised constructors with our own view models. But we need it to interact with repository class and that is only possible with paramaterised constructor .So to do such c changes in our own vie models. We will create a ViewModelProviderFactory to define how our customized view model should look like.
Repository class in repository package : this class is interacting with the database as well as network API .So we will pass the instance of database class to its constructor as a parameter .It can access the API from our newsAPI interface but that can be directly accessed through RetrofitInstance class (named as “api” there) .
ViewModelProviderFactory class: it will inherit ViewmodelProvider.Factory interface and implement its  “create” fxn. And this fxn will return the parametrized constructor call to our own view model class passing repository as the parameter. This class will take the parameter as newsrepo type.
Go to main activity and call this view model classes. Create an instance of newsrepo—>create an instance of customized provider factory —> create an instance of ViewmodelProvider(“passing parameters such as customized provider).get(“name of view model class) .Assign it to the reference of NewsViewModel class) .
Now go to each of the fragment and instantiate the view model for those.
Create a Sealed “Resource” generic class (suggested by google) in util package to wrap our response from the API request. It is useful to tell , whet to do of success or failure . Helps us to handle the loading state i.e. till the response is loading , show the progress bar instead.It takes (default nullable )parameter such as “data “ that acts as the body of response and “message: that could be error message or so.  Sealed class is like an abstract class but we explicitly define the name of the classes in its body , which are allowed to inherit the Resource class. Mostly there are three “Success class” , "Loading class” and “Error class” . 
Any class can be declared as sealed class ,if we already know that how many classes it needs to inherit already. Sealed class can have objects, data class, regular class and another sealed class.
 Anything which does not have properties or state can be declared as object type and there is also no chance of getting multiple instances out of it to increase confusion. 
Anything which has properties must be declared as data class .

14. Implement actual data access
To make the actual request response using view model ,before writing code in view model class,(as we know view model class will fetch data from repository class) we should make our repository class ready to interact with our local database and network API .
We remember that there are two operations on two fragments respectively I.e. breaking news and search news.                         To deal with breaking news —->So first, in our repository class , we will fetch the breaking news from the network API using coroutines through RestrofitInstance class. To do this , create a suspend fxn same as that created in RetrofitNewsApi by declaring same kind of arguments. for example in retrofitNetworkAPI class we have a fxn:
suspend fun  getBreakingNews(countryCode:String,PageNumber:Int){}
Define the same kind of fxn in repository class and inside that fxn call the RetrofitInstance “api” object and then that same kind of fxn of RetrofitNetworkApi class which is getBreakingNews(_,_) in our case.
1. Now that the repo class is requesting for the breaking news from the network api. We will try to use the response of this request in the view model class. Inside the ViewModel class. Create an object of MutableLiveData (which will tell about the dynamic changes using live data) this data will be of type (user defined)Resource <T> class where T is of type NewsResponse. 

2. Inside ViewModel class Create a fxn getBreakingNews(countryCode) . We know that a suspend fxn can only be called from either the suspend fxn or a coroutine block. So in our case as this is not a network call fxn we will not making it suspend fxn , instead we will call the repo’s class “getbreakingnews” suspend fxn from the coroutine block opened for the current viewmodel scope .
			 viewmodelScope.launch{}
3. When you use MutableLiveData class , it has a fxn called  postValue(“parameters of type Resource) method which is used to set the value of a MutableLiveData instance asynchronously. It should be called from a background thread, as it posts the value update to the main thread's message queue. This method ensures that the value is set on the main thread, which is essential for maintaining UI consistency. First call mutableData.postValue() for “Resource” class’s “Loading” class and then request the response by calling “get breaking news” fxn from repo class. At this point we have got the response for our request of “Response” class object type . Now call mutabledata.postValue(call the handler fxn by passing response as an argument)
4. We will handle the response as success or failure in a separate fxn .create a fxn handle(“takes argument of type predefined “Response”  class type): returns Resource type response.In this fxn we check whether the response given is a success or failure by calling the predefined “Response" class body() or message(). If it is success it will return the “Resource” class success else return “Resource” class Error.
5. Now go to the dedicated breaking news fragment. 
	i. This fragment will show the breaking news on the recycler view ,So it must have the reference to newsAdapater for Rv. And it should setup the RV on it dynamically as a separate fxn.
	Ii. The fragment will observe the mutable data in “overridden fxn onViewCreated()” .As we have used mutable live data in the viewModel class, so our fragments will subscribe to that live data as observers . That mean whenever we post any change in the live data such as loading ,success or failure, then fragments with already notify about the change and immediately get the count up to date from our view model. It must have the reference to view model class which will further be used to observe the mutabledata of view model class.
	viewmodel.breakingnews.observe(). 
Where “breaking news” is the variable holding the mutable data in view model class.
Iii. Now the fragment will observe the mutable data as it is a success ,error or loading.If loading then show progress bar.if success then show data on rv.If error then show message.
Iv. View binding would be done in “override onCreateView()” .
v. It will three fxn such as setupRV(), showProgressbar(), hideProgressBar()

C. To search News : It will have the same code as that of breaking news in repository class as well as in view model class.The only changes will be in the name of fans and variables i.e. from breakinhgnews it will be searchNews.
Now go the dedicated searchNews fragment,Everything will remain the same. Except inside OnViewCreated() we will define the search code. It would be written in the coroutine Job interface mainScope.launch{} block.
D. Article Fragment: Now when user click on the news , it should be opened in the web view in the Article Fragment. So basically we want that whenever the news is clicked from “breaking news fragment” or “search news fragment” .the clicked news from the current  fragment should be passed as an argument to Article fragment , where it will be shown on web view.
i. Transition of data between fragments is possible through “safeArgs” component of navigation component library. Add plugins of safe args to gradle(project) and gradle(app).
Ii. We know that news is of class type “Article “ which is complex in nature , we have to serialize it for transition.Which will be done when “Article” class inherits the Serializable interface of java.io. The android will implicitly do the serialization.
Iii. Now add the safeArgs to the Article fragment by going to nav graph design —> click on article fragment—>click + on arguments—> give name to the argument—> choose type as custom serializable—>click add .
Iv. Now implement click listener on the news in each of the fragment.
	a. First of all go to adapter of recycler view and define a fxn for click operations. Take a ref variable of type ((Article class-> Unit).
Now inside onBindHolder() paste the following code:
holder.itemView.setOnClickListener{
    listenItemClick?.let { it1 -> it1(article) }
    }
It says on the current holder call click listener which will give article in return. Now to access this returned article globally in the app , we can call the onBindViewHolder() from anywhere in the app but it will send the other data as well which is not a nacessity. So we will make a separate fxn will make a separate fxn in which the clickListener ref variable will be assigned to another local argument variable of fxn and can be accessed outside.

b. Now go to each of the fragment and call the click listener fxn of adapter inside onViewCreated() .As it is getting the Article now write lambda expression . Create an object of bundle , make it serialized and pass the article to it. 
c. Now we want to send the data from 3 fragments(breaking,saved,search) to one fragment i.e. ArticleFragment , for that we will use navigation controller.Inside same lambda expression, call fxn “findNavController().navigate(1st arg,2nd arg) .where 1st arg is the action defined in the nav_graph xml and 2nd arg is the bundle object consisting of serialized data.
d. Now go to destination fragment i.e. ArticleFragment.and create a ref of type “ ClassNameArgs” (it is a implicitly generated because of safeargs of navigation component library.
e. Fetch that article in ref variable using “args.articleArgs” . Now who this article in web view .
Article fragment has a floating action button which will be used to save the article in the database.

TO PERFORM upsert, getAll and delete from the Database.

We know that repository class is the one which interact with the DB as well. So we will define the three major operations such as upset, getSavedArticle and delete article in the form of three fxn in the repository class. Which will parallel defined in the view model as well to showcase the changes.

Upsert: the article can be stores in database when t is opened on the web view. i.e. ArticleFragment. It has one floating action button. Set the click listener on FAB that if it is clicked , save this article to db.
viewModel.fxn_of_upsert()

B. GetAllTheupsertedArticles : Go to SavedNews fragment 
And call the viewModel class getAllArticle() fxn .If there is any new upset in the database ,So it will get that too .For that it will observe the data.and whenever the change happens. Load the recycler view items again.
C. Delete the article: if we want to perform the drag, drop and swipe action on the recyclerview. There is a utility class called “ItemTouchHelper“ .This is a utility class to add swipe to dismiss and drag &drop support to RecyclerView.
It works with a RecyclerView and a Callback class (ItemTouchHelperCallback), which configures what type of interactions are enabled and also receives events when user performs these actions.Depending on which functionality you support, you should override onMove and / or onSwiped.
First create an anonymous callback class
Val obj=object:ItemTouchHelper.simpleCallback(mention the two arguments related to drag uo or down, swipe left or right) and implement its two methods. onMove() and onSwipe(). We will be using swipe to delete.So whatsoever item is swiped , get its position ,get the article at that position and call viewmodel’s delete() on the article.

Pagination in android
Pagination is to load data according to requirement rather than loading complete data at a time. So this helps to reduce the loading time for our data from our API as well as increase the performance of our application. 



Check Internet
Internet connected or not can be checked through a system service called connectivity manager .As it is a system service , it should use the context app .Context are always related to activity . We are making the project using MVVM . Where we should keep the context and UI view model data as separate as possible. 
One way to check the internet connection inside view model class but view model should not have the context . (Android ViewModel predefined class provides the application context) .
So the better approach is to make the internet connection observable i.e. create a live connection check .
Create a repository class to check internet connection which is taking “activity context” or “application context” as an argument . And it will inherit the “LiveData<>” class .
Get the system service of “connectivity manager” in a ref variable:
var connectivtyManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

C. Now create one mutable live data object 

## Use of kotlin Flow in Kotlin coroutines: In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value. For example, you can use a flow to receive live updates from a database. Flow is used to compute a stream of data asynchronously which is returning multiple values of same type as that mentioned in “<>” 



