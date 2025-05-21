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
