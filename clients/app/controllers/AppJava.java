package controllers;

import play.mvc.*;

import models.*;

public class AppJava extends Controller {

	public static Result test() {
		return ok("Hello World");
	}
}