package controllers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

public class Application extends Controller {

	public static List<WebSocket.Out<JsonNode>> sockets = new ArrayList<WebSocket.Out<JsonNode>>();
	
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static WebSocket<JsonNode> addPhoto() {
		System.out.println("Call Add Photo");
		return new WebSocket<JsonNode>() {

			@Override
			public void onReady(play.mvc.WebSocket.In<JsonNode> in,
					play.mvc.WebSocket.Out<JsonNode> out) {

				sockets.add(out);
				
			}

		};
	}

}