Errai Getting Started Demo
=====================

This simple demo app allows users to file complaints. The user interface is designed using plain HTML5 and CSS. Errai UI enables the injection of UI fields into client side Java classes as well as adding dynamic behaviour to these fields (such as event handlers). The demo also makes use of Errai's two-way data binding and page navigation modules. This can all be seen in `ComplaintForm.java` (which is the companion Java class of the complaint form template defined in `ComplaintForm.html`).

Creating complaints is done using a simple JAX-RS endpoint and Errai's typesafe REST caller support (see the `@EventHandler` method for the submit button in `ComplaintForm.java`). Every time a new complaint is created, the server will fire a CDI event which will be observed by all connected clients (new complaints will automatically appear on all admin pages of all connected clients). The relevant code for firing and observing this CDI event can be found in `UserComplaintEndpointImpl.java` and `Admin.java`.  

The filed complaints are persisted on the server as well as in the browser's offline storage (using Errai JPA), so that admin users can access all complaints while offline. The complaints are automatically kept in sync with the server using Errai's data sync capabilities (see `Admin.java`).

The demo can be packaged as a native mobile app using Errai's Cordova module which also allows for the simple injection of integrated mobile hardware (see the injection of the Camera object in `ComplaintForm.java` for an example).

This demo is designed to work with a full Java EE 6 server such as JBoss EAP 6 or AS 7. Although it should be possible to craft a deployment of this demo to a simpler web server, it's much simpler to deploy to an EE 6 capable app server.

Prerequisites
-------------

 * Maven 3 (run `mvn --version` on the command line to check)
 * JBoss AS 7 or EAP 6 running on your local machine

Build and deploy
----------------

To build a .war file and deploy it to the local running JBoss EAP 6 or AS 7 instance:

    % mvn clean package jboss-as:deploy

Once the above command has completed, you should be able to access the app at the following URL:

    http://localhost:8080/errai-tutorial

Code-and-Refresh
----------------

To try it in GWT's dev mode, after completing the above steps, do this:

    % mvn gwt:run

Then click "Launch in Default Browser" on the GWT Dev Mode window that appears.

Alternatively, to start debugging the client side code in your IDE, run:

    % mvn gwt:debug

and attach your IDE's remote debugger to localhost:8000.

Build and deploy as native app to your mobile device
-----------------------------------------------------

The native application will have to know the absolute path to your server or cluster. For this demo you will have to change the REST and Errai Bus endpoint URL in Config.java and App.java (see comments) before building the native application.

To produce the native applications run:

    % mvn clean package -Pmobile,jboss7

After that you can simply install the native app on your phone or tablet. 

e.g for Android run:

    % adb install target/template/platforms/android/bin/HelloCordova-debug.apk
