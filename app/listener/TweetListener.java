package listener;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import controllers.Application;

public class TweetListener implements StatusListener {

	public static List<WebSocket.Out<JsonNode>> sockets = Application.sockets;
	
	public void onStatus(Status status) {
		
		Logger.debug("@" + status.getUser().getScreenName() + " - "
				+ status.getText());

		if (status.getMediaEntities()!= null){
			for (WebSocket.Out<JsonNode> out : sockets){
				
				ObjectNode tweet = Json.newObject();
				tweet.put("user", status.getUser().getScreenName());
				tweet.put("message", status.getText());
				tweet.put("image", status.getMediaEntities()[0].getMediaURL().toString());
				
				out.write(tweet);
			}	
		}
		
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:"
				+ statusDeletionNotice.getStatusId());
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:"
				+ numberOfLimitedStatuses);
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId
				+ " upToStatusId:" + upToStatusId);
	}

	public void onException(Exception ex) {
		ex.printStackTrace();
	}

}
