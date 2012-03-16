import java.util.ArrayList;

import listener.TweetListener;
import play.Application;
import play.GlobalSettings;
import play.Play;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Global extends GlobalSettings{

	
	static StatusListener listener = new TweetListener();
	private TwitterStream twitterStream = null;
	
	@Override
	public void onStart(play.Application app) {
		
		System.out.println("Key : " + Play.application().configuration().getString("twitter.consumerKey "));
		
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(Play.application().configuration().getString("twitter.consumerKey "))
          .setOAuthConsumerSecret(Play.application().configuration().getString("twiter.consumerSecret"))
          .setOAuthAccessToken(Play.application().configuration().getString("twitter.accessToken"))
          .setOAuthAccessTokenSecret(Play.application().configuration().getString("twitter.accessTokenSecret"));
        
        
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(listener);
        
        ArrayList<String> track = new ArrayList<String>();
        track.add("paris");
        track.add("marseille");
        
        String[] arrayTrack = new String[9];
        arrayTrack[0] = "Paris";
        arrayTrack[1] = "Marseille";
        arrayTrack[2] = "London";
        arrayTrack[3] = "Berlin";
        arrayTrack[4] = "Sydney";
        arrayTrack[5] = "NY";
        arrayTrack[6] = "Madrid";
        arrayTrack[7] = "Barcelona";
        arrayTrack[7] = "Tokyo";
        

        
        twitterStream.filter(new FilterQuery(0, null, arrayTrack));
        
		super.onStart(app);
	}


	@Override
	public void onStop(Application app) {
		twitterStream.shutdown();
		super.onStop(app);
	}

	
	
	
}
