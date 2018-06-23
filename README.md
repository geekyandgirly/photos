<p>
This is a sample app to demonstrate how to architect an Android app in <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel">
MVVM</a> pattern, and how to use <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">VidewModel</a>
and <a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a>.
</p>

<p>The app signs you into Google, fetches your Google photo albums using Retrofit. Clicking on an album cover photo takes you
into the album where you can browse your photos in that album.</p>

<p>
<bold>TODO:</bold><br/>
-- pagination (using <a href="https://developer.android.com/topic/libraries/architecture/paging/">Paging Library</a>)<br/>
-- local db (<a href="https://developer.android.com/topic/libraries/architecture/room">Room</a>) to cache albums & photos<br/>
-- DataRepository to merge network and locally stored data<br/>
