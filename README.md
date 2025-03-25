<h2>Map Missionary</h2>
<p></p>A second year university project to build an Android app that makes use of an online, JSON returning API. I chose to make an app that can provide the user with a grid reference and other formatted location information, mainly because this is an operation I've had to perform multiple times for my role in Cave Rescue, which I previously had to do in multiple steps using Google maps and another grid reference providing website (for which the UI doesn't work very well on a mobile device). The app is named Map Missionary because it does "conversions" of location units. In order to do these conversions it uses the GeoDojo.net api.</p>
<p>https://api.geodojo.net/doc/locate/</p>

<p>Currently the app is capable of providing latitude and longitude coordinates and a grid reference (plus a few other handy pieces of information) for the user's current location. It can also perform a location search by keywords or by postcode, and returns a grid reference and coordinates of the search result. I've paused development of this project for the moment due to time constraints now that it it meets the requirements for the assignment, but in order to make this more useful to me in the future I would like to implement the following features:</p>

- Provide a shareable Google maps link for a found location.
- Direct conversion of user entered coordinates to a grid reference.
- Button to copy multiple pieces of information into an email shareable format.

I also would like to look into doing the conversions locally in the future rather than using an API, so that it doesn't require internet activity. It uses the API in order to meet assignment requirements but I may change this later.
